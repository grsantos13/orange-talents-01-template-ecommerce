package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.transaction.Transaction;

public interface PaymentGatewayResponse {

    Transaction toTransaction(Purchase purchase);
}
