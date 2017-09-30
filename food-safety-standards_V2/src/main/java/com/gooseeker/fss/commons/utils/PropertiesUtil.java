package com.gooseeker.fss.commons.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 通过key值读取.properties文件中的value值
 * 
 * @author  yangyang
 * @version  [版本号, 2015年5月22日]
 */
public class PropertiesUtil
{
    static Properties properties;
    static
    {
        InputStream in = PropertiesUtil.class.getResourceAsStream("/parameters-config.properties");
        try
        {
            properties = new Properties();
            properties.load(in);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                in.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        
    }
   
    public static String getProperty(String key)
    {
        return properties.getProperty(key).trim();
    }
    
    public static int getIntProperty(String key)
    {
        String value = getProperty(key);
        return Integer.parseInt(value);
    }
}
