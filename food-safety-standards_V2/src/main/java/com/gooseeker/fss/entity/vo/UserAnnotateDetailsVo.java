package com.gooseeker.fss.entity.vo;

public class UserAnnotateDetailsVo
{
    private String userName;
    //用户打标结果数
    private int tagResultNum;
    //打标任务数
    private int tagTaskNum;
    //审核任务数
    private int checkTaskNum;
    //用户角色
    private String roles;
    public String getUserName()
    {
        return userName;
    }
    public void setUserName(String userName)
    {
        this.userName = userName;
    }
    public int getTagResultNum()
    {
        return tagResultNum;
    }
    public void setTagResultNum(int tagResultNum)
    {
        this.tagResultNum = tagResultNum;
    }
    public int getTagTaskNum()
    {
        return tagTaskNum;
    }
    public void setTagTaskNum(int tagTaskNum)
    {
        this.tagTaskNum = tagTaskNum;
    }
    public int getCheckTaskNum()
    {
        return checkTaskNum;
    }
    public void setCheckTaskNum(int checkTaskNum)
    {
        this.checkTaskNum = checkTaskNum;
    }
    public String getRoles()
    {
        return roles;
    }
    public void setRoles(String roles)
    {
        this.roles = roles;
    }
    
    
}
