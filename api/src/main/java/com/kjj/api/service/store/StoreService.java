package com.kjj.api.service.store;

import com.kjj.api.dto.calculate.CalculateMenuForGraphDto;
import com.kjj.api.dto.calculate.CalculatePreWeekDto;
import com.kjj.api.dto.sbiz.WeeklyFoodPredictDto;
import com.kjj.api.dto.store.*;
import com.kjj.api.exception.exceptions.CantFindByIdException;
import com.kjj.api.exception.exceptions.SameNameException;
import com.kjj.api.exception.exceptions.StringToMapException;
import com.kjj.api.exception.exceptions.UploadFileException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface StoreService {
    StoreDto isSetting();

    StoreDto getStoreData() throws CantFindByIdException;

    StoreDto setSetting(StoreSettingDto dto) throws SameNameException;

    void setStoreImage(MultipartFile file) throws CantFindByIdException, UploadFileException;

    StoreTodayDto getToday();

    List<StoreWeekendDto> getLastWeeksUser();

    int getAllUsers();

    List<CalculateMenuForGraphDto> getPopularMenus();

    StoreDto updateStoreTitle(StoreTitleDto dto) throws CantFindByIdException;

    StoreDto updateStoreInfo(StoreInfoDto dto) throws CantFindByIdException;

    StoreStateDto setOff(StoreOffDto dto, int year, int month, int day);

    List<StoreStateDto> getClosedDays(int year, int month);

    StorePreDto getCalculatePreUser();

    Map<String, Integer> getCalculatePreFood() throws StringToMapException, CantFindByIdException;

    Map<String, Integer> getCalculatePreMenu() throws StringToMapException, CantFindByIdException;

    CalculatePreWeekDto getNextWeeksUser();

    WeeklyFoodPredictDto getNextWeeksFood();

    StoreSalesDto getSales();
}
