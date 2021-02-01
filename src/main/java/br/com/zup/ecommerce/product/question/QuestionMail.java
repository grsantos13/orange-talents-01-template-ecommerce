package br.com.zup.ecommerce.product.question;

import br.com.zup.ecommerce.shared.mail.MailDetail;

import java.net.URI;

public class QuestionMail implements MailDetail {
    private Question question;
    private URI location;

    public QuestionMail(Question question, URI location) {
        this.question = question;
        this.location = location;
    }

    @Override
    public String message() {
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

    @Override
    public String to() {
        return question.getProduct().getOwner().getLogin();
    }

    @Override
    public String subject() {
        return "New question received for product " + question.getProduct().getName();
    }
}
