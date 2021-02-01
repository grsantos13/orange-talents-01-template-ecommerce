package br.com.zup.ecommerce.purchase.transaction;

import br.com.zup.ecommerce.purchase.Purchase;

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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @NotBlank
    @Column(nullable = false)
    private String idPaymentGateway;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Purchase purchase;

    @Deprecated
    public Transaction() {
    }

    public Transaction(@NotNull TransactionStatus status,
                       @NotBlank String idPaymentGateway,
                       @Valid @NotNull Purchase purchase) {
        this.status = status;
        this.idPaymentGateway = idPaymentGateway;
        this.purchase = purchase;
        this.createdAt = LocalDateTime.now();
    }

    public boolean successfulTransaction(){
        return this.status.equals(TransactionStatus.SUCCESS);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return idPaymentGateway.equals(that.idPaymentGateway);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPaymentGateway);
    }
}
