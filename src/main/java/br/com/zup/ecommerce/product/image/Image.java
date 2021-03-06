package br.com.zup.ecommerce.product.image;

import br.com.zup.ecommerce.product.Product;
import org.hibernate.validator.constraints.URL;

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
import java.util.Objects;

@Entity
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @URL
    @NotBlank
    @Column(nullable = true)
    private String link;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Product product;

    @Deprecated
    public Image() {
    }

    public Image(@Valid @NotNull Product product, @URL @NotBlank String link) {
        this.link = link;
        this.product = product;
    }

    public String getLink() {
        return link;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return link.equals(image.link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(link);
    }
}
