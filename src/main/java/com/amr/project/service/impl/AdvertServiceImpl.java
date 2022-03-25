package com.amr.project.service.impl;

import com.amr.project.converter.AdvertMapper;
import com.amr.project.dao.abstracts.AdvertDao;
import com.amr.project.inserttestdata.repository.AdvertRepository;
import com.amr.project.model.dto.AdvertDto;
import com.amr.project.model.entity.Advert;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.AdvertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdvertServiceImpl extends ReadWriteServiceImpl<Advert, Long> implements AdvertService {

    private final AdvertDao advertDao;
    private final AdvertRepository advertRepository;
    private final AdvertMapper advertMapper;

    @Autowired
    public AdvertServiceImpl(AdvertDao advertDao, AdvertRepository advertRepository, AdvertMapper advertMapper) {
        super(advertDao);
        this.advertDao = advertDao;
        this.advertRepository = advertRepository;
        this.advertMapper = advertMapper;
    }


    @Override
    @Transactional
    public List<Advert> getUnmoderatedAdvets() {
      return advertDao.getUnmoderatedAdverts();
    }

    @Override
    @Transactional
    public List<Advert> getModeratedAdverts() {
        return advertDao.getModeratedAdverts();
    }

    @Override
    @Transactional
    public Advert getAdvertByName(String nameAdvert) {
        return advertDao.getAdvertName(nameAdvert);

    }

    @Override
    public Advert getAdvertById(long id) {
        Advert advert = advertRepository.findById(id).orElse(null);
        return advert;
    }

    @Override
    public List<Advert> getAllUser(User user) {
        return advertDao.getAllUser(user);
    }


    @Override
    public AdvertDto getAdvertDtoId(long id) {
        AdvertDto advertDto = advertMapper.advertToDto(advertRepository.findById(id).orElse(null));
        return advertDto;
    }

    @Override
    public List<AdvertDto> getAllAdverts() {
        List<Advert> advertList = advertRepository.findAll();
        List<AdvertDto> advertDtoList = advertList.stream().map(advert -> advertMapper.advertToDto(advert)).collect(Collectors.toList());
        return advertDtoList;
    }


}
