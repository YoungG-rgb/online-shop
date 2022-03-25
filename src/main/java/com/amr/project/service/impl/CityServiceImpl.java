package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CityDao;
import com.amr.project.inserttestdata.repository.CityRepository;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import com.amr.project.service.abstracts.CityService;
import com.amr.project.service.abstracts.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CityServiceImpl extends ReadWriteServiceImpl<City,Long> implements CityService {

    private final CityDao cityDao;
    private final CityRepository cityRepository;
    private final CountryService countryService;

    @Autowired
    public CityServiceImpl(CityDao cityDao, CityRepository cityRepository, CountryService countryService) {
        super(cityDao);
        this.cityDao = cityDao;
        this.cityRepository = cityRepository;
        this.countryService = countryService;
    }

    @Override
    public City getByName(String name) {        ;
        return cityRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(City city) {
        cityRepository.save(city);
    }

    @Override
    @Transactional
    public boolean createAndSaveCity(String city, String country) {
        if(!cityRepository.existsByName(city)){
            try {
                City newCity = City.builder()
                        .name(city)
                        .build();
                cityRepository.save(newCity);
                Country c = countryService.getByName(country);
                if (c != null) {
                    c.getCities().add(newCity);
                    countryService.persist(c);
                }
                return true;
            } catch (Exception e) {
                System.out.format("Не удалось создать или сохранить город %s", city);
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }

    }
}
