package br.com.zup.ecommerce.category;

import org.springframework.util.Assert;

public class CategoryResponse {

    private String name;
    private String motherCategoryName;

    public CategoryResponse(Category category) {
        this.name = category.getName();

        if (category.getMotherCategory() != null){
            this.motherCategoryName = category.getMotherCategory().getName();
            Assert.notNull(motherCategoryName, "As there is a mother category, its name must not be null");
        }
    }

    public String getName() {
        return name;
    }

    public String getMotherCategoryName() {
        return motherCategoryName;
    }

}
