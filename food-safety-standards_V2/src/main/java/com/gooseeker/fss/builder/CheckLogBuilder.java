package com.gooseeker.fss.builder;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.Doc;

import org.apache.log4j.Logger;

import com.gooseeker.fss.commons.entity.AnnotatePropertyDictionary;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.PluginMarkContainer;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.DocCheck;

public class CheckLogBuilder
{
    private static Logger logger = Logger.getLogger(CheckLogBuilder.class);
    
    /**
     * 得到审核的进度
     * @param tagState 文档的状态
     * @param user 当前的用户
     * @param antUser 正在审核的打标结果的创建者
     * @param roleLv 当前用户的权限等级
     * @return map(flag:是否需要添加审核记录，grade:审核的进度 一级审核/二级审核)
     */
    public static Map<String, Integer> getCheckProgress (int tagState, String user, String antUser, String roleLv)
    {
        Map<String, Integer> map = new HashMap<String, Integer>();
        //是否需要添加审核记录
        int flag = 0;
        //审核的进度（一级审核/二级审核）
        int grade = Constant.CHECK_GRADE_LV1;
        if(tagState == Constant.TAG_STATE_FCHECK)
        {
            logger.info("一级审核");
            if(!user.equals(antUser) || Constant.ROLE_LV_STR_ADMIN.equals(roleLv))
            {
                flag = 1;
            }
        }
        if(tagState == Constant.TAG_STATE_SCHECK)
        {
            logger.info("二级审核");
            grade = Constant.CHECK_GRADE_LV2;
            if(!user.equals(antUser) || Constant.ROLE_LV_STR_ADMIN.equals(roleLv))
            {
                flag = 1;
            }
        }
        map.put("flag", flag);
        map.put("grade", grade);
        return map;
    }
    
    /**
     * 是否需要添加审核记录
     * @param createUser 打标结果的创建者
     * @param checkUser 正在审核的人员
     * @return
     */
    public static boolean isAddCheckLog(String createUser, String checkUser)
    {
        if(!createUser.equals(checkUser))
        {
            return true;
        }
        return false;
    }
    
    /**
     * 构造审核信息
     * @param annotate
     * @param user
     * @param docNo
     * @param grade
     * @return
     */
    public static DocCheck buildDeleteCheckLog(Annotate annotate, String user, String docNo, int grade)
    {
        String product = annotate.getProduct();
        String factor = annotate.getFactor();
        int antId = Integer.parseInt(String.valueOf(annotate.getId()));
        long docId = annotate.getDocId();
        return buildDeleteCheckLog(docNo, docId, antId, product, factor, grade, user);
    }
    
    /** 
     * 获取两个对象同名属性内容不相同的列表 得到审核信息
     * @param dbAnt 对象1 数据库中查找出来的
     * @param ant 对象2 参数中解析出来的
     * @return 
     * @throws ClassNotFoundException 
     * @throws IllegalAccessException 
     */  
    public static List<DocCheck> buildModifyCheckLogs(Annotate dbAnt, Annotate ant, int antId, String user, int grade, long docId, String docNo)
            throws ClassNotFoundException, IllegalAccessException
    {
        Date date = new Date();
        Timestamp createTime = new Timestamp(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createTimeStr = sdf.format(date);
        List<DocCheck> list = new ArrayList<DocCheck>();
        if(dbAnt != null)
        {
            // 获取对象的属性列表
            Field[] field1 = dbAnt.getClass().getDeclaredFields();
            Field[] field2 = ant.getClass().getDeclaredFields();
            // 遍历属性列表field1
            for (int i = 0; i < field1.length; i++)
            {
                // 遍历属性列表field2
                for (int j = 0; j < field2.length; j++)
                {
                    String field1Name = field1[i].getName();
                    String field2Name = field2[j].getName();
                    // 如果field1[i]属性名与field2[j]属性名内容相同
                    if (AnnotatePropertyDictionary.isBasicProperty(field1Name) && field1Name.equals(field2Name))
                    {
                        field1[i].setAccessible(true);
                        field2[j].setAccessible(true);
                        // 如果field1[i]属性值与field2[j]属性值内容不相同
                        Object field1Value = field1[i].get(dbAnt);
                        Object field2Value = field2[j].get(ant);
                        String attrName = AnnotatePropertyDictionary.getNameByAttribute(field1Name);
                        Map<String, String> descMap = getCheckLogDescription(attrName, String.valueOf(field1Value), 
                                String.valueOf(field2Value), grade, user);
                        if(descMap != null)
                        {
                            String operation = descMap.get("operation");
                            String description = descMap.get("description");
                            list.add(buildCheckLog(operation, description, grade, user, 
                                    docId, antId, createTime, createTimeStr));
                        }
                        break;
                    }
                }
            }
        }
        else
        {
            String product = ant.getProduct();
            String factor = ant.getFactor();
            DocCheck check = buildAddCheckLog(docId, antId, product, factor, docNo, grade, user, createTime, createTimeStr);
            list.add(check);
        }
        return list;
    }
    
    /**
     * 属性字段值改变时构造审核记录信息
     * @param operation
     * @param description
     * @param grade
     * @param user
     * @param docId
     * @param antId
     * @param createTime
     * @return
     */
    private static DocCheck buildCheckLog(String operation, String description, int grade, 
            String user, long docId, int antId, Timestamp createTime, String date)
    {
        StringBuilder desc = new StringBuilder(description);
        if(Constant.CHECK_GRADE_LV1 == grade)
        {
            desc.append("，审核=[一级审核]");
        }
        else
        {
            desc.append("，审核=[二级审核]");
        }
        desc.append("，用户=[")
            .append(user)
            .append("]，时间=[")
            .append(date)
            .append("]}");
        DocCheck check = new DocCheck();
        check.setOperation(operation);
        check.setDescription(desc.toString());
        check.setCreateTime(createTime);
        check.setCreateUser(user);
        check.setAntId(antId);
        check.setGrade(grade);
        check.setDocId(docId);
        return check;
    }
    
    /**
     * 添加一条打标结果信息时构造审核信息
     * @param docId
     * @param antId
     * @param product
     * @param factor
     * @param grade
     * @param user
     * @param createTime
     * @param createTimeStr
     * @return
     */
    private static DocCheck buildAddCheckLog(long docId, int antId, String product, String factor, String docNo,
            int grade, String user, Timestamp createTime, String date)
    {
        StringBuilder desc = new StringBuilder();
        desc.append("{打标结果添加；编号=[")
            .append(docNo)
            .append("]，产品=[")
            .append(product)
            .append("]，指标=[")
            .append(factor)
            .append("]");
        return buildCheckLog(Constant.STR_ONE, desc.toString(), grade, user, docId, antId, createTime, date);
    }
    
    /**
     * 删除打标信息时构造审核信息
     * @param docNo
     * @param docId
     * @param antId
     * @param product
     * @param factor
     * @param grade
     * @param user
     * @return
     */
    private static DocCheck buildDeleteCheckLog(String docNo, long docId, int antId, String product, String factor,int grade, String user)
    {
        Date date = new Date();
        Timestamp createTime = new Timestamp(date.getTime());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String createTimeStr = sdf.format(date);
        StringBuilder desc = new StringBuilder();
        desc.append("{删除编号=[").append(docNo)
            .append("]，产品=[").append(product)
            .append("]，指标=[").append(factor).append("]");

        return buildCheckLog(Constant.STR_TWO, desc.toString(), grade, user, docId, antId, createTime, createTimeStr);
    }
    
    /**
     * 比较2个对象属性的不同处构造出审核信息对象
     * @param fieldName
     * @param field1Value
     * @param field2Value
     * @param grade
     * @param user
     * @param createTime
     * @return
     */
    private static Map<String, String> getCheckLogDescription(String fieldName, String field1Value, 
            String field2Value, int grade, String user)
    {
        String operation;
        StringBuilder desc = new StringBuilder();
        if((field1Value == null || StringUtil.isEmpty(field1Value.toString())) 
                && (field2Value != null && StringUtil.isNotBlank(field2Value.toString())))
        {
            operation = Constant.STR_ONE;
            desc.append("{[")
                .append(fieldName)
                .append("]属性添加；")
                .append(fieldName)
                .append("=[")
                .append(field2Value)
                .append("]");
            //数据库中没有，参数中有，新添加
        }
        else if((field1Value != null && StringUtil.isNotBlank(field1Value.toString())) 
                && (field2Value == null || StringUtil.isEmpty(field2Value.toString())))
        {
            operation = Constant.STR_TWO;
            desc.append("{[")
                .append(fieldName)
                .append("]属性删除；")
                .append("被删除的值[")
                .append(field1Value)
                .append("]");
            //数据库中有，参数没有，删除
        }
        else if((field1Value != null && StringUtil.isNotBlank(field1Value.toString()))
                && (field2Value != null && StringUtil.isNotBlank(field2Value.toString()))
                && !field1Value.toString().equals(field2Value.toString()))
        {
            //两个值都不为并且值不相等---修改
            operation = Constant.STR_THREE;
            desc.append("{[")
                .append(fieldName)
                .append("]属性修改；[")
                .append(field1Value)
                .append("]修改为[")
                .append(field2Value)
                .append("]");
        }
        else
        {
            return null;
        }
        String description = desc.toString();
        Map<String, String> map = new HashMap<String, String>();
        map.put("operation", operation);
        map.put("description", description);
        return map;
    }
}
