package br.com.zup.ecommerce.purchase.payment.after;


import br.com.zup.ecommerce.purchase.Purchase;

public interface SuccessPurchase {

    void process(Purchase purchase);
}
