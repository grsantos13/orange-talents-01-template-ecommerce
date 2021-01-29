package br.com.zup.ecommerce.product.question;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;

@Service
public class MailSender {

    @Autowired
    private Mailer mailer;

    public void newQuestion(Question question, URI location){
        String message = MailStandards.newQuestionMail(question, location);

        mailer.send("no-reply@mercadolivre.com",
                question.getInterestedPerson().getLogin(),
                question.getProduct().getOwner().getLogin(),
                "New question received for product " + question.getProduct().getName() ,
                message);
    }


}
