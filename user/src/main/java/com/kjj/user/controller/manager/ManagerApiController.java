package com.kjj.user.controller.manager;

import com.kjj.user.dto.info.ManagerInfoDto;
import com.kjj.user.exception.CantFindByUsernameException;
import com.kjj.user.service.manager.ManagerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/manager")
public class ManagerApiController {
    private final ManagerService managerService;

    @GetMapping("/info")
    public ResponseEntity<ManagerInfoDto> getInfo(@RequestParam("username") String username) throws CantFindByUsernameException {
        return ResponseEntity.ok(managerService.getInfo(username));
    }
}
