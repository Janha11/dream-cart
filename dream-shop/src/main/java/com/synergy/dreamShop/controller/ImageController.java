package com.synergy.dreamShop.controller;

import com.synergy.dreamShop.dto.ImageDto;
import com.synergy.dreamShop.exceptions.ResourceNotFoundException;
import com.synergy.dreamShop.model.Image;
import com.synergy.dreamShop.response.ApiResponse;
import com.synergy.dreamShop.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    @Autowired
    private final IImageService iImageService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse>saveImages(@RequestParam List<MultipartFile> file ,@RequestParam Long productId ) {
        try {
            List<ImageDto> imageDtos = iImageService.saveImage(file, productId);
            return ResponseEntity.ok(new ApiResponse("Upload success", imageDtos));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Upload Failed", e.getMessage()));
        }
    }

    @GetMapping("image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long imageId) throws SQLException {
        Image image = iImageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }
    @PutMapping("/image{id}/update")
    public ResponseEntity<ApiResponse> updateImage(@PathVariable Long imageId,@RequestParam MultipartFile file ) {
        try {
            Image image = iImageService.getImageById(imageId);
            if (image != null) {
                iImageService.update(file, imageId);
                return ResponseEntity.ok(new ApiResponse("Update Success", null));
            }
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed",INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image{id}/delete")
    public ResponseEntity<ApiResponse> deleteImage(@PathVariable Long imageId ) {
        try {
            Image image = iImageService.getImageById(imageId);
            if (image != null) {
                iImageService.deleteImageById( imageId);
                return ResponseEntity.ok(new ApiResponse("Delete Success", null));
            }
        }catch (ResourceNotFoundException e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Update Failed",INTERNAL_SERVER_ERROR));
    }




}
