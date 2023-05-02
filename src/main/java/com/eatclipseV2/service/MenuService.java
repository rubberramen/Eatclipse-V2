package com.eatclipseV2.service;

import com.eatclipseV2.domain.menu.dto.MenuFormDto;
import com.eatclipseV2.entity.Menu;
import com.eatclipseV2.entity.Shop;
import com.eatclipseV2.repository.MenuRepository;
import com.eatclipseV2.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final ShopRepository shopRepository;

    @PersistenceContext
    EntityManager em;

    public List<Menu> findMenusByShopId(Long shopId) {
        List<Menu> menusByShopId = menuRepository.findMenusByShopId(shopId);
        return menusByShopId;
    }

    public Menu addMenu(MenuFormDto menuFormDto, Long shopId) {
        Optional<Shop> byId = shopRepository.findById(shopId);
        Shop shop = byId.get();
        Menu menu = new Menu(menuFormDto.getName(), menuFormDto.getPrice(),
                menuFormDto.getMenuDtl(), menuFormDto.getMenuSellStatus(), shop);
        menuRepository.save(menu);
        return menu;
    }

    public Menu menuDtl(Long menuId) {
        return menuRepository.findById(menuId).get();
    }

    public void updateMenu(Long shopId, MenuFormDto menuFormDto) {

        Menu menu = menuFormDto.createMenu();
        List<Menu> menusByShopId = menuRepository.findMenusByShopId(shopId);

        Menu byId = menuRepository.findById(menu.getId()).get();
        byId.setName(menu.getName());
        byId.setMenuDtl(menu.getMenuDtl());
        byId.setMenuSellStatus(menu.getMenuSellStatus());
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        menuRepository.deleteById(menuId);
    }

    public Menu findMenuById(Long menuId) {
        return menuRepository.findById(menuId).get();
    }
}
