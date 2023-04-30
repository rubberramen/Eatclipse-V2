package com.eatclipseV2.domain.rider.dto;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RiderLoginFormDto {

    @NotBlank(message = "닉네임을 입력해 주세요")
    private String nickName;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;

    private static ModelMapper modelMapper = new ModelMapper();

}
