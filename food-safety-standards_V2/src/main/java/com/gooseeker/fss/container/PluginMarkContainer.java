package com.gooseeker.fss.container;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.entity.AnnotateDocument;

public class PluginMarkContainer
{
    // 错误信息
    private String errorMessage = Constant.STR_EMPTY;
    // 获得的值
    private Object value = Constant.STR_EMPTY;
    // 成功信息
    private String success = Constant.STR_EMPTY;
    //操作类型0：打标，1：一审，2：二审
    private int operate;
    //当前打标或审核的文档
    private AnnotateDocument doc;
    //当前用户
    private String user;
    //是否有操作权限
    private boolean hasAuthority;
    //角色等级
    private String roleLv;
    //正在打标的文档的网址
    private String reqUrl;
    //文档id
    //private  String docId;
    public String getErrorMessage()
    {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
    public Object getValue()
    {
        return value;
    }
    public void setValue(Object value)
    {
        this.value = value;
    }
    public String getSuccess()
    {
        return success;
    }
    public void setSuccess(String success)
    {
        this.success = success;
    }
    public int getOperate()
    {
        return operate;
    }
    public void setOperate(int operate)
    {
        this.operate = operate;
    }
    public AnnotateDocument getDoc()
    {
        return doc;
    }
    public void setDoc(AnnotateDocument doc)
    {
        this.doc = doc;
    }
    public String getUser()
    {
        return user;
    }
    public void setUser(String user)
    {
        this.user = user;
    }
    public boolean isHasAuthority()
    {
        return hasAuthority;
    }
    public void setHasAuthority(boolean hasAuthority)
    {
        this.hasAuthority = hasAuthority;
    }
    public String getRoleLv()
    {
        return roleLv;
    }
    public void setRoleLv(String roleLv)
    {
        this.roleLv = roleLv;
    }
    public String getReqUrl()
    {
        return reqUrl;
    }
    public void setReqUrl(String reqUrl)
    {
        this.reqUrl = reqUrl;
    }
    
    private String toJsonString()
    {
        JSONObject json = new JSONObject();
        json.put("value", this.value);
        json.put("Success", this.success);
        json.put("ErrorMessage", this.errorMessage);
        json.put("docId", this.doc.getId());
        return json.toString();
    }
    
    public void write(HttpServletResponse response) throws IOException
    {
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(toJsonString());
    }
}
