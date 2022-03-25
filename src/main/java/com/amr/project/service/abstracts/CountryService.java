package com.amr.project.service.abstracts;

import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;

public interface CountryService extends ReadWriteService<Country,Long>{
    Country getByName(String name);
    boolean createAndSaveCountry(String name, City city);
    void save(Country country);
}
