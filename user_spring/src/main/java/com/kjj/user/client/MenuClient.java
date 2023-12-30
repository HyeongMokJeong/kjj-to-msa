package com.kjj.user.client;

import com.kjj.user.dto.menu.MenuNameAndCostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuClient {

    private final RestTemplate restTemplate;
    private final ClientUriTool uriTool;

    public Boolean existsById(Long menuId) {
        String uri = uriTool.getRequestURI("/v1/client/menu/exist", Map.of("menuId", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, Boolean.class);
    }

    public MenuNameAndCostDto findNameAndCostById(Long menuId) {
        String uri = uriTool.getRequestURI("/v1/client/menu/name-cost", Map.of("menuId", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, MenuNameAndCostDto.class);
    }

    public String findNameById(Long menuId) {
        String uri = uriTool.getRequestURI("/v1/client/menu/name", Map.of("menuId", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, String.class);
    }
}
