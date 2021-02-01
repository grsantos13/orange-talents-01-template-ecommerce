package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.shared.mail.MailDetail;

public class NewPurchaseMail implements MailDetail {

    private Purchase purchase;

    public NewPurchaseMail(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public String message() {
        StringBuilder message = new StringBuilder();
        message.append("New purchase interest shown: \n");
        message.append(purchase.getCustomer().getLogin());
        message.append(" is acquiring the product ");
        message.append(purchase.getProduct().getName());
        return message.toString();
    }

    @Override
    public String to() {
        return purchase.getProduct().getOwner().getLogin();
    }

    @Override
    public String subject() {
        return "Interest has been shown for product " + purchase.getProduct().getName();
    }
}
