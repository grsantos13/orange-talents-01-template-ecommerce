package br.com.zup.ecommerce.product.question;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.user.User;

import javax.validation.constraints.NotBlank;

public class NewQuestionRequest {

    @NotBlank
    private String title;

    @Deprecated
    public NewQuestionRequest() {
    }

    public NewQuestionRequest(@NotBlank String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Question toModel(Product product, User interestedPerson) {
        return new Question(this.title, product, interestedPerson);
    }
}
