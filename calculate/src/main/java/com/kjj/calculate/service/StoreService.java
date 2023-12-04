package com.kjj.calculate.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kjj.calculate.dto.*;
import com.kjj.calculate.dto.weeklyFoodPredict.WeeklyFoodPredictDto;
import com.kjj.calculate.entity.Calculate;
import com.kjj.calculate.entity.CalculateMenu;
import com.kjj.calculate.entity.CalculatePre;
import com.kjj.calculate.exception.CantFindByIdException;
import com.kjj.calculate.exception.StringToMapException;
import com.kjj.calculate.repository.*;
import com.kjj.calculate.tool.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class StoreService {

    private final CalculateRepository calculateRepository;
    private final CalculateMenuRepository calculateMenuRepository;
    private final CalculatePreWeekRepository calculatePreWeekRepository;
    private final CalculatePreRepository calculatePreRepository;
    private final WeelkyFoodPredictRepository weelkyFoodPredictRepository;
    private final DateUtil dateUtil;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Transactional(readOnly = true)
    public StoreTodayDto getToday() {
        return StoreTodayDto.from(calculateRepository.findLastTwoToday());
    }

    
    @Transactional(readOnly = true)
    public StoreSalesDto getSales() {
        return StoreSalesDto.from(calculateRepository.findLastTwoSales());
    }

    
    @Transactional(readOnly = true)
    public List<StoreWeekendDto> getLastWeeksUser() {
        List<StoreWeekendDto> result = new ArrayList<>();
        LocalDate date = dateUtil.getLastWeeksMonday(0);

        int dataSize = 5;
        for (int i = 0; i < dataSize; i ++) {
            LocalDate targetDate = date.plusDays(i);
            Calculate calculate = calculateRepository.findByDate(targetDate).orElse(null);

            if (calculate == null) result.add(StoreWeekendDto.newZeroDataStoreWeekendDto(targetDate));
            else result.add(StoreWeekendDto.of(targetDate, calculate.getToday()));
        }

        return result;
    }

    
    @Transactional(readOnly = true)
    public int getAllUsers() {
        Integer result = calculateMenuRepository.getAllUsers();
        return (result != null) ? result : 0;
    }


    private Map<String, Integer> getMenuCountMap(List<Long> idList) {
        Map<String, Integer> result = new HashMap<>();

        List<CalculateMenu> calculateMenus = calculateMenuRepository.getPopularMenus(idList);
        calculateMenus.forEach(calculateMenu -> {
            String name = calculateMenu.getMenu();
            Integer count = calculateMenu.getCount();
            result.put(name, result.getOrDefault(name, 0) + count);
        });
        return result;
    }

    @Transactional(readOnly = true)
    public List<CalculateMenuForGraphDto> getPopularMenus() {
        // 최근 5영업일 정산 데이터 id 조회
        List<Long> idList = calculateRepository.findTop5IdOrderByIdDesc();

        Map<String, Integer> menuCountMap = getMenuCountMap(idList);
        // maxHeap 생성
        PriorityQueue<Map.Entry<String, Integer>> maxHeap = new PriorityQueue<>(
                (entry1, entry2) -> Integer.compare(entry2.getValue(), entry1.getValue())
        );
        maxHeap.addAll(menuCountMap.entrySet());

        // result 생성
        List<CalculateMenuForGraphDto> result = new ArrayList<>();
        int size = 3;
        while (size > 0 && !maxHeap.isEmpty()) {
            Map.Entry<String, Integer> poll = maxHeap.poll();
            result.add(CalculateMenuForGraphDto.from(poll.getKey(), poll.getValue()));
            size--;
        }
        return result;
    }
    
    @Transactional(readOnly = true)
    public StorePreDto getCalculatePreUser() {
        CalculatePre calculatePre = calculatePreRepository.findTopByOrderByIdDesc();
        Calculate calculate = calculateRepository.findTopByOrderByIdDesc();

        return StorePreDto.from(List.of(calculate.getToday(), calculatePre.getPredictUser()));
    }

    
    @Transactional(readOnly = true)
    public Map<String, Integer> getCalculatePreFood() throws StringToMapException, CantFindByIdException {
        CalculatePre calculatePre = calculatePreRepository.findTopByOrderByIdDesc();

        try {
            return objectMapper.readValue(calculatePre.getPredictFood(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new StringToMapException("""
                    calculatePre는 존재하지만, 
                    PredictMenu 필드를 HashMap으로 변환하는 과정에서 에러가 발생했습니다.
                    PredictFood : """ + calculatePre.getPredictFood() ,e);
        } catch (NullPointerException e) {
            throw new CantFindByIdException("""
                    calculatePre가 null 입니다.
                    데이터가 존재하는지 확인해주세요.
                    """, e);
        }
    }

    
    @Transactional(readOnly = true)
    public Map<String, Integer> getCalculatePreMenu() throws StringToMapException, CantFindByIdException {
        CalculatePre calculatePre = calculatePreRepository.findTopByOrderByIdDesc();

        try {
            return objectMapper.readValue(calculatePre.getPredictMenu(), HashMap.class);
        } catch (JsonProcessingException e) {
            throw new StringToMapException("""
                    calculatePre는 존재하지만, 
                    PredictMenu 필드를 HashMap으로 변환하는 과정에서 에러가 발생했습니다.
                    PredictFood : """ + calculatePre.getPredictFood() ,e);
        } catch (NullPointerException e) {
            throw new CantFindByIdException("""
                    calculatePre가 null 입니다.
                    데이터가 존재하는지 확인해주세요.
                    """ , e);
        }
    }

    
    @Transactional(readOnly = true)
    public CalculatePreWeekDto getNextWeeksUser() {
        return CalculatePreWeekDto.from(calculatePreWeekRepository.findFirstByOrderByIdDesc());
    }

    
    @Transactional(readOnly = true)
    public WeeklyFoodPredictDto getNextWeeksFood() {
        return WeeklyFoodPredictDto.from(weelkyFoodPredictRepository.findFirstByOrderByIdDesc());
    }
}
