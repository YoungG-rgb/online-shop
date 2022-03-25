package com.amr.project.dao.impl;

import com.amr.project.dao.abstracts.CouponDao;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Shop;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CouponDaoImpl extends ReadWriteDaoImpl<Coupon, Long> implements CouponDao {

    @Override
    public List<Coupon> findByShop(Shop shop) {
        return entityManager.createQuery("SELECT c from Coupon c where c.shop.id = :id", Coupon.class)
                .setParameter("id", shop.getId()).getResultList();
    }

}
