package br.com.zup.ecommerce.product.image;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class NewImageRequest {

    @NotNull
    @Size(min = 1)
    private List<MultipartFile> images = new ArrayList<>();

    public List<MultipartFile> getImages() {
        return images;
    }
}
