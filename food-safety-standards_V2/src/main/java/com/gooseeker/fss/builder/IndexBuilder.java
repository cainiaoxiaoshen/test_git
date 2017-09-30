package com.gooseeker.fss.builder;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.Term;
import org.apache.lucene.queries.BooleanFilter;
import org.apache.lucene.queries.TermFilter;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.BooleanClause.Occur;
import org.springframework.util.StringUtils;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.container.QueryConditionContainer;
import com.gooseeker.fss.entity.AnnotateDocument;
import com.gooseeker.fss.index.IndexManager;

public class IndexBuilder
{
    /**
     * AnnotateDocument对象--》Document对象转换
     * @param aDoc 自定义的打标文档对象
     * @return
     */
    public static Document builderIndexDocument(AnnotateDocument aDoc)
    {
        Document doc = new Document();
        FieldType fieldtype = new FieldType();
        fieldtype.setIndexed(true);
        fieldtype.setTokenized(false);
        fieldtype.setStored(true);

        doc.add(new Field("reqUrl", aDoc.getReqUrl(), fieldtype));
        
        String standardNo = aDoc.getStandardNo();
        if (StringUtils.isEmpty(standardNo))
        {
            standardNo = "";
        }
        doc.add(new TextField("standardNo", standardNo, Field.Store.YES));

        String createUser = aDoc.getCreateUser();
        if (StringUtils.isEmpty(createUser))
        {
            createUser = "";
        }
        doc.add(new StringField("createUser", createUser, Field.Store.YES));

        String createTime = "";
        if (aDoc.getCreateTime() != null)
        {
            createTime = String.valueOf(aDoc.getCreateTime());
        }
        doc.add(new StringField("createTime", createTime, Field.Store.YES));

        String modiUser = aDoc.getModiUser();
        if (StringUtils.isEmpty(modiUser))
        {
            modiUser = "";
        }
        doc.add(new StringField("modiUser", modiUser, Field.Store.YES));

        String modiTime = "";
        if (aDoc.getModiTime() != null)
        {
            modiTime = String.valueOf(aDoc.getModiTime());
        }
        doc.add(new StringField("modiTime", modiTime, Field.Store.YES));

        String type = aDoc.getType();
        if (StringUtils.isEmpty(type))
        {
            type = "";
        }
        doc.add(new StringField("type", type, Field.Store.YES));

        String docFileName = aDoc.getFileName();
        if (StringUtils.isEmpty(docFileName))
        {
            docFileName = "";
        }
        doc.add(new StringField("fileName", docFileName, Field.Store.YES));

        String format = aDoc.getFormat();
        if (StringUtils.isEmpty(format))
        {
            format = "";
        }
        doc.add(new StringField("format", format, Field.Store.YES));

        String docName = aDoc.getDocName();
        if (StringUtils.isEmpty(docName))
        {
            docName = "";
        }
        doc.add(new TextField("docName", docName, Field.Store.YES));

        String language = aDoc.getLanguage();
        if (StringUtils.isEmpty(language))
        {
            language = "";
        }
        doc.add(new StringField("language", language, Field.Store.YES));

        String country = aDoc.getCountry();
        if (StringUtils.isEmpty(country))
        {
            country = "";
        }
        doc.add(new Field("country", country, fieldtype));

        String pubTime = "";
        if (aDoc.getPubTime() != null)
        {
            pubTime = String.valueOf(aDoc.getPubTime());
        }
        doc.add(new StringField("pubTime", pubTime, Field.Store.YES));

        String impTime = "";
        if (aDoc.getImpTime() != null)
        {
            impTime = String.valueOf(aDoc.getImpTime());
        }
        doc.add(new StringField("impTime", impTime, Field.Store.YES));

        String description = aDoc.getDescription();
        if (StringUtils.isEmpty(description))
        {
            description = "";
        }
        doc.add(new TextField("description", description, Field.Store.YES));

        String replace = "";
        if (aDoc.getReplace() != null)
        {
            replace = String.valueOf(aDoc.getReplace());
        }
        doc.add(new StringField("replace", replace, Field.Store.YES));

        String tagState = "";
        if (aDoc.getTagState() != null)
        {
            tagState = String.valueOf(aDoc.getTagState());
        }
        doc.add(new StringField("tagState", tagState, Field.Store.YES));

        String proSystem = aDoc.getProSystem();
        if (StringUtils.isEmpty(proSystem))
        {
            proSystem = "";
        }
        doc.add(new StringField("proSystem", proSystem, Field.Store.YES));

        String firstCheckUser = aDoc.getFirstCheckUser();
        if (StringUtils.isEmpty(firstCheckUser))
        {
            firstCheckUser = "";
        }
        doc.add(new Field("firstCheckUser", firstCheckUser, fieldtype));

        String annotateUser = aDoc.getAnnotateUser();
        if (StringUtils.isEmpty(annotateUser))
        {
            annotateUser = "";
        }
        doc.add(new Field("annotateUser", annotateUser, fieldtype));

        String secondCheckUser = aDoc.getSecondCheckUser();
        if (StringUtils.isEmpty(secondCheckUser))
        {
            secondCheckUser = "";
        }
        doc.add(new Field("secondCheckUser", secondCheckUser, fieldtype));
        
        String keyWord = aDoc.getKeyWord();
        if (StringUtils.isEmpty(keyWord))
        {
            keyWord = "";
        }
        doc.add(new TextField("keyWord", keyWord, Field.Store.YES));
        
        String str = aDoc.getText();
        if(StringUtils.isEmpty(str))
        {
            str = "";
        }
        doc.add(new TextField("text", str, Field.Store.YES));
        return doc;
    }
    
    /**
     * 
     * 打标文档实体对象--》Lucene Document对象
     * @param docs
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<Document> builderIndexDocuments(List<AnnotateDocument> docs)
    {
        List<Document> documents = new ArrayList<Document>();
        for (int i = 0; i < docs.size(); i++)
        {
            AnnotateDocument doc = docs.get(i);
            Document document = builderIndexDocument(doc);
            documents.add(document);
        }
        return documents;
    }
    
    
    /**
     * 索引Document对象转换成AnnotateDocument对象
     * @param indexManager
     * @param docs
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static List<AnnotateDocument> builderAnnotateDocuments(IndexManager indexManager, ScoreDoc[] docs)
    {
        List<AnnotateDocument> docList = new ArrayList<AnnotateDocument>();
        for (ScoreDoc scoreDoc : docs)
        {
            int docID = scoreDoc.doc;
            Document document = null;
            try
            {
                document = indexManager.getIndexSearcher().doc(docID);
                AnnotateDocument docIndex = new AnnotateDocument();
                docIndex.setCreateUser(document.get("createUser"));
                docIndex.setCreateTime(Timestamp.valueOf(document.get("createTime")));
                docIndex.setModiUser(document.get("modiUser"));
                String modiTime = document.get("modiTime");
                if (!StringUtils.isEmpty(modiTime))
                {
                    docIndex.setModiTime(Timestamp.valueOf(modiTime));
                }
                else
                {
                    docIndex.setModiTime(null);
                }
                docIndex.setType(document.get("type"));
                docIndex.setFileName(document.get("fileName"));
                docIndex.setReqUrl(document.get("reqUrl"));
                docIndex.setStandardNo(document.get("standardNo"));
                docIndex.setFormat(document.get("format"));
                docIndex.setDocName(document.get("docName"));
                docIndex.setProSystem(document.get("proSystem"));
                docIndex.setLanguage(document.get("language"));
                docIndex.setCountry(document.get("country"));
                docIndex.setKeyWord(document.get("keyWord"));
                String pubTime = document.get("pubTime");
                if (!StringUtils.isEmpty(pubTime))
                {
                    docIndex.setPubTime(Timestamp.valueOf(pubTime));
                }
                else
                {
                    docIndex.setPubTime(null);
                }
                String impTime = document.get("impTime");
                if (!StringUtils.isEmpty(impTime))
                {
                    docIndex.setImpTime(Timestamp.valueOf(impTime));
                }
                else
                {
                    docIndex.setImpTime(null);
                }
                docIndex.setDescription(document.get("description"));
                String replace = document.get("replace");
                if (StringUtils.isEmpty(replace))
                {
                    replace = "0";
                }
                docIndex.setReplace(Integer.parseInt(replace));
                String tagState = document.get("tagState");
                if (StringUtils.isEmpty(tagState))
                {
                    tagState = "0";
                }
                docIndex.setTagState(Integer.parseInt(tagState));
                docIndex.setText(document.get("text"));
                docIndex.setAnnotateUser(document.get("annotateUser"));
                docIndex.setFirstCheckUser(document.get("firstCheckUser"));
                docIndex.setSecondCheckUser(document.get("secondCheckUser"));
                docList.add(docIndex);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            
        }
        return docList;
    }
    
    public static Filter fielterBuilder(QueryConditionContainer container)
    {
        BooleanFilter booleanFilter = new BooleanFilter();
        List<Term> termList = new ArrayList<Term>();
        if (container != null)
        {
            String country = container.getCountry();
            if (StringUtil.isNotBlank(country))
            {
                termList.add(new Term("country", country));
                Filter f1 = new TermFilter(new Term("country", country));
                booleanFilter.add(f1, Occur.MUST);
            }
            String tagState = container.getTagState();
            if (StringUtil.isNumerical(tagState))
            {
                termList.add(new Term("tagState", tagState));
                Filter f2 = new TermFilter(new Term("tagState", tagState));
                booleanFilter.add(f2, Occur.MUST);
            }
            String type = container.getType();
            if (StringUtil.isNumerical(type))
            {
                termList.add(new Term("type", type));
                Filter f3 = new TermFilter(new Term("type", type));
                booleanFilter.add(f3, Occur.MUST);
            }
        }
        if (booleanFilter.clauses().size() < 1)
        {
            booleanFilter = null;
        }
        
        return booleanFilter;
    }
    
    public static Query queryBuilder(String user, String roleLv, List<String> terms)
    {
        BooleanQuery query = new BooleanQuery();
        BooleanQuery booleanQuery = new BooleanQuery();

        //关键字条件过滤
        for (int i = 0; i < terms.size(); i++)
        {
            // 组合BooleanQuery查询
            TermQuery termQuery = new TermQuery(new Term("text", terms.get(i)));
            booleanQuery.add(termQuery, BooleanClause.Occur.SHOULD);

            TermQuery termQuery2 = new TermQuery(new Term("docName", terms.get(i)));
            booleanQuery.add(termQuery2, BooleanClause.Occur.SHOULD);

            TermQuery termQuery3 = new TermQuery(new Term("description", terms.get(i)));
            booleanQuery.add(termQuery3, BooleanClause.Occur.SHOULD);
            
            TermQuery termQuery4 = new TermQuery(new Term("keyWord", terms.get(i)));
            booleanQuery.add(termQuery4, BooleanClause.Occur.SHOULD);
            
            TermQuery termQuery5 = new TermQuery(new Term("standardNo", terms.get(i)));
            booleanQuery.add(termQuery5, BooleanClause.Occur.SHOULD);
        }
        //权限条件过滤
        if (Constant.ROLE_LV_STR_CHECK.equals(roleLv))
        {
            BooleanQuery checkRoleQuery = booleanQuery.clone();
            TermQuery firstCheckUserQuery = new TermQuery(new Term(
                    "firstCheckUser", user));
            booleanQuery.add(firstCheckUserQuery, BooleanClause.Occur.MUST);

            TermQuery secondCheckUserQuery = new TermQuery(new Term(
                    "secondCheckUser", user));
            checkRoleQuery.add(secondCheckUserQuery,
                    BooleanClause.Occur.MUST);

            query.add(booleanQuery, BooleanClause.Occur.SHOULD);
            query.add(checkRoleQuery, BooleanClause.Occur.SHOULD);
        }
        else if (Constant.ROLE_LV_STR_ANNOTATECHECK.equals(roleLv))
        {
            BooleanQuery annotateCheckRole1Query = booleanQuery.clone();
            BooleanQuery annotateCheckRole2Query = booleanQuery.clone();

            TermQuery annotateUserQuery = new TermQuery(new Term(
                    "annotateUser", user));
            booleanQuery.add(annotateUserQuery, BooleanClause.Occur.MUST);

            TermQuery firstCheckUserQuery = new TermQuery(new Term(
                    "firstCheckUser", user));
            annotateCheckRole1Query.add(firstCheckUserQuery,
                    BooleanClause.Occur.MUST);
            booleanQuery.add(annotateCheckRole1Query,
                    BooleanClause.Occur.SHOULD);

            TermQuery secondCheckUserQuery = new TermQuery(new Term(
                    "secondCheckUser", user));
            annotateCheckRole2Query.add(secondCheckUserQuery,
                    BooleanClause.Occur.MUST);

            query.add(booleanQuery, BooleanClause.Occur.SHOULD);
            query.add(annotateCheckRole1Query, BooleanClause.Occur.SHOULD);
            query.add(annotateCheckRole2Query, BooleanClause.Occur.SHOULD);
        }
        else
        {
            query.add(booleanQuery, BooleanClause.Occur.SHOULD);
        }
        
        return query;
    }
}
