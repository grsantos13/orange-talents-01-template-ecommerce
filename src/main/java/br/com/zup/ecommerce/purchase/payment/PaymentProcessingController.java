package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.payment.after.NewPurchaseEvent;
import br.com.zup.ecommerce.purchase.transaction.TransactionStatus;
import br.com.zup.ecommerce.shared.mail.Mailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
public class PaymentProcessingController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Mailer mailer;

    @Autowired
    private NewPurchaseEvent event;

    @PostMapping("/paypal-response/{id:\\d+}")
    @Transactional
    public ResponseEntity<?> paypalPaymentProcessing(@PathVariable("id") Long idPurchase,
                                                     @RequestBody @Valid PaypalResponseRequest request,
                                                     UriComponentsBuilder uriBuilder){
        Purchase merged = processPayment(request, idPurchase, uriBuilder);
        return ResponseEntity.ok(merged.getTransactionStatus());
    }

    @PostMapping("/pagseguro-response/{id:\\d+}")
    @Transactional
    public ResponseEntity<?> pagseguroPaymentProcessing(@PathVariable("id") Long idPurchase,
                                                        @RequestBody @Valid PagseguroResponseRequest request,
                                                        UriComponentsBuilder uriBuilder){
        Purchase merged = processPayment(request, idPurchase, uriBuilder);
        return ResponseEntity.ok(merged.getTransactionStatus());
    }

    private Purchase processPayment(PaymentGatewayResponse request, Long idPurchase, UriComponentsBuilder uriBuilder) {
        Purchase purchase = manager.find(Purchase.class, idPurchase);
        if (purchase == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Purchase not found");

        purchase.addTransaction(request);

        manager.merge(purchase);

        if (purchase.getTransactionStatus().equals(TransactionStatus.SUCCESS))
            mailer.send(new SuccessPurchaseMail(purchase));
        else
            mailer.send(new FailurePurchaseMail(purchase, purchase.redirectingUrl(uriBuilder)));

        event.process(purchase);

        return purchase;
    }
}
