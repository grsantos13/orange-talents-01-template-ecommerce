package br.com.zup.ecommerce.product.image;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.product.ProductResponse;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class NewImagesController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Uploader uploader;

    @PostMapping("/products/{id:\\d+}/images")
    @Transactional
    public ResponseEntity<?> addImages(@PathVariable("id") Long id, @Valid NewImageRequest request, @AuthenticationPrincipal ActiveUser user){
        Product product = manager.find(Product.class, id);
        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

        if (!product.belongsTo(user.getUser()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Product with id " + id + " is not your property");

        if (!request.areAllImagesValid())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Images must be JPG, JPEG or PNG");


        Set<String> uploadList = uploader.upload(request.getImages());
        product.addImages(uploadList);

        manager.merge(product);

        Set<String> response = product.getImages().stream()
                .map(image -> image.getLink())
                .collect(Collectors.toSet());
        return ResponseEntity.ok(response);
    }
}
