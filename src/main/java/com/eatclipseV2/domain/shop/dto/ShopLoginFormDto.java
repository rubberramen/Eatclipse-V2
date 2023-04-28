package com.eatclipseV2.domain.shop.dto;

import com.eatclipseV2.entity.Shop;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class ShopLoginFormDto {

    @NotBlank(message = "식당명을 입력해 주세요")
    private String name;

    @NotBlank(message = "비밀번호를 입력해 주세요")
    private String password;

    private static ModelMapper modelMapper = new ModelMapper();

    public Shop createShop() {
        return modelMapper.map(this, Shop.class);
    }
}
