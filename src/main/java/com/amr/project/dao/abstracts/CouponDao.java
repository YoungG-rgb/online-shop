package com.amr.project.dao.abstracts;

import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Shop;
import java.util.List;

public interface CouponDao extends ReadWriteDao<Coupon, Long> {

    List<Coupon> findByShop(Shop shop);
}
