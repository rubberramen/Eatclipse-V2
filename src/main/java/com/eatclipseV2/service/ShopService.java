package com.eatclipseV2.service;

import com.eatclipseV2.domain.shop.dto.ShopLoginFormDto;
import com.eatclipseV2.entity.Shop;
import com.eatclipseV2.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ShopService {

    private final ShopRepository shopRepository;

    public Shop loginShop(String name, String password) {
        Shop shop = shopRepository.findByName(name);
        if (shop == null) {
            throw new IllegalStateException("식당이 존재하지 않습니다");
        } else {
            if (shop.getPassword().equals(password)) {
                return shop;
            } else {
                throw new IllegalStateException("비밀번호가 일치하지 않습니다");
            }
        }
    }

    public List<Shop> findAllShop() {
        return shopRepository.findAll();
    }

    public Shop findShop(Long shopId) {
        return shopRepository.findById(shopId).get();
    }
}
