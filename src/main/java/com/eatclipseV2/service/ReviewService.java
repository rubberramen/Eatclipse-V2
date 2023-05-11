package com.eatclipseV2.service;

import com.eatclipseV2.domain.review.dto.ReviewFormDto;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.entity.Review;
import com.eatclipseV2.entity.Shop;
import com.eatclipseV2.repository.MemberRepository;
import com.eatclipseV2.repository.ReviewRepository;
import com.eatclipseV2.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final MemberRepository memberRepository;
    private final ShopRepository shopRepository;

    // TODO: 2023-05-11 011 findAll이 delete_yn이 0인 것만 표시해야 겠네 
    public List<Review> findAllReviews() {
        return reviewRepository.findAllOrder();
    }

    public List<Review> findReviewsByShop(Long shopId) {
        List<Review> reviewByShop = reviewRepository.findByShopIdOrder(shopId);
        return reviewByShop;
    }

    public Review findReview(Long reviewId) {
        Review review = reviewRepository.findById(reviewId).get();
        int viewCnt = review.getViewCnt();
        review.setViewCnt(++viewCnt);
        return review;
    }

    public Review saveReview(ReviewFormDto reviewFormDto, Long memberId, Long shopId) {
        Member member = memberRepository.findById(memberId).get();
        Shop shop = shopRepository.findById(shopId).get();

        Review review = reviewFormDto.createReview();

        reviewRepository.save(review);
        return review;
    }

    public void deleteReview(Long reviewId) {  // TODO: 2023-05-11 011 삭제가 아니라 delete_yn 변경으로
        reviewRepository.deleteById(reviewId);
    }
}
