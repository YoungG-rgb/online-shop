package com.amr.project.service.impl;

import com.amr.project.dao.abstracts.CountryDao;
import com.amr.project.inserttestdata.repository.CountryRepository;
import com.amr.project.model.entity.City;
import com.amr.project.model.entity.Country;
import com.amr.project.service.abstracts.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountryServiceImpl extends ReadWriteServiceImpl<Country,Long> implements CountryService {

    private CountryDao countryDao;
    private final CountryRepository countryRepository;

    @Autowired
    public CountryServiceImpl(CountryDao countryDao, CountryRepository countryRepository) {
        super(countryDao);
        this.countryDao = countryDao;
        this.countryRepository = countryRepository;
    }

    @Override
    public Country getByName(String name) {
        return countryRepository.findByName(name);
    }

    @Override
    @Transactional
    public void save(Country country) {
        countryRepository.save(country);
    }

    @Override
    @Transactional
    public boolean createAndSaveCountry(String name, City city) {
        if(!countryRepository.existsByName(name)) {
            try{
                List<City> c = new ArrayList<>();
                c.add(city);
                Country country = Country.builder()
                        .name(name)
                        .cities(c)
                        .build();
                countryRepository.save(country);
                return true;
            } catch (Exception e) {
                System.out.format("Не удалось создать или сохранить страну %s", name);
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }

    }
}
