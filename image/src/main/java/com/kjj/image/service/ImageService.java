package com.kjj.image.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.kjj.image.exception.UploadFileException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ImageService {
    private String makeFilePath(String filename, String uploadDir) {
        return Paths.get(uploadDir, filename).toString();
    }

    public BufferedImage getOrderQr(Long orderId) throws WriterException {
        String domain = "http://localhost:8080";
        String endPoint = "/api/user/order/" + orderId + "/qr";

        return createQRCode(domain + endPoint);
    }


    private BufferedImage createQRCode(String data) throws WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 100, 100);

        return MatrixToImageWriter.toBufferedImage(bitMatrix);
    }

    public String uploadImage(MultipartFile file, String uploadDir) throws UploadFileException {
        String fileName = RandomStringUtils.randomAlphanumeric(10);
        String filePath = makeFilePath(fileName + ".png", uploadDir);
        try {
            Files.createDirectories(Paths.get(uploadDir));
            Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UploadFileException(String.format("""
                    파일을 저장할 디렉토리를 생성하고,
                    파일을 복사하는 과정에서 에러가 발생했습니다.
                    fileName : %s / filePath : %s""", fileName, filePath));
        }
        String separator = FileSystems.getDefault().getSeparator();
        filePath = filePath.replace(separator, "/");
        return filePath;
    }

    public void updateImage(MultipartFile file, String oldPath) throws UploadFileException {
        try {
            Files.copy(file.getInputStream(), Paths.get(oldPath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new UploadFileException(String.format("""
                    기존 경로의 파일에 덮어쓰기 과정 중에 에러가 발생했습니다.
                    fileOldPath : %s""", oldPath));
        }
    }
}