package com.gooseeker.fss.service;

import java.util.List;

import com.gooseeker.fss.commons.generic.service.GenericService;
import com.gooseeker.fss.entity.Country;

public interface CountryService extends GenericService<Country, Integer>
{
    public List<Country> findAll();
}
