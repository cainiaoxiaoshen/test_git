package com.gooseeker.fss.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.commons.generic.service.impl.GenericServiceImpl;
import com.gooseeker.fss.entity.Country;
import com.gooseeker.fss.mapper.CountryMapper;
import com.gooseeker.fss.service.CountryService;

@Service("countryServiceImpl")
public class CountryServiceImpl extends GenericServiceImpl<Country, Integer> implements CountryService
{
    
    @Resource(name = "countryMapper")
    private CountryMapper countryMapper;
    
    @Override
    public GenericMapper<Country, Integer> getMapper()
    {
        return countryMapper;
    }
    
    @Override
    public List<Country> findAll()
    {
        return countryMapper.findAll();
    }
    
}
