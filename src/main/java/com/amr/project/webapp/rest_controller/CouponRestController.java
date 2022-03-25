package com.amr.project.webapp.rest_controller;

import com.amr.project.converter.CouponMapper;
import com.amr.project.model.dto.CouponDto;
import com.amr.project.model.entity.Coupon;
import com.amr.project.model.enums.CouponStatus;
import com.amr.project.service.abstracts.CouponService;
import com.amr.project.service.abstracts.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;
import java.util.List;

@RestController
@RequestMapping("/api/coupon")
@Api(tags = "API для работы с купонами")
@RequiredArgsConstructor
public class CouponRestController {

    private final CouponService couponService;
    private final CouponMapper couponMapper;
    private final ShopService shopService;

    @ApiOperation(value = "Добавление купона")
    @PostMapping("/addCoupon")
    public ResponseEntity<?> addCoupon(@RequestBody CouponDto couponDto){
        if (couponDto.getSum() == null && couponDto.getShop() == null) {
            return ResponseEntity.badRequest().build();
        }
        couponDto.setStatus(CouponStatus.ACTUAL);
        couponDto.setShop(shopService.getShopId(couponDto.getShopId()));
        Coupon coupon = couponMapper.couponDtoToCoupon(couponDto);
        couponService.persist(coupon);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "для обновления статуса купона на USED")
    @PutMapping("/update/used/{id}")
    public ResponseEntity<?> updateToUsed(@PathVariable("id") long id){
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        Coupon coupon = couponService.getByKey(id);
        coupon.setStatus(CouponStatus.USED);
        couponService.update(coupon);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "для обновления статуса купона на OVERDUE")
    @PutMapping("/update/overdue/{id}")
    public ResponseEntity<?> updateToOverdue(@PathVariable("id") long id){
        if (id == 0) {
            return ResponseEntity.badRequest().build();
        }
        Coupon coupon = couponService.getByKey(id);
        coupon.setStatus(CouponStatus.OVERDUE);
        couponService.update(coupon);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{name}")
    @ApiOperation(value = "для получения купона BY SHOP NAME")
    public ResponseEntity<List<CouponDto>> getAllCoupons(@PathVariable("name") String nameShop){
        if (shopService.getShop(nameShop) == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().body(couponService.findByShop(shopService.getShop(nameShop)));
    }
}
