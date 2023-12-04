package com.kjj.calculate.controller.manager;

import com.kjj.calculate.dto.*;
import com.kjj.calculate.dto.weeklyFoodPredict.WeeklyFoodPredictDto;
import com.kjj.calculate.exception.CantFindByIdException;
import com.kjj.calculate.exception.StringToMapException;
import com.kjj.calculate.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/state/manager")
public class CalculateManagerApiController {

    private final StoreService storeService;

    @GetMapping("/store/sales")
    public ResponseEntity<StoreSalesDto> getSales() {
        return ResponseEntity.ok(storeService.getSales());
    }

    @GetMapping("/today")
    public ResponseEntity<StoreTodayDto> getToday() {
        return ResponseEntity.ok(storeService.getToday());
    }

    @GetMapping("/last-week/user")
    public ResponseEntity<List<StoreWeekendDto>> getLastWeeksUser() {
        return ResponseEntity.ok(storeService.getLastWeeksUser());
    }

    @GetMapping("/next-week/user")
    public ResponseEntity<CalculatePreWeekDto> getNextWeeksUser() {
        return ResponseEntity.ok(storeService.getNextWeeksUser());
    }

    @GetMapping("/next-week/food")
    public ResponseEntity<WeeklyFoodPredictDto> getNextWeeksFood() {
        return ResponseEntity.ok(storeService.getNextWeeksFood());
    }

    @GetMapping("/all")
    public ResponseEntity<Integer> getAllUsers() {
        return ResponseEntity.ok(storeService.getAllUsers());
    }

    @GetMapping("/predict/user")
    public ResponseEntity<StorePreDto> getCalculatePreUser() {
        return ResponseEntity.ok(storeService.getCalculatePreUser());
    }

    @GetMapping("/predict/food")
    public ResponseEntity<Map<String, Integer>> getCalculatePreFood() throws CantFindByIdException, StringToMapException {
        return ResponseEntity.ok(storeService.getCalculatePreFood());
    }

    @GetMapping("/predict/menu")
    public ResponseEntity<Map<String, Integer>> getCalculatePreMenu() throws CantFindByIdException, StringToMapException {
        return ResponseEntity.ok(storeService.getCalculatePreMenu());
    }
}
