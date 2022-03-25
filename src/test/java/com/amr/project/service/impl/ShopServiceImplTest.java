package com.amr.project.service.impl;

import com.amr.project.AbstractIntegrationTest;
import com.amr.project.converter.ShopMapper;
import com.amr.project.model.dto.CountryDto;
import com.amr.project.model.dto.ImageDto;
import com.amr.project.model.dto.ShopDto;
import com.amr.project.model.entity.Shop;
import com.amr.project.model.entity.User;
import com.amr.project.service.abstracts.ShopService;
import com.amr.project.service.abstracts.UserService;
import com.amr.project.service.email.EmailSenderService;
import com.amr.project.util.TrackedEmailShop;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.parameters.P;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ShopServiceImplTest extends AbstractIntegrationTest {

    private final ShopMapper shopMapper;
    private final ShopService shopService;
    private final UserService userService;

    @Autowired
    public ShopServiceImplTest(ShopMapper shopMapper, ShopService shopService,
                               UserService userService) {
        this.shopMapper = shopMapper;
        this.shopService = shopService;
        this.userService = userService;
    }

    @Test()
    void shouldBeAddShop() throws IOException {
        Shop shopSave = shopMapper.dtoToShop(shopDto());
        shopSave.setUser(userService.findByUsername("user6_username"));
        shopService.persist(shopSave);
        Shop shopGet = shopService.getShop(shopSave.getName());

        assertNotNull(shopGet);
        assertEquals(shopDto().getName(), shopGet.getName());
    }

    @Test
    void shouldBeGetAllShops() {
        List<Shop> shops = shopService.getAll();
        assertNotNull(shops);
    }

    @Test
    void shouldBeGetShopById() {
        assertNotNull(shopService.getShopId(1L));
    }

    @Test
    void shouldBegetShopByName() {
        assertNotNull(shopService.getShop("shop1"));
    }

    @Test
    void shouldBeGetMostPopularShop() {
        List<Shop> shopsTest = shopService.getAll();
        shopsTest.sort(Comparator.comparing(Shop::getRating));
        List<Shop> shops = shopService.getMostPopular(shopsTest.size());
        for (int i = 0; i < shops.size(); i++) {
            assertEquals(shops.get(i).getName(), shops.get(i).getName());
        }
    }

    private ShopDto shopDto() throws IOException {
        return ShopDto.builder()
                .logo(List.of(getImageDto()))
                .name("shop_test")
                .description("desc_test")
                .phone("88855535355")
                .email("mail@test.r")
                .rating(0)
                .username("user6_username")
                .location(CountryDto.builder().id(1L).name("Russia").build())
                .build();
    }

    private ImageDto getImageDto() throws IOException {
        File shop1_image = ResourceUtils.getFile("classpath:static/images/shops/shop1_image.jpg");
        byte[] array_shop1_image = Files.readAllBytes(shop1_image.toPath());
        ImageDto shop1Image = ImageDto.builder().picture(array_shop1_image).build();
        return shop1Image;
    }
}