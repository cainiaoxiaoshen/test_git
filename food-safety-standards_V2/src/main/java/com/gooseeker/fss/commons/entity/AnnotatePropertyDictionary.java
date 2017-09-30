package com.gooseeker.fss.commons.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotatePropertyDictionary
{
    private static Map<String, String[]> attrs = new HashMap<String, String[]>();
    
    /**
     * 属性名称列表
     */
    private static List<String> attrNamelist = new ArrayList<String>();
    
    /**
     * 对外显示列表
     */
    private static List<String> outNamelist = new ArrayList<String>();
    
    static
    {
        initDic();
    }
    
    public static String getNameByAttribute(String attr)
    {
        String[] values = attrs.get(attr);
        if(values != null && values.length == 3)
        {
            return values[1];
        }
        return null;
    }
    
    public static String getTypeByAttribute(String attr)
    {
        String[] values = attrs.get(attr);
        if(values != null && values.length == 3)
        {
            return values[2];
        }
        return null;
    }
    
    public static List<String> getAttrNamelist()
    {
        return attrNamelist;
    }
    
    public static List<String> getOutNamelist()
    {
        return outNamelist;
    }
    
    public static List<String> getOutNamelistWithOutPage()
    {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < outNamelist.size(); i++)
        {
            String value = outNamelist.get(i);
            if(!"页码数".equals(value))
            {
                list.add(value);
            }
        }
        return list;
    }
    
    private static void initDic()
    {
        //属性，属性名
        String[] productAttr = {"product", "产品", "1"};
        attrs.put("product", productAttr);
        attrNamelist.add("product");
        outNamelist.add("产品");
        
        String[] factorAttr = {"factor", "指标" , "2"};
        attrs.put("factor", factorAttr);
        attrNamelist.add("factor");
        outNamelist.add("指标");
        
        String[] maxAttr = {"max", "最大限值", "3"};
        attrs.put("max", maxAttr);
        attrNamelist.add("max");
        outNamelist.add("最大限值");

        String[] minAttr = {"min", "最小限值", "4"};
        attrs.put("min", minAttr);
        attrNamelist.add("min");
        outNamelist.add("最小限值");

        String[] unitAttr = {"unit", "单位", "5"};
        attrs.put("unit", unitAttr);
        attrNamelist.add("unit");
        outNamelist.add("单位");

        String[] adiAttr = {"adi", "ADI", "6"};
        attrs.put("adi", adiAttr);
        attrNamelist.add("adi");
        outNamelist.add("ADI");
        
        String[] adiWebAttr = {"adiWeb", "AdiWeb", "7"};
        attrs.put("adiWeb", adiWebAttr);
        attrNamelist.add("adiWeb");
        outNamelist.add("AdiWeb");

        String[] cnsAttr = {"cns", "CNS", "8"};
        attrs.put("cns", cnsAttr);
        attrNamelist.add("cns");
        outNamelist.add("CNS");

        String[] insAttr = {"ins", "INS", "9"};
        attrs.put("ins", insAttr);
        attrNamelist.add("ins");
        outNamelist.add("INS");

        String[] casAttr = {"cas", "CAS", "10"};
        attrs.put("cas", casAttr);
        attrNamelist.add("cas");
        outNamelist.add("CAS");

        String[] strucAttr = {"struc", "结构方程式", "11"};
        attrs.put("struc", strucAttr);
        attrNamelist.add("struc");
        outNamelist.add("结构方程式");

        String[] moleAttr = {"mole", "摩尔方程式", "12"};
        attrs.put("mole", moleAttr);
        attrNamelist.add("mole");
        outNamelist.add("摩尔方程式");
        
        String[] propertyAttr = {"property", "属性备注", "13"};
        attrs.put("property", propertyAttr);
        attrNamelist.add("property");
        outNamelist.add("属性备注");

        String[] toxicoAttr = {"toxico", "毒理学指标", "14"};
        attrs.put("toxico", toxicoAttr);
        attrNamelist.add("toxico");
        outNamelist.add("毒理学指标");

        String[] biologicalAttr = {"biological", "生物学指标", "15"};
        attrs.put("biological", biologicalAttr);
        attrNamelist.add("biological");
        outNamelist.add("生物学指标");

        String[] functAttr = {"funct", "功能", "16"};
        attrs.put("funct", functAttr);
        attrNamelist.add("funct");
        outNamelist.add("功能");

        String[] diseaseAttr = {"disease", "病理学指标", "17"};
        attrs.put("disease", diseaseAttr);
        attrNamelist.add("disease");
        outNamelist.add("病理学指标");

        String[] proremarkAttr = {"proremark", "产品备注", "18"};
        attrs.put("proremark", proremarkAttr);
        attrNamelist.add("proremark");
        outNamelist.add("产品备注");
        
        String[] facremarkAttr = {"facremark", "物质备注", "19"};
        attrs.put("facremark", facremarkAttr);
        attrNamelist.add("facremark");
        outNamelist.add("物质备注");
        
        String[] prostdAttr = {"prostd", "产品标准", "20"};
        attrs.put("prostd", prostdAttr);
        attrNamelist.add("prostd");
        outNamelist.add("产品标准");
        
        String[] testsIdAttr = {"testsId", "检测标准", "21"};
        attrs.put("testsId", testsIdAttr);
        attrNamelist.add("testsId");
        outNamelist.add("检测标准");
        
        String[] createDateAttr = {"createDate", "创建时间", "-1"};
        attrs.put("createDate", createDateAttr);
        attrNamelist.add("createDate");
        outNamelist.add("创建时间");
        
        String[] userAttr = {"user", "创建者", "-1"};
        attrs.put("user", userAttr);
        attrNamelist.add("user");
        outNamelist.add("创建者");
        
        String[] pageAttr = {"page", "页码数", "-1"};
        attrs.put("page", pageAttr);
        attrNamelist.add("page");
        outNamelist.add("页码数");
    }
    
    /**
     * 检查是否为基本属性
     * @param property
     * @return
     */
    public static boolean isBasicProperty(String property)
    {
        if(!"id".equals(property) && !"docId".equals(property)
                && !"page".equals(property) && !"createDate".equals(property)
                && !"user".equals(property) && !"annotateXpaths".equals(property))
        {
            return true;
        }
        return false;
    }
}
