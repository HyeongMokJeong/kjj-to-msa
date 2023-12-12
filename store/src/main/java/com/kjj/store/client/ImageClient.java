package com.kjj.store.client;

import com.kjj.store.util.ClientUriTool;
import com.kjj.store.util.HttpTools;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageClient {
    private final RestTemplate restTemplate;
    private final HttpTools httpTools;
    private final ClientUriTool clientUriTool;
    @Value("${my.client.image.host}") private String imageServerHost;

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