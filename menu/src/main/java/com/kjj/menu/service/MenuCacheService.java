package com.kjj.menu.service;

import com.kjj.menu.client.ImageClient;
import com.kjj.menu.client.OrderClient;
import com.kjj.menu.client.UserClient;
import com.kjj.menu.dto.*;
import com.kjj.menu.entity.Menu;
import com.kjj.menu.entity.MenuInfo;
import com.kjj.menu.exception.CantFindByIdException;
import com.kjj.menu.exception.SameNameException;
import com.kjj.menu.exception.WrongParameterException;
import com.kjj.menu.repository.MenuFoodRepository;
import com.kjj.menu.repository.MenuInfoRepository;
import com.kjj.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class MenuCacheService {
    private final MenuRepository menuRepository;
    private final MenuInfoRepository menuInfoRepository;
    private final MenuFoodRepository menuFoodRepository;
    private final ImageClient imageClient;
    private final OrderClient orderClient;
    private final UserClient userClient;

    private static final String REDIS_CACHE_MANAGER = "redisCacheManager";

    private static final String ALL_MENUS_CACHE_KEY = "1";
    private static final String ALL_MENUS_CACHE_VALUE = "AllMenus";

    public static final String ALL_FOODS_CACHE_KEY = "2";
    public static final String ALL_FOODS_CACHE_VALUE = "AllFoods";

    public Boolean existsById(Long id) {
        return menuRepository.existsById(id);
    }

    public MenuNameAndCostDto getNameAndCost(Long id) {
        return menuRepository.findById(id)
                .map(MenuNameAndCostDto::from)
                .orElseThrow(() -> new CantFindByIdException("""
                        해당 id를 가진 메뉴를 찾을 수 없습니다.
                        menuId = """ + id));
    }

    public String getName(Long id) {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new CantFindByIdException("""
                        해당 id를 가진 메뉴를 찾을 수 없습니다.
                        menuId = """ + id));
        return menu.getName();
    }

    @Transactional(readOnly = true)
    @Cacheable(value = ALL_MENUS_CACHE_VALUE, key = ALL_MENUS_CACHE_KEY, cacheManager = REDIS_CACHE_MANAGER)
    public MenuUserInfoDtos getMenus() {
        return MenuUserInfoDtos.from(menuRepository.findAllWithMenuInfoAndMenuFood().stream()
                .map(MenuUserInfoDto::from)
                .toList());
    }

    @Transactional
    @CacheEvict(value = ALL_MENUS_CACHE_VALUE, key = ALL_MENUS_CACHE_KEY, cacheManager = REDIS_CACHE_MANAGER)
    public MenuDto addMenu(MenuUpdateDto dto, String filePath) throws SameNameException {
        if (menuRepository.existsByName(dto.getName())) throw new SameNameException("""
                이미 해당 이름을 가진 메뉴가 존재합니다.
                dto : """ + dto);
        if (menuRepository.existsByUsePlannerTrue() && dto.getUsePlanner()) throw new SameNameException("""
                이미 식단을 사용중인 메뉴가 있는 상태입니다.
                식단을 미사용하거나, 기존에 식단을 사용하는 메뉴를 취소해주세요.
                dto : """ + dto);

        MenuInfo menuInfo = menuInfoRepository.save(dto.from());
        Menu menu = menuRepository.save(Menu.of(dto, menuInfo, filePath));
        return MenuDto.from(menu);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = ALL_FOODS_CACHE_VALUE, key = ALL_FOODS_CACHE_KEY, cacheManager = REDIS_CACHE_MANAGER)
    public MenuFoodDtos getFood() {
        return MenuFoodDtos.from(menuFoodRepository.findAll().stream()
                .map(MenuFoodDto::from)
                .toList());
    }

    @Transactional
    @CacheEvict(value = ALL_MENUS_CACHE_VALUE, key = ALL_MENUS_CACHE_KEY, cacheManager = REDIS_CACHE_MANAGER)
    public MenuInfoDto updateMenu(MenuUpdateDto dto, MultipartFile file, Long id, String uploadDir) throws CantFindByIdException, IOException {
        Menu menu = menuRepository.findByIdWithMenuInfo(id).orElseThrow(() ->
                new CantFindByIdException("""
                해당 id를 가진 Menu를 찾을 수 없습니다.
                menuId : """ + id));

        if (file != null) {
            if (menu.getImage() == null) menu.setImage(imageClient.uploadImage(file, uploadDir));
            else imageClient.updateImage(file, menu.getImage());
        }
        MenuInfo menuInfo = menu.getMenuInfo();

        menu.patch(dto);
        menuInfo.patch(dto);
        return MenuInfoDto.from(menu);
    }

    @Transactional
    @CacheEvict(value = ALL_MENUS_CACHE_VALUE, key = ALL_MENUS_CACHE_KEY, cacheManager = REDIS_CACHE_MANAGER)
    public MenuDto deleteMenu(Long id) throws CantFindByIdException {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Menu를 찾을 수 없습니다.
                menuId : """ + id));

        menuRepository.delete(menu);
        CompletableFuture.runAsync(() -> orderClient.deleteAllByMenu(menu.getName()));
        CompletableFuture.runAsync(() -> userClient.clearPolicyByDeletedMenu(id));

        return MenuDto.from(menu);
    }

    @Transactional
    @CacheEvict(value = ALL_MENUS_CACHE_VALUE, key = ALL_MENUS_CACHE_KEY, cacheManager = REDIS_CACHE_MANAGER)
    public MenuDto setSoldOut(Long id, String type) throws CantFindByIdException {
        Menu menu = menuRepository.findById(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Menu를 찾을 수 없습니다.
                menuId : """ + id));

        switch (type) {
            case "n" -> menu.setSoldFalse();
            case "y" -> menu.setSoldTrue();
            default -> throw new WrongParameterException(type);
        }
        return MenuDto.from(menu);
    }
}
