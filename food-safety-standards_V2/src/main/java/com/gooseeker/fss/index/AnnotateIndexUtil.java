package com.gooseeker.fss.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;







import org.apache.log4j.Logger;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.BooleanFilter;
import org.apache.lucene.queries.TermFilter;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.SortField.Type;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gooseeker.fss.builder.DocumentPathBuilder;
import com.gooseeker.fss.builder.IndexBuilder;
import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.entity.Page;
import com.gooseeker.fss.commons.utils.PropertiesUtil;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.index.entity.IndexPage;

public class AnnotateIndexUtil
{
    private static Logger logger = Logger.getLogger(AnnotateIndexUtil.class);
    
    /**
     * 索引存放的路径
     */
    private static String indexDir = PropertiesUtil.getProperty("doc.pdf.index");
    
    private static IndexManager indexManager = IndexManager.getInstance(Version.LUCENE_47, new IKAnalyzer(), indexDir);
    
    /**
     * 创建索引
     * @param docs
     * @see [类、类#方法、类#成员]
     */
    public void createIndex(List<AnnotateDocument> docs)
    {
        List<Document> documents = IndexBuilder.builderIndexDocuments(docs);
        indexManager.createIndex(documents);
    }
    
    /**
     * 添加索引
     * @param doc
     * @see [类、类#方法、类#成员]
     */
    public void addIndex(AnnotateDocument doc)
    {
        Document document = IndexBuilder.builderIndexDocument(doc);
        indexManager.addIndex(document);
    }
    
    /**
     * 添加索引
     * @param docs
     * @see [类、类#方法、类#成员]
     */
    public void addIndexs(List<AnnotateDocument> docs)
    {
        List<Document> documents = IndexBuilder.builderIndexDocuments(docs);
        indexManager.addIndexs(documents);
    }
    
    /**
     * 更新索引
     * @param document
     * @see [类、类#方法、类#成员]
     */
    public void updateIndex(AnnotateDocument doc)
    {
        Term term = new Term("standardNo", doc.getStandardNo());
        Document document = IndexBuilder.builderIndexDocument(doc);
        indexManager.updateIndex(document, term);
    }
    
    /**
     * 更新索引
     * @param docs
     * @see [类、类#方法、类#成员]
     */
    public void updateIndexs(List<AnnotateDocument> docs)
    {
        for (int i = 0; i < docs.size(); i++)
        {
            AnnotateDocument doc = docs.get(i);
            Term term = new Term("standardNo", doc.getStandardNo());
            Document document = IndexBuilder.builderIndexDocument(doc);
            indexManager.updateIndex(document, term);
        }
    }
    
    public Page<AnnotateDocument> query(QueryConditionContainer container) throws IOException
    {
        IndexPage indexPage = new IndexPage();
        indexPage.setPageSize(Constant.PAGE_LIMIT);
        List<String> terms = indexManager.analyzerWords(container.getKeyWord());
        Query query = IndexBuilder.queryBuilder(container.getUser(), container.getRoleLv(), terms);
        indexPage.setQuery(query);
        indexPage.setFilter(IndexBuilder.fielterBuilder(container));
        Sort sort = new Sort(new SortField("tagState", Type.SCORE), SortField.FIELD_SCORE);
        indexPage.setSort(sort);
        indexPage.setPageNum(container.getPageNum());
        indexManager.pageQuery(indexPage);
        Page<AnnotateDocument> page = new Page<AnnotateDocument>(indexPage.getTotal(), container.getPageNum(), Constant.PAGE_LIMIT);
        page.setList(IndexBuilder.builderAnnotateDocuments(indexManager, indexPage.getScoreDocs()));
        return page;
    }
}
