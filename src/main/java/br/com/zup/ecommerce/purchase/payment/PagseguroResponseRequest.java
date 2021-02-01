package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.transaction.Transaction;
import br.com.zup.ecommerce.purchase.transaction.TransactionStatus;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PagseguroResponseRequest implements PaymentGatewayResponse {

    @NotBlank
    private String idPayment;

    @NotNull
    private PagseguroPaymentStatus status;

    public PagseguroResponseRequest(@NotBlank String idPayment, @NotNull PagseguroPaymentStatus status) {
        this.idPayment = idPayment;
        this.status = status;
    }

    @Override
    public Transaction toTransaction(Purchase purchase) {
        TransactionStatus status = this.status.convert();
        return new Transaction(status, idPayment, purchase);
    }
}
