package com.amr.project.converter;

import com.amr.project.model.dto.AdvertDto;
import com.amr.project.model.entity.Advert;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", uses = ImageMapper.class)
public interface AdvertMapper {

    AdvertDto advertToDto(Advert advert);

    Advert dtoToAdvert(AdvertDto advertDto);
}
