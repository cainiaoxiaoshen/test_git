package com.gooseeker.fss.entity;

public class PreselectionWords
{
    private long id;
    //关键词
    private String word;
    //状态
    private int state;
    //词频
    private int frequency;
    
    public PreselectionWords() {}
    
    public PreselectionWords(String word, int state, int frequency)
    {
        this.word = word;
        this.state = state;
        this.frequency = frequency;
    }
    
    public long getId()
    {
        return id;
    }
    public void setId(long id)
    {
        this.id = id;
    }
    public String getWord()
    {
        return word;
    }
    public void setWord(String word)
    {
        this.word = word;
    }
    public int getState()
    {
        return state;
    }
    public void setState(int state)
    {
        this.state = state;
    }
    public int getFrequency()
    {
        return frequency;
    }
    public void setFrequency(int frequency)
    {
        this.frequency = frequency;
    }
    
    
}
