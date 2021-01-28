package br.com.zup.ecommerce.category;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "category", uniqueConstraints = {
        @UniqueConstraint(name = "category_name_uk", columnNames = {"name"})})
public class Category {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{category.name.blank}")
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Category motherCategory;

    @Deprecated
    public Category() {
    }

    public Category(@NotBlank String name, Category motherCategory) {
        this.name = name;
        this.motherCategory = motherCategory;
    }

    public String getName() {
        return name;
    }

    public Category getMotherCategory() {
        return motherCategory;
    }
}
