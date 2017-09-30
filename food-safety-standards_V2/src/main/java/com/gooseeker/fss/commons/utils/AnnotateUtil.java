package com.gooseeker.fss.commons.utils;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gooseeker.fss.commons.entity.AnnotatePropertyDictionary;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.exception.FssViewException;
import com.gooseeker.fss.entity.Annotate;
import com.gooseeker.fss.entity.AnnotateXpath;

public class AnnotateUtil
{

    protected static Logger logger = Logger.getLogger(AnnotateUtil.class);

    /**
     * AnnotateXpath对象转化成json对象
     * 
     * @param annotateXpath
     * @return
     */
    private static JSONObject annotateXpathToJson(AnnotateXpath annotateXpath)
    {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("xpath", annotateXpath.getXpath());
        jsonObject.put("text", annotateXpath.getText());
        jsonObject.put("start", annotateXpath.getStart());
        jsonObject.put("length", annotateXpath.getLength());
        jsonObject.put("index", annotateXpath.getIndex());
        jsonObject.put("type", annotateXpath.getType());
        jsonObject.put("product", annotateXpath.getProduct());
        jsonObject.put("factor", annotateXpath.getFactor());
        jsonObject.put("updatedText", annotateXpath.getUpdatedText());
        jsonObject.put("updated", annotateXpath.getUpdated());
        return jsonObject;
    }
    
    /**
     * 把查出的数据构造markJson形式的数据
     * 
     * @param annotateXpaths
     *            查出的已经打好标的xpath信息
     * @return
     */
    public static JSONArray constructMarkJson(List<Annotate> annotates)
    {
        JSONArray markJsonArray = new JSONArray();
        for (int i = 0; i < annotates.size(); i++)
        {
            Annotate annotate = annotates.get(i);
            
            JSONObject jsonObject = new JSONObject();
            
            List<AnnotateXpath> xpaths = annotate.getAnnotateXpaths();
            
            // 获取对象的class
            Class<?> clazz1 = Annotate.class;
            // 获取对象的属性列表
            Field[] field1 = clazz1.getDeclaredFields();
            // 遍历属性列表field1
            for (int j = 0; j < field1.length; j++)
            {
                // 遍历属性列表field2
                String fieldName = field1[j].getName();
                if(AnnotatePropertyDictionary.isBasicProperty(fieldName))
                {
                    AnnotateXpath annotateXpath = getFieldXpathValue(xpaths, fieldName);
                    JSONObject value;
                    if(annotateXpath == null)
                    {
                        String text = getFieldValue(annotate, fieldName);
                        if(StringUtil.isEmpty(text))
                        {
                            jsonObject.put(fieldName, Constant.STR_EMPTY);
                        }
                        else
                        {
                            String type = AnnotatePropertyDictionary.getTypeByAttribute(fieldName);
                            annotateXpath = new AnnotateXpath(text, false, Integer.valueOf(type));
                            value = annotateXpathToJson(annotateXpath);
                            jsonObject.put(fieldName, value);
                        }
                        
                    }
                    else
                    {
                        value = annotateXpathToJson(annotateXpath);
                        jsonObject.put(fieldName, value);
                    }
                    
                }
            }
            jsonObject.put("page", annotate.getPage());
            markJsonArray.put(jsonObject);
        }
        return markJsonArray;
    }
    
    private static AnnotateXpath getFieldXpathValue(List<AnnotateXpath> xpaths, String fieldName)
    {
        AnnotateXpath annotateXpath = null;
        String type = AnnotatePropertyDictionary.getTypeByAttribute(fieldName);
        for (int i = 0; i < xpaths.size(); i++)
        {
            AnnotateXpath xpath = xpaths.get(i);
            if(Integer.valueOf(type) == xpath.getType())
            {
                annotateXpath = xpath;
                break;
            }
        }
        return annotateXpath;
    }

    /**
     * 从请求的body里面得到传过来的参数数据并解析
     * 
     * @param request
     * @param docId
     * @param user
     * @return
     * @throws Exception 
     */
    public static Map<String, Object> getMarkDatasFromRequest(HttpServletRequest request,
            long docId, String user) throws Exception
    {
        String text = requestDataResolve(request);
        logger.info("接收到要保存的打标信息: " + text);
        return MarkJsonDatasToAnnotateObj(text, docId, user);
    }
    
    /**
     * 解析请求的数据
     * @param request
     * @return
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public static String requestDataResolve(HttpServletRequest request) throws IOException
    {
        BufferedReader reader;
        StringBuffer buffer = new StringBuffer();
        reader = request.getReader();
        String str;
        while ((str = reader.readLine()) != null)
        {
            buffer.append(str);
        }
        return buffer.toString();
    }

    /**
     * 解析参数封装成Annotate和AnnotateXpath对象
     * 
     * @param markJsonDatas
     * @param docId
     * @param user
     * @return
     * @throws Exception 
     */
    public static Map<String, Object> MarkJsonDatasToAnnotateObj(String markJsonDatas, long docId, String user) throws Exception
    {
        Annotate annotate = null;
        List<AnnotateXpath> annotateXpathList = new ArrayList<AnnotateXpath>();
        JSONObject jsonObj = new JSONObject(markJsonDatas);
        // json对象中annotates内容为json数组
        Object antObj = jsonObj.get("annotates");
        if (antObj != null)
        {
            JSONArray antsObjArray = new JSONArray(antObj.toString());
            JSONObject antJsonObj = new JSONObject(antsObjArray.get(0).toString());
            annotate = JsonToAnnotate(antJsonObj, docId, user);
        }
        Object antXpathObj = jsonObj.get("quotaXpaths");
        if (antXpathObj != null)
        {
            JSONArray antXpathsArray = new JSONArray(antXpathObj.toString());
            for (int i = 0; i < antXpathsArray.length(); i++)
            {
                JSONObject antXpathJsonObj = new JSONObject(antXpathsArray.get(i).toString());
                //转换成AnnotateXpath对象
                AnnotateXpath annotateXpath = JsonToAnnotateXpath(antXpathJsonObj, docId);
                if (annotateXpath != null)
                {
                    annotateXpathList.add(annotateXpath);
                }
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        if (annotate == null || (annotateXpathList.size() < 2))
        {
            logger.error("json 数据格式错误");
            throw new FssViewException("json 数据格式错误");
        }
        else
        {
            map.put("annotate", annotate);
            map.put("xpathList", annotateXpathList);
        }
        return map;
    }

    /**
     * json对象转化为AnnotateXpath对象
     * 
     * @param antXpathJsonObj
     * @param docId
     * @return
     * @throws Exception 
     */
    private static AnnotateXpath JsonToAnnotateXpath(JSONObject antXpathJsonObj, long docId) throws Exception
    {
        String xpath = antXpathJsonObj.getString("xpath");
        String text = antXpathJsonObj.getString("text");
        String product = antXpathJsonObj.getString("product");
        String factor = antXpathJsonObj.getString("factor");
        if (StringUtil.isNotBlank(xpath) && StringUtil.isNotBlank(text)
                && StringUtil.isNotBlank(product)
                && StringUtil.isNotBlank(factor))
        {
            String start = antXpathJsonObj.getString("start");
            int length = antXpathJsonObj.getInt("length");
            int type = antXpathJsonObj.getInt("type");
            String updatedText = antXpathJsonObj.getString("updatedText");
            Object updated = antXpathJsonObj.get("updated");
            String textIndex = antXpathJsonObj.getString("textIndex");

            AnnotateXpath annotateXpath = new AnnotateXpath();
            annotateXpath.setDocId(docId);
            annotateXpath.setXpath(xpath);
            annotateXpath.setText(text);
            annotateXpath.setProduct(product);
            annotateXpath.setFactor(factor);
            annotateXpath.setStart(start);
            annotateXpath.setLength(length);
            annotateXpath.setType(type);
            annotateXpath.setUpdatedText(updatedText);
            annotateXpath.setUpdated(Boolean.valueOf(String
                    .valueOf(updated)));
            annotateXpath.setIndex(textIndex);
            return annotateXpath;
        }
        else
        {
            return null;
        }
    }

    private static Annotate JsonToAnnotate(JSONObject antJsonObj, long docId, String user)
    {
        String product = antJsonObj.getString("product");
        String factor = antJsonObj.getString("substance");
        if (StringUtil.isNotBlank(product) && StringUtil.isNotBlank(factor))
        {
            String unit = antJsonObj.getString("unit");
            String max = antJsonObj.getString("max");
            String min = antJsonObj.getString("min");
            String adi = antJsonObj.getString("adi");
            String adiWeb = antJsonObj.getString("adiWeb");
            String cns = antJsonObj.getString("cns");
            String ins = antJsonObj.getString("ins");
            String cas = antJsonObj.getString("cas");
            String struc = antJsonObj.getString("struc");

            String mole = antJsonObj.getString("mole");
            String property = antJsonObj.getString("property");
            String toxico = antJsonObj.getString("toxico");
            String biological = antJsonObj.getString("biological");
            String funct = antJsonObj.getString("funct");
            String disease = antJsonObj.getString("disease");

            String proremark = antJsonObj.getString("proremark");
            String facremark = antJsonObj.getString("facremark");
            String testsId = antJsonObj.getString("testsId");
            String prostd = antJsonObj.getString("prostd");
            int page = antJsonObj.getInt("page");

            Annotate annotate = new Annotate();
            annotate.setCreateDate(new Timestamp(System.currentTimeMillis()));
            annotate.setDocId(docId);
            annotate.setUser(user);
            annotate.setProduct(product);
            annotate.setFactor(factor);
            annotate.setMax(max);
            annotate.setMin(min);
            annotate.setUnit(unit);

            annotate.setAdi(adi);
            annotate.setAdiWeb(adiWeb);
            annotate.setCns(cns);
            annotate.setIns(ins);
            annotate.setCas(cas);
            annotate.setStruc(struc);

            annotate.setMole(mole);
            annotate.setProperty(property);
            annotate.setToxico(toxico);
            annotate.setBiological(biological);
            annotate.setFunct(funct);
            annotate.setDisease(disease);

            annotate.setProremark(proremark);
            annotate.setFacremark(facremark);
            annotate.setTestsId(testsId);
            annotate.setProstd(prostd);
            annotate.setPage(page);

            return annotate;
        }
        return null;
    }
    
    /**
     * 通过对象的属性名得到该属性的值
     * @param obj
     * @param filed
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getFieldValue(Object obj, String filed) {
        try 
        {  
            Class clazz = obj.getClass();  
            PropertyDescriptor pd = new PropertyDescriptor(filed, clazz);
            Method getMethod = pd.getReadMethod();//获得get方法  
            if (pd != null) {  
                Object o = getMethod.invoke(obj);//执行get方法返回一个Object
                if(o != null)
                {
                    return o.toString();
                }
            }  
        } 
        catch (Exception e)
        {  
            e.printStackTrace();  
        }
        return Constant.STR_EMPTY;
    }  
}
