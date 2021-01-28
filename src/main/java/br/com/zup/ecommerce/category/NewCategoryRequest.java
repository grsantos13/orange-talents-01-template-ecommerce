package br.com.zup.ecommerce.category;

import javax.persistence.EntityManager;
import javax.validation.constraints.NotBlank;

import br.com.zup.ecommerce.shared.validation.annotation.ExistsResource;
import br.com.zup.ecommerce.shared.validation.annotation.Unique;
import org.springframework.util.Assert;

public class NewCategoryRequest {
	
	@NotBlank(message = "{category.name.blank}")
	@Unique(field = "name", domainClass = Category.class)
	private String name;

	@ExistsResource(field = "id", domainClass = Category.class)
	private Long idMotherCategory;

	public String getName() {
		return name;
	}

	public Long getIdMotherCategory() {
		return idMotherCategory;
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
