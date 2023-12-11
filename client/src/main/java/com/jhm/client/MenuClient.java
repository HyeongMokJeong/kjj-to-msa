package com.jhm.client;

import com.jhm.dto.menu.MenuNameAndCostDto;
import com.jhm.tool.ClientUriTool;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class MenuClient {

    private final RestTemplate restTemplate;
    private final ClientUriTool uriTool;
    @Value("${kjj.client.menu.host}") private String menuServerHost;

    public Boolean existsById(Long menuId) {
        String uri = uriTool.getRequestURI(menuServerHost, "/v1/menu/exist", Map.of("id", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, Boolean.class);
    }

    public MenuNameAndCostDto findNameAndCostById(Long menuId) {
        String uri = uriTool.getRequestURI(menuServerHost, "/v1/menu/name-cost", Map.of("id", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, MenuNameAndCostDto.class);
    }

    public String findNameById(Long menuId) {
        String uri = uriTool.getRequestURI(menuServerHost, "/v1/menu/name", Map.of("id", String.valueOf(menuId)));
        return restTemplate.getForObject(uri, String.class);
    }
}
