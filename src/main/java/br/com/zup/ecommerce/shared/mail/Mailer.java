package br.com.zup.ecommerce.shared.mail;

import br.com.zup.ecommerce.purchase.Purchase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class Mailer {

    @Autowired
    private JavaMailSender emailSender;

    @Value("${mail.sender}")
    private String from;

    public void send(MailDetail detail) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(this.from);
        mail.setTo(detail.to());
        mail.setSubject(detail.subject());
        mail.setText(detail.message());
        emailSender.send(mail);
    }

    public void successfullPurchase(Purchase purchase) {
    }

    public void errorPurchase(Purchase purchase, String redirectingUrl) {
    }
}
