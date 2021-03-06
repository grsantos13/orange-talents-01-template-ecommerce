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

    public NewProductRequest(@NotBlank String name,
                             @NotNull @Positive BigDecimal value,
                             @NotNull @Min(0) int availableAmount,
                             @Valid @NotNull @Size(min = 3) Set<NewFeatureRequest> features,
                             @NotBlank @Size(max = 1000) String description,
                             @NotNull Long idCategory) {
        this.name = name;
        this.value = value;
        this.availableAmount = availableAmount;
        this.features = features;
        this.description = description;
        this.idCategory = idCategory;
    }

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
        Assert.state(category != null, "Category must not be null");
        return new Product(this.name,
                           this.value,
                           this.availableAmount,
                           this.features,
                           this.description,
                           category,
                           owner);
    }

    public Set<String> findSameFeatures() {
        HashSet<String> sameName = new HashSet<>();
        HashSet<String> list = new HashSet<>();

        for (NewFeatureRequest feature : this.features) {
            String name = feature.getName();
            if (!sameName.add(name))
                list.add(name);
        }

        return list;
    }
}
