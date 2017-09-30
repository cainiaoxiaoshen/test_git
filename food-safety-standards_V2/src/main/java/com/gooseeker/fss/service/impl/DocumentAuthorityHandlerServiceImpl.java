package com.gooseeker.fss.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.PluginMarkContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.plugin.controller.AnnotatePluginController;
import com.gooseeker.fss.service.AnnotateDocumentService;
import com.gooseeker.fss.service.DocumentAuthorityHandlerService;
import com.gooseeker.fss.service.StandardsUserService;

/**
 * 文档权限处理服务器 用户对文档的操作
 * 
 * @author lenovo
 *
 */
@Service("documentAuthorityHandlerServiceImpl")
public class DocumentAuthorityHandlerServiceImpl implements DocumentAuthorityHandlerService
{
    private static Logger logger = Logger.getLogger(DocumentAuthorityHandlerServiceImpl.class);
    
    @Resource(name = "annotateDocumentServiceImpl")
    private AnnotateDocumentService documentService;
    
    @Resource(name = "standardsUserServiceImpl")
    private StandardsUserService standardsUserService;
    
    /**
     * 检查用户对文档的操作权限
     * @param docNo 文档的编号
     * @param user 当前操作的用户名
     * @return
     * @throws Exception
     */
    @Override
    public boolean hasOperateAuthority(long docId, String user)
    {
        boolean hasAuthority = false;
        try
        {
            AnnotateDocument doc = documentService.selectByPrimaryKey(docId);
            String roleLv = standardsUserService.getUserRoleLv(user);
            hasAuthority = hasOperateAuthority(doc, user, roleLv);
        }
        catch (Exception e)
        {
            logger.error("文档操作权限检查出错", e);
        }
        
        return hasAuthority;
    }
    
    /**
     * 
     * @param docNo 文档编号
     * @param user 当前操作的用户名
     * @param roleLv 用户的角色等级
     * @return
     * @throws Exception
     */
    @Override
    public boolean hasOperateAuthority(long docId, String user, String roleLv)
    {
        boolean hasAuthority = false;
        try
        {
            AnnotateDocument doc = documentService.selectByPrimaryKey(docId);
            hasAuthority = hasOperateAuthority(doc, user, roleLv);
        }
        catch (Exception e)
        {
            logger.error("文档操作权限检查出错", e);
        }
        return hasAuthority;
    }
    
    /**
     * 
     * @param docNo 文档编号
     * @param user 当前操作的用户名
     * @param roleLv 用户的角色等级
     * @return
     * @throws Exception
     */
    @Override
    public boolean hasOperateAuthority(AnnotateDocument doc, String user, String roleLv)
    {
        boolean hasAuthority = false;
        if(doc != null)
        {
            switch (doc.getTagState())
            {
                case Constant.TAG_STATE_START:// 未打标
                case Constant.TAG_STATE_TAGING:// 打标中
                    if (user.equals(doc.getAnnotateUser()))
                    {
                        // 文档处于未打标或者打标中的状态，文档的打标者可以操作该文档
                        hasAuthority = true;
                    }
                    break;
                case Constant.TAG_STATE_FCHECK:// 一审
                    if (user.equals(doc.getFirstCheckUser()))
                    {
                        // 文档处于一审的状态，文档的一审员可以操作该文档
                        hasAuthority = true;
                    }
                    break;
                case Constant.TAG_STATE_SCHECK:// 二审
                    if (user.equals(doc.getSecondCheckUser()))
                    {
                        // 文档处于二审的状态，文档的二审员可以操作该文档
                        hasAuthority = true;
                    }
                    break;
            }
            if (Constant.ROLE_LV_STR_ADMIN.equals(roleLv))
            {
                //管理员账号
                hasAuthority = true;
            }
        }
        return hasAuthority;
    }
    
    /**
     * 状态确认的权限检查
     * @param user
     * @param state
     * @param doc
     * @return
     */
    public boolean authorityCheckForStateUpdate(AnnotateDocument doc, 
        String user, String roleLv, int state)
    {
        boolean hasAuthority = false;
        if(doc != null)
        {
            if(stateUpCheck(state, doc.getTagState(), roleLv))
            {
                hasAuthority = hasOperateAuthority(doc, user, roleLv);
            }
        }
        return hasAuthority;
    }
    
    /**
     * 
     * 文档状态更新检查，不能向下级更新
     * @param updateState 要更新的状态
     * @param currState 文档当前的状态
     * @return
     * @see [类、类#方法、类#成员]
     */
    private boolean stateUpCheck(int updateState, int currState, String roleLv)
    {
        boolean flag = false;
        switch (currState)
        {
            case Constant.TAG_STATE_START://未打标状态，只能变成打标中状态
                if(updateState == Constant.TAG_STATE_TAGING)
                {
                    flag = true;
                }
            break;
            case Constant.TAG_STATE_TAGING://打标中状态，只能变成一审状态
                if(updateState == Constant.TAG_STATE_FCHECK)
                {
                    flag = true;
                }
            break;
            case Constant.TAG_STATE_FCHECK://一审状态，只能变成二审状态
                if(updateState == Constant.TAG_STATE_SCHECK)
                {
                    flag = true;
                }
            break;
            case Constant.TAG_STATE_SCHECK://二审状态，只能变成入库状态
                if(updateState == Constant.TAG_STATE_FNISH)
                {
                    flag = true;
                }
            break;
            case Constant.TAG_STATE_FNISH://入库状态，由管理员重置为未打标状态
                if(updateState == Constant.TAG_STATE_START 
                        && Constant.ROLE_LV_STR_ADMIN.equals(roleLv))
                {
                    flag = true;
                }
            break;
        }
        return flag;
    }
    
    
    /**
     * 打标操作时权限检查
     * @param user 当前的用户
     * @param docNo 文档编号
     */
    @Override
    public PluginMarkContainer hasOperateAuthorityForMark(String user, String reqUrl)
    {
        logger.info("打标权限检查");
        PluginMarkContainer container = new PluginMarkContainer();
        boolean hasAuthority = false;
        if (StringUtil.isNotBlank(user))
        {
            container.setUser(user);
            AnnotateDocument doc = documentService.selectByReqUrl(reqUrl);
            if (doc != null)
            {
                container.setDoc(doc);
                String roleLv = standardsUserService.getUserRoleLv(user);
                switch (doc.getTagState())
                {
                    case Constant.TAG_STATE_START:// 未打标
                    case Constant.TAG_STATE_TAGING:// 打标中
                        if (user.equals(doc.getAnnotateUser()))
                        {
                            hasAuthority = true;
                        }
                        container.setOperate(0);
                        logger.info("打标操作");
                        break;
                    case Constant.TAG_STATE_FCHECK:// 一审
                        if (user.equals(doc.getFirstCheckUser()))
                        {
                            hasAuthority = true;
                        }
                        container.setOperate(1);
                        logger.info("一审操作");
                        break;
                    case Constant.TAG_STATE_SCHECK:// 二审
                        if (user.equals(doc.getSecondCheckUser()))
                        {
                            hasAuthority = true;
                        }
                        container.setOperate(2);
                        logger.info("二审操作");
                        break;
                }
                if (Constant.ROLE_LV_STR_ADMIN.equals(roleLv))
                {
                    hasAuthority = true;
                }
                if(!hasAuthority)
                {
                    container.setErrorMessage(AnnotatePluginController.NO_AUTHORITY);
                }
            }
            else
            {
                container.setErrorMessage(AnnotatePluginController.DOC_IS_NULL);
            }
        }
        else
        {
            container.setErrorMessage(AnnotatePluginController.NO_LOGIN);
        }
        container.setHasAuthority(hasAuthority);
        return container;
    }
}
