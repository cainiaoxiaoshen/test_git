package com.gooseeker.fss.commons.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.exception.FssViewException;

public class FileUtil
{

    /**
     * 
     * @param zipFilePath zip文件所在的路径
     * @param destDir 解压的路径
     * @throws FssViewException
     */
    public static void unZip(String zipFilePath, String destDir) throws FssViewException
    {
        ZipFile zipFile = null;
        byte[] buffer = new byte[1024];
        try
        {
            if (System.getProperty("os.name").toLowerCase().indexOf("windows") >= 0)
            {
                zipFile = new ZipFile(zipFilePath, "GBK");
            }
            else if (System.getProperty("os.name").toLowerCase().indexOf("linux") >= 0)
            {
                zipFile = new ZipFile(zipFilePath, "UTF-8");
            }
            Enumeration<?> enu = zipFile.getEntries();
            while (enu.hasMoreElements())
            {
                ZipEntry entry = (ZipEntry) enu.nextElement();
                String fileName = entry.getName().replace('\\', '/');
                File newFile = new File(destDir + File.separator + fileName);
                if (entry.isDirectory())
                {
                    // 是目录，则创建之
                    newFile.mkdir();
                }
                else
                {
                    InputStream inputStream = null;
                    FileOutputStream fos = null;
                    try
                    {
                        // 是文件
                        new File(newFile.getParent()).mkdirs();
                        inputStream = zipFile.getInputStream(entry);
                        fos = new FileOutputStream(newFile);
                        int len;
                        while ((len = inputStream.read(buffer)) > 0)
                        {
                            fos.write(buffer, 0, len);
                        }
                    }
                    catch (IOException e)
                    {
                        throw new FssViewException("文件解压出错");
                    }
                    finally
                    {
                        fos.close();
                        inputStream.close();
                    }
                }
            }

        }
        catch (IOException e)
        {
            throw new FssViewException("文件解压出错");
        }
        finally
        {
            try
            {
                zipFile.close();
            }
            catch (IOException e)
            {
                throw new FssViewException("ZipFile关闭出错");
            }
        }
    }

    /**
     * 得到文件夹里面的所有文件，此文件夹中不包含其他文件夹
     * 
     * @param path
     * @return
     */
    public static List<File> getDirectoryAllFile(String path)
    {
        List<File> files = new ArrayList<File>();
        File file = new File(path);
        if (file != null && file.exists() && file.isDirectory())
        {
            String[] tempList = file.list();
            File temp = null;
            for (int i = 0; i < tempList.length; i++)
            {
                if (path.endsWith(File.separator))
                {
                    temp = new File(path + tempList[i]);
                }
                else
                {
                    temp = new File(path + File.separator + tempList[i]);
                }
                if (temp.isFile())
                {
                    files.add(temp);
                }
            }
        }
        return files;
    }
    
    /**
     * 
     * 文件下载
     * <功能详细描述>
     * @param response
     * @param request
     * @param file 下载的目标文件
     * @param contentType
     * @see [类、类#方法、类#成员]
     */
    public static void download(HttpServletResponse response, HttpServletRequest request, File file, String contentType)
    {
        OutputStream out = null;
        BufferedInputStream input = null;
        try
        {
            String fileName = toUtf8String(request, file.getName());
            response.addHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType(contentType);
            out = response.getOutputStream();
            input = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            while ((len = input.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            out.flush();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                out.close();
                input.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /** 
     * 根据不同浏览器将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名. 
     * @param s 原文件名       
     * @return 重新编码后的文件名 
     */ 
    private static String toUtf8String(HttpServletRequest request, String s)
    {
        String agent = request.getHeader("User-Agent");
        try
        {
            boolean isFireFox = (agent != null && agent.toLowerCase().indexOf("firefox") != -1);
            if (isFireFox)
            {
                s = new String(s.getBytes("UTF-8"), "ISO8859-1");
            }
            else
            {
                s = toUtf8String(s);
                if ((agent != null && agent.indexOf("MSIE") != -1))
                {
                    if (s.length() > 150)
                    {
                        // 根据request的locale 得出可能的编码
                        s = new String(s.getBytes("UTF-8"), "ISO8859-1");
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return s;
    }
    
    private static String toUtf8String(String s)
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255)
            {
                sb.append(c);
            }
            else
            {
                byte[] b;
                try
                {
                    b = Character.toString(c).getBytes("utf-8");
                }
                catch (Exception ex)
                {
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++)
                {
                    int k = b[j];
                    if (k < 0)
                        k += 256;
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
    }
    
    public static boolean deleteDirectory(File dir)
    {
        if (dir.isDirectory())
        {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++)
            {
                boolean success = deleteDirectory(new File(dir, children[i]));
                if (!success)
                {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
