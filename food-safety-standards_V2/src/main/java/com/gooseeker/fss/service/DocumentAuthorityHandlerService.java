package com.gooseeker.fss.service;

import javax.annotation.Resource;

import com.gooseeker.fss.container.PluginMarkContainer;
import com.gooseeker.fss.entity.AnnotateDocument;

/**
 * 文档权限处理服务器 用户对文档的操作
 * 
 * @author lenovo
 *
 */
public interface DocumentAuthorityHandlerService
{
    /**
     * 检查用户对文档的操作权限
     * @param docNo 文档的编号
     * @param user 当前操作的用户名
     * @return
     * @throws Exception
     */
    public boolean hasOperateAuthority(long docId, String user);
    
    /**
     * 
     * @param docNo 文档编号
     * @param user 当前操作的用户名
     * @param roleLv 用户的角色等级
     * @return
     * @throws Exception
     */
    public boolean hasOperateAuthority(long docId, String user, String roleLv);
    
    /**
     * 
     * @param docNo 文档编号
     * @param user 当前操作的用户名
     * @param roleLv 用户的角色等级
     * @return
     * @throws Exception
     */
    public boolean hasOperateAuthority(AnnotateDocument doc, String user, String roleLv);
    
    /**
     * 文档状态更新操作权限检查
     * @param doc 被操作的文档对象
     * @param user 当前用户
     * @param roleLv 用户的角色等级
     * @param state 要被更新的结果状态
     * @return
     */
    public boolean authorityCheckForStateUpdate(AnnotateDocument doc, 
        String user, String roleLv, int state);
    
    /**
     * 打标操作时权限检查
     * @param user 当前的用户
     * @param reqUrl 文档url
     */
    public PluginMarkContainer hasOperateAuthorityForMark(String user, String reqUrl);
}
