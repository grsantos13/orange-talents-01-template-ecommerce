package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.category.CategoryResponse;
import br.com.zup.ecommerce.product.feature.FeatureResponse;
import br.com.zup.ecommerce.product.image.ImageResponse;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductResponse {
    private String name;
    private BigDecimal value;
    private int availableAmount;
    private Set<FeatureResponse> features;
    private String description;
    private CategoryResponse category;
    private Set<ImageResponse> images;

    public ProductResponse(Product product) {
        this.name = product.getName();
        this.value = product.getValue();
        this.availableAmount = product.getAvailableAmount();
        this.features = product.getFeatures().stream()
                                             .map(f -> new FeatureResponse(f))
                                             .collect(Collectors.toSet());
        this.description = product.getDescription();
        this.category = new CategoryResponse(product.getCategory());
        this.images = product.getImages().stream()
                                        .map(i -> new ImageResponse(i.getLink()))
                                        .collect(Collectors.toSet());
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

    public Set<FeatureResponse> getFeatures() {
        return features;
    }

    public String getDescription() {
        return description;
    }

    public CategoryResponse getCategory() {
        return category;
    }

    public Set<ImageResponse> getImages() {
        return images;
    }
}
