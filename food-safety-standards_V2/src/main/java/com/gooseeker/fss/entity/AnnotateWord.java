package com.gooseeker.fss.entity;

public class AnnotateWord
{
    private long id;
    private String annotateWord;
    private String version;
    
    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public String getAnnotateWord()
    {
        return annotateWord;
    }

    public void setAnnotateWord(String annotateWord)
    {
        this.annotateWord = annotateWord;
    }

    public String getVersion()
    {
        return version;
    }

    public void setVersion(String version)
    {
        this.version = version;
    }

}
