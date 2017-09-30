package com.gooseeker.fss.commons.entity;

import java.util.List;

/**
 * excel实体对象
 * 每一个ExcelEntity实体对应一个excel或excel的一个sheet页
 * 
 * @author yangyang
 * @version  [版本号, 2015年9月15日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelEntity<T>
{
    /**
     * excel的body内容
     * 每个Object对象都是excel表格的每一行
     */
    private List<T> bodys;
    
    /**
     * excel的head内容
     */
    private List<String> heads;
    
    /**
     * excel的sheet页名字
     */
    private String sheetName = "Sheet1";
    
    
    public List<T> getBodys()
    {
        return bodys;
    }
    public void setBodys(List<T> bodys)
    {
        this.bodys = bodys;
    }
    public List<String> getHeads()
    {
        return heads;
    }
    public void setHeads(List<String> heads)
    {
        this.heads = heads;
    }
    public String getSheetName()
    {
        return sheetName;
    }
    public void setSheetName(String sheetName)
    {
        this.sheetName = sheetName;
    }
}
