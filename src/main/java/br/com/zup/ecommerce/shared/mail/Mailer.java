package br.com.zup.ecommerce.shared.mail;

import br.com.zup.ecommerce.product.question.Question;
import br.com.zup.ecommerce.purchase.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class Mailer {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${mail.sender}")
    private String from;

    public void newQuestion(Question question, URI location){
        String message = MailStandards.newQuestionMail(question, location);
        String subject = "New question received for product " + question.getProduct().getName();
        String to = question.getProduct().getOwner().getLogin();

        configureAndSend(to, subject, message);
    }

    public void newPurchase(Purchase purchase) {
        String message = MailStandards.newPurchaseMail(purchase);
        String to = purchase.getProduct().getOwner().getLogin();
        String subject = "Interest has been shown for product " + purchase.getProduct().getName();
        configureAndSend(to, subject, message);
    }

    private void configureAndSend(String to, String subject, String message ){
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(this.from);
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(message);
        emailSender.send(mail);
    }
}
