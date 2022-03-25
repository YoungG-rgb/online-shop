package com.amr.project.converter;

import com.amr.project.model.dto.UserUpdateDto;
import com.amr.project.model.entity.User;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring" ,uses = AddressMapper.class)
public interface UserUpdateMapper {
    UserUpdateDto userToDto(User user);

    User dtoToUser(UserUpdateDto userUpdateDto);
}
