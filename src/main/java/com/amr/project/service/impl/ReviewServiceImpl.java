package com.amr.project.service.impl;


import com.amr.project.dao.impl.ReviewDaoImpl;
import com.amr.project.model.entity.Order;
import com.amr.project.model.entity.Review;
import com.amr.project.model.enums.Status;
import com.amr.project.service.abstracts.ReviewService;
import com.amr.project.service.email.EmailSenderService;
import com.amr.project.util.TrackedEmailReview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReviewServiceImpl extends ReadWriteServiceImpl<Review, Long> implements ReviewService {

    private final ReviewDaoImpl reviewDao;
    private final EmailSenderService emailSenderService;
    private final TrackedEmailReview trackedEmailReview;
    private final OrderServiceImpl orderService;


    @Autowired
    public ReviewServiceImpl(TrackedEmailReview trackedEmailService, ReviewDaoImpl reviewDao, EmailSenderService emailSenderService, OrderServiceImpl orderService) {
        super(reviewDao);
        this.reviewDao = reviewDao;
        this.trackedEmailReview = trackedEmailService;
        this.emailSenderService = emailSenderService;
        this.orderService = orderService;
    }

    @Override
    public List<Review> getUnmoderatedReviews() {
        return reviewDao.getUnmoderatedReviews();
    }

    @Override
    public List<Review> getModeratedReviews() {
        return reviewDao.getModeratedReviews();
    }

    @Override
    public Review getReviewById(long id) {
        return reviewDao.getByKey(id);
    }

    @Override
    @Transactional
    public void persist(Review review) {
        boolean isEmptyOrder = true;

        if (review.getShop() != null) {
            List<Order> orders = orderService.findAllByUserAndStatusAndShopId(
                    review.getUser().getId(),
                    Status.COMPLETE,
                    review.getShop().getId());

            if (!orders.isEmpty()) {
                isEmptyOrder = false;
            }
        }

        if (review.getItem() != null) {
            List<Order> orders = orderService.findAllByUserAndStatusAndItemId(
                    review.getUser().getId(),
                    Status.COMPLETE,
                    review.getItem().getId());

            if (!orders.isEmpty()) {
                isEmptyOrder = false;
            }
        }

        if (!isEmptyOrder) {
            if (review.getShop() != null) {
                emailSenderService.sendSimpleEmail(trackedEmailReview.trackedEmailReviewPersist(review));
            }
            reviewDao.persist(review);
        }

    }

    @Override
    @Transactional
    public void delete(Review review) {
        emailSenderService.sendSimpleEmail(trackedEmailReview.trackedEmailReviewDelete(review));
        reviewDao.delete(review);

    }
}
