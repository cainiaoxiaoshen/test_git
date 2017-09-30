package com.gooseeker.fss.handlers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.entity.AnnotateWord;
import com.gooseeker.fss.entity.PageInclude;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

public class PdfHandler
{
    private Logger logger = Logger.getLogger(PdfHandler.class);
    /**
     * 解析pdf得到文本内容
     * @param standardNo pdf文档编号
     * @param annotateWords 关键词
     * @param pdfFile pdf文档的路径
     * @return
     * @throws Exception 
     * @see [类、类#方法、类#成员]
     */
    public Map<String, Object> parsePdfText(String standardNo, String pdfFile, List<AnnotateWord> annotateWords)
    {
        List<PageInclude> pageIncludes = new ArrayList<PageInclude>();
        PdfReader reader = null;
        StringBuilder pdfText = new StringBuilder();
        StringBuilder txtText = new StringBuilder();
        try
        {
            reader = new PdfReader(pdfFile);
            PdfReaderContentParser parser = new PdfReaderContentParser(reader);
            TextExtractionStrategy strategy;
            for (int i = 1; i <= reader.getNumberOfPages(); i++)
            {
                strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
                if (strategy != null)
                {
                    String text = strategy.getResultantText();
                    if (text == null)
                    {
                        text = Constant.STR_EMPTY;
                    }
                    if (!StringUtils.isEmpty(text))
                    {
                        PageInclude pageInclude = parsePageIncludes(standardNo, i, text, annotateWords);
                        if (pageInclude != null)
                        {
                            pageIncludes.add(pageInclude);
                        }
                    }
                    String trimText = text.replace(" ", Constant.STR_EMPTY);
                    pdfText.append(trimText);
                    txtText.append(trimText)
                                  .append(System.getProperty("line.separator"))
                                  .append("[FSS_PAGE_NUM=")
                                  .append(i)
                                  .append("]")
                                  .append(System.getProperty("line.separator"));
                }
            }
        }
        catch (Exception e)
        {
            logger.error("pdf解析异常", e);
        }
        finally
        {
            if (reader != null)
            {
                reader.close();
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        //关键词提取
        map.put("pageIncludes", pageIncludes);
        //解析出来的pdf文本内容
        map.put("pdfText", pdfText.toString());
        //往txt文件里写的文本内容
        map.put("txtText", txtText.toString());
        return map;
    }
    
    /**
     * 
     * 解析出每一页所包含的关键词
     * @param docNo 文档编号
     * @param pageNum 页数
     * @param text 这一页的文本内容
     * @param annotateWords 关键词集合
     */
    private static PageInclude parsePageIncludes(String docNo, int pageNum,
            String text, List<AnnotateWord> annotateWords)
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < annotateWords.size(); i++)
        {
            String annotateWord = annotateWords.get(i).getAnnotateWord();
            if (!StringUtils.isEmpty(annotateWord))
            {
                if (text.contains(annotateWord))
                {
                    builder.append(annotateWord).append(Constant.ESCAPE_COMMA);
                }
            }
        }
        String includes = builder.toString();
        if (!StringUtils.isEmpty(includes))
        {
            PageInclude include = new PageInclude();
            include.setDocNo(docNo);
            include.setPage(pageNum);
            include.setIncludes(includes);
            return include;
        }
        return null;
    }
}
