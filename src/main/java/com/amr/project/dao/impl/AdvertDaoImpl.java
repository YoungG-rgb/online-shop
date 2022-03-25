package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.AdvertDao;
import com.amr.project.model.entity.Advert;
import com.amr.project.model.entity.Item;
import com.amr.project.model.entity.User;
import com.amr.project.util.QueryResultWrapper;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class AdvertDaoImpl extends ReadWriteDaoImpl<Advert, Long> implements AdvertDao {

    @Override
    public List<Advert> getAllUser(User user) {
        Query query = entityManager.createQuery("SELECT c FROM Advert c JOIN User u ON u.id = c.user.id WHERE u.id = :userId");
        query.setParameter("userId", user.getId());
        return query.getResultList();
    }

    @Override
    public Advert getAdvertName(String nameAdvert) {
        TypedQuery<Advert> query = (TypedQuery<Advert>) entityManager.createQuery("select u from Advert u where u.name=:nameAdvert");
        query.setParameter("nameAdvert", nameAdvert);
        return QueryResultWrapper.wrapGetSingleResult(query);
    }

    @Override
    public List<Advert> getUnmoderatedAdverts() {
        return entityManager.createQuery("SELECT i FROM Advert i WHERE i.isModerateAccept = false AND i.isModerated = false", Advert.class)
                .getResultList();
    }

    @Override
    public List<Advert> getModeratedAdverts() {
        return entityManager.createQuery("SELECT i FROM Advert i WHERE i.isModerateAccept = true AND i.isModerated = true ", Advert.class)
                .getResultList();
    }


}
