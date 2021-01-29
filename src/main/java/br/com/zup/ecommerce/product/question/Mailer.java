package br.com.zup.ecommerce.product.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class Mailer {

    @Autowired
    private JavaMailSender emailSender;

    public void newQuestion(Question question, URI location){
        String message = MailStandards.newQuestionMail(question, location);
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(question.getInterestedPerson().getLogin());
        mail.setTo(question.getProduct().getOwner().getLogin());
        mail.setSubject("New question received for product " + question.getProduct().getName());
        mail.setText(message);
        emailSender.send(mail);
    }


}
