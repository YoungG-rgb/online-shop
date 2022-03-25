package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.UserDao;
import com.amr.project.model.entity.Role;
import com.amr.project.model.entity.User;
import com.amr.project.model.enums.Status;
import com.amr.project.util.QueryResultWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl extends ReadWriteDaoImpl<User, Long> implements UserDao {

    private final EntityManager entityManager;

    @Override
    public User findByUsername(String username) throws NoResultException {
        TypedQuery<User> query = entityManager.createQuery("from User where username = :username", User.class);
        query.setParameter("username", username);
        return QueryResultWrapper.wrapGetSingleResult(query);
    }

    @Override
    public User findByEmail(String email) throws NoResultException {
        TypedQuery<User> query = entityManager.createQuery("from User where email = :email", User.class);
        query.setParameter("email", email);
        return QueryResultWrapper.wrapGetSingleResult(query);
    }


    @Override
    public User findByPhone(String phone) throws NoResultException {
        TypedQuery<User> query = entityManager.createQuery("from User where phone = :phone", User.class);
        query.setParameter("phone", phone);
        return QueryResultWrapper.wrapGetSingleResult(query);
    }

    @Override
    public List<User> findByRole(String role) throws NoResultException {
        TypedQuery<User> query = entityManager.createQuery(
                "select u from User u join u.roles r where r.name=:role", User.class);
        query.setParameter("role", role);
        return query.getResultList();
    }

    @Override
    public int deactivateUser(long id) {
        Query query = entityManager.createQuery(
                "update User set activate = false where id =:id")
                .setParameter("id", id);
       return query.executeUpdate();
    }

    @Override
    public List<User> findByStatusOrder(Status status) {
        TypedQuery<User> query = entityManager.createQuery(
                "select distinct u from User u join u.orders o where o.status=:status", User.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public List<User> findByStatusOrderAndShopOwnerUser(Status status, User user) {
        TypedQuery<User> query = entityManager.createQuery(
                "select distinct u from User u join u.orders o join o.items i join i.shop s " +
                        "where o.status=:status and s.user=:user", User.class);
        query.setParameter("status", status).setParameter("user", user);
        return query.getResultList();
    }
}
