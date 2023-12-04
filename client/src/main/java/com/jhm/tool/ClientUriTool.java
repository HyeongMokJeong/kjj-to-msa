package com.jhm.tool;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ClientUriTool {

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
}
