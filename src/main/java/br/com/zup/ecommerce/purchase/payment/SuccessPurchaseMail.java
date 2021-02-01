package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.shared.mail.MailDetail;

public class SuccessPurchaseMail implements MailDetail {

    private Purchase purchase;

    public SuccessPurchaseMail(Purchase purchase) {
        this.purchase = purchase;
    }

    @Override
    public String message() {
        StringBuilder message = new StringBuilder();
        message.append("Congratulations for your acquisition! \n\n");
        message.append("Your product will be delivered soon. We'll keep you informed!\n\n");
        message.append("Product: ");
        message.append(purchase.getProduct().getName());
        message.append("\n");
        message.append("Quantity: ");
        message.append(purchase.getQuantity());
        message.append("\n");
        message.append("Transaction status: ");
        message.append(purchase.getTransactionStatus());
        message.append("\n");
        message.append("Payment gateway: ");
        message.append(purchase.getGateway());
        message.append("\n");
        message.append("\n");
        message.append("Mercado Livre's team appreciate your preference!");
        message.append("\n\n");
        message.append("Any complaints, please send directly to our Customer Attendance Service team through the e-mail below: \n");
        message.append("customerservice@mercadolivre.com");
        message.append("\n\n");
        message.append("Thank you!");
        return message.toString();
    }

    @Override
    public String to() {
        return purchase.getCustomer().getLogin();
    }

    @Override
    public String subject() {
        return "Product " + purchase.getProduct().getName() + " has been successfully purchased.";
    }
}
