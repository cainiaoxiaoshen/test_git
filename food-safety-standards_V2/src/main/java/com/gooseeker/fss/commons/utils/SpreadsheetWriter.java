package com.gooseeker.fss.commons.utils;

import java.io.IOException;
import java.io.Writer;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.poi.ss.util.CellReference;

public class SpreadsheetWriter
{
    private Writer out;
    private int rownum;
    private String encoding;

    public SpreadsheetWriter(Writer out, String encoding)
    {
        this.out = out;
        this.encoding = encoding;
    }

    public void beginSheet() throws IOException
    {
        out.write("<?xml version=\"1.0\" encoding=\""
                + encoding
                + "\"?>"
                + "<worksheet xmlns=\"http://schemas.openxmlformats.org/spreadsheetml/2006/main\">");
        out.write("<sheetData>\n");
    }

    public void endSheet() throws IOException
    {
        out.write("</sheetData>");
        out.write("</worksheet>");
    }

    /**
     * Insert a new row
     *
     * @param rownum
     *            0-based row number
     */
    public void insertRow(int rownum) throws IOException
    {
        out.write("<row r=\"" + (rownum + 1) + "\">\n");
        this.rownum = rownum;
    }

    /**
     * Insert row end marker
     */
    public void endRow() throws IOException
    {
        out.write("</row>\n");
    }

    public void createCell(int columnIndex, String value, int styleIndex)
            throws IOException
    {
        String ref = new CellReference(rownum, columnIndex).formatAsString();
        out.write("<c r=\"" + ref + "\" t=\"inlineStr\"");
        if (styleIndex != -1)
            out.write(" s=\"" + styleIndex + "\"");
        out.write(">");
        out.write("<is><t>" + StringEscapeUtils.escapeXml(value) + "</t></is>");
        out.write("</c>");
    }

    public void createCell(int columnIndex, double value, int styleIndex)
            throws IOException
    {
        String ref = new CellReference(rownum, columnIndex).formatAsString();
        out.write("<c r=\"" + ref + "\" t=\"n\"");
        if (styleIndex != -1)
            out.write(" s=\"" + styleIndex + "\"");
        out.write(">");
        out.write("<v>" + value + "</v>");
        out.write("</c>");
    }
}