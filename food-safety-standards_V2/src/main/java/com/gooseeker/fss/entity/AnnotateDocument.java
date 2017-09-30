package com.gooseeker.fss.entity;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.gooseeker.fss.commons.entity.Constant;

public class AnnotateDocument
{
    // 文档ID
    private Long id;
    // 创建人
    private String createUser;
    // 创建时间
    private Timestamp createTime;
    // 修改者
    private String modiUser;
    // 修改时间
    private Timestamp modiTime;
    // 类型：1标准/2法规/3研究报告
    private String type;
    // 导入的文件名
    private String fileName;
    //文档的访问url
    private String reqUrl;
    // 文件格式
    private String format;
    // 文档名称
    private String docName;
    // 产品体系
    private String proSystem;
    // 语种
    private String language;
    // 国家
    private String country;
    // 定制时间
    private Timestamp pubTime;
    // 实施时间
    private Timestamp impTime;
    // 摘要
    private String description;
    // 文档代替关系，0:没有代替文档；1：有代替文档
    private Integer replace;
    // 打标状态，1：打标中；2：未打标；3：一审；4：二审；5：入库
    private Integer tagState;
    // 打标员
    private String annotateUser = Constant.STR_EMPTY;
    // 一审员
    private String firstCheckUser = Constant.STR_EMPTY;
    // 二审员
    private String secondCheckUser = Constant.STR_EMPTY;
    // 关键词
    private String keyWord;
    //对应的文档的内容
    private String text;
    //被同步的词的版本
    private String version = null;
    //标准编号
    private String standardNo;
    
    private List<DocReplace> docReplaces;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCreateUser()
    {
        return createUser;
    }

    public void setCreateUser(String createUser)
    {
        this.createUser = createUser;
    }

    public Timestamp getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime)
    {
        this.createTime = createTime;
    }

    public String getModiUser()
    {
        return modiUser;
    }

    public void setModiUser(String modiUser)
    {
        this.modiUser = modiUser;
    }

    public Timestamp getModiTime()
    {
        return modiTime;
    }

    public void setModiTime(Timestamp modiTime)
    {
        this.modiTime = modiTime;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getFileName()
    {
        return fileName;
    }

    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getReqUrl()
    {
        return reqUrl;
    }

    public void setReqUrl(String reqUrl)
    {
        this.reqUrl = reqUrl;
    }

    public String getFormat()
    {
        return format;
    }

    public void setFormat(String format)
    {
        this.format = format;
    }

    public String getDocName()
    {
        return docName;
    }

    public void setDocName(String docName)
    {
        this.docName = docName;
    }

    public String getProSystem()
    {
        return proSystem;
    }

    public void setProSystem(String proSystem)
    {
        this.proSystem = proSystem;
    }

    public String getLanguage()
    {
        return language;
    }

    public void setLanguage(String language)
    {
        this.language = language;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
    }

    public Timestamp getPubTime()
    {
        return pubTime;
    }

    public void setPubTime(Timestamp pubTime)
    {
        this.pubTime = pubTime;
    }

    public Timestamp getImpTime()
    {
        return impTime;
    }

    public void setImpTime(Timestamp impTime)
    {
        this.impTime = impTime;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getReplace()
    {
        return replace;
    }

    public void setReplace(Integer replace)
    {
        this.replace = replace;
    }

    public Integer getTagState()
    {
        return tagState;
    }

    public void setTagState(Integer tagState)
    {
        this.tagState = tagState;
    }

    public String getAnnotateUser()
    {
        return annotateUser;
    }

    public void setAnnotateUser(String annotateUser)
    {
        this.annotateUser = annotateUser;
    }

    public String getFirstCheckUser()
    {
        return firstCheckUser;
    }

    public void setFirstCheckUser(String firstCheckUser)
    {
        this.firstCheckUser = firstCheckUser;
    }

    public String getSecondCheckUser()
    {
        return secondCheckUser;
    }

    public void setSecondCheckUser(String secondCheckUser)
    {
        this.secondCheckUser = secondCheckUser;
    }

    public String getKeyWord()
    {
        return keyWord;
    }

    public void setKeyWord(String keyWord)
    {
        this.keyWord = keyWord;
    }

    public String getText()
    {
        return text;
    }
    public void setText(String text)
    {
        this.text = text;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

    public String getStandardNo()
    {
        return standardNo;
    }

    public void setStandardNo(String standardNo)
    {
        this.standardNo = standardNo;
    }

    public List<DocReplace> getDocReplaces()
    {
        return docReplaces;
    }

    public void setDocReplaces(List<DocReplace> docReplaces)
    {
        this.docReplaces = docReplaces;
    }
    
}
