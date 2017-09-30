package com.gooseeker.fss.index;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.util.Version;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.gooseeker.fss.index.entity.IndexPage;

public class IndexManager
{
    private static Logger logger = Logger.getLogger(IndexManager.class);
    private IndexReader reader;
    private IndexSearcher searcher;
    private static IndexManager singleton = null;
    private String indexDir;    
    private Version matchVersion;
    private Analyzer analyzer;

    /**
     * 单例
     * @return
     */
    public static IndexManager getInstance(Version matchVersion, Analyzer analyzer, String indexDir)
    {
        if (null == singleton)
        {
            synchronized (IndexManager.class)
            {
                if (null == singleton)
                {
                    singleton = new IndexManager(matchVersion, analyzer, indexDir);
                }
            }
        }
        return singleton;
    }

    private IndexManager(Version matchVersion, Analyzer analyzer, String indexDir)
    {
        this.matchVersion = matchVersion;
        this.analyzer = analyzer;
        this.indexDir = indexDir;
    }
    
    private FSDirectory openFSDirectory()
    {
        FSDirectory directory = null;
        try
        {
            directory = FSDirectory.open(new File(indexDir));
        }
        catch (IOException e)
        {
            logger.error("打开索引目录异常", e);
        }
        return directory;
    }

    private IndexWriter getIndexWriter(Directory dir, IndexWriterConfig config)
    {
        if (null == dir)
        {
            throw new IllegalArgumentException("Directory can not be null.");
        }
        if (null == config)
        {
            throw new IllegalArgumentException(
                    "IndexWriterConfig can not be null.");
        }
        try
        {
            if (IndexWriter.isLocked(dir))
            {
                throw new LockObtainFailedException(
                        "Directory of index had been locked.");
            }
        }
        catch (LockObtainFailedException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        IndexWriter writer = null;
        try
        {
            writer = new IndexWriter(dir, config);
        }
        catch (IOException e)
        {
            throw new IllegalArgumentException(
                    "IndexWriterConfig can not be null.");
        }
        return writer;
    }
    
    /**
     * 得到创建索引时的writer
     * @return
     * @see [类、类#方法、类#成员]
     */
    private IndexWriter getIndexWriterForCreate(Directory directory)
    {
        IndexWriterConfig config = new IndexWriterConfig(matchVersion, analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        IndexWriter writer = getIndexWriter(directory, config);
        return writer;
    }
    
    /**
     * 得到添加或更新时的writer
     * @return
     * @see [类、类#方法、类#成员]
     */
    private IndexWriter getIndexWriterForAppend(Directory directory)
    {
        IndexWriterConfig config = new IndexWriterConfig(matchVersion, analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = getIndexWriter(directory, config);
        return writer;
    }
    
    /**
     * 创建索引
     * @param documents
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public void createIndex(List<Document> documents)
    {
        Directory directory = openFSDirectory();
        IndexWriter writer = getIndexWriterForCreate(directory);
        try
        {
            writer.addDocuments(documents);
            writer.commit();
        }
        catch (IOException e)
        {
            logger.error("创建索引异常", e);
            try
            {
                writer.close();
                writer = null;
                directory.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
        finally
        {
            try
            {
                writer.close();
                directory.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 添加多个索引
     * @param documents
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public void addIndexs(List<Document> documents)
    {
        Directory directory = openFSDirectory();
        IndexWriter writer = getIndexWriterForAppend(directory);
        try
        {
            writer.updateDocuments(null, documents);
            writer.commit();
        }
        catch (IOException e)
        {
            logger.error("添加索引异常", e);
            try
            {
                writer.close();
                writer = null;
                directory.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
        finally
        {
            try
            {
                writer.close();
                directory.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 添加索引
     * @param document
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public void addIndex(Document document)
    {
        Directory directory = openFSDirectory();
        IndexWriter writer = getIndexWriterForAppend(directory);
        try
        {
            writer.updateDocument(null, document);
        }
        catch (IOException e)
        {
            logger.error("添加索引异常", e);
            try
            {
                writer.close();
                writer = null;
                directory.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
        finally
        {
            try
            {
                writer.close();
                directory.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 更新索引
     * @param document
     * @param term
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public void updateIndex(Document document, Term term)
    {
        Directory directory = openFSDirectory();
        IndexWriter writer = getIndexWriterForAppend(directory);
        try
        {
            writer.updateDocument(term, document);
        }
        catch (IOException e)
        {
            logger.error("更新索引异常", e);
            try
            {
                writer.close();
                writer = null;
                directory.close();
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
        }
        finally
        {
            try
            {
                writer.close();
                directory.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    /**
     * 
     * 设置page的TotalRecord 得到上一页的最后ScoreDoc
     * 
     * @param page
     * @param search
     * @param query
     * @return
     * @see [类、类#方法、类#成员]
     */
    private ScoreDoc getLastScoreDoc(IndexPage page, IndexSearcher search)
    {
        try
        {
            TopFieldDocs topDocs = search.search(page.getQuery(), page.getFilter(), 
                    Integer.MAX_VALUE, page.getSort());
            
            if (topDocs == null || topDocs.scoreDocs == null
                    || topDocs.scoreDocs.length == 0)
            {
                page.setTotal(0);
            }
            else
            {
                page.setTotal(topDocs.scoreDocs.length);
            }
            //总条数
            int total = page.getTotal();
            //每页显示的条数
            int size = page.getPageSize();
            //总页数
            int totalPage = (total % size == 0 ? total / size : (total / size + 1));
            //当前页数
            int currentPage = page.getPageNum();
            if (currentPage > totalPage)
            {
                currentPage = 1;
            }
            if (currentPage > 1)
            {
                int num = (page.getPageNum() - 1) * page.getPageSize();
                if (num > topDocs.scoreDocs.length)
                {
                    num = topDocs.scoreDocs.length - 1;
                    page.setPageNum(currentPage - 1);
                }
                return topDocs.scoreDocs[num - 1];
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
    
    /**
     * 
     * 分页查询 <功能详细描述>
     * 
     * @param searcher
     * @param query
     * @param page
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    public void pageQuery(IndexPage page) throws IOException
    {
        IndexSearcher searcher = getIndexSearcher();
        // 得到上一页的最后一个ScoreDoc用于分页查询
        ScoreDoc afterScore = getLastScoreDoc(page, searcher);
        // 分页查询
        TopDocs topDocs = searcher.searchAfter(afterScore, page.getQuery(), 
                page.getFilter(), page.getPageSize(), page.getSort(), true, false);
        ScoreDoc[] docs = topDocs.scoreDocs;
        page.setScoreDocs(docs);
    }
    
    private IndexReader getIndexReader()
    {
        try
        {
            if (null == reader)
            {
                reader = DirectoryReader.open(openFSDirectory());
            }
            else
            {
                if (reader instanceof DirectoryReader)
                {
                    // 开启近实时Reader,能立即看到动态添加/删除的索引变化
                    IndexReader newReader = DirectoryReader
                            .openIfChanged((DirectoryReader) reader);
                    if (newReader != null)
                    {
                        // 关闭旧的reader
                        reader.close();
                        reader = newReader;
                        searcher = new IndexSearcher(reader);
                    }
                }
            }
        }
        catch (IOException e)
        {
            logger.error("获取reader异常", e);
        }
        return reader;
    }
    
    public IndexSearcher getIndexSearcher()
    {
        IndexReader reader = getIndexReader();
        if (searcher == null)
        {
            searcher = new IndexSearcher(reader);
        }
        return searcher;
    }
    
    public void closeIndexReader()
    {
        try
        {
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        reader = null;
    }
    
    public void closeIndexSearcher()
    {
        searcher = null;
    }
    
    /**
     * 
     * 把搜索关键词分词装入list中 <功能详细描述>
     * 
     * @param text
     * @return
     * @see [类、类#方法、类#成员]
     */
    public List<String> analyzerWords(String text)
    {
        List<String> terms = new ArrayList<String>();
        TokenStream tokenStream = null;
        try
        {
            tokenStream = analyzer.tokenStream(null, new StringReader(text));
            CharTermAttribute term = tokenStream
                    .addAttribute(CharTermAttribute.class);
            tokenStream.reset();// 必须先调用reset方法，否则会报下面的错，
            // java.lang.IllegalStateException: TokenStream contract violation:
            // reset()/close() call missing, reset() called multiple times,

            while (tokenStream.incrementToken())
            {
                terms.add(term.toString());
            }
        }
        catch (Exception e)
        {
            logger.error("分词关键词错误", e);
        }
        finally
        {
            try
            {
                if (tokenStream != null)
                {
                    tokenStream.end();
                    tokenStream.close();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return terms;
    }
    
    public static void main(String[] args)
    {
        
        IndexManager manager = IndexManager.getInstance(Version.LUCENE_47, new IKAnalyzer(), "F:/fss/testIndex");
        List<String> aa = manager.analyzerWords("Air quality control regions designated by the Administrator pursuant to section 107 of the Act are listed in this subpart. Regions so designated are subject to revision, and additional regions may be designated, as the Administrator determines necessary to protect the public health and welfare. ");
        for (int i = 0; i < aa.size(); i++)
        {
            System.out.println(aa.get(i));
        }
    }
    
}
