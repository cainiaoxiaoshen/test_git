package com.gooseeker.fss.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.gooseeker.fss.commons.generic.mapper.GenericMapper;
import com.gooseeker.fss.entity.Country;

@Repository("countryMapper")
public interface CountryMapper extends GenericMapper<Country, Integer> {
    
    public List<Country> findAll();
}
