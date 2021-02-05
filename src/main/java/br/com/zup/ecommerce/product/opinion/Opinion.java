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
import java.util.Objects;

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
        Assert.isTrue(!product.belongsTo(user), "Product owner cannot be the one who posts an opinion");
        Assert.isTrue(this.score >= 1 && this.score <= 5, "Score must be between 1 and 5");
    }

    public int getScore() {
        return score;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Opinion opinion = (Opinion) o;
        return title.equals(opinion.title) && description.equals(opinion.description) && product.equals(opinion.product) && user.equals(opinion.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, product, user);
    }
}
