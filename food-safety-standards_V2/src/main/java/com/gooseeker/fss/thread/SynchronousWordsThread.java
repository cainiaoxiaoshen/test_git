package com.gooseeker.fss.thread;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.wltea.analyzer.cfg.*;
import org.wltea.analyzer.dic.Dictionary;

import com.gooseeker.fss.commons.utils.SynchronousWordsUtil;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.PageInclude;
import com.gooseeker.fss.index.AnnotateIndexUtil;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.AnnotateWordService;
import com.gooseeker.fss.service.PageIncludeService;

public class SynchronousWordsThread implements Runnable
{
    protected static Logger logger = Logger.getLogger(SynchronousWordsThread.class);
    
    private AnnotateWordService annotateWordService;
    
    private AnnotateDocumentService docService;
        
    public SynchronousWordsThread(AnnotateDocumentService docService,
            AnnotateWordService annotateWordService)
    {
        this.annotateWordService = annotateWordService;
        this.docService = docService;
    }
    
    @Override
    public void run()
    {
        SynchronousWordsUtil.synchronous = true;
        try
        {
            List<String> words = annotateWordService.getNoSynchronizedWords();
            if(words.size() > 0)
            {
                List<AnnotateDocument> docs = docService.findSynchronousDoc();
                docService.updateSynchronousDocVersion(docs);
                logger.info("自动识别关键词提取完成，开始索引更新");
                
                //先初始化一下词典
                Dictionary.initial(DefaultConfig.getInstance());
                //得到词典对象
                Dictionary dictionary = Dictionary.getSingleton();
                //添加词
                dictionary.addWords(words);
                AnnotateIndexUtil indexUtil = new AnnotateIndexUtil();
                indexUtil.updateIndexs(docs);
                logger.info("索引更新完成");
            }
        }
        catch (Exception e)
        {
            logger.error("同步关键词出错", e);
        }
        finally
        {
            SynchronousWordsUtil.synchronous = false;
        }
    }

}
