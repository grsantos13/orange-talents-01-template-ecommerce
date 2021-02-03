package br.com.zup.ecommerce.purchase.payment.after;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.payment.PaymentGatewayResponse;
import br.com.zup.ecommerce.purchase.transaction.Transaction;
import br.com.zup.ecommerce.purchase.transaction.TransactionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

public class NewPurchaseEventTest {

    @Test
    @DisplayName("It should run success event")
    void test1(){
        Purchase purchase = new Purchase();
        PaymentGatewayResponse gatewayResponse = (p) -> new Transaction(TransactionStatus.SUCCESS, "2", p);
        purchase.addTransaction(gatewayResponse);
        SuccessPurchase success = Mockito.mock(SuccessPurchase.class);
        Set<SuccessPurchase> successPurchases = Set.of(success);

        NewPurchaseEvent event = new NewPurchaseEvent(successPurchases);

        event.process(purchase);

        Mockito.verify(success).process(purchase);
    }

    @Test
    @DisplayName("It shouldn't run any event")
    void test2(){
        Purchase purchase = new Purchase();
        PaymentGatewayResponse gatewayResponse = (p) -> new Transaction(TransactionStatus.ERROR, "2", p);
        purchase.addTransaction(gatewayResponse);
        SuccessPurchase successEvent = Mockito.mock(SuccessPurchase.class);
        Set<SuccessPurchase> successPurchasesEvents = Set.of(successEvent);

        NewPurchaseEvent event = new NewPurchaseEvent(successPurchasesEvents);

        event.process(purchase);

        Mockito.verify(successEvent, Mockito.never()).process(purchase);
    }
}
