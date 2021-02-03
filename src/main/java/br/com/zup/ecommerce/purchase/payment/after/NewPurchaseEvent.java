package br.com.zup.ecommerce.purchase.payment.after;

import br.com.zup.ecommerce.purchase.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class NewPurchaseEvent {

    private Set<SuccessPurchase> successPurchases;

    public NewPurchaseEvent(Set<SuccessPurchase> successPurchases) {
        this.successPurchases = successPurchases;
    }

    public void process(Purchase purchase){
        if (purchase.successfullyProcessed())
            successPurchases.forEach(s -> s.process(purchase));
    }

}
