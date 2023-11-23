package com.kjj.api.controller.user.manager;

import com.kjj.api.aop.annotation.RestControllerClass;
import com.kjj.api.auth.JwtTemplate;
import com.kjj.api.auth.JwtUtil;
import com.kjj.api.dto.user.info.ManagerInfoDto;
import com.kjj.api.exception.exceptions.CantFindByUsernameException;
import com.kjj.api.service.user.manager.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@RestControllerClass("/api/manager")
public class ManagerApiController {
    private final ManagerService managerService;
    private final JwtUtil jwtUtil;
    private final JwtTemplate jwtTemplate = new JwtTemplate();

    /**
     * usernamer과 password로 관리자 로그인
     *
     * @return ManagerInfoDto
     */
    @Operation(summary="관리자 로그인", description="username과 password를 입력받아 로그인 시도")
    @PostMapping("/login/id")
    public ResponseEntity<ManagerInfoDto> managerLogin(HttpServletRequest request) throws CantFindByUsernameException {
        String username = (String) request.getAttribute("username");
        return ResponseEntity.ok(managerService.getInfoForUsername(username));
    }

    /**
     * 관리자 대표정보 조회
     * id, username
     *
     * @return ManagerInfoDto
     */
    @Operation(summary="관리자 대표정보 조회")
    @GetMapping("/info")
    public ResponseEntity<ManagerInfoDto> getInfo(HttpServletRequest request) throws CantFindByUsernameException {
        String username = jwtUtil.getUsernameFromToken(request.getHeader(jwtTemplate.getHeaderString()));
        return ResponseEntity.ok(managerService.getInfo(username));
    }
}