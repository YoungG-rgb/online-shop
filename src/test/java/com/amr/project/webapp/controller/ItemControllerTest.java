package com.amr.project.webapp.controller;


import com.amr.project.inserttestdata.repository.ItemRepository;
import com.amr.project.model.entity.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ItemControllerTest {

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPage_thenStatus200andItemIdReturned() throws Exception {
        Item item1 = Item.builder()
                .name("test")
                .price(new BigDecimal("13990"))
                .categories(null)
                .images(null)
                .reviews(null)
                .count(100)
                .rating(1.0)
                .description("Система активного подавления шума")
                .discount(0)
                .shop(null)
                .isModerated(false)
                .isModerateAccept(false)
                .moderatedRejectReason(null)
                .isPretendentToBeDeleted(false)
                .build();
        itemRepository.save(item1);
        mockMvc.perform(get("/item/{id}", itemRepository.findByName("test").getId()))
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}