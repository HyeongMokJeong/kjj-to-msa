package com.kjj.user.controller.manager;

import com.kjj.user.dto.info.ManagerInfoDto;
import com.kjj.user.exception.CantFindByUsernameException;
import com.kjj.user.service.manager.ManagerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ManagerApiController.class)
class ManagerApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ManagerService managerService;

    @Test
    void getInfo() throws Exception{
        // 1. 정상 요청
        {
            // Given
            Long testManagerId = 1L;
            String testManagerName = "test";
            ManagerInfoDto expected = new ManagerInfoDto(testManagerId, testManagerName);
            when(managerService.getInfo(testManagerName)).thenReturn(expected);

            // When & Then
            mockMvc.perform(get("/v1/manager/info").param("username", testManagerName))
                    .andExpect(status().isOk());
        }
        // 2. username 데이터가 없는 경우
        {
            // Given
            String testManagerNameNotExist = "notExist";
            when(managerService.getInfo(testManagerNameNotExist)).thenThrow(new CantFindByUsernameException("""
                username을 가진 유저 데이터가 없습니다.
                username : """ + testManagerNameNotExist));

            // When & Then
            mockMvc.perform(get("/v1/manager/info").param("username", testManagerNameNotExist))
                    .andExpect(status().is(400));
        }
    }
}