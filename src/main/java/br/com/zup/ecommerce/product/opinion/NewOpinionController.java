package br.com.zup.ecommerce.product.opinion;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NewOpinionController {

    @PersistenceContext
    private EntityManager manager;

    public NewOpinionController(EntityManager manager) {
        this.manager = manager;
    }

    @PostMapping("/products/{id:\\d+}/opinions")
    @Transactional
    public ResponseEntity<?> addOpinion(@PathVariable("id") Long id,
                                        @RequestBody @Valid NewOpinionRequest request,
                                        @AuthenticationPrincipal ActiveUser user){

        Product product = manager.find(Product.class, id);
        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

        if (product.belongsTo(user.getUser()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The owner cannot evaluate their own product");

        Opinion opinion = request.toModel(product, user.getUser());
        manager.persist(opinion);
        return ResponseEntity.ok(product.getOpinions());
    }
}
