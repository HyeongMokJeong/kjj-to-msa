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

    @PatchMapping("/date")
    public ResponseEntity<PolicyDto> setUserDatePolicy(@RequestParam("id") Long id, @RequestBody DatePolicyDto dto) throws CantFindByIdException {

        return ResponseEntity.ok(userService.setUserDatePolicy(dto, id));
    }

    @PatchMapping("/menu/{menuId}")
    public ResponseEntity<PolicyDto> setUserMenuPolicy(@RequestParam("id") Long id, @PathVariable Long menuId) throws CantFindByIdException {
        return ResponseEntity.ok(userService.setUserMenuPolicy(id, menuId));
    }

    @GetMapping("/date")
    public ResponseEntity<PolicyDto> getUserPolicy(@RequestParam("id") Long id) throws CantFindByIdException {

        return ResponseEntity.ok(userService.getUserPolicy(id));
    }
}