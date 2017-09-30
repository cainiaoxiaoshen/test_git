package com.gooseeker.fss.mapper;

import java.util.*;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.mapper.common.SUserExample;
import com.gooseeker.fss.entity.MyUser;
import com.gooseeker.fss.entity.StandardsUser;
import com.gooseeker.fss.entity.vo.UserAnnotateDetailsVo;
import com.gooseeker.fss.entity.vo.UserWorkTasksVo;

@Repository("userMapper")
public interface UserMapper extends GenericMapper<MyUser, Long>
{
    List<MyUser> selectByExample(SUserExample example);
    
    int updateByExampleSelective(@Param("record") MyUser record, @Param("example") SUserExample example);

}
