package com.gooseeker.fss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.ConditionSqlBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.Authority;
import com.gooseeker.fss.entity.StandardsUser;
import com.gooseeker.fss.entity.StandardsUserRole;
import com.gooseeker.fss.entity.vo.UserAnnotateDetailsVo;
import com.gooseeker.fss.entity.vo.UserWorkTasksVo;
import com.gooseeker.fss.mapper.StandardsUserMapper;
import com.gooseeker.fss.service.AuthorityService;
import com.gooseeker.fss.service.StandardsUserService;

@Service("standardsUserServiceImpl")
public class StandardsUserServiceImpl extends GenericServiceImpl<StandardsUser, Long> implements StandardsUserService
{
    @Resource(name = "standardsUserMapper")
    private StandardsUserMapper standardsUserMapper;

    @Resource(name = "authorityServiceImpl")
    private AuthorityService authorityService;

    @Override
    public GenericMapper<StandardsUser, Long> getMapper()
    {
        return standardsUserMapper;
    }

    /**
     * 得到用户信息（包括权限信息）
     * @param name
     * @return
     */
    @Override
    public StandardsUser findByUserName(String name)
    {
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        sqlBuilder.addAndEqCondition("t_user.`NAME`", name);
        List<StandardsUser> users = selectByWhereSql(sqlBuilder.getCondition());
        if(users.size() > 0)
        {
            return users.get(0);
        }
        return null;
    }

    /**
     * 得到用户的任务信息
     */
    @Override
    public PageInfo<UserWorkTasksVo> findUserWorkTasksByPage(int p)
    {
        PageHelper.startPage(p, Constant.PAGE_LIMIT);
        List<UserWorkTasksVo> tasksVos = standardsUserMapper.findUserWorkTasks(null);
        PageInfo<UserWorkTasksVo> pageInfo = new PageInfo<UserWorkTasksVo>(tasksVos);
        return pageInfo;
    }

    /**
     * 用户的打标数据统计信息
     */
    @Override
    public UserAnnotateDetailsVo findAnnotateDetails(String user)
    {
        UserAnnotateDetailsVo annotateDetailsVo = new UserAnnotateDetailsVo();
        annotateDetailsVo.setUserName(user);
        standardsUserMapper.findAnnotateDetails(annotateDetailsVo);
        return annotateDetailsVo;
    }
    
    /**
     * 查找角色=role的账号
     */
    @Override
    public List<UserWorkTasksVo> findAccountItems(String role)
    {
        if(Constant.STR_ZERO.equals(role))
        {
            role = Constant.ROLE_ANNOTATE;//打标员
        }
        else
        {
            role = Constant.ROLE_CHECK;//审核员
        }
        ConditionSqlBuilder sqlBuilder = new ConditionSqlBuilder();
        StringBuilder sql = new StringBuilder();
        sql.append(" AND (t_authority.`ROLE` = '")
           .append(role)
           .append("' OR t_authority.`ROLE` = '")
           .append(Constant.ROLE_ADMIN)
           .append("')");
        sqlBuilder.addConditionSql(sql.toString());
        List<UserWorkTasksVo> tasks = standardsUserMapper.findUserWorkTasks(sqlBuilder.getCondition());
        return tasks;
    }

    /**
     * 添加用户
     * 
     */
    @Transactional
    @Override
    public void addAccount(StandardsUserRole user) throws Exception
    {
        saveOrUpdate(user);
        if (StringUtil.isNotBlank(user.getRole()))
        {
            //添加用户权限
            String[] roles = user.getRole().split(",");
            for (int i = 0; i < roles.length; i++)
            {
                Authority authority = new Authority(user.getId(), roles[i]);
                authorityService.saveOrUpdate(authority);
            }
        }
    }

    /**
     * 修改用户信息
     */
    @Transactional
    @Override
    public void updateAccount(String uid, String password, String role)
            throws Exception
    {
        StandardsUser standardsUser = selectByPrimaryKey(Long.valueOf(uid));
        // 判断用户是否存在
        if (standardsUser != null)
        {
            standardsUser.setPassword(password);
            saveOrUpdate(standardsUser);
            // 修改权限的方法---删除原来权限，然后再添加新设置的权限进去
            authorityService.deleteAuthorityByUid(standardsUser.getId());
            String[] roles = role.split(",");
            for (int i = 0; i < roles.length; i++)
            {
                Authority authority = new Authority(standardsUser.getId(), roles[i]);
                authorityService.saveOrUpdate(authority);
            }
        }
    }
    
    /**
     * 
     * 得到用户的权限等级
     * @param user
     * @return
     */
    @Override
    public String getUserRoleLv(String user)
    {
        List<Authority> authorities = findByUserName(user).getAuthorities();
        String roleLv = null;
        for (int i = 0; i < authorities.size(); i++)
        {
            Authority authority = authorities.get(i);
            String role = authority.getRole();
            if (Constant.ROLE_ANNOTATE.equals(role))
            {
                roleLv = Constant.ROLE_LV_STR_ANNOTATE;
            }
            if (Constant.ROLE_CHECK.equals(role))
            {
                if (Constant.ROLE_LV_STR_ANNOTATE.equals(roleLv))
                {
                    roleLv = roleLv + Constant.ROLE_LV_STR_CHECK;
                }
                else
                {
                    roleLv = Constant.ROLE_LV_STR_CHECK;
                }
            }
            if (Constant.ROLE_ADMIN.equals(role))
            {
                roleLv = Constant.ROLE_LV_STR_ADMIN;
                break;
            }
        }
        return roleLv;
    }
}
