package br.com.zup.ecommerce.thirdparties;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.shared.validation.annotation.ExistsResource;
import br.com.zup.ecommerce.user.User;

import javax.validation.constraints.NotNull;

public class NewPurchaseInvoiceRequest {

    @NotNull
    @ExistsResource(field = "id", domainClass = Purchase.class)
    private Long idPurchase;

    @NotNull
    @ExistsResource(field = "id", domainClass = User.class)
    private Long idCustomer;

    public NewPurchaseInvoiceRequest(Long idPurchase, Long idCustomer) {
        this.idPurchase = idPurchase;
        this.idCustomer = idCustomer;
    }

    public Long getIdPurchase() {
        return idPurchase;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }
}
