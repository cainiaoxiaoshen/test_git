package com.gooseeker.fss.commons.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.PageInclude;
import com.gooseeker.fss.handlers.TxtHandler;

public class SynchronousWordsUtil
{
    public static boolean synchronous = false;
    protected static Logger logger = Logger.getLogger(SynchronousWordsUtil.class);
    
    /**
     * 提取文档关键词
     * @param docs 需要同步新的关键词的文档
     * @param annotateWords 需要同步的关键词
     * @return List<PageInclude> 
     * @see [类、类#方法、类#成员]
     */
    public static List<PageInclude> synchronousWords(List<AnnotateDocument> docs, List<String> words)
    {
        List<PageInclude> pageIncludes = new ArrayList<PageInclude>();
        for (int i = 0; i < docs.size(); i++)
        {
            AnnotateDocument doc = docs.get(i);
            //得到txt文件的路径
            String txtPath = DocumentPathBuilder.getPDFTxtPath(doc.getReqUrl(), doc.getStandardNo());
            
            
            BufferedReader reader;
            try
            {
                File file = new File(txtPath);
                if(file.exists())
                {
                    reader = new BufferedReader(new FileReader(file));
                    String text = null;
                    //用于存放每一页的内容
                    StringBuilder pageTextBuilder = new StringBuilder();
                    //文件的全部文本
                    StringBuilder fileText = new StringBuilder();
                    try
                    {
                        while ((text = reader.readLine()) != null)
                        {
                            //[FSS_PAGE_NUM= 页码标志
                            if(text.contains("[FSS_PAGE_NUM="))
                            {
                                int pageNum = getPageNum(text);
                                //存放匹配到的关键词
                                StringBuilder includeBuilder = new StringBuilder();
                                String pageText = pageTextBuilder.toString();
                                for (int j = 0; j < words.size(); j++)
                                {
                                    String keyWord = words.get(j);
                                    if(pageText.contains(keyWord))
                                    {
                                        includeBuilder.append(keyWord).append(Constant.ESCAPE_COMMA);
                                    }
                                }
                                if(includeBuilder.length() > 0)
                                {
                                    PageInclude include = new PageInclude();
                                    include.setDocNo(doc.getStandardNo());
                                    include.setPage(pageNum);
                                    include.setIncludes(includeBuilder.toString());
                                    pageIncludes.add(include);
                                }
                                //循环一页后把pageTextBuilder内容清除
                                pageTextBuilder.delete(0, pageTextBuilder.length());
                            }
                            else
                            {
                                pageTextBuilder.append(text);
                            }
                            fileText.append(text);
                        }
                        docs.get(i).setText(fileText.toString());
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    finally
                    {
                        try
                        {
                            reader.close();
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                    }
                }
            }
            catch (FileNotFoundException e)
            {
                logger.error(txtPath + "文件没找到", e);
            }
        }
        return pageIncludes;
    }
    
    private static int getPageNum(String text)
    {
        String[] strs = text.split("=");
        String pageNum = strs[1].replace("]", "");
        return Integer.parseInt(pageNum);
    }
}
