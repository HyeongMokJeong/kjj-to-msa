package com.kjj.noauth.client;

import com.kjj.noauth.dto.user.JoinDto;
import com.kjj.noauth.dto.user.UserDto;
import com.kjj.noauth.exception.CantFindByUsernameException;
import com.kjj.noauth.util.HttpTools;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class UserClient {
    private final RestTemplate restTemplate;
    private final HttpTools httpTools;
    @Value("${my.client.user.host}") private String userServerHost;

    private String getRequestURI(String path) {
        return userServerHost + path;
    }

    private String getRequestURI(String path, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(userServerHost + path + '?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuilder.append(String.join("=", entry.getKey(), entry.getValue()));
            stringBuilder.append("&");
        }
        return stringBuilder.toString();
    }


    public UserDto findByUsername(String username) {
        String uri = getRequestURI("/v1/user/info/all", Map.of("username", username));
        UserDto userDto = restTemplate.getForObject(uri, UserDto.class);
        if (userDto == null) throw new CantFindByUsernameException("""
                해당 username을 가진 유저를 찾을 수 없습니다.
                username = """ + username);
        return userDto;
    }

    public Boolean existsByUsername(String username) {
        String uri = getRequestURI("/v1/user/exist", Map.of("username", username));
        return restTemplate.getForObject(uri, Boolean.class);
    }

    public Boolean join(JoinDto dto) {
        String uri = getRequestURI("/v1/user/join");
        return restTemplate.postForObject(uri, httpTools.getRequestEntityTypeJson(dto), Boolean.class);
    }

    public UserDto joinKeycloak(UserDto dto) {
        String uri = getRequestURI("/v1/user/join/keycloak");
        return restTemplate.postForObject(uri, httpTools.getRequestEntityTypeJson(dto), UserDto.class);
    }

    public Boolean withdraw(String username) {
        String uri = getRequestURI("/v1/user/withdraw", Map.of("username", username));
        return restTemplate.getForObject(uri, Boolean.class);
    }

    public Boolean checkPassword(String username, String password) {
        String uri = getRequestURI("/v1/user/check/password", Map.of("username", username, "password", password));
        return restTemplate.getForObject(uri, Boolean.class);
    }
}
