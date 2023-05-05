package com.eatclipseV2.domain.review.dto;

import lombok.Getter;
import lombok.Setter;

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
}
