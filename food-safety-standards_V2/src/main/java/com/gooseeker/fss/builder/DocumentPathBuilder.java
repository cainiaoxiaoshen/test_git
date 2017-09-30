package com.gooseeker.fss.builder;

import java.io.File;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.utils.PropertiesUtil;

/**
 * 处理文件路径的构造器
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年8月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DocumentPathBuilder
{
    /**
     * 文档格式数组集合
     */
    public static final String[] DOC_FORMATS_ARRAY = {"pdf", "zip", "html", "htm"};
    
    /**
     * 标准的文档格式分类集合
     */
    public static final String[] DOC_STANDARD_FORMATS = {"pdf", "html", "other"};
    
    /**
     * 文档类型
     */
    public static final String[] DOC_TYPES = {"standards", "regulations", "report"};
    
    /**
     * 文件上传时被上传到的临时文件
     */
    private static final String TEMP_DIR_PATH = "/fileTemp";
    
    /**
     * pdf格式文档
     */
    public static final String DOC_FORMAT_PDF = "pdf";
    
    /**
     * html格式文档
     */
    public static final String DOC_FORMAT_HTML = "html";
    
    /**
     * 文档格式htm
     */
    public static final String DOC_FORMAT_HTM = "htm";
    
    /**
     * 文档格式zip
     */
    public static final String DOC_FORMAT_ZIP = "zip";
    
    /**
     * 其他格式文档
     */
    public static final String DOC_FORMAT_OTHER = "other";
    
    /**
     * 标准文档
     */
    public static final String DOC_TYPE_STANDARDS = "standards";
    
    /**
     * 法规文档
     */
    public static final String DOC_TYPE_REGULATIONS = "regulations";
    
    /**
     * 报告文档
     */
    public static final String DOC_TYPE_REPORT = "report";
    static
    {
        initCreateDir();
    }
    /**
     * 创建文件夹
     */
    public static void initCreateDir()
    {

        String basePath = PropertiesUtil.getProperty("doc.file.base.upload.path");
        
        StringBuilder pathBuilder = new StringBuilder();
        //创建文档上传时需要的文件夹
        for (int i = 0; i < DOC_TYPES.length; i++)
        {
            for (int j = 0; j < DOC_STANDARD_FORMATS.length; j++)
            {
                pathBuilder.append(basePath)
                            .append("/")
                            .append(DOC_TYPES[i])
                            .append("/")
                            .append(DOC_STANDARD_FORMATS[j])
                            .append("/")
                            .append("txt");
                
                File directory = new File(pathBuilder.toString());
                if (!directory.exists() || !directory.isDirectory())
                {
                    directory.mkdirs();
                }
                pathBuilder.delete(0, pathBuilder.length());
            }
        }
        
        //文档上传时先上传到的临时文件夹
        File fileTempDir = new File(TEMP_DIR_PATH);
        if(!fileTempDir.exists() || !fileTempDir.isDirectory())
        {
            fileTempDir.mkdir();
        }
        
        //文档索引目录
        File indexDir = new File(PropertiesUtil.getProperty("doc.pdf.index"));
        if(!indexDir.exists() || !indexDir.isDirectory())
        {
            indexDir.mkdir();
        }
    }
    /**
     * 构造临时文件夹的路径
     * @param user
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getTempDirectoryPath(String user)
    {
        // F:/fss/temp
        String basePath = PropertiesUtil.getProperty("doc.file.base.upload.path");
        StringBuilder builder = new StringBuilder(basePath);
        // F:/fss/temp/user/
        builder.append("/").append(user).append("/");
        //临时文件夹路径
        return builder.toString();
    }
    
    /**
     * 构造文件临时上传的路径
     * @param user
     * @param fileName
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getUploadTempPath(String user, String fileName)
    {
        //F:/fss/temp/user/xxx.pdf
        String uploadPath = getTempDirectoryPath(user) + fileName;
        return uploadPath;
    }
    
    /**
     * 构造文件真正上传的路径
     * @param type 上传的文件类型 1标准/2法规/3研究报告
     * @param fileName 上传的文件名
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getUploadPath(String type, String fileName)
    {
        String format = fileName.substring(fileName.lastIndexOf(".") + 1);
        
        StringBuilder uploadPath = new StringBuilder();
        uploadPath.append(getTypePath(type)).append("/");
        //pdf文件
        if(DOC_FORMAT_PDF.equals(format))
        {
            uploadPath.append(DOC_FORMAT_PDF);
        }
        //html文件
        else if(DOC_FORMAT_HTML.equals(format) || DOC_FORMAT_HTML.equals(format))
        {
            uploadPath.append(DOC_FORMAT_HTML);
        }
        else 
        {
            //其他文件
            uploadPath.append(DOC_FORMAT_OTHER);
        }
        return uploadPath.append("/").append(fileName).toString();
    }
    
    /**
     * 根据文件的类型构造出类型部分的路径
     * @param type
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static String getTypePath(String type)
    {
        String path = PropertiesUtil.getProperty("doc.file.base.upload.path");
        if(Constant.STR_ONE.equals(type))
        {
            path = path + "/" + DOC_TYPE_STANDARDS;
        }
        //法规
        else if(Constant.STR_TWO.equals(type))
        {
            path = path + "/" + DOC_TYPE_REGULATIONS;
        }
        //研究报告
        else
        {
            path = path + "/" + DOC_TYPE_REPORT;
        }
        return path;
    }
    
    /**
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * @param uploadPath
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getDocumentRequestUrl(String uploadPath)
    {
        String basePath = PropertiesUtil.getProperty("doc.file.base.upload.path");
        String docNo = uploadPath.substring(uploadPath.indexOf(basePath) + basePath.length(), uploadPath.length());
        StringBuilder url = new StringBuilder();
        url.append(PropertiesUtil.getProperty("fss.pdf.http")).append(docNo);
        return url.toString();
    }
    
    /**
     * 得到pdf文件上传保存的路径
     * @param 文档访问的url
     * @see [类、类#方法、类#成员]
     */
    public static String getPdfUploadPath(String reqUrl)
    {
        String pdfFile = reqUrl;
        String baseReqUrl = PropertiesUtil.getProperty("fss.pdf.http");
        pdfFile = pdfFile.substring(pdfFile.indexOf(baseReqUrl) + baseReqUrl.length(), pdfFile.length());
        String basePath = PropertiesUtil.getProperty("doc.file.base.upload.path");
        pdfFile = basePath + pdfFile;
        return pdfFile;
    }
    
    /**
     * 得到txt文件的路径
     * @param 文档访问的url
     * @param standardNo 真正的文档编号
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getTxtPathByPdfPath(String pdfPath, String standardNo)
    {
        int index = pdfPath.lastIndexOf("/");
        String path = pdfPath.substring(0, index);
        StringBuilder txtPath = new StringBuilder(path);
        txtPath.append("/")
               .append("txt")
               .append("/")
               .append(standardNo)
               .append(".txt");
        return txtPath.toString();
    }
    
    /**
     * 得到txt文件的路径
     * @param 文档访问的url
     * @param standardNo 真正的文档编号
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getPDFTxtPath(String reqUrl, String standardNo)
    {
        String baseReqUrl = PropertiesUtil.getProperty("fss.pdf.http");
        reqUrl = reqUrl.substring(reqUrl.indexOf(baseReqUrl) + baseReqUrl.length(), reqUrl.length());
        reqUrl = reqUrl.substring(0, reqUrl.lastIndexOf("/"));
        String basePath = PropertiesUtil.getProperty("doc.file.base.upload.path");
        StringBuilder txtPath = new StringBuilder();
        txtPath.append(basePath)
               .append(reqUrl)
               .append("/")
               .append("txt")
               .append("/")
               .append(standardNo)
               .append(".txt");
        return txtPath.toString();
    }
    
    public static String getTxtPath(int type, String format, String filename)
    {
        //1标准/2法规/3研究报告
        StringBuilder path = new StringBuilder(getTypePath(String.valueOf(type)));
        if(DOC_FORMAT_HTML.equals(format))
        {
            path.append("/").append(DOC_FORMAT_HTML);
        }
        else if(DOC_FORMAT_PDF.equals(format))
        {
            path.append("/").append(DOC_FORMAT_PDF);
        }
        else
        {
            path.append("/").append(DOC_FORMAT_OTHER);
        }
        path.append("/").append("txt").append("/").append(filename).append(".txt");
        return path.toString();
    }
}
