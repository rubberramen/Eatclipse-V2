package com.eatclipseV2.domain.review.dto;

import com.eatclipseV2.entity.Review;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ReviewFormDto {

    private Long id;

    @NotEmpty(message = "필수 입력 값")
    private String title;

    @NotEmpty(message = "필수 입력 값")
    private String content;

    @NotNull(message = "필수 입력 값")
    private Long memberId;

    @NotNull(message = "필수 입력 값")
    private Long shopId;

    private static ModelMapper modelMapper = new ModelMapper();

    // TODO: 2023-05-05 005 리뷰 수정시 of 메서드 만들기

    public Review createReview() {
        return modelMapper.map(this, Review.class);
    }
}
