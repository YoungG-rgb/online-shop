package com.amr.project.service.impl;

import com.amr.project.converter.CouponMapper;
import com.amr.project.dao.abstracts.CouponDao;
import com.amr.project.model.dto.CouponDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.CouponService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl extends ReadWriteServiceImpl<Coupon, Long> implements CouponService {

    private final CouponDao couponDao;
    private final CouponMapper couponMapper;

    public CouponServiceImpl(CouponDao couponDao, CouponMapper couponMapper) {
        super(couponDao);
        this.couponDao = couponDao;
        this.couponMapper = couponMapper;
    }


    @Override
    public List<CouponDto> findByShop(Shop shop) {
        List<Coupon> couponList = couponDao.findByShop(shop);
        if (couponList == null) {
            return new ArrayList<>();
        }
        return couponList.stream().map(couponMapper::couponToCouponDto).collect(Collectors.toList());
    }

}
