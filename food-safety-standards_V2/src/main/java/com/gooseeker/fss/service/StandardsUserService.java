package com.gooseeker.fss.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.StandardsUser;
import com.gooseeker.fss.entity.StandardsUserRole;
import com.gooseeker.fss.entity.vo.UserAnnotateDetailsVo;
import com.gooseeker.fss.entity.vo.UserWorkTasksVo;

public interface StandardsUserService extends GenericService<StandardsUser, Long>
{

    /**
     * 得到用户信息（包括权限信息）
     * @param name
     * @return
     */
    public StandardsUser findByUserName(String name);

    /**
     * 分页找寻用户的任务信息
     * 
     * @param p
     * @return
     */
    public PageInfo<UserWorkTasksVo> findUserWorkTasksByPage(int p);

    /**
     * 得到用户的角色等级
     * 
     * @param user
     * @return
     */
    public String getUserRoleLv(String user);

    /**
     * 用户的打标数据统计信息
     * 
     * @param user
     * @return
     */
    public UserAnnotateDetailsVo findAnnotateDetails(String user);

//    /**
//     * 根据用户名找寻用户
//     * 
//     * @param name
//     * @return
//     */
//    public StandardsUser findUserByName(String name);

    /**
     * 找寻符合角色的用户任务信息
     * 
     * @param role
     * @return
     */
    public List<UserWorkTasksVo> findAccountItems(String role);

    /**
     * 添加用户以及用户的权限
     * 
     * @param user
     * @return
     */
    public void addAccount(StandardsUserRole user) throws Exception;

    /**
     * 修改用户信息
     * 
     * @throws Exception
     */
    public void updateAccount(String uid, String password, String role)
            throws Exception;

}
