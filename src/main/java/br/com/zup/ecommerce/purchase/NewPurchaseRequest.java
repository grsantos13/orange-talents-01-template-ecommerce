package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.shared.validation.annotation.ExistsResource;
import br.com.zup.ecommerce.user.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class NewPurchaseRequest {

    @NotNull
    @ExistsResource(field = "id", domainClass = Product.class)
    private Long idProduct;

    @NotNull
    @Positive
    private int quantity;

    @NotNull
    private PaymentGateway gateway;

    public Long getIdProduct() {
        return idProduct;
    }

    public int getQuantity() {
        return quantity;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public Purchase toModel(Product product, User user) {
        return new Purchase(product, this.quantity, this.gateway, user);
    }
}
