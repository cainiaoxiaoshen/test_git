package com.gooseeker.fss.handlers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.utils.AnnotateUtil;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.AnnotateWord;
import com.gooseeker.fss.entity.PageInclude;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

/**
 * txt文件处理器
 * @author  姓名 工号
 * @version  [版本号, 2017年8月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TxtHandler
{
    private static Logger logger = Logger.getLogger(TxtHandler.class);
    
    /**
     * 往txt文档中写入内容
     * @param page 页码数
     * @param doc 操作的文档
     * @param text 要写入的内容
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public static void writeToTxt(String text, AnnotateDocument doc, int page) throws IOException
    {
        int type = Integer.parseInt(doc.getType());
        String format = doc.getFormat();
        String standardNo = doc.getStandardNo();
        //添加页码内容
        text = TxtHandler.appendPageFlagToTxt(text, page);
        String txtPath = DocumentPathBuilder.getTxtPath(type, format, standardNo);
        //得到文件
        File txtFile = new File(txtPath);
        if(txtFile == null || !txtFile.exists())
        {
            //创建一个新的文件
            txtFile.createNewFile();
        }
        //文件存在，把内容添加进去
        TxtHandler.appendWriteToTxt(text, txtFile);
    }
    
    /**
     * 往txt文件中写入内容
     * @param text 要写入的内容
     * @param txtPath txt文件路径
     * @see [类、类#方法、类#成员]
     */
    public void writeToTxtForUpload(String text, String txtPath)
    {
        File txtFile = new File(txtPath);
        if (txtFile != null && txtFile.exists())
        {
            txtFile.delete();
        }
        FileOutputStream fos = null;
        OutputStreamWriter os = null;
        try
        {
            txtFile.createNewFile();
            fos = new FileOutputStream(txtFile);
            os = new OutputStreamWriter(fos, "UTF-8");
            os.write(text);
        }
        catch (IOException e)
        {
            logger.error(e.getMessage());
        }
        finally
        {
            try
            {
                os.close();
                fos.close();
            }
            catch (IOException e)
            {
                logger.error(e.getMessage());
            }
        }
    }
    
    /**
     * 往txt文件中添加内容
     * @param text 要添加的内容
     * @param file 要操作的文件
     * @see [类、类#方法、类#成员]
     */
    public static void appendWriteToTxt(String text, File file)
    {
        BufferedWriter out = null;
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file, true)));
            out.write(text);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 往txt文件中添加内容
     * @param text 要添加的内容
     * @param pathname 文件所在的路径
     * @see [类、类#方法、类#成员]
     */
    public static void appendWriteToTxt(String text, String pathname)
    {
        BufferedWriter out = null;
        try
        {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(pathname, true)));
            out.write(text);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if (out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 给txt文本内容添加页码的标志
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String appendPageFlagToTxt(String text, int page)
    {
        StringBuilder content = new StringBuilder();
        content.append(text.replace(" ", ""))
               .append(System.getProperty("line.separator"))
               .append("[FSS_PAGE_NUM=")
               .append(page).append("]")
               .append(System.getProperty("line.separator"));
        return content.toString();
    }
    
    /**
     * 读取doc文档对应的txt文件的内容
     * @param format 文档的格式
     * @param type 1标准/2法规/3研究报告
     * @param standardNo 文档标准编号
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String readTxt(AnnotateDocument doc)
    {
        String standardNo = doc.getStandardNo();
        String format = doc.getFormat();
        int type = Integer.parseInt(doc.getType());
        StringBuilder builder = new StringBuilder();
        String txtPath = DocumentPathBuilder.getTxtPath(type, format, standardNo);
        File file = new File(txtPath);
        if(file.exists() && file.isFile())
        {
            InputStream in = null;
            InputStreamReader reader = null;
            BufferedReader bufferedReader = null;
            try
            {
                in = new FileInputStream(file);
                reader = new InputStreamReader(in, "UTF-8");
                bufferedReader = new BufferedReader(reader);
                String text = Constant.STR_EMPTY;
                while ((text = bufferedReader.readLine()) != null)
                {
                    builder.append(text);
                }
            }
            catch (IOException e)
            {
                logger.error("读取" + standardNo + "txt文件内容出错", e);
            }
            finally
            {
                try
                {
                    in.close();
                    reader.close();
                    bufferedReader.close();
                }
                catch (IOException e)
                {
                    logger.error("读取" + standardNo + "txt文件内容出错", e);
                }
            }
        }
        return builder.toString();
    }
    
    /**
     * 
     * @param doc 要解析的txt文件的目标文档对象
     * @param words 需要匹配的关键词
     * @return
     */
    public static Map<String, Object> parseTxtText(AnnotateDocument doc, List<String> words)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        List<PageInclude> pageIncludes = new ArrayList<PageInclude>();
        StringBuilder fileAllTextBuilder = new StringBuilder();
        //得到txt文件的路径
        String txtPath = DocumentPathBuilder.getPDFTxtPath(doc.getReqUrl(), doc.getStandardNo());
        BufferedReader reader;
        try
        {
            File file = new File(txtPath);
            if(file.exists() && file.isFile())
            {
                reader = new BufferedReader(new FileReader(file));
                //每一行的内容
                String lineText = null;
                //用于存放每一页的内容
                StringBuilder pageTextBuilder = new StringBuilder();
                //文件的全部文本
                try
                {
                    while ((lineText = reader.readLine()) != null)
                    {
                        //[FSS_PAGE_NUM= 页码标志
                        if(lineText.contains("[FSS_PAGE_NUM="))
                        {
                            int pageNum = getPageNum(lineText);
                            //存放匹配到的关键词
                            StringBuilder includeBuilder = new StringBuilder();
                            String pageText = pageTextBuilder.toString();
                            for (int j = 0; j < words.size(); j++)
                            {
                                String keyWord = words.get(j);
                                if(pageText.contains(keyWord))
                                {
                                    //把关键词中的,转义后在保存
                                    keyWord = keyWord.replace(Constant.SPLIT_COMMA, Constant.ESCAPE_COMMA);
                                    includeBuilder.append(keyWord).append(Constant.SPLIT_COMMA);
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
                            //当前页的内容还没读取玩则继续拼接内容
                            pageTextBuilder.append(lineText);
                        }
                        fileAllTextBuilder.append(lineText);
                    }
                }
                catch (IOException e)
                {
                    logger.error(e.getMessage());
                }
                finally
                {
                    try
                    {
                        reader.close();
                    }
                    catch (IOException e)
                    {
                        logger.error(e.getMessage());
                    }
                }
            }
        }
        catch (FileNotFoundException e)
        {
            logger.error(txtPath + "文件没找到", e);
        }
        map.put("pageIncludes", pageIncludes);
        map.put("txtText", fileAllTextBuilder.toString());
        return map;
    }
    
    /**
     * 得到页码数
     * @param text
     * @return
     */
    private static int getPageNum(String text)
    {
        String[] strs = text.split("=");
        String pageNum = strs[1].replace("]", "");
        return Integer.parseInt(pageNum);
    }
}
