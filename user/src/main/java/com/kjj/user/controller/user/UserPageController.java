package com.kjj.user.controller.user;

import com.kjj.user.dto.user.MyPageDto;
import com.kjj.user.dto.user.UsePointDto;
import com.kjj.user.exception.CantFindByIdException;
import com.kjj.user.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/user/page")
public class UserPageController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<MyPageDto> getMyPage(@RequestParam("userId") Long id) throws CantFindByIdException {

        return ResponseEntity.ok(userService.getMyPage(id));
    }

    @PostMapping("/point")
    public ResponseEntity<Integer> usePoint(@RequestParam("userId") Long id, @RequestBody UsePointDto dto) throws CantFindByIdException {

        return ResponseEntity.ok(userService.usePoint(id, dto));
    }
}