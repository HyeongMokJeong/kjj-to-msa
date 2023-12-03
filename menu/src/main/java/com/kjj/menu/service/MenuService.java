package com.kjj.menu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjj.menu.dto.*;
import com.kjj.menu.entity.Menu;
import com.kjj.menu.entity.MenuFood;
import com.kjj.menu.exception.CantFindByIdException;
import com.kjj.menu.exception.WrongParameterException;
import com.kjj.menu.repository.MenuFoodRepository;
import com.kjj.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuFoodRepository menuFoodRepository;

    @Transactional(readOnly = true)
    public List<MenuManagerInfoDto> getMenusForManager() {
        return menuRepository.findAllWithMenuInfoAndMenuFood().stream()
                .map(MenuManagerInfoDto::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public Boolean isPlanned() { return menuRepository.existsByUsePlannerTrue(); }

    @Transactional
    public void setFood(Long menuId, Long foodId) throws CantFindByIdException {
        Menu menu = menuRepository.findById(menuId).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Menu를 찾을 수 없습니다.
                menuId : """ + menuId));
        if (foodId == 0) menu.clearMenuFood();
        else menu.setMenuFood(menuFoodRepository.getReferenceById(foodId));
    }

    @Transactional
    public MenuDto setPlanner(Long id) throws CantFindByIdException, WrongParameterException {
        if (menuRepository.existsByUsePlannerTrue()) throw new WrongParameterException("""
                이미 식단표를 사용하고 있습니다.
                id의 메뉴 정보를 수정할 수 없습니다.
                id : """ + id);

        Menu menu = menuRepository.findById(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Menu를 찾을 수 없습니다.
                menuId : """ + id));
        menu.usePlanner();
        return MenuDto.from(menu);
    }

    @Transactional
    public MenuDto changePlanner(Long id) throws CantFindByIdException {
        menuRepository.findByUsePlanner(true).ifPresent(Menu::notUsePlanner);

        Menu menu = menuRepository.findById(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Menu를 찾을 수 없습니다.
                menuId : """ + id));
        menu.usePlanner();
        return MenuDto.from(menu);
    }

    public MenuFoodDto addFood(String name, Map<String, Integer> data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return MenuFoodDto.from(menuFoodRepository.save(MenuFood.of(name, objectMapper.writeValueAsString(data))));
    }

    @Transactional(readOnly = true)
    public MenuFoodDto getOneFood(Long id) throws CantFindByIdException {
        return MenuFoodDto.from(menuFoodRepository.findById(id).orElseThrow(() -> new CantFindByIdException("""
                해당 id를 가진 Menu를 찾을 수 없습니다.
                menuId : """ + id)));
    }
}
