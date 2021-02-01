package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.transaction.Transaction;
import br.com.zup.ecommerce.purchase.transaction.TransactionStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PaypalResponseRequest implements PaymentGatewayResponse {

    @NotBlank
    private String idPayment;

    @Min(0)
    @Max(1)
    @NotNull
    private int status;

    public PaypalResponseRequest(@NotBlank String idPayment, @Min(0) @Max(1) @NotNull int status) {
        this.idPayment = idPayment;
        this.status = status;
    }

    @Override
    public Transaction toTransaction(Purchase purchase) {
        TransactionStatus status = this.status == 1 ? TransactionStatus.SUCCESS : TransactionStatus.ERROR;
        return new Transaction(status, idPayment, purchase);
    }
}
