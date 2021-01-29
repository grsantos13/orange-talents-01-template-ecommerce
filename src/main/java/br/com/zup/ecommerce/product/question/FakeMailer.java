package br.com.zup.ecommerce.product.question;

import br.com.zup.ecommerce.product.question.Mailer;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Component
public class FakeMailer implements Mailer {

    @Override
    public void send(@NotBlank @Email String from, @NotBlank String fromName, @NotBlank @Email String to, @NotBlank String subject, @NotBlank String body) {
        System.out.println(from);
        System.out.println(fromName);
        System.out.println("");
        System.out.println(to);
        System.out.println("");
        System.out.println(subject);
        System.out.println("");
        System.out.println(body);
    }
}
