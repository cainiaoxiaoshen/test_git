package com.gooseeker.fss.entity;

public class AnnotateXpath {
    private Long id;
    private long docId;
    private String product;
    private String factor;
    private String xpath;
    private String text;
    private String start;
    private int length;
    private int type;//1：产品，2：物质，3：其他
    private String index;
    private String updatedText;
    private Boolean updated;
    
    public AnnotateXpath()
    {
        
    }
    
    public AnnotateXpath(String text, Boolean updated, int type)
    {
        this.text = text;
        this.updated = updated;
        this.type = type;
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    
    public long getDocId()
    {
        return docId;
    }

    public void setDocId(long docId)
    {
        this.docId = docId;
    }

    public String getProduct() {
        return product;
    }
    public void setProduct(String product) {
        this.product = product;
    }
    public String getFactor() {
        return factor;
    }
    public void setFactor(String factor) {
        this.factor = factor;
    }
    public String getXpath() {
        return xpath;
    }
    public void setXpath(String xpath) {
        this.xpath = xpath;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;
    }
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getIndex() {
        return index;
    }
    public void setIndex(String index) {
        this.index = index;
    }
    public String getUpdatedText() {
        return updatedText;
    }
    public void setUpdatedText(String updatedText) {
        this.updatedText = updatedText;
    }
    public Boolean getUpdated() {
        return updated;
    }
    public void setUpdated(Boolean updated) {
        this.updated = updated;
    }
    
    @Override
    public String toString() {
        return "AnnotateXpath [id=" + id + ", docId=" + docId + ", product="
                + product + ", factor=" + factor + ", xpath=" + xpath
                + ", text=" + text + ", start=" + start + ", length=" + length
                + ", type=" + type + ", index=" + index + "]";
    }
    
}
