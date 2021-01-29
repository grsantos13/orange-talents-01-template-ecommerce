package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.category.Category;
import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.user.CleanPassword;
import br.com.zup.ecommerce.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ProductTest {

    @DisplayName("It should construct a product successfully considering 3 features")
    @ParameterizedTest
    @MethodSource(value = "test1Generator")
    void test1(Set<NewFeatureRequest> features){
        Category category = new Category("category", null);
        User owner = new User("email@teste.com", new CleanPassword("123456"));

        new Product("name", BigDecimal.TEN, 5, features,"description", category, owner);

    }

    static Stream<Arguments> test1Generator(){
        return Stream.of(
                Arguments.of(
                        Set.of(new NewFeatureRequest("feature1", "feature1"),
                                new NewFeatureRequest("feature2", "feature2"),
                                new NewFeatureRequest("feature3", "feature3"))),
                Arguments.of(
                        Set.of(new NewFeatureRequest("feature1", "feature1"),
                                new NewFeatureRequest("feature2", "feature2"),
                                new NewFeatureRequest("feature3", "feature3"),
                                new NewFeatureRequest("feature4", "feature4")))
        );
    }

    @DisplayName("It should return an error on constructing a product with less than 3 features")
    @ParameterizedTest
    @MethodSource(value = "test2Generator")
    void test2(Set<NewFeatureRequest> features){
        Category category = new Category("category", null);
        User owner = new User("email@teste.com", new CleanPassword("123456"));

        Assertions.assertThrows(IllegalArgumentException.class, () -> new Product("name", BigDecimal.TEN, 5, features,"description", category, owner));

    }

    private static Stream<Arguments> test2Generator(){
        return Stream.of(
                Arguments.of(
                        Set.of(new NewFeatureRequest("feature1", "feature1"),
                                new NewFeatureRequest("feature2", "feature2"))),
                Arguments.of(
                        Set.of(new NewFeatureRequest("feature1", "feature1")))
        );
    }
}
