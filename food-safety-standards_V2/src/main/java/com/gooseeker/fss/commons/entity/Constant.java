package com.gooseeker.fss.commons.entity;

public class Constant
{

    /**
     * 每页显示条数
     */
    public static final int PAGE_LIMIT = 2;
    
    /**
     * 打标结果每页显示的条数
     */
    public static final int TAG_RESULT_PAGE_NUM = 10;
    
    /**
     * excel导入最大的条数
     */
    public static final int MAX_IMPORT_NUM = 10000;

    /**
     * 管理员
     */
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    /**
     * 打标员
     */
    public static final String ROLE_ANNOTATE = "ROLE_ANNOTATE";

    /**
     * 审核
     */
    public static final String ROLE_CHECK = "ROLE_CHECK";

    /**
     * 默认的页数
     */
    public static final int PAGE_NUM = 1;
    
    /**
     * 空字符串
     */
    public static final String STR_EMPTY = "";

    /**
     * 字符串0
     */
    public static final String STR_ZERO = "0";

    /**
     * 字符串1/标准
     */
    public static final String STR_ONE = "1";
    
    /**
     * 字符串2/法规
     */
    public static final String STR_TWO = "2";
    
    /**
     * 字符串3/报告
     */
    public static final String STR_THREE = "3";

    /**
     * 字符串-1
     */
    public static final String STR_LESS_ONE = "-1";

    /**
     * 角色等级ADMIN
     */
    public static final String ROLE_LV_STR_ADMIN = "ADMIN";

    /**
     * 角色等级ANNOTATE
     */
    public static final String ROLE_LV_STR_ANNOTATE = "ANNOTATE";

    /**
     * 角色等级CHECK
     */
    public static final String ROLE_LV_STR_CHECK = "CHECK";

    /**
     * 角色等级ANNOTATECHECK
     */
    public static final String ROLE_LV_STR_ANNOTATECHECK = "ANNOTATECHECK";

    /**
     * 打标中状态
     */
    public static final int TAG_STATE_TAGING = 1;

    /**
     * 未打标
     */
    public static final int TAG_STATE_START = 2;

    /**
     * 一审
     */
    public static final int TAG_STATE_FCHECK = 3;

    /**
     * 二审
     */
    public static final int TAG_STATE_SCHECK = 4;

    /**
     * 入库
     */
    public static final int TAG_STATE_FNISH = 5;

    /**
     * 成功标志
     */
    public static final String SUCCESS_FLAG = "success";
    
    /**
     * 错误标志
     */
    public static final String ERROR_FLAG = "error";
    
    /**
     * 一级审核标志
     */
    public static final int CHECK_GRADE_LV1 = 1;
    
    /**
     * 二级审核标志
     */
    public static final int CHECK_GRADE_LV2 = 2;
    
    /**
     * 转义符 #&&$
     */
    public static final String ESCAPE_COMMA = "#&&$";
    
    /**
     * excel 格式 xls
     */
    public static final String EXCEL_FORMAT_XLS = "xls";
    
    /**
     * excel 格式 xlsx
     */
    public static final String EXCEL_FORMAT_XLSX = "xlsx";
    
    /**
     * 分割符
     */
    public static final String SPLIT_COMMA = ",";
}
