package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;

import javax.persistence.NoResultException;
import java.util.List;

public interface UserDao extends ReadWriteDao<User, Long> {
    User findByUsername(String username) throws NoResultException;

    User findByEmail(String email) throws NoResultException;

    User findByPhone(String phone) throws NoResultException;

    List<User> findByRole(String role) throws NoResultException;

    int deactivateUser(long id);

    List<User> findByStatusOrder(Status status);

    List<User> findByStatusOrderAndShopOwnerUser(Status status, User user);
}

