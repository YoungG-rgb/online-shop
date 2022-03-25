package com.amr.project.service.abstracts;

import com.amr.project.model.entity.City;

public interface CityService extends ReadWriteService<City,Long>{
    City getByName(String name);
    boolean createAndSaveCity(String city, String country);
    void save(City city);
}
