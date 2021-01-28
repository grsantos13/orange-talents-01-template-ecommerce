package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.product.image.NewImageRequest;
import br.com.zup.ecommerce.product.image.Uploader;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/products")
public class ProductController {

    @PersistenceContext
    private EntityManager manager;

    private Uploader uploader;

    @PostMapping
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal ActiveUser user){
        Product product = request.toModel(manager, user.getUser());
        manager.persist(product);
        ProductResponse response = new ProductResponse(product);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id:\\d+}/images")
    @Transactional
    public ResponseEntity<?> addImages(@PathVariable("id") Long id, @Valid NewImageRequest request, @AuthenticationPrincipal ActiveUser user){
        Product product = manager.find(Product.class, id);
        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        if (!product.belongsTo(user.getUser()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Product with id " + id + " is not yours");

        Set<String> uploadList = uploader.upload(request.getImages());

        product.addImages(uploadList);

        manager.merge(product);

        ProductResponse response = new ProductResponse(product);

        return ResponseEntity.ok(response);
    }
}
