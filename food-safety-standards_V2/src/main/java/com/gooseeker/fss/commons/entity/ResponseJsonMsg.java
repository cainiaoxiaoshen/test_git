package com.gooseeker.fss.commons.entity;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * 返回的json格式信息
 * 
 * @author  姓名 工号
 * @version  [版本号, 2017年7月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ResponseJsonMsg
{
    /**
     * 成功标识
     */
    private boolean success = false;
    
    /**
     * 错误信息
     */
    private String errorMsg = "";
    
    public ResponseJsonMsg()
    {
        super();
    }

    public ResponseJsonMsg(boolean success)
    {
        super();
        this.success = success;
    }

    public ResponseJsonMsg(boolean success, String errorMsg)
    {
        super();
        this.success = success;
        this.errorMsg = errorMsg;
    }

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }
    
    private String toJsonString()
    {
        JSONObject json = new JSONObject();
        json.put("success", this.success);
        json.put("errorMsg", this.errorMsg);
        return json.toString();
    }
    
    public void write(HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(toJsonString());
    }
}
