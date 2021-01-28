package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.category.Category;
import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.shared.validation.annotation.ExistsResource;
import br.com.zup.ecommerce.user.User;
import io.jsonwebtoken.lang.Assert;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

public class NewProductRequest {

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private BigDecimal value;

    @NotNull
    @Min(0)
    private int availableAmount;

    @Valid
    @NotNull
    @Size(min = 3)
    private Set<NewFeatureRequest> features = new HashSet<>();

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    @ExistsResource(domainClass = Category.class, field = "id")
    private Long idCategory;

    public String getName() {
        return name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public Set<NewFeatureRequest> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public Long getIdCategory() {
        return idCategory;
    }

    public Product toModel(EntityManager manager, User owner) {
        Category category = manager.find(Category.class, idCategory);
        Assert.state(category != null, "Category must nt be null");
        return new Product(this.name,
                           this.value,
                           this.availableAmount,
                           this.features,
                           this.description,
                           category,
                           owner);
    }
}
