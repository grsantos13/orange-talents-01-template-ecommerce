package br.com.zup.ecommerce.thirdparties;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.shared.validation.annotation.ExistsResource;
import br.com.zup.ecommerce.user.User;

import javax.validation.constraints.NotNull;

public class NewRankingRequest {
    @NotNull
    @ExistsResource(field = "id", domainClass = Purchase.class)
    private Long idPurchase;

    @NotNull
    @ExistsResource(field = "id", domainClass = User.class)
    private Long idOwner;

    public NewRankingRequest(Long idPurchase, Long idOwner) {
        this.idPurchase = idPurchase;
        this.idOwner = idOwner;
    }

    public Long getIdPurchase() {
        return idPurchase;
    }

    public Long getIdOwner() {
        return idOwner;
    }
}
