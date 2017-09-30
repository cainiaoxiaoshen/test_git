package com.gooseeker.fss.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.ResponseJsonMsg;
import com.gooseeker.fss.commons.generic.controller.GenericController;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.commons.utils.SynchronousWordsUtil;
import com.gooseeker.fss.entity.AnnotateWord;
import com.gooseeker.fss.entity.PreselectionWords;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateWordService;
import com.gooseeker.fss.service.PreselectionWordsService;
import com.gooseeker.fss.thread.SynchronousWordsThread;

@Controller
@RequestMapping("standards/words")
public class KeyWordsController extends GenericController
{
    @Resource(name = "preselectionWordsServiceImpl")
    private PreselectionWordsService wordsService;
    
    @Resource(name = "annotateWordServiceImpl")
    private AnnotateWordService annotateWordService;
    
    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService annotateDocumentService;
    
    /**
     * 词库管理
     */
    @RequestMapping("/keyWordsManage.html")
    public String keyWordsManage(Model model, @RequestParam(value = "word", required = false) String word, 
            @RequestParam(value = "p", required = false, defaultValue = "1") int p)
    {
        PageInfo<PreselectionWords> page = wordsService.findNotSelectedByPage(word, p);
        model.addAttribute("page", page);
        return "manage/keyWordsManage";
    }
    
    /**
     * 筛选关键词
     * @throws IOException 
     */
    @RequestMapping("/selectedWords.html")
    public void selectedWords(HttpServletResponse response,
            @RequestParam(value = "words[]", required = true) String[] words) throws IOException
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(true);
        try
        {
            if(words.length > 0)
            {
                annotateWordService.addAnnotateWords(words);
            }
        }
        catch (Exception e)
        {
            msg.setSuccess(false);
        }
        msg.write(response);
    }
    
    /**
     * 词库管理
     */
    @RequestMapping("/annotateWords.html")
    public String annotateWords(Model model, @RequestParam(value = "p", required = false, defaultValue = "1") int p)
    {
        PageInfo<AnnotateWord> page = annotateWordService.findByPage(p);
        model.addAttribute("page", page);
        model.addAttribute("synchronous", SynchronousWordsUtil.synchronous);
        return "manage/annotateWords";
    }
    
    @RequestMapping("/deleteWord.html")
    public void deleteWord(HttpServletResponse response, @RequestParam("word") String word,
            @RequestParam("id") long id) throws NumberFormatException, Exception
    {
        ResponseJsonMsg msg = new ResponseJsonMsg(false);
        if(StringUtil.isNotBlank(word))
        {
            annotateWordService.deleteAnnotateWord(id, word);
            msg.setSuccess(true);
        }
        msg.write(response);
    }
    
    @RequestMapping("/synchronousWords.html")
    public void synchronousWords(HttpServletRequest request, HttpServletResponse response)
    {
        if(!SynchronousWordsUtil.synchronous)
        {
                SynchronousWordsThread thread = new SynchronousWordsThread(annotateDocumentService, annotateWordService);
                new Thread(thread).start();
            
        }
    }
}
