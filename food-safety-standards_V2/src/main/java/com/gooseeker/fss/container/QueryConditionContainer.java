package com.gooseeker.fss.container;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.utils.StringUtil;

public class QueryConditionContainer
{
    /**
     * 类型：1标准/2法规/3研究报告
     */
    private String type;
    /**
     * 打标状态
     */
    private String tagState;
    /**
     * 国家
     */
    private String country;
    
    /**
     * 用户名
     */
    private String user;
    
    /**
     * 权限等级
     */
    private String roleLv;
    
    /**
     * 当前页
     */
    private int pageNum = Constant.PAGE_NUM;
    
    /**
     * 搜索的关键词
     */
    private String keyWord;
    
    private int startNum;
    
    private int pageSize;
    
    private String outputType = Constant.STR_ZERO;

    private String outputTagState= Constant.STR_ZERO;

    private String outputCountry= Constant.STR_ZERO;
    
    //private Map<String, Object> queryMap = new HashMap<String, Object>();
    
    public QueryConditionContainer(String type, String tagState, String country,
            String user, String roleLv, int pageNum, String keyWord)
    {
        super();
        
        if (StringUtil.isNumerical(type) && !Constant.STR_ZERO.equals(type))
        {
            this.type = type;
            this.outputType = type;
        }
        if (StringUtil.isNumerical(tagState) && !Constant.STR_ZERO.equals(tagState))
        {
            this.tagState = tagState;
            this.outputTagState = tagState;
        }
        if (StringUtil.isNotBlank(country) && !Constant.STR_ZERO.equals(country))
        {
            country = StringUtil.decode(country);
            this.country = country;
            this.outputCountry = country;
        }
        if(StringUtil.isNotBlank(keyWord))
        {
            keyWord = StringUtil.decode(keyWord);// 解码
            keyWord = StringUtil.removeAllHtmlElement(keyWord.trim());
            keyWord = StringEscapeUtils.escapeSql(keyWord.trim());
            this.keyWord = keyWord;
        }
        this.user = user;
        this.roleLv = roleLv;
        this.pageNum = pageNum;
    }
    
//    public Map<String, Object> getQueryMap()
//    {
//        queryMap.put("user", this.user);
//        queryMap.put("roleLv", this.roleLv);
//        if (StringUtil.isNumerical(this.tagState))
//        {
//            queryMap.put("tagState", this.tagState);
//        }
//        if (StringUtil.isNumerical(this.type))
//        {
//            queryMap.put("type", this.type);
//        }
//        if (StringUtil.isNotBlank(this.country))
//        {
//            queryMap.put("country", this.country);
//        }
//        return queryMap;
//    }

    public String getType()
    {
        return type;
    }

    public String getTagState()
    {
        return tagState;
    }

    public String getCountry()
    {
        return country;
    }

    public String getUser()
    {
        return user;
    }

    public String getRoleLv()
    {
        return roleLv;
    }

    public int getPageNum()
    {
        return pageNum;
    }

    public String getOutputType()
    {
        return outputType;
    }

    public String getOutputTagState()
    {
        return outputTagState;
    }

    public String getOutputCountry()
    {
        return outputCountry;
    }

    public String getKeyWord()
    {
        return keyWord;
    }

    public int getStartNum()
    {
        return startNum;
    }

    public void setStartNum(int startNum)
    {
        this.startNum = startNum;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

}
