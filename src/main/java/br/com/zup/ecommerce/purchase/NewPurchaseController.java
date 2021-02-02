package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.shared.mail.Mailer;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;

@RestController
public class NewPurchaseController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Mailer mailer;

    @PostMapping("/purchases")
    @Transactional
    public ResponseEntity<?> create(@RequestBody @Valid NewPurchaseRequest request,
                                    @AuthenticationPrincipal ActiveUser user,
                                    UriComponentsBuilder uriComponentsBuilder) {
        Product product = manager.find(Product.class, request.getIdProduct());
        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

        boolean isStorageDecreased = product.decreaseStorage(request.getQuantity());

        if (!isStorageDecreased)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "There isn't enough product available.");

        Purchase purchase = request.toModel(product, user.getUser());
        manager.persist(purchase);

        mailer.send(new NewPurchaseMail(purchase));

        return ResponseEntity.status(302).body(purchase.redirectingUrl(uriComponentsBuilder));
    }
}
