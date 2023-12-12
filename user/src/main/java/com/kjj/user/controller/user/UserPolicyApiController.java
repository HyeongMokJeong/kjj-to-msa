package com.kjj.user.controller.user;

import com.kjj.user.dto.user.DatePolicyDto;
import com.kjj.user.dto.user.PolicyDto;
import com.kjj.user.exception.CantFindByIdException;
import com.kjj.user.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user/policy")
public class UserPolicyApiController {

    private final UserService userService;

    // 이용자 요일 정책 설정하는 api
    @PatchMapping("/date")
    public ResponseEntity<PolicyDto> setUserDatePolicy(@RequestParam("userId") Long id, @RequestBody DatePolicyDto dto) throws CantFindByIdException {

        return ResponseEntity.ok(userService.setUserDatePolicy(dto, id));
    }

    // 이용자 메뉴 정책 설정하는 api
    @PatchMapping("/menu/{menuId}")
    public ResponseEntity<PolicyDto> setUserMenuPolicy(@RequestParam("userId") Long userId, @PathVariable Long menuId) throws CantFindByIdException {
        return ResponseEntity.ok(userService.setUserMenuPolicy(userId, menuId));
    }

    // 유저 정책 조회하는 api
    @GetMapping("/date")
    public ResponseEntity<PolicyDto> getUserPolicy(@RequestParam("userId") Long id) throws CantFindByIdException {

        return ResponseEntity.ok(userService.getUserPolicy(id));
    }
}