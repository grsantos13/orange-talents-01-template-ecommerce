package br.com.zup.ecommerce.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RestController
public class ProductDetailsController {

    @PersistenceContext
    private EntityManager manager;

    @GetMapping("/products/{id:\\d+}")
    public ResponseEntity<?> getProductDetails(@PathVariable("id") Long id){
        Product product = manager.find(Product.class, id);
        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

        return ResponseEntity.ok(new ProductDetailsResponse(product));

    }
}
