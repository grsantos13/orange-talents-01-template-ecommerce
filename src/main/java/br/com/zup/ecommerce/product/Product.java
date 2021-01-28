package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.category.Category;
import br.com.zup.ecommerce.product.feature.Feature;
import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.user.User;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Positive
    @Column(nullable = false)
    private BigDecimal value;

    @NotNull
    @Positive
    @Column(nullable = false)
    private int availableAmount;

    @Valid
    @NotNull
    @OneToMany(mappedBy = "product", cascade = CascadeType.PERSIST)
    @Size(min = 3)
    private Set<Feature> features = new HashSet<>();

    @NotBlank
    @Size(max = 1000)
    @Column(nullable = false)
    private String description;

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @NotNull
    @PastOrPresent
    private LocalDateTime createdAt = LocalDateTime.now();

    @Valid
    @NotNull
    @ManyToOne
    @JoinColumn(nullable = false)
    private User owner;

    @Deprecated
    public Product() {
    }

    public Product(@NotBlank String name,
                   @NotNull @Positive BigDecimal value,
                   @NotNull @Positive int availableAmount,
                   @Valid @NotNull @Size(min = 3) Set<NewFeatureRequest> features,
                   @NotBlank @Size(max = 1000) String description,
                   @Valid @NotNull Category category,
                   @Valid @NotNull User owner) {
        this.name = name;
        this.value = value;
        this.availableAmount = availableAmount;
        this.features.addAll(features.stream()
                                     .map(f -> f.toModel(this))
                                     .collect(Collectors.toSet()));
        this.description = description;
        this.category = category;
        this.owner = owner;
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

    public Set<Feature> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public Category getCategory() {
        return category;
    }
}
