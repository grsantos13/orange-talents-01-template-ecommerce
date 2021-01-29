package br.com.zup.ecommerce.product.feature;

import br.com.zup.ecommerce.product.Product;

import javax.validation.constraints.NotBlank;

public class NewFeatureRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public NewFeatureRequest(@NotBlank String name, @NotBlank String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Feature toModel(Product product){
        return new Feature(this.name, this.description, product);
    }
}
