package br.com.zup.ecommerce.purchase;

import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

public enum PaymentGateway {
    paypal{
        @Override
        public String url(UriComponentsBuilder uriBuilder, Purchase purchase) {
            URI uri = uriBuilder.path("/paypal-response/{id:\\d+}")
                    .buildAndExpand(purchase.getId())
                    .toUri();
            return "paypal.com/" + purchase.getId() + "?redirectUrl=" + uri;
        }
    },
    pagseguro{
        @Override
        public String url(UriComponentsBuilder uriBuilder, Purchase purchase) {
            URI uri = uriBuilder.path("/pagseguro-respnse/{id:\\d+}")
                    .buildAndExpand(purchase.getId())
                    .toUri();
            return "pagseguro.com?returnId=" + purchase.getId() + "&redirectUrl=" + uri;
        }
    };

    public abstract String url(UriComponentsBuilder uriBuilder, Purchase purchase);
}