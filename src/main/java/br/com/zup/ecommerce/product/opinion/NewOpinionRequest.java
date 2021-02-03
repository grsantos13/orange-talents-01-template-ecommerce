package br.com.zup.ecommerce.product.opinion;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.user.User;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class NewOpinionRequest {

    @Min(1)
    @Max(5)
    @NotNull
    private int score;

    @NotBlank
    private String title;

    @NotBlank
    @Size(max = 500)
    private String description;

    public NewOpinionRequest(@Min(1) @Max(5) @NotNull int score, @NotBlank String title, @NotBlank @Size(max = 500) String description) {
        this.score = score;
        this.title = title;
        this.description = description;
    }

    public Opinion toModel(Product product, User user) {
        return new Opinion(
                this.score,
                this.title,
                this.description,
                product,
                user
        );
    }
}
