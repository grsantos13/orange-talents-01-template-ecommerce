package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.user.User;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @NotNull
    @Positive
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentGateway gateway;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private User customer;

    public Purchase(@Valid @NotNull Product product,
                    @NotNull @Positive int quantity,
                    PaymentGateway gateway,
                    @Valid @NotNull User customer) {
        this.product = product;
        this.quantity = quantity;
        this.gateway = gateway;
        this.customer = customer;
    }

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public PaymentGateway getGateway() {
        return gateway;
    }

    public User getCustomer() {
        return customer;
    }

    public String redirectingUrl(UriComponentsBuilder uriComponentsBuilder) {
        return this.gateway.url(uriComponentsBuilder, this);
    }
}
