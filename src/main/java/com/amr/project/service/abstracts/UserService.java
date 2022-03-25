package com.amr.project.service.abstracts;

import com.amr.project.model.dto.UserUpdateDto;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;

import javax.persistence.NoResultException;
import java.util.List;

public interface UserService extends ReadWriteService<User, Long> {
    User findByUsername(String username) throws NoResultException;

    User findByEmail(String email) throws NoResultException;

    User findByPhone(String phone) throws NoResultException;

    List<User> findByRole(String role) throws NoResultException;

    User getAuthorized();

    User getUserId(Long id);

    UserUpdateDto getUserUpdateDtoById(Long id);

    void updateUserDto(UserUpdateDto userUpdateDto);

    int deactivateUser(long id);

    List<User> findByStatusOrder(Status status) throws NoResultException;

    List<User> findByStatusOrderAndShopOwnerUser(Status status, User user);
}
