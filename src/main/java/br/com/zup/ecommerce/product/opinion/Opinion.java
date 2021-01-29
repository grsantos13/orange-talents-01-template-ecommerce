package br.com.zup.ecommerce.product.opinion;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.user.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(1)
    @Max(5)
    @NotNull
    @Column(nullable = false)
    private int score;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotBlank
    @Size(max = 500)
    @Column(nullable = false, length = 500)
    private String description;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;

    @Deprecated
    public Opinion() {
    }

    public Opinion(@Min(1) @Max(5) @NotNull int score,
                   @NotBlank String title,
                   @NotBlank @Size(max = 500) String description,
                   @Valid @NotNull Product product,
                   @Valid @NotNull User user) {
        this.score = score;
        this.title = title;
        this.description = description;
        this.product = product;
        this.user = user;
        Assert.isTrue(this.score >= 1 && this.score <= 5, "Score must be between 1 and 5");
    }
}