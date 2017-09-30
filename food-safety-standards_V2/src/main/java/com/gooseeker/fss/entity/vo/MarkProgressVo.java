package com.gooseeker.fss.entity.vo;

/**
 * 打标进展实体
 * 
 * @author yy
 *
 */
public class MarkProgressVo
{
    // 总数
    private int total;
    // 未打标的文档总数
    private int tagStartCount;
    // 正在打标的文档总数
    private int tagActionCount;
    // 一审状态的文档总数
    private int firstCheckCount;
    // 二审状态的文档总数
    private int secondCheckCount;
    // 入库的文档总数
    private int finishCount;

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }

    public int getTagStartCount()
    {
        return tagStartCount;
    }

    public void setTagStartCount(int tagStartCount)
    {
        this.tagStartCount = tagStartCount;
    }

    public int getTagActionCount()
    {
        return tagActionCount;
    }

    public void setTagActionCount(int tagActionCount)
    {
        this.tagActionCount = tagActionCount;
    }

    public int getFirstCheckCount()
    {
        return firstCheckCount;
    }

    public void setFirstCheckCount(int firstCheckCount)
    {
        this.firstCheckCount = firstCheckCount;
    }

    public int getSecondCheckCount()
    {
        return secondCheckCount;
    }

    public void setSecondCheckCount(int secondCheckCount)
    {
        this.secondCheckCount = secondCheckCount;
    }

    public int getFinishCount()
    {
        return finishCount;
    }

    public void setFinishCount(int finishCount)
    {
        this.finishCount = finishCount;
    }
}
