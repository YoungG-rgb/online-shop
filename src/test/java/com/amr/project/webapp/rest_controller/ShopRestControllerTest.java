package com.amr.project.webapp.rest_controller;

import com.amr.project.AbstractApiTest;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.CountryDto;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Veilas on 11/8/2021.
 * Class: ShopRestControllerTest.java
 */

public class ShopRestControllerTest extends AbstractApiTest {

    private final static String REGISTRATION_URL = "/shop_api/";
    private final static String GET_ALL_URL = "/shop_api/shops";
    private final static String GET_SHOP_BY_ID_URL = "/shop_api/shop/1";
    private final static String GET_SHOP_BY_NAME_URL = "/shop_api/shop1";

    private ShopMapper shopMapper;
    private ShopService shopService;
    private UserService userService;

    @Autowired
    public ShopRestControllerTest(ShopMapper shopMapper, ShopService shopService, UserService userService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
        this.userService = userService;
    }

    @Test
    @DataSet(value = {"datasets/review/ReviewEmpty.xml","datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml",  "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml", "datasets/shop/Shop.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    void getAllShopsTest() throws Exception {
        mvc.perform(get(GET_ALL_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = {"datasets/review/ReviewEmpty.xml","datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml",  "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml", "datasets/shop/ShopOne.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    void getShopByIdTest() throws Exception {
        mvc.perform(get(GET_SHOP_BY_ID_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = {"datasets/review/ReviewEmpty.xml","datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml",  "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml", "datasets/shop/ShopOne.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    void getShopByNameTest() throws Exception {
        mvc.perform(get(GET_SHOP_BY_NAME_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user6_username")
    @DataSet(value = {"datasets/review/ReviewEmpty.xml","datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml",  "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    @ExpectedDataSet(value = "datasets/expected/shopExpected.xml")
    void registrationNewShop_then200Status() throws Exception {
        Shop shop = shopMapper.dtoToShop(shopDto());
        mvc.perform(post(REGISTRATION_URL)
                        .content(asJsonString(shop))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @WithMockUser(username = "user1_username")
    @DataSet(value = {"datasets/review/ReviewEmpty.xml","datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml",  "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    void registrationNewShop_WrongValidation_then400Status() throws Exception {
        Shop shopWrong = shopMapper.dtoToShop(shopDtoWrong());

        mvc.perform(post(REGISTRATION_URL)
                        .content(asJsonString(shopWrong))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }


    public ShopDto shopDto() throws IOException {
        ShopDto shopDto = ShopDto.builder()
                .logo(List.of(getImageDto()))
                .name("shop_test")
                .description("desc_test")
                .phone("88855535355")
                .email("mail@test.r")
                .rating(1)
                .username("user6_username")
                .location(CountryDto.builder().id(1L).name("Russia").build())
                .build();
        return shopDto;
    }

    public ShopDto shopDtoWrong() throws IOException {
        ShopDto shopDto = ShopDto.builder()
                .logo(List.of(getImageDto()))
                .name("shop_test")
                .description("desc_test")
                .phone("0")
                .email("mail@test.r")
                .rating(1)
                .username("user6_username")
                .location(CountryDto.builder().id(1L).name("Russia").build())
                .build();
        return shopDto;
    }

    private ImageDto getImageDto() throws IOException {
        File shop1_image = ResourceUtils.getFile("classpath:static/images/shops/shop1_image.jpg");
        byte[] array_shop1_image = Files.readAllBytes(shop1_image.toPath());
        ImageDto shop1Image = ImageDto.builder().picture(array_shop1_image).build();
        return shop1Image;
    }
}
