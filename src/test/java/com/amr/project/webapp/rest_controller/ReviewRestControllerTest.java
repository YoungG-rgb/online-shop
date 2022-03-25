package com.amr.project.webapp.rest_controller;

import com.amr.project.AbstractApiTest;
import com.amr.project.converter.ReviewMapper;
import com.amr.project.model.dto.ReviewDto;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ItemService;
import com.amr.project.service.abstracts.ReviewService;
import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.assertj.core.util.DateUtil.now;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewRestControllerTest extends AbstractApiTest {

    private final static String ADD_REVIEW_FOR_ITEM_URL = "/api/review/item";
    private final static String ADD_REVIEW_FOR_SHOP_URL = "/api/review/shop";
    private final static String GET_REVIEW_BY_ID_URL = "/api/review/1";
    private final static String GET_ALL_ITEMS_REVIEW_URL = "/api/review/items";
    private final static String GET_ALL_SHOPS_REVIEW_URL = "/api/review/shops";


    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;
    private final ItemService itemService;


    @Autowired
    public ReviewRestControllerTest(ReviewService reviewService, ReviewMapper reviewMapper, ItemService itemService) {
        this.reviewService = reviewService;
        this.reviewMapper = reviewMapper;
        this.itemService = itemService;
    }

    @Test
    @WithMockUser(username = "user6_username")
    @DataSet(value = {"datasets/review/ReviewEmpty.xml", "datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml", "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    @ExpectedDataSet(value = "datasets/expected/review/reviewItemExpected.xml")
    void shouldBeAddReviewItem() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                .dignity("dignity_review_user6")
                .date(now())
                .flaw("flaw_review_user6")
                .text("text_review_user6")
                .rating(4)
                .itemName("item1")
                .build();
        Review review = reviewMapper.dtoToReview(reviewDto);
        mvc.perform(post(ADD_REVIEW_FOR_ITEM_URL)
                        .content(asJsonString(review))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    @WithMockUser(username = "user6_username")
    @DataSet(value = {"datasets/review/ReviewEmpty.xml", "datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml", "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    @ExpectedDataSet(value = "datasets/expected/review/reviewShopExpected.xml")
    void shouldBeAddReviewShop() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                .dignity("dignity_review_user6")
                .date(now())
                .flaw("flaw_review_user6")
                .text("text_review_user6")
                .rating(4)
                .shopName("shop1")
                .build();
        Review review = reviewMapper.dtoToReview(reviewDto);
        mvc.perform(post(ADD_REVIEW_FOR_SHOP_URL)
                        .content(asJsonString(review))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = {"datasets/review/ReviewEmpty.xml", "datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml", "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    @WithMockUser(username = "user6_username")
    void notShouldBeAddReviewItem() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                .dignity("dignity_review_user6")
                .date(now())
                .text("text_review_user6")
                .rating(4)
                .itemName("item1")
                .build();
        Review review = reviewMapper.dtoToReview(reviewDto);
        mvc.perform(
                        post(ADD_REVIEW_FOR_ITEM_URL)
                                .content(asJsonString(review))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }


    @Test
    @DataSet(value = {"datasets/review/ReviewEmpty.xml", "datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml", "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml"},
            cleanBefore = true, useSequenceFiltering = false)
    @WithMockUser(username = "user6_username")
    void notShouldBeAddReviewShop() throws Exception {
        ReviewDto reviewDto = ReviewDto.builder()
                .date(now())
                .flaw("flaw_review_user6")
                .text("text_review_user6")
                .rating(4)
                .shopName("shop1")
                .build();
        Review review = reviewMapper.dtoToReview(reviewDto);
        mvc.perform(post(ADD_REVIEW_FOR_SHOP_URL)
                        .content(asJsonString(review))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DataSet(value = {"datasets/review/ReviewEmpty.xml", "datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml", "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/Favorite.xml", "datasets/expected/review/reviewOneExpected.xml",},
            cleanBefore = true, useSequenceFiltering = false)
    void shouldBeGetReviewById() throws Exception {
        mvc.perform(get(GET_REVIEW_BY_ID_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = {"datasets/review/ReviewEmpty.xml", "datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml", "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/shop/Shop.xml", "datasets/Item.xml",
            "datasets/Favorite.xml", "datasets/review/ReviewShops.xml",},
            cleanBefore = true, useSequenceFiltering = false)
    void shouldBeGetAllReviewItems() throws Exception {
        mvc.perform(get(GET_ALL_ITEMS_REVIEW_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @DataSet(value = {"datasets/review/ReviewEmpty.xml", "datasets/City.xml",
            "datasets/Country.xml", "datasets/Address.xml", "datasets/Image.xml",
            "datasets/user/data/User.xml", "datasets/shop/Shop.xml", "datasets/Item.xml",
            "datasets/Favorite.xml", "datasets/review/ReviewShops.xml",},
            cleanBefore = true, useSequenceFiltering = false)
    void shouldBeGetAllReviewShops() throws Exception {
        mvc.perform(get(GET_ALL_SHOPS_REVIEW_URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }
}