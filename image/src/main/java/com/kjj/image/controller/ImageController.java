package com.kjj.image.controller;

import com.google.zxing.WriterException;
import com.kjj.image.service.ImageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/image")
public class ImageController {

    private final ImageService imageService;

    @GetMapping
    public ResponseEntity<FileSystemResource> getImage(@RequestParam("dir") String dir) throws IOException {
        String localPath = "./";
        FileSystemResource resource = new FileSystemResource(localPath + dir);

        HttpHeaders httpHeaders = new HttpHeaders();
        Path path = Paths.get(localPath + dir);
        httpHeaders.add("Content-Type", Files.probeContentType(path));

        return new ResponseEntity<>(resource, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/order/{id}")
    public void getOrderQr(HttpServletResponse response, @PathVariable Long id) throws IOException, WriterException {
        response.setContentType("image/png");
        response.setHeader("Content-Disposition", "inline; filename=qrcode.png");
        ImageIO.write(imageService.getOrderQr(id), "png", response.getOutputStream());
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadImage(
            @RequestPart("file")  MultipartFile file,
            @RequestPart("dir") String dir) {
        return ResponseEntity.ok(imageService.uploadImage(file, dir));
    }

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateImage(
            @RequestPart("file")  MultipartFile file,
            @RequestPart("dir") String dir) {
        imageService.updateImage(file, dir);
    }
}
