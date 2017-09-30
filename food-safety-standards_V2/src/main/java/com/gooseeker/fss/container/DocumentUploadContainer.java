package com.gooseeker.fss.container;

import java.util.List;

import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.entity.PageInclude;

/**
 * 文件上传，解析文件时用到的容器用于存在在这个过程中产生的一些数据
 * @author lenovo
 *
 */
public class DocumentUploadContainer
{
    /**
     * 上传时解析pdf文本内容匹配到的PageInclude关键词对象信息
     */
    private List<PageInclude> pageIncludes;
    
    /**
     * 文件上传时文件解析构造成AnnotateDocument对象
     */
    private List<AnnotateDocument> documents;
    
    /**
     * 文件上传时重复的文件名
     */
    private String repeatFileNames;

    public List<PageInclude> getPageIncludes()
    {
        return pageIncludes;
    }

    public void setPageIncludes(List<PageInclude> pageIncludes)
    {
        this.pageIncludes = pageIncludes;
    }

    public List<AnnotateDocument> getDocuments()
    {
        return documents;
    }

    public void setDocuments(List<AnnotateDocument> documents)
    {
        this.documents = documents;
    }

    public String getRepeatFileNames()
    {
        return repeatFileNames;
    }

    public void setRepeatFileNames(String repeatFileNames)
    {
        this.repeatFileNames = repeatFileNames;
    }

}
