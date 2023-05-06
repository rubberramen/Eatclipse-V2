package com.eatclipseV2.controller;

import com.eatclipseV2.common.MessageDto;
import com.eatclipseV2.common.StringConst;
import com.eatclipseV2.domain.review.dto.ReviewFormDto;
import com.eatclipseV2.entity.Member;
import com.eatclipseV2.entity.Review;
import com.eatclipseV2.entity.Shop;
import com.eatclipseV2.service.ReviewService;
import com.eatclipseV2.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;
    private final ShopService shopService;

    @GetMapping("/member")
    public String reviews(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                          Model model) {

        model.addAttribute("member", loginMember);
        List<Review> allReviews = reviewService.findAllReviews();
        model.addAttribute("reviews", allReviews);

        return "reviews/reviewListByMember";
    }

    @GetMapping("/shop")
    public String reviews(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                          Model model) {

        model.addAttribute("shop", loginShop);
        List<Review> reviews = reviewService.findReviewsByShop(loginShop.getId());
        model.addAttribute("reviews", reviews);

        return "reviews/reviewListByShop";
    }

    @GetMapping("/member/{reviewId}")
    public String reviewDtlByMember(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                            @PathVariable Long reviewId,
                            Model model) {

        model.addAttribute("member", loginMember);
        Review review = reviewService.findReview(reviewId);
        model.addAttribute("review", review);

        return "reviews/reviewDtlByMember";
    }

    @GetMapping("/shop/{reviewId}")
    public String reviewDtlByShop(@SessionAttribute(name = StringConst.LOGIN_SHOP) Shop loginShop,
                            @PathVariable Long reviewId,
                            Model model) {

        model.addAttribute("shop", loginShop);
        Review review = reviewService.findReview(reviewId);
        model.addAttribute("review", review);

        return "reviews/reviewDtlByShop";
    }

    @GetMapping("/new")
    public String writeReview(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                              @ModelAttribute ReviewFormDto reviewFormDto, Model model) {
        
        model.addAttribute("member", loginMember);

        List<Shop> allShop = shopService.findAllShop();
        model.addAttribute("allShop", allShop);

        return "reviews/reviewWriteForm";  // TODO: 2023-05-04 004 작성 중
    }

    @PostMapping("/new")
    public String writeReview(@SessionAttribute(name = StringConst.LOGIN_MEMBER) Member loginMember,
                      @Valid ReviewFormDto reviewFormDto, BindingResult bindingResult,
                      Model model) {

        model.addAttribute("member", loginMember);

        if (bindingResult.hasErrors()) {
            List<Shop> allShop = shopService.findAllShop();
            model.addAttribute("allShop", allShop);
            return "reviews/reviewWriteForm";
        }
        Shop shop = shopService.findShop(reviewFormDto.getShopId());
        Review review = reviewService.saveReview(reviewFormDto, loginMember.getId(), shop.getId());


        MessageDto messageDto = new MessageDto("리뷰가 작성되었습니다",
                "/reviews/member/" + review.getId(), RequestMethod.GET, null);
        return showMessageAndRedirect(messageDto, model);
    }

    private String showMessageAndRedirect(final MessageDto messageDto, Model model) {
        model.addAttribute("params", messageDto);
        return "common/messageRedirect";
    }
}
