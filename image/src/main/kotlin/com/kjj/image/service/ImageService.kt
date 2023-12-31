package com.kjj.image.service

import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import com.kjj.image.exception.FileUploadException
import org.apache.commons.lang3.RandomStringUtils
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.awt.image.BufferedImage
import java.io.IOException
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardCopyOption

@Service
class ImageService {
    private fun makeFilePath(filename: String, uploadDir: String): String {
        return Paths.get(uploadDir, filename).toString()
    }

    private fun createQRCode(data: String): BufferedImage {
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 100, 100)
        return MatrixToImageWriter.toBufferedImage(bitMatrix)
    }

    fun getOrderQr(orderId: Long): BufferedImage {
        val domain = "http://localhost:8080"
        val endPoint = "/api/user/order/$orderId/qr"
        return createQRCode(domain + endPoint)
    }

    fun uploadImage(file: MultipartFile, uploadDir: String): String {
        val fileName = RandomStringUtils.randomAlphanumeric(10)
        var filePath = makeFilePath("$fileName.png", uploadDir)
        try {
            Files.createDirectories(Paths.get(uploadDir))
            Files.copy(file.inputStream, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            throw FileUploadException(
                """
                    파일을 저장할 디렉토리를 생성하고,
                    파일을 복사하는 과정에서 에러가 발생했습니다.
                    fileName : $fileName / filePath :$filePath
                    """
                )
        }
        val separator = FileSystems.getDefault().separator
        filePath = filePath.replace(separator, "/")
        return filePath
    }

    fun updateImage(file: MultipartFile, oldPath: String) {
        try {
            Files.copy(file.inputStream, Paths.get(oldPath), StandardCopyOption.REPLACE_EXISTING)
        } catch (e: IOException) {
            throw FileUploadException(
                """
                    기존 경로의 파일에 덮어쓰기 과정 중에 에러가 발생했습니다.
                    fileOldPath : $oldPath
                    """
            )
        }
    }
}