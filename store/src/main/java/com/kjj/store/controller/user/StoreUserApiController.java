package com.kjj.store.controller.user;

import com.kjj.store.dto.StoreDto;
import com.kjj.store.dto.StoreStateDto;
import com.kjj.store.exception.CantFindByIdException;
import com.kjj.store.exception.WrongParameterException;
import com.kjj.store.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/store/user")
public class StoreUserApiController {

    private final StoreService storeService;

    @GetMapping
    public ResponseEntity<StoreDto> getStoreData() throws CantFindByIdException {
        return ResponseEntity.ok(storeService.getStoreData());
    }

    @GetMapping("/off/{year}/{month}")
    public ResponseEntity<List<StoreStateDto>> getClosedDays(@PathVariable int year, @PathVariable int month) throws WrongParameterException {
        if (0 >= month || month > 12) throw new WrongParameterException("month(1 ~ 12) : " + month);
        return ResponseEntity.ok(storeService.getClosedDays(year, month));
    }
}
