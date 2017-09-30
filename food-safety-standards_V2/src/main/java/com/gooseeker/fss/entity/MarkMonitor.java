package com.gooseeker.fss.entity;

public class MarkMonitor
{
    //文档的主键自增id
    private Long id;
    //文档编号
    private String docNo;
    //文档名称
    private String docName;
    //文档类型 类型：1标准/2法规/3研究报告
    private int type;
    //文档状态 1：打标中；2：未打标；3：一审；4：二审；5：入库
    private int tagState;
    //国家
    private String country;
    //打标员
    private String annotateUser;
    //一审员
    private String firstCheckUser;
    //二审员
    private String secondCheckUser;
    //标准文档编号
    private String standardNo;
    //打标审核数（从审核记录数中除去针对相同的一条打标结果的记录，只保留一条）
    private int antChkNum;
    //打标数
    private int antNum;
    //审核记录数
    private int checkRecordNum;
    //出错率
    private int errorRate;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getDocNo()
    {
        return docNo;
    }

    public void setDocNo(String docNo)
    {
        this.docNo = docNo;
    }

    public String getDocName()
    {
        return docName;
    }

    public void setDocName(String docName)
    {
        this.docName = docName;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public int getTagState()
    {
        return tagState;
    }

    public void setTagState(int tagState)
    {
        this.tagState = tagState;
    }

    public String getCountry()
    {
        return country;
    }

    public void setCountry(String country)
    {
        this.country = country;
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

    public String getStandardNo()
    {
        return standardNo;
    }

    public void setStandardNo(String standardNo)
    {
        this.standardNo = standardNo;
    }

    public int getAntChkNum()
    {
        return antChkNum;
    }

    public void setAntChkNum(int antChkNum)
    {
        this.antChkNum = antChkNum;
    }

    public int getCheckRecordNum()
    {
        return checkRecordNum;
    }

    public void setCheckRecordNum(int checkRecordNum)
    {
        this.checkRecordNum = checkRecordNum;
    }

    public int getAntNum()
    {
        return antNum;
    }

    public void setAntNum(int antNum)
    {
        this.antNum = antNum;
    }

    public int getErrorRate()
    {
        return errorRate;
    }

    public void setErrorRate(int errorRate)
    {
        this.errorRate = errorRate;
    }

    
    
}
