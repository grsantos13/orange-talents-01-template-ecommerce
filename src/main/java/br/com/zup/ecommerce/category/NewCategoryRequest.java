package br.com.zup.ecommerce.category;

import br.com.zup.ecommerce.shared.validation.annotation.ExistsResource;
import br.com.zup.ecommerce.shared.validation.annotation.Unique;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

public class NewCategoryRequest {

    @NotBlank
    @Unique(field = "name", domainClass = Category.class)
    private String name;

    @ExistsResource(field = "id", domainClass = Category.class)
    private Long idMotherCategory;

    public NewCategoryRequest(@NotBlank String name, Long idMotherCategory) {
        this.name = name;
        this.idMotherCategory = idMotherCategory;
    }

    public Category toModel(EntityManager manager) {
        Category category = null;

        if (idMotherCategory != null) {
            category = manager.find(Category.class, idMotherCategory);
            Assert.notNull(category, "Category must not be null");
        }

        return new Category(name, category);
    }
}
