package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.purchase.payment.PaymentGateway;
import br.com.zup.ecommerce.purchase.payment.PaymentGatewayResponse;
import br.com.zup.ecommerce.purchase.transaction.Transaction;
import br.com.zup.ecommerce.purchase.transaction.TransactionStatus;
import br.com.zup.ecommerce.user.User;
import org.springframework.util.Assert;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.MERGE)
    private Set<Transaction> transactions = new HashSet<>();

    @Deprecated
    public Purchase() {
    }

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

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(PaymentGatewayResponse gatewayResponse){
        Transaction transaction = gatewayResponse.toTransaction(this);

        Assert.state(!this.transactions.contains(transaction), "This transaction is already created.");

        Assert.state(!this.successfullyProcessed(), "This purchase has already been successfully finished." );

        this.transactions.add(transaction);

    }

    private Set<Transaction> successfulTransaction() {
        Set<Transaction> transactions = this.transactions.stream()
                .filter(Transaction::successfulTransaction)
                .collect(Collectors.toSet());

        Assert.isTrue(transactions.size() <= 1, "There's more than 1 transaction successfully completed ");

        return transactions;
    }


    public boolean successfullyProcessed() {
        return !successfulTransaction().isEmpty();
    }

    public TransactionStatus getTransactionStatus(){
        return successfullyProcessed() ? TransactionStatus.SUCCESS : TransactionStatus.ERROR;
    }
}
