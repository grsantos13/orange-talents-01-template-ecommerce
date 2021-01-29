package br.com.zup.ecommerce.product.question;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.user.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Question implements Comparable<Question>{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String title;

    @NotNull
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private User interestedPerson;

    @Deprecated
    public Question() {
    }

    public Question(@NotBlank String title,
                    @Valid @NotNull Product product,
                    @Valid @NotNull User interestedPerson) {
        this.title = title;
        this.product = product;
        this.interestedPerson = interestedPerson;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Product getProduct() {
        return product;
    }

    public User getInterestedPerson() {
        return interestedPerson;
    }

    @Override
    public int compareTo(Question o) {
        return this.title.compareTo(o.title);
    }
}
