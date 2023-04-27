package com.eatclipseV2.domain.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberFormDto {

    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;

    @NotEmpty(message = "닉네임은 필수 입력 값입니다.")
    private String nickName;

    @NotEmpty(message = "비밀번호는 필수 입력 값입니다.")
    @Length(min=4, max=16, message = "비밀번호는 4자 이상, 16자 이하로 입력해주세요")
    private String password;

    @NotEmpty(message = "도시는 필수 입력 값입니다.")
    private String city;
    @NotEmpty(message = "거리명은 필수 입력 값입니다.")
    private String street;
    @NotEmpty(message = "우편번호는 필수 입력 값입니다.")
    private String zipcode;
}
