package com.kjj.image.controller

import com.google.zxing.WriterException
import com.kjj.image.service.ImageService
import jakarta.servlet.http.HttpServletResponse
import org.springframework.core.io.FileSystemResource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO

@RestController
@RequestMapping("/v1/image")
class ImageController(
    val imageService: ImageService
) {
    @GetMapping
    fun getImage(@RequestParam("dir") dir: String): ResponseEntity<FileSystemResource> {
        val localPath = "./"
        val resource = FileSystemResource(localPath + dir)
        val httpHeaders = HttpHeaders()
        val path = Paths.get(localPath + dir)
        httpHeaders.add("Content-Type", Files.probeContentType(path))
        return ResponseEntity(resource, httpHeaders, HttpStatus.OK)
    }

    @GetMapping("/order/{id}")
    @Throws(IOException::class, WriterException::class)
    fun getOrderQr(response: HttpServletResponse, @PathVariable id: Long) {
        response.contentType = "image/png"
        response.setHeader("Content-Disposition", "inline; filename=qrcode.png")
        ImageIO.write(imageService.getOrderQr(id), "png", response.outputStream)
    }

    @PostMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun uploadImage(
        @RequestPart("file") file: MultipartFile,
        @RequestPart("dir") dir: String
    ): ResponseEntity<String> {
        return ResponseEntity.ok(imageService.uploadImage(file, dir))
    }

    @PatchMapping(consumes = [MediaType.MULTIPART_FORM_DATA_VALUE])
    fun updateImage(
        @RequestPart("file") file: MultipartFile,
        @RequestPart("dir") dir: String
    ) {
        imageService.updateImage(file, dir)
    }
}