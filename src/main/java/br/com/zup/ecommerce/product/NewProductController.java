package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.shared.security.ActiveUser;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NewProductController {

    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/products")
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewProductRequest request, @AuthenticationPrincipal ActiveUser user){
        Product product = request.toModel(manager, user.getUser());
        manager.persist(product);
        ProductResponse response = new ProductResponse(product);
        return ResponseEntity.ok(response);
    }
}
