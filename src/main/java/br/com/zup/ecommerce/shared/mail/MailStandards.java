package br.com.zup.ecommerce.shared.mail;

import br.com.zup.ecommerce.product.question.Question;
import br.com.zup.ecommerce.purchase.Purchase;

import java.net.URI;

public class MailStandards {

    public static String newQuestionMail(Question question, URI location) {
        StringBuilder message = new StringBuilder();
        message.append("New message: \n");
        message.append(question.getTitle());
        message.append("\n");
        message.append("To visualize this question, please click on the link below: \n");
        message.append(location);
        message.append("\n\n");
        message.append("Thank you.");
        return message.toString();
    }

    public static String newPurchaseMail(Purchase purchase) {
        StringBuilder message = new StringBuilder();
        message.append("New purchase interest shown: \n");
        message.append(purchase.getCustomer().getLogin());
        message.append(" is acquiring the product ");
        message.append(purchase.getProduct().getName());
        return message.toString();
    }
}
