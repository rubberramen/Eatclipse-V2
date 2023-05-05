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

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId).get();
    }

    public Review saveReview(ReviewFormDto reviewFormDto, Long memberId, Long shopId) {
        Member member = memberRepository.findById(memberId).get();
        Shop shop = shopRepository.findById(shopId).get();
        // TODO: 2023-05-05 005 model mapper로 Refactoring
        Review review = new Review(reviewFormDto.getTitle(), reviewFormDto.getContent(),
                0, 0, member, shop);
        reviewRepository.save(review);
        return review;
    }
}
