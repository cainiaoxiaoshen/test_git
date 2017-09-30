package com.gooseeker.fss.builder;

import java.io.File;
import java.sql.Timestamp;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.entity.AnnotateDocument;

/**
 * AnnotateDocument对象构建器
 * @author yy
 *
 */
public class AnnotateDocumentBuilder
{
    /**
     * 根据上传的文件构造出文档信息对象
     * @param file 文件
     * @param type 1标准/2法规/3研究报告
     * @param user
     * @return
     */
    public static AnnotateDocument builderFromFile(File file, String type, String user)
    {
        String fileName = file.getName();
        //文件转移的路径
        String uploadPath = DocumentPathBuilder.getUploadPath(type, fileName);
        // 文档编号
        String standardNo = fileName.substring(0, fileName.lastIndexOf("."));
        // 格式
        String format = fileName.substring(fileName.lastIndexOf(".") + 1);
        String reqUrl = DocumentPathBuilder.getDocumentRequestUrl(uploadPath);
        AnnotateDocument doc = new AnnotateDocument();
        doc.setType(type);
        doc.setReqUrl(reqUrl);
        doc.setFormat(format);
        doc.setFileName(fileName);
        doc.setTagState(2);
        doc.setReplace(0);
        doc.setCreateUser(user);
        doc.setAnnotateUser(Constant.STR_EMPTY);
        doc.setFirstCheckUser(Constant.STR_EMPTY);
        doc.setSecondCheckUser(Constant.STR_EMPTY);
        doc.setCreateTime(new Timestamp(System.currentTimeMillis()));
        doc.setKeyWord(Constant.STR_EMPTY);
        doc.setStandardNo(standardNo);
        return doc;
    }
    
    public static AnnotateDocument builder(String user, String standardNo, 
        String reqUrl, String type, String format, boolean isRepeat)
    {
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        AnnotateDocument doc = builder(user, standardNo, reqUrl, 
            type, format, createTime, isRepeat);
        return doc;
    }
    
    public static AnnotateDocument builder(String user, String standardNo, String reqUrl, 
        String type, String format, Timestamp createTime, boolean isRepeat)
    {
        AnnotateDocument doc = new AnnotateDocument();
        doc.setReqUrl(reqUrl);
        doc.setStandardNo(standardNo);
        // 新添加
        doc.setType(type);
        doc.setFormat(format);
        doc.setTagState(2);
        doc.setReplace(0);
        doc.setFileName(Constant.STR_EMPTY);
        doc.setAnnotateUser(Constant.STR_EMPTY);
        doc.setFirstCheckUser(Constant.STR_EMPTY);
        doc.setSecondCheckUser(Constant.STR_EMPTY);
        doc.setKeyWord(Constant.STR_EMPTY);
        if(isRepeat)
        {
            doc.setModiUser(user);
            doc.setModiTime(createTime);
        }
        doc.setCreateUser(user);
        doc.setCreateTime(createTime);
        return doc;
    }
}
