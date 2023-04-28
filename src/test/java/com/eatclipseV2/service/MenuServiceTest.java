package com.eatclipseV2.service;

import com.eatclipseV2.entity.Menu;
import com.eatclipseV2.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuServiceTest {

    @Autowired
    MenuRepository menuRepository;

    @Test
    public void delete() throws Exception {
        // given
        List<Menu> all = menuRepository.findAll();
        for (Menu menu : all) {
            System.out.println("menu = " + menu);
        }

        menuRepository.deleteById(4L);
        // when

        // then
    }


}