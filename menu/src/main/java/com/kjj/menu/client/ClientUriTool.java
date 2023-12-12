package com.kjj.menu.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientUriTool {
    @Value("${kjj.client.host}") private String clientHost;

    public String getRequestURI(String host, String path) {
        return host + path;
    }

    public String getRequestURI(String host, String path, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(host + path + '?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuilder.append(String.join("=", entry.getKey(), entry.getValue()));
            stringBuilder.append("&");
        }
        return stringBuilder.toString();
    }

    public String getRequestURI(String path, Map<String, String> params) {
        StringBuilder stringBuilder = new StringBuilder(clientHost + path + '?');
        for (Map.Entry<String, String> entry : params.entrySet()) {
            stringBuilder.append(String.join("=", entry.getKey(), entry.getValue()));
            stringBuilder.append("&");
        }
        return stringBuilder.toString();
    }
}
