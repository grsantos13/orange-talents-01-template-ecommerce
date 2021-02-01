package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.shared.mail.MailDetail;

public class FailurePurchaseMail implements MailDetail {

    private Purchase purchase;
    private String location;

    public FailurePurchaseMail(Purchase purchase, String location) {
        this.purchase = purchase;
        this.location = location;
    }

    @Override
    public String message() {
        StringBuilder message = new StringBuilder();
        message.append("An error happened when trying to confirm your payment.");
        message.append("\n");
        message.append("Please, for new try click on the link below:");
        message.append("\n");
        message.append(location);
        message.append("\n\n");
        message.append("Thank you.");
        return message.toString();
    }

    @Override
    public String to() {
        return purchase.getCustomer().getLogin();
    }

    @Override
    public String subject() {
        return "Payment issue - product " + purchase.getProduct().getName();
    }
}
