package com.gooseeker.fss.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.commons.utils.StringUtil;
import com.gooseeker.fss.entity.AnnotateXpath;
import com.gooseeker.fss.mapper.AnnotateXpathMapper;
import com.gooseeker.fss.service.AnnotateXpathService;

@Service("annotateXpathServiceImpl")
public class AnnotateXpathServiceImpl extends
        GenericServiceImpl<AnnotateXpath, Long> implements AnnotateXpathService
{

    private static Logger logger = Logger
            .getLogger(AnnotateXpathServiceImpl.class);

    @Resource(name = "annotateXpathMapper")
    private AnnotateXpathMapper annotateXpathMapper;

    @Override
    public GenericMapper<AnnotateXpath, Long> getMapper()
    {

        return annotateXpathMapper;
    }

    @Override
    @Transactional
    public int insertAnnotateXpaths(List<AnnotateXpath> list) throws Exception
    {
        for (int i = 0; i < list.size(); i++)
        {
            AnnotateXpath annotateXpath = list.get(i);
            String product = annotateXpath.getProduct();
            String factor = annotateXpath.getFactor();
            String text = annotateXpath.getText();
            String xpath = annotateXpath.getXpath();
            String index = annotateXpath.getIndex();
            if (StringUtil.isNotBlank(product) && StringUtil.isNotBlank(factor)
                    && StringUtil.isNotBlank(text)
                    && StringUtil.isNotBlank(xpath)
                    && StringUtil.isNotBlank(index))
            {
                int result = 1;//insertModel(annotateXpath);
                if (result < 1)
                {
                    String errorMsg = "[docId=" + annotateXpath.getDocId()
                            + "产品=" + annotateXpath.getProduct() + "    物质="
                            + annotateXpath.getFactor() + "    type="
                            + annotateXpath.getType() + "]插入出错";
                    logger.error(errorMsg);
                }
            }
        }
        return 1;
    }

    @Override
    public int deleteAnntateXpath(long docId, String product, String factor)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("docId", docId);
        map.put("product", product);
        map.put("factor", factor);
        try
        {
            return annotateXpathMapper.deleteByUniqueKey(map);
        }
        catch (Exception e)
        {
            logger.error("删除docId=[" + docId + "]，product=[" + product
                    + "]，factor=[" + factor + "]出错", e);
        }
        finally
        {
            map.clear();
        }
        return 0;
    }
}
