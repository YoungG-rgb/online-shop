package com.amr.project.service.abstracts;

import com.amr.project.model.dto.AdvertDto;
import com.amr.project.model.entity.Advert;
import com.amr.project.model.entity.CartItem;
import com.amr.project.model.entity.User;

import java.util.List;


public interface AdvertService extends ReadWriteService<Advert, Long>{

    List<Advert> getUnmoderatedAdvets();

    List<Advert> getModeratedAdverts();

    Advert getAdvertByName(String nameAdvert);


    Advert getAdvertById(long id);

    List<Advert> getAllUser(User user);

    AdvertDto getAdvertDtoId(long id);

    List<AdvertDto> getAllAdverts();
}
