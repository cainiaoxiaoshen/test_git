package com.gooseeker.fss.commons.utils;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gooseeker.fss.builder.AnnotateDocumentBuilder;
import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.entity.ExcelEntity;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;




/**
 * 
 * excel处理的工具类
 * 
 * @author  yangyang
 * @version  [版本号, 2016年7月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ExcelUtil<T>
{
    //日志
    private static Logger logger = Logger.getLogger(ExcelUtil.class);
    
    public void creatExcel(File file, List<ExcelEntity<T>> excelEntitys)
    {
        Writer fw = null;
        OutputStream out = null;
        FileOutputStream os = null;
        File tmpTemplate = null;
        File tmp = null;
        XSSFWorkbook wb = new XSSFWorkbook();
        ArrayList<String> sheets = new ArrayList<String>();
        List<String> sheetNames = new ArrayList<String>();
        ArrayList<File> tmpXmlFiles = new ArrayList<File>();
        
        try
        {
            for (int i = 0; i < excelEntitys.size(); i++)
            {
                ExcelEntity<T> excelEntity = excelEntitys.get(i);
                String sheetName = excelEntity.getSheetName();
                if(sheetName != null && sheetName != "")
                {
                    sheetNames.add(sheetName);
                }
                else
                {
                    sheetNames.add("sheet" + i);
                }
                //创建.xml文件把要导出的内容写入文件中
                tmp = File.createTempFile("sheet", ".xml");
                tmpXmlFiles.add(tmp);
                fw = new OutputStreamWriter(new FileOutputStream(tmp), "utf-8");
                // 在服务器上生成文件
                createExcel(wb, fw, excelEntity);
                // TODO
                fw.close();
                fw = null;
                out = new FileOutputStream(file);
            }
            sheets = createSheet(wb, sheetNames, sheets);
            //创建.xlsx临时空文件
            tmpTemplate = File.createTempFile("template", ".xlsx");
            os = new FileOutputStream(tmpTemplate);
            wb.write(os);
            // 文件替换
            substituteArrayXlsx(tmpTemplate, tmpXmlFiles, sheets, out);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            //删除临时文件
            if(tmpTemplate != null)
            {
                tmpTemplate.delete();
            }
            for (int i = 0; i < tmpXmlFiles.size(); i++)
            {
                if(tmpXmlFiles.get(i) != null)
                {
                    tmpXmlFiles.get(i).delete();
                }
            }
            try
            {
                if(out != null)
                {
                    out.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 构造excel
     * <功能详细描述>
     * @param fw
     * @param excelEntity
     * @see [类、类#方法、类#成员]
     */
    private void createExcel(XSSFWorkbook wb,  Writer fw, ExcelEntity<T> excelEntity)
    {
        try
        {
            SpreadsheetWriter sw = new SpreadsheetWriter(fw, "utf-8");
            sw.beginSheet();
            int rowNo = 0;
            if(excelEntity.getHeads() != null)
            {
                //构造excel头部
                createExcelHead(wb, sw, rowNo, excelEntity.getHeads());
                rowNo++;
            }
            if(excelEntity.getBodys() != null)
            {
                //构造excel内容
                createExcelBody(sw, rowNo, excelEntity.getBodys());
            }
            sw.endSheet();
        }
        catch (IOException e)
        {
            logger.info("构造excel异常");
        }
    }
    
    /**
     * 
     * 构造excel的头部
     * <功能详细描述>
     * @param sheet
     * @param rowNo
     * @param head
     * @see [类、类#方法、类#成员]
     */
    private static void createExcelHead(XSSFWorkbook wb, SpreadsheetWriter sheet, int rowNo, List<String> head)
    {
        try
        {
            //设置表头样式
            XSSFCellStyle style = wb.createCellStyle();
            style.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
            style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
            sheet.insertRow(rowNo);
            int colNo = 0;
            for (int i = 0; i < head.size(); i++)
            {
                sheet.createCell(colNo, String.valueOf(head.get(i)), style.getIndex());
                colNo++;
            }
            sheet.endRow();
        }
        catch (Exception e)
        {
            logger.error("构造excel头部异常");
        }
    }
    
    /**
     * excel内容构造
     * <功能详细描述>
     * @param sheet
     * @param rowNo
     * @param body，list中的Object可以是实体对象实体对象属性必须要有get方法，也可以是Map<String, Object>对象
     * @see [类、类#方法、类#成员]
     */
    private void createExcelBody(SpreadsheetWriter sheet, int rowNo, List<T> bodys)
    {
        try
        {
            for (Object obj : bodys)
            {
                sheet.insertRow(rowNo);
                int colNo = 0;
                //对象为实体对象时，通过反射机制得到对象属性的get方法取到属性的值
                Class clazz = obj.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields)
                {
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);
                    Method method = pd.getReadMethod();//获得读方法
                    String value = String.valueOf(method.invoke(obj));
                    sheet.createCell(colNo, value, -1);
                    colNo++;
                }
                sheet.endRow();
                rowNo++;
            }
        }
        catch(Exception e)
        {
            logger.error("构造excel内容异常");
        }
    }
    
    /**
     * 
     * 创建excel的sheet页
     * <功能详细描述>
     * @param wb
     * @param sheetNames
     * @param sheets
     * @return
     * @see [类、类#方法、类#成员]
     */
    private static ArrayList<String> createSheet(XSSFWorkbook wb, List<String> sheetNames, ArrayList<String> sheets)
    {
        //创建sheet
        if(sheetNames != null && sheetNames.size() > 0)
        {
            for (int i = 0; i < sheetNames.size(); i++)
            {
                XSSFSheet xssSheet = wb.createSheet(sheetNames.get(i));
                String sheet = xssSheet.getPackagePart().getPartName().getName();
                sheets.add(sheet.substring(1));
            }
        }
        else
        {
            XSSFSheet xssSheet = wb.createSheet();
            String sheet = xssSheet.getPackagePart().getPartName().getName();
            sheets.add(sheet.substring(1));
        }
        return sheets;
    }
    
    /**
     * 
     * <一句话功能简述>
     * <功能详细描述>
     * @param tmpTemplate .xlsx临时文件
     * @param tmpXmlFiles .xml临时文件
     * @param sheets sheet信息
     * @param out
     * @see [类、类#方法、类#成员]
     */
    private void substituteArrayXlsx(File tmpTemplate, ArrayList<File> tmpXmlFiles, ArrayList<String> sheets, OutputStream out)
    {
        ZipOutputStream zos = null;
        InputStream is = null;
        try
        {
            ZipFile zip = new ZipFile(tmpTemplate);
            zos = new ZipOutputStream(out);
            Enumeration<ZipEntry> en = (Enumeration<ZipEntry>) zip.entries();

            while (en.hasMoreElements())
            {
                ZipEntry ze = en.nextElement();
                boolean findEntry = false;
                int pos = 0;
                for (int i = 0; i < sheets.size(); i++)
                {
                    if (ze.getName().equals(sheets.get(i)))
                    {
                        findEntry = true;
                        pos = i;
                        break;
                    }
                }
                if (!findEntry)
                {
                    zos.putNextEntry(new ZipEntry(ze.getName()));
                    is = zip.getInputStream(ze);
                    copyStream(is, zos);
                    is.close();
                }
                else
                {
                    zos.putNextEntry(new ZipEntry(sheets.get(pos)));
                    is = new FileInputStream(tmpXmlFiles.get(pos));
                    copyStream(is, zos);
                    is.close();
                }
            }
            zos.close();
            zos = null;
        }
        catch (Exception e)
        {
            try
            {
                if (is != null)
                {
                    is.close();
                }
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }
            try
            {
                if (zos != null)
                {
                    zos.close();
                }
            }
            catch (Exception ee)
            {
                ee.printStackTrace();
            }

        }

    }
    
    /**
     * 
     * 复制流
     * <功能详细描述>
     * @param in
     * @param out
     * @see [类、类#方法、类#成员]
     */
    private void copyStream(InputStream in, OutputStream out)
    {
        try
        {
            byte[] chunk = new byte[1024];
            int count;
            while ((count = in.read(chunk)) >= 0)
            {
                out.write(chunk, 0, count);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    //---------------------excel 文件解析----------------------
    
    public static List<AnnotateDocument> parseToDocument(InputStream input, String user, String type) throws InvalidFormatException, IOException
    {
        Timestamp createTime = new Timestamp(System.currentTimeMillis());
        List<AnnotateDocument> datas = new ArrayList<AnnotateDocument>();
        Workbook workbook = WorkbookFactory.create(input);
        //遍历每一个sheet页去除里面的数据
        for (int i = 0; i < workbook.getNumberOfSheets(); i++)
        {
            Sheet sheet = workbook.getSheetAt(i);
            if(sheet != null)
            {
                //起始行
                int startRowNum = sheet.getFirstRowNum();
                //最后行
                int endRowNum = sheet.getLastRowNum();
                if(endRowNum > Constant.MAX_IMPORT_NUM)
                {
                    endRowNum = Constant.MAX_IMPORT_NUM;
                }
                //遍历每一行
                for (int rowNum = startRowNum; rowNum <= endRowNum; rowNum++)
                {
                    //标准文档编号
                    String standardNo = null;
                    String reqUrl = null;
                    Row row = sheet.getRow(rowNum);
                    if (row == null)
                    {
                        continue;
                    }
                    //起始列
                    int startCellNum = row.getFirstCellNum();
                    //最后一列
                    int endCellNum = row.getLastCellNum();
                    //遍历每一列，找出2列有值的分别赋给standardNo和reqUrl
                    for (int cellNum = startCellNum; cellNum < endCellNum; cellNum++)
                    {
                        Cell cell = row.getCell(cellNum);
                        if(cell != null)
                        {
                            String value = getCellValue(cell);
                            if(StringUtil.isNotBlank(value))
                            {
                                if(standardNo == null)
                                {
                                    standardNo = value;
                                }
                                if(standardNo != null && reqUrl == null)
                                {
                                    if(isUrl(value))
                                    {
                                        reqUrl = value;
                                    }
                                }
                                if(standardNo != null && reqUrl != null)
                                {
                                    AnnotateDocument doc = AnnotateDocumentBuilder.builder(user, standardNo, reqUrl, 
                                        type, DocumentPathBuilder.DOC_FORMAT_HTML, createTime, false);
                                    datas.add(doc);
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }
        return datas;
    }
    private static String getCellValue(Cell cell)
    {
        int type = cell.getCellType();
        Object value = null;
        switch (type)
        {
            case Cell.CELL_TYPE_NUMERIC:// 数值、日期类型
                value = subZeroAndDot(String.valueOf(cell.getNumericCellValue()));
                if (HSSFDateUtil.isCellDateFormatted(cell))
                {   // 日期类型
                    value = "";
                }
            break;
            case Cell.CELL_TYPE_BLANK:// 空白单元格
                value = "";
            break;
            case Cell.CELL_TYPE_STRING:// 字符类型
                value = cell.getStringCellValue();
            break;
            case Cell.CELL_TYPE_BOOLEAN:// 布尔类型
                value = cell.getBooleanCellValue();
            break;
            case Cell.CELL_TYPE_ERROR: // 故障
            break;
            default:
                value = cell.getStringCellValue();
            break;
        }
        if(value != null)
        {
            return String.valueOf(value).trim();
        }
        return null;
    }  
    /**  
     * 使用java正则表达式去掉多余的.与0  
     * @param s  
     * @return   
     */    
    private static String subZeroAndDot(String s)
    {    
        if(s.indexOf(".") > 0){    
            s = s.replaceAll("0+?$", "");//去掉多余的0    
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉    
        }    
        return s;    
    }
    
    private static boolean isUrl(String reqUrl)
    {
        if(reqUrl.indexOf("http://") != -1 || reqUrl.indexOf("https://") != -1
            || reqUrl.indexOf("ftp://") != -1 || reqUrl.indexOf("file://") != -1
            || reqUrl.indexOf("about://") != -1 || reqUrl.indexOf("chrome://") != -1)
        {
            return true;
        }
        return false;
    }
}
