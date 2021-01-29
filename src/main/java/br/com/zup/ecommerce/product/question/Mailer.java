package br.com.zup.ecommerce.product.question;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public interface Mailer {
    void send(@NotBlank @Email String from,
              @NotBlank String fromName,
              @NotBlank @Email String to,
              @NotBlank String subject,
              @NotBlank String body);
}
