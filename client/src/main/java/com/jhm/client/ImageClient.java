package com.jhm.client;

import com.jhm.tool.ClientUriTool;
import com.jhm.tool.HttpTools;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class ImageClient {
    private final RestTemplate restTemplate;
    private final HttpTools httpTools;
    private final ClientUriTool clientUriTool;
    @Value("${kjj.client.image.host}") private String imageServerHost;

    public ImageClient(RestTemplate restTemplate, HttpTools httpTools, ClientUriTool clientUriTool) {
        this.restTemplate = restTemplate;
        this.httpTools = httpTools;
        this.clientUriTool = clientUriTool;
    }

    public String uploadImage(MultipartFile file, String dir) throws IOException {
        return restTemplate.postForObject(
                clientUriTool.getRequestURI(imageServerHost, "/v1/image"),
                httpTools.getRequestEntityTypeFormData(file, dir),
                String.class);
    }

    public void updateImage(MultipartFile file, String dir) throws IOException {
        restTemplate.patchForObject(
                clientUriTool.getRequestURI(imageServerHost, "/v1/image"),
                httpTools.getRequestEntityTypeFormData(file, dir),
                Void.class);
    }
}
