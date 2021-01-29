package br.com.zup.ecommerce.product.image;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NewImageRequest {

    @NotNull
    @Size(min = 1)
    private List<MultipartFile> images = new ArrayList<>();

    public void setImages(List<MultipartFile> images) {
        this.images = images;
    }

    public List<MultipartFile> getImages() {
        return images;
    }

    public boolean areAllImagesValid(){

        List<MultipartFile> validImages = this.images.stream().filter(image -> {
            String format = image.getContentType().split("/")[1];
            return format.equals("jpg") || format.equals("jpeg") || format.equals("png");
        }).collect(Collectors.toList());

        return validImages.size() == this.images.size();
    }
}
