package com.kjj.store.service;

import com.kjj.store.client.ImageClient;
import com.kjj.store.dto.*;
import com.kjj.store.entity.Store;
import com.kjj.store.entity.StoreState;
import com.kjj.store.exception.CantFindByIdException;
import com.kjj.store.exception.DataExistsException;
import com.kjj.store.repository.StoreRepository;
import com.kjj.store.repository.StoreStateRepository;
import com.kjj.store.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class StoreService {

    private final ImageClient imageClient;
    private final StoreRepository storeRepository;
    private final StoreStateRepository storeStateRepository;
    private final DateUtil dateUtil;

    private static final Long FINAL_ID = 1L;


    @Transactional(readOnly = true)
    public StoreDto isSetting() {
        Store store = storeRepository.findById(FINAL_ID).orElse(null);
        if (store == null) return null;
        return StoreDto.from(store);
    }

    
    @Transactional(readOnly = true)
    public StoreDto getStoreData() throws CantFindByIdException {
        return StoreDto.from(storeRepository.findById(FINAL_ID).orElseThrow(() -> new CantFindByIdException("""
                ID가 1인 Store 데이터가 존재하지 않습니다.
                존재 여부를 확인해주세요.
                storeId : """ + FINAL_ID)));
    }

    
    @Transactional
    public StoreDto setSetting(StoreSettingDto dto) throws DataExistsException {
        if (storeRepository.existsById(FINAL_ID)) throw new DataExistsException("""
                이미 ID가 1인 Store 데이터가 존재합니다.
                dto : """ + dto);

        return StoreDto.from(storeRepository.save(Store.of(FINAL_ID, dto)));
    }

    
    @Transactional
    public void setStoreImage(MultipartFile file) throws CantFindByIdException, IOException {
        Store store = storeRepository.findById(FINAL_ID).orElseThrow(() -> new CantFindByIdException("""
                ID가 1인 Store 데이터가 존재하지 않습니다.
                존재 여부를 확인해주세요.
                storeId : """ + FINAL_ID));
        String uploadDir = "img/store";
        if (store.getImage() == null) store.setImage(imageClient.uploadImage(file, uploadDir));
        else imageClient.updateImage(file, store.getImage());
    }
    
    @Transactional
    public StoreDto updateStoreTitle(StoreTitleDto dto) throws CantFindByIdException {
        Store store = storeRepository.findById(FINAL_ID).orElseThrow(() -> new CantFindByIdException("""
                ID가 1인 Store 데이터가 존재하지 않습니다.
                존재 여부를 확인해주세요.
                storeId : """ + FINAL_ID));
        store.setName(dto.getName());

        return StoreDto.from(store);
    }

    
    @Transactional
    public StoreDto updateStoreInfo(StoreInfoDto dto) throws CantFindByIdException {
        Store store = storeRepository.findById(FINAL_ID).orElseThrow(() -> new CantFindByIdException("""
                ID가 1인 Store 데이터가 존재하지 않습니다.
                존재 여부를 확인해주세요.
                storeId : """ + FINAL_ID));
        store.setInfo(dto.getInfo());

        return StoreDto.from(store);
    }

    
    @Transactional
    public StoreStateDto setOff(StoreOffDto dto, int year, int month, int day) {
        LocalDate date = dateUtil.makeLocalDate(year, month, day);

        StoreState storeState = storeStateRepository.findByDate(date).orElse(null);
        if (storeState == null) storeState = storeStateRepository.save(StoreState.createNewOffStoreState(storeRepository.getReferenceById(FINAL_ID), date, dto));
        storeState.setOff(dto);
        return StoreStateDto.from(storeState);
    }

    
    @Transactional(readOnly = true)
    public List<StoreStateDto> getClosedDays(int year, int month) {
        LocalDate start = dateUtil.makeLocalDate(year, month, 1);
        LocalDate end = dateUtil.makeLocalDate(year, month, dateUtil.getLastDay(year, month));

        return storeStateRepository.findAllByDateBetween(start, end).stream()
                .map(StoreStateDto::from)
                .toList();
    }
}
