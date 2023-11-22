package com.kjj.api.service.menu;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kjj.api.dto.menu.*;
import com.kjj.api.exception.exceptions.CantFindByIdException;
import com.kjj.api.exception.exceptions.SameNameException;
import com.kjj.api.exception.exceptions.UploadFileException;
import com.kjj.api.exception.exceptions.WrongParameter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface MenuService {
    MenuUserInfoDtos getMenus();

    List<MenuManagerInfoDto> getMenusForManager();

    Boolean isPlanned();

    MenuDto addMenu(MenuUpdateDto dto, String filePath) throws SameNameException;

    void setFood(Long menuId, Long foodId) throws CantFindByIdException;

    MenuFoodDtos getFood();

    MenuInfoDto updateMenu(MenuUpdateDto dto, MultipartFile file, Long id, String uploadDir) throws CantFindByIdException, UploadFileException;

    MenuDto deleteMenu(Long id) throws CantFindByIdException;

    MenuDto setSoldOut(Long id, String type) throws CantFindByIdException, WrongParameter;

    MenuDto setPlanner(Long id) throws CantFindByIdException, WrongParameter;

    MenuDto changePlanner(Long id) throws CantFindByIdException;

    MenuFoodDto addFood(String name, Map<String, Integer> data) throws JsonProcessingException;

    MenuFoodDto getOneFood(Long id) throws CantFindByIdException;
}
