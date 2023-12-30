package com.kjj.user.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientUriTool {

    @Value("${kjj.client.host}") private String clientServerHost;

    public String getRequestURI(String path, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(clientServerHost + path + '?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuilder.append(String.join("=", entry.getKey(), entry.getValue()));
            stringBuilder.append("&");
        }
        return stringBuilder.toString();
    }
}
