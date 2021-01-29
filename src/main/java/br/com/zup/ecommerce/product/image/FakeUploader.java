package br.com.zup.ecommerce.product.image;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FakeUploader implements Uploader {
    @Override
    public Set<String> upload(List<MultipartFile> images) {
        Assert.isTrue(images.size() > 0, "The images' size must be at least 1");
        return images.stream()
                    .map(image -> "https://api.mercadolibre.com/pictures/" + UUID.randomUUID() + image.getOriginalFilename().replace(" ", ""))
                    .collect(Collectors.toSet());
    }
}
