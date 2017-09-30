package com.gooseeker.fss.commons.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

public class StringUtil
{
    public static boolean isNumerical(String value)
    {
        try
        {
            Integer.valueOf(value);
            return true;
        }
        catch (NumberFormatException e)
        {
            // e.printStackTrace();
        }
        return false;
    }

    public static boolean isNullOrTrimEmpty(String str)
    {
        return (str == null) || (str.isEmpty()) || (str.trim().isEmpty()) || ("null".equals(str));
    }

    public static boolean valueCompara(String str1, String str2)
    {
        if(isNullOrTrimEmpty(str1) && isNullOrTrimEmpty(str2))
        {
            return true;
        }
        if (!isNullOrTrimEmpty(str1) && str1.equals(str2))
        {
            return true;
        }
        
        if (!isNullOrTrimEmpty(str2) && str2.equals(str1))
        {
            return true;
        }
        
        return false;
    }

    public static boolean isNotBlank(String str)
    {

        if (StringUtils.isNotBlank(str) && !"null".equalsIgnoreCase(str)
                && StringUtils.isNotBlank(str.trim()))
        {
            return true;
        }

        return false;
    }

    public static boolean isEmpty(String str)
    {
        return !isNotBlank(str);
    }
    
    public static String removeAllHtmlElement(String input)
    {
        if ((input == null) || (input.trim().equals("")))
        {
            return "";
        }

        String str = input.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll(
                "<[^>]*>", "");
        str = str.replaceAll("[()><]", "");

        str = str.replaceFirst(":", "");
        str = removeScript(str);
        return str;
    }

    public static String encode(String text)
    {
        if ((text == null) || (text.isEmpty()))
        {
            return text;
        }
        try
        {
            text = URLEncoder.encode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
        }

        return text;
    }

    public static String decode(String text)
    {
        if ((text == null) || (text.isEmpty()))
        {
            return text;
        }
        try
        {
            text = URLDecoder.decode(text, "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    public static String removeAnnotate(String html)
    {
        String regex = "<!---*[^-]*-*>";
        html = html.replaceAll(regex, "");
        return html;
    }

    public static String removeScript(String html)
    {
        String regex = "<script[^>]*>[^<]+</script>";
        html = html.replaceAll(regex, "");
        return html;
    }

    public static String removeHtmlUseless(String html)
    {
        html = html.replace("&nbsp;", "");
        html = html.replace("&quot;", "");
        html = html.replace("&&amp;", "");
        html = html.replace("&gt;", "");
        return html;
    }
}
