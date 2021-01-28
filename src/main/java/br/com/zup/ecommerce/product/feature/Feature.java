package br.com.zup.ecommerce.product.feature;

import br.com.zup.ecommerce.product.Product;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Feature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotNull
    @ManyToOne
    private Product product;

    public Feature(@NotBlank String name,
                   @NotBlank String description,
                   @NotNull Product product) {
        this.name = name;
        this.description = description;
        this.product = product;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
