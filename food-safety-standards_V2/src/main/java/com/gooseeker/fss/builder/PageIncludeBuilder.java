package com.gooseeker.fss.builder;

import java.util.List;

import com.gooseeker.fss.commons.entity.Constant;
import com.gooseeker.fss.entity.PageInclude;

public class PageIncludeBuilder
{
    
    public static PageInclude builderPageIncludes(int page, String text, String standarNo, List<String> words)
    {
        StringBuilder builder = new StringBuilder();
        for (int j = 0; j < words.size(); j++)
        {
            String keyWord = words.get(j);
            if(text.contains(keyWord))
            {
                builder.append(keyWord).append(Constant.ESCAPE_COMMA);
            }
        }
        if(builder.length() > 0)
        {
            PageInclude include = new PageInclude();
            include.setDocNo(standarNo);
            include.setPage(page);
            include.setIncludes(builder.toString());
            return include;
        }
        return null;
    }
    
    
}
