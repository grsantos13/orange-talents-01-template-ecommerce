package br.com.zup.ecommerce.purchase.payment.after;

import br.com.zup.ecommerce.purchase.Purchase;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class Ranking implements SuccessPurchase {

    @Override
    public void process(Purchase purchase) {
        Assert.isTrue(purchase.successfullyProcessed(), "Purchase not successfully processed.");
        RestTemplate template = new RestTemplate();
        Map<String, Object> request = Map.of("idPurchase", purchase.getId(), "idOwner", purchase.getProduct().getOwner().getId());
        template.postForEntity("http://localhost:8080/ranking", request, String.class);
    }
}
