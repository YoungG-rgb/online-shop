package com.amr.project.service.impl;

import com.amr.project.AbstractIntegrationTest;
import com.amr.project.model.entity.Review;
import com.amr.project.service.abstracts.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.util.DateUtil.now;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Created by Veilas on 11/12/2021.
 * Class: ReviewServiceImplTest.java
 */

public class ReviewServiceImplTest extends AbstractIntegrationTest {

    private ReviewService reviewService;

    @Autowired
    public ReviewServiceImplTest(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    private Review review() {
        return Review.builder()
                .logo(new ArrayList<>())
                .file(null)
                .item(null)
                .date(now())
                .dignity("dignity_test")
                .flaw("flaw_test")
                .text("text_test")
                .rating(5)
                .shop(null)
                .user(null)
                .isModerateAccept(false)
                .isModerated(false)
                .build();
    }

    @Test()
    void shouldBeAddReview() {
        reviewService.persist(review());
        Review review = reviewService.getAll().get(2);
        assertNotNull(review);
        assertEquals(review().getText(), review.getText());

    }

    @Test
    void shouldBeGetAllReviews() {
        List<Review> reviews = reviewService.getAll();
        assertNotNull(reviews);
    }

    @Test
    void shouldBegetReviewById() {
        reviewService.persist(review());
        assertNotNull(reviewService.getReviewById(3L));
    }

}
