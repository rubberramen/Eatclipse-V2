package com.eatclipseV2.domain.menu.dto;

import com.eatclipseV2.entity.Menu;
import com.eatclipseV2.entity.enums.MenuSellStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MenuFormDto {

    private Long id;

    @NotBlank(message = "이름은 필수 값입니다.")
    private String name;

    @NotNull(message = "가격은 필수 입력 값입니다.")
    @Range(min=3000, max=100000, message = "가격은 3000원 이상, 100,000원 이하입니다.")
    private int price;

    @NotEmpty(message = "메뉴설명은 필수 입력 값입니다.")
    private String menuDtl;

//    @EnumValid
    private MenuSellStatus menuSellStatus;

    private static ModelMapper modelMapper = new ModelMapper();

    public static MenuFormDto of(Menu menu) {
        return modelMapper.map(menu, MenuFormDto.class);
    }

    public Menu createMenu() {
        return modelMapper.map(this, Menu.class);
    }
}
