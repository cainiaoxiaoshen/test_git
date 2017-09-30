package com.gooseeker.fss.mapper;

import java.util.*;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.StandardsUser;
import com.gooseeker.fss.entity.vo.UserAnnotateDetailsVo;
import com.gooseeker.fss.entity.vo.UserWorkTasksVo;

@Repository("standardsUserMapper")
public interface StandardsUserMapper extends GenericMapper<StandardsUser, Long>
{
//    /**
//     * 得到用户信息（包括权限信息）
//     * @param name
//     * @return
//     */
//    public StandardsUser findUserRole(String name);

//    /**
//     * 用户的任务信息
//     * 
//     * @param map
//     * @return
//     */
//    public List<UserWorkTasksVo> findPageUserWorkTasks(Map<String, Object> map)
//            throws Exception;

    /**
     * 用户的打标数据统计信息
     */
    public void findAnnotateDetails(UserAnnotateDetailsVo annotateDetailsVo);

//    /**
//     * 根据用户名查找用户
//     * 
//     * @param name
//     * @return
//     */
//    public StandardsUser findUserByName(String name) throws Exception;
    
    public List<UserWorkTasksVo> findUserWorkTasks(Map<String, String> map);

}
