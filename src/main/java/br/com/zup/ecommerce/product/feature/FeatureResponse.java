package br.com.zup.ecommerce.product.feature;

public class FeatureResponse {
    private String name;
    private String description;

    public FeatureResponse(Feature feature) {
        this.name = feature.getName();
        this.description = feature.getDescription();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
