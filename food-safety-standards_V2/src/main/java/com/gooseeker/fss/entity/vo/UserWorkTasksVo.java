package com.gooseeker.fss.entity.vo;

import java.sql.Timestamp;

/**
 * 
 * 用户的工作任务
 * 
 * @author 姓名 工号
 * @version [版本号, 2017年7月14日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserWorkTasksVo
{
    /**
     * 用户id
     */
    private Long id;
    /**
     * 名字
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 创建日期
     */
    private Timestamp createDate;
    /**
     * 角色信息
     */
    private String roles;
    /**
     * 审核文档数
     */
    private int checkDocNum;
    /**
     * 打标文档数
     */
    private int annotateDocNum;

    public UserWorkTasksVo()
    {
        super();
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Timestamp getCreateDate()
    {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate)
    {
        this.createDate = createDate;
    }

    public String getRoles()
    {
        return roles;
    }

    public void setRoles(String roles)
    {
        this.roles = roles;
    }

    public int getCheckDocNum()
    {
        return checkDocNum;
    }

    public void setCheckDocNum(int checkDocNum)
    {
        this.checkDocNum = checkDocNum;
    }

    public int getAnnotateDocNum()
    {
        return annotateDocNum;
    }

    public void setAnnotateDocNum(int annotateDocNum)
    {
        this.annotateDocNum = annotateDocNum;
    }
}
