package com.amr.project.converter;

import com.amr.project.model.dto.CouponDto;
import com.amr.project.model.entity.Coupon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ShopMapper.class})
public interface CouponMapper {
    Coupon couponDtoToCoupon(CouponDto couponDto);
    CouponDto couponToCouponDto(Coupon coupon);
}
