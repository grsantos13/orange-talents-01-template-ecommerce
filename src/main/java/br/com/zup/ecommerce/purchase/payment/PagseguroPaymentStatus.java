package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.purchase.transaction.TransactionStatus;

public enum PagseguroPaymentStatus {
    SUCESSO, ERRO;

    public TransactionStatus convert() {
        return this.equals(SUCESSO) ? TransactionStatus.SUCCESS : TransactionStatus.ERROR;
    }
}
