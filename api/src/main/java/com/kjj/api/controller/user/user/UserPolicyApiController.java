package com.kjj.api.controller.user.user;

import com.kjj.api.aop.annotation.RestControllerClass;
import com.kjj.api.auth.JwtTemplate;
import com.kjj.api.auth.JwtUtil;
import com.kjj.api.dto.user.user.UserDatePolicyDto;
import com.kjj.api.dto.user.user.UserPolicyDto;
import com.kjj.api.exception.exceptions.CantFindByIdException;
import com.kjj.api.exception.exceptions.WrongParameter;
import com.kjj.api.service.user.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@RestControllerClass("/api/user/policy")
public class UserPolicyApiController {

    private final UserService userService;
    private final JwtUtil jwtUtil;
    private final JwtTemplate jwtTemplate = new JwtTemplate();

    /**
     * 일반 유저 요일 정책 설정
     *
     * @param dto - monday ~ friday bool값
     * @return UserPolicyDto
     * @throws CantFindByIdException - userPolicy가 없을 경우 발생
     */
    @Operation(summary="일반 유저 요일정책 설정", description="유저 요일정책 설정")
    @PatchMapping("/date")
    public ResponseEntity<UserPolicyDto> setUserDatePolicy(HttpServletRequest request, @RequestBody UserDatePolicyDto dto) throws CantFindByIdException {
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));

        return ResponseEntity.ok(userService.setUserDatePolicy(dto, id));
    }

    /**
     * 일반 유저 메뉴 정책 설정
     *
     * @param menuId - 설정할 메뉴 ID
     * @return UserPolicyDto
     * @throws CantFindByIdException - UserPolicy가 없을 경우 발생
     * @throws WrongParameter - 메뉴가 없을 경우 발생
     */
    @Operation(summary="일반 유저 메뉴정책 설정", description="유저 메뉴정책 설정")
    @PatchMapping("/menu/{menuId}")
    public ResponseEntity<UserPolicyDto> setUserMenuPolicy(HttpServletRequest request, @PathVariable Long menuId) throws CantFindByIdException, WrongParameter {
        System.out.println("controlelr in");
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));

        return ResponseEntity.ok(userService.setUserMenuPolicy(id, menuId));
    }

    @Operation(summary="일반 유저 정책 조회", description="유저 요일정책 조회")
    @GetMapping("/date")
    public ResponseEntity<UserPolicyDto> getUserPolicy(HttpServletRequest request) throws CantFindByIdException {
        Long id = jwtUtil.getIdFromToken(request.getHeader(jwtTemplate.getHeaderString()));

        return ResponseEntity.ok(userService.getUserPolicy(id));
    }
}