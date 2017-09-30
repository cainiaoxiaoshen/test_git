package com.gooseeker.fss.index.entity;


import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Sort;

public class IndexPage
{
    /**
     * 当前第几页(从1开始计算)
     */
    private int pageNum;
    /**
     * 每页显示几条
     */
    private int pageSize;
    /**
     * 总记录数
     */
    private int total;
    /**
     * 分页数据集合
     */
    private ScoreDoc[] scoreDocs;
    
    /**
     * 查询条件
     */
    private Query query; 
    
    /**
     * 过滤条件
     */
    private Filter filter;
    
    /**
     * 排序条件
     */
    private Sort sort;

    public int getPageNum()
    {
        return pageNum;
    }

    public void setPageNum(int pageNum)
    {
        this.pageNum = pageNum;
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }

    public int getTotal()
    {
        return total;
    }

    public void setTotal(int total)
    {
        this.total = total;
    }
    
    public ScoreDoc[] getScoreDocs()
    {
        return scoreDocs;
    }

    public void setScoreDocs(ScoreDoc[] scoreDocs)
    {
        this.scoreDocs = scoreDocs;
    }

    public Query getQuery()
    {
        return query;
    }

    public void setQuery(Query query)
    {
        this.query = query;
    }

    public Filter getFilter()
    {
        return filter;
    }

    public void setFilter(Filter filter)
    {
        this.filter = filter;
    }

    public Sort getSort()
    {
        return sort;
    }

    public void setSort(Sort sort)
    {
        this.sort = sort;
    }
    
    

}
