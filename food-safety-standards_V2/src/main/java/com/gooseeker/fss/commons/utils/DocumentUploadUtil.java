package com.gooseeker.fss.commons.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import com.gooseeker.fss.builder.AnnotateDocumentBuilder;
import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.exception.FssViewException;
import com.gooseeker.fss.container.DocumentUploadContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.AnnotateWord;
import com.gooseeker.fss.entity.PageInclude;
import com.gooseeker.fss.handlers.PdfHandler;
import com.gooseeker.fss.handlers.TxtHandler;

public class DocumentUploadUtil
{
    private static Logger logger = Logger.getLogger(DocumentUploadUtil.class);
    
    /**
     * 文件导入
     * @param multiFile 页面导入的文档
     * @param user
     * @param type 上传的文件类型 1标准/2法规/3研究报告
     * @param words 需要匹配的关键词（用于找出pdf文档每页的内容所包含的关键词）
     * @return
     * @throws IllegalStateException
     * @throws IOException
     * @throws FssViewException
     */
    public static DocumentUploadContainer upload(MultipartFile multiFile, String user, String type, List<AnnotateWord> words) throws IllegalStateException, IOException, FssViewException
    {
        List<File> files = new ArrayList<File>();
        //文件名
        String fileName = multiFile.getOriginalFilename();
        //文件格式
        String format = fileName.substring(fileName.lastIndexOf(".") + 1);
        
        if(isAllowFileFormat(format) && StringUtil.isNumerical(type))
        {
            //临时文件夹路径，文档先上传到临时文件夹
            String dirPath = DocumentPathBuilder.getTempDirectoryPath(user);
            File directory = new File(dirPath);
            // 如果路径不存在则创建
            if (!(directory.isDirectory()))
            {
                directory.mkdir();
            }
            // 上传的路径 + 上传的文件名
            String filePath = DocumentPathBuilder.getUploadTempPath(user, fileName);
            // 上传
            File file = new File(filePath);
            multiFile.transferTo(file);
            logger.info("文件[" + fileName + "]上传成功");
            
            if (DocumentPathBuilder.DOC_FORMAT_ZIP.equals(format))
            {
                FileUtil.unZip(filePath, dirPath);
                //删除zip文件
                file.delete();
                //取出解压的文件
                files = FileUtil.getDirectoryAllFile(dirPath);
            }
            else if (DocumentPathBuilder.DOC_FORMAT_PDF.equals(format) 
                    || DocumentPathBuilder.DOC_FORMAT_HTML.equals(format)
                    || DocumentPathBuilder.DOC_FORMAT_HTML.equals(format))
            {
                File uploadedFile = new File(filePath);
                files.add(uploadedFile);
            }
            DocumentUploadContainer container = getUploadDocuments(files, type, user, words);
            String repeatFileNames = container.getRepeatFileNames();
            //如果没有重复的文件则把临时文件夹删除
            if(StringUtil.isEmpty(repeatFileNames))
            {
                directory.delete();
            }
            return container;
        }
        return null;
    }
    
    /**
     * 重复导入
     * @param rptDocNames 重复导入的文档名称
     * @param user
     * @param type 上传的文件类型 1标准/2法规/3研究报告
     * @param words 需要匹配的关键词（用于找出pdf文档每页的内容所包含的关键词）
     * @return
     */
    public static DocumentUploadContainer reUpload(String[] rptDocNames, String user, String type, List<AnnotateWord> words)
    {
        List<PageInclude> pageIncludes = new ArrayList<PageInclude>();
        List<AnnotateDocument> documents = new ArrayList<AnnotateDocument>();
        Timestamp modiTime = new Timestamp(System.currentTimeMillis());
        String dirPath = DocumentPathBuilder.getTempDirectoryPath(user);
        PdfHandler pdfHandler = new PdfHandler();
        TxtHandler txtHandler = new TxtHandler();
        for (int i = 0; i < rptDocNames.length; i++)
        {
            String fileName = rptDocNames[i];
            String tempFilePath = dirPath + fileName;
            File tempFile = new File(tempFilePath);
            //文件转移的路径
            String uploadPath = DocumentPathBuilder.getUploadPath(type, tempFile.getName());
            //把临时文件夹的文档转移到真正需要存放的路径
            tempFile.renameTo(new File(uploadPath));
            String reqUrl = DocumentPathBuilder.getDocumentRequestUrl(uploadPath);
            AnnotateDocument document = new AnnotateDocument();
            document.setReqUrl(reqUrl);
            String standardNo = fileName.substring(0, fileName.lastIndexOf("."));
            document.setStandardNo(standardNo);
            document.setModiUser(user);
            document.setModiTime(modiTime);
            
            if(DocumentPathBuilder.DOC_FORMAT_PDF.equals(document.getFormat()))
            {
                String pdfFile = DocumentPathBuilder.getPdfUploadPath(reqUrl);
                //解析pdf
                Map<String, Object> handleMap = pdfHandler.parsePdfText(standardNo, pdfFile, words);
                pageIncludes.addAll((List<PageInclude>)handleMap.get("pageIncludes"));
                //pdf文本内容
                String pdfText = (String)handleMap.get("pdfText");
                //设置文本内容
                document.setText(pdfText);
                //需要写入txt的文本内容
                String txtText = (String)handleMap.get("txtText");
                String txtPath = DocumentPathBuilder.getPDFTxtPath(reqUrl, standardNo);
                //把文本内容写入txt文件
                txtHandler.writeToTxtForUpload(txtText, txtPath);
            }
            documents.add(document);
        }
        FileUtil.deleteDirectory(new File(dirPath));
        DocumentUploadContainer container = new DocumentUploadContainer();
        container.setPageIncludes(pageIncludes);
        container.setDocuments(documents);
        return container;
    }
    
    /**
     * 得到需要上传的文档信息对象等
     * @param files 上传的文件
     * @param type 上传的文件类型 1标准/2法规/3研究报告
     * @param user
     * @param words 需要匹配的关键词（用于找出pdf文档每页的内容所包含的关键词）
     * @return map 
     * pageIncludes 匹配出的pdf文档包含的关键词信息对象 
     * documents 需要添加的文档对象
     * fileNames 重复导入的文件名
     */
    private static DocumentUploadContainer getUploadDocuments(List<File> files, String type, 
        String user, List<AnnotateWord> words)
    {
        List<PageInclude> pageIncludes = new ArrayList<PageInclude>();
        List<AnnotateDocument> documents = new ArrayList<AnnotateDocument>();
        PdfHandler pdfHandler = new PdfHandler();
        TxtHandler txtHandler = new TxtHandler();
        StringBuilder repeatFileNames = new StringBuilder();
        for (int i = 0; i < files.size(); i++)
        {
            //判断文件是否存在
            File tempFile = files.get(i);
            String fileName = tempFile.getName();
            String uploadPath = DocumentPathBuilder.getUploadPath(type, fileName);
            File file = new File(uploadPath);
            if (file != null && file.exists())
            {
                repeatFileNames.append(fileName).append(Constant.SPLIT_COMMA);
            }
            else
            {
                tempFile.renameTo(file);
                String format = fileName.substring(fileName.lastIndexOf(".") + 1);
                AnnotateDocument document = AnnotateDocumentBuilder.builderFromFile(files.get(i), type, user);
                if(DocumentPathBuilder.DOC_FORMAT_PDF.equals(format))
                {
                    String reqUrl = document.getReqUrl();
                    String standardNo = document.getStandardNo();
                    String pdfFile = DocumentPathBuilder.getPdfUploadPath(reqUrl);
                    //解析pdf
                    Map<String, Object> handleMap = pdfHandler.parsePdfText(standardNo, pdfFile, words);
                    pageIncludes.addAll((List<PageInclude>)handleMap.get("pageIncludes"));
                    //pdf文本内容
                    String pdfText = (String)handleMap.get("pdfText");
                    document.setText(pdfText);
                    //需要写入txt的文本内容
                    String txtText = (String)handleMap.get("txtText");
                    String txtPath = DocumentPathBuilder.getPDFTxtPath(reqUrl, standardNo);
                    //把文本内容写入txt文件
                    txtHandler.writeToTxtForUpload(txtText, txtPath);
                }
                documents.add(document);
            }
        }
        DocumentUploadContainer container = new DocumentUploadContainer();
        container.setPageIncludes(pageIncludes);
        container.setDocuments(documents);
        container.setRepeatFileNames(repeatFileNames.toString());
        return container;
    }
    
    
    
    public static List<AnnotateDocument> uploadUrlExcel(MultipartFile excel, String user, int type)
    {
        // 文件名
        String fileName = excel.getOriginalFilename();
        // 文件格式
        String format = fileName.substring(fileName.lastIndexOf(".") + 1);
        //文件格式检查
        if (!Constant.EXCEL_FORMAT_XLS.equals(format) 
            || !Constant.EXCEL_FORMAT_XLSX.equals(format))
        {
            List<AnnotateDocument> documents = null;
            try
            {
                documents = ExcelUtil.parseToDocument(excel.getInputStream(), user, String.valueOf(type));
            }
            catch (InvalidFormatException e)
            {
                e.printStackTrace();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return documents;
        }
        return null;
    }
    
    public static AnnotateDocument addHTMLUrl(AnnotateDocument urlDoc, AnnotateDocument noDoc, String user,
            String reqUrl, String standardNo, String type, boolean isRepeat)
    {
        if((urlDoc == null && noDoc == null) || isRepeat)
        {
            //(urlDoc == null && noDoc == null) 表示数据库中没有存在该编号或者改URL的文档
            //isRepeat 表示再次导入
            AnnotateDocument doc = AnnotateDocumentBuilder.builder(user, standardNo, reqUrl, 
                type, DocumentPathBuilder.DOC_FORMAT_HTML, isRepeat);
            return doc;
        }
        return null;
    }
    
    /**
     * 检查文件格式是不是允许的范围
     * @param format
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isAllowFileFormat(String format)
    {
        boolean flag = false;
        String[] formats =  DocumentPathBuilder.DOC_FORMATS_ARRAY;
        for (int i = 0; i < formats.length; i++)
        {
            if(format.equals(formats[i]))
            {
                flag = true;
                break;
            }
        }
        return flag;
    }
}
