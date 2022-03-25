package com.amr.project.model.dto;

import com.amr.project.model.enums.CouponStatus;
import lombok.*;
import java.util.Calendar;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Builder
public class CouponDto {
    private Long id;

    private ShopDto shop;

    private Calendar start;

    private Calendar end;

    private CouponStatus status;

    private Integer sum;

    private Long shopId;
}
