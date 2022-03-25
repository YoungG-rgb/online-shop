package com.amr.project.webapp.rest_controller;

import com.amr.project.model.dto.CouponDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.database.rider.core.api.dataset.DataSet;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class CouponRestControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void addCouponTest() throws Exception {
        CouponDto couponDto = CouponDto.builder()
                .shopId(3L)
                .start(new GregorianCalendar(2021, Calendar.NOVEMBER,10))
                .end(new GregorianCalendar(2021, Calendar.DECEMBER,10))
                .sum(90).build();

        this.mockMvc.perform(post("/api/coupon/addCoupon")
                        .content(objectMapper.writeValueAsString(couponDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "datasets/Coupon.xml", cleanBefore = true)
    public void updateToUsedTest() throws Exception {
        this.mockMvc.perform(put("/api/coupon/update/used/" + 3L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = "datasets/Coupon.xml", cleanBefore = true)
    public void updateToOverdueTest() throws Exception {
        this.mockMvc.perform(put("/api/coupon/update/overdue/" + 2L))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getCouponsByShopNameTest() throws Exception {
        this.mockMvc.perform(get("/api/coupon/{name}", "samsung"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
