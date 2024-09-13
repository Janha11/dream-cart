package com.synergy.dreamShop.service.image;

import com.synergy.dreamShop.dto.ImageDto;
import com.synergy.dreamShop.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto> saveImage(List<MultipartFile> file , Long productId);
    void update(MultipartFile file, Long imageId);
}
