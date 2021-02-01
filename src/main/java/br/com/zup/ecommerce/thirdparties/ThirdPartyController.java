package br.com.zup.ecommerce.thirdparties;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class ThirdPartyController {

    @PostMapping("/invoices")
    public void create(@RequestBody @Valid NewPurchaseInvoiceRequest request) {
        System.out.println("Invoice's being generated.");
    }

    @PostMapping("/ranking")
    public void create(@RequestBody @Valid NewRankingRequest request) {
        System.out.println("Ranking's being generated.");
    }
}
