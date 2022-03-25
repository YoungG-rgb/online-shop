package com.amr.project.service.abstracts;

import com.amr.project.model.dto.CouponDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Shop;
import java.util.List;

public interface CouponService extends ReadWriteService<Coupon,Long>{

    List<CouponDto> findByShop(Shop shop);
}
