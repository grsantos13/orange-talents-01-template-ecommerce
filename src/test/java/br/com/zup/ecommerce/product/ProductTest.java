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
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
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

    @Test
    @DisplayName("It should decrease available amount of a product")
    void test3(){
        Product product = new Product("name", new BigDecimal(500), 5, Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature2", "feature2"),
                new NewFeatureRequest("feature3", "feature3"),
                new NewFeatureRequest("feature4", "feature4")), "description", new Category("category", null), new User());

        product.decreaseStorage(5);

        Assertions.assertEquals(0, product.getAvailableAmount());
    }

    @ParameterizedTest
    @DisplayName("It should return an error because of the amount requested not being positive")
    @CsvSource({"0", "-1", "-100"})
    void test4(int amount){
        Product product = new Product("name", new BigDecimal(500), 5, Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature2", "feature2"),
                new NewFeatureRequest("feature3", "feature3"),
                new NewFeatureRequest("feature4", "feature4")), "description", new Category("category", null), new User());

        Assertions.assertThrows(IllegalArgumentException.class, () -> product.decreaseStorage(amount));
    }

    @ParameterizedTest
    @DisplayName("It should return an error because of the amount requested not being enough")
    @CsvSource({"1,1,true", "1,2,false", "5,3,true", "5,7,false"})
    void test5(int availableAmount, int quantity, boolean result){
        Product product = new Product("name", new BigDecimal(500), availableAmount, Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature2", "feature2"),
                new NewFeatureRequest("feature3", "feature3"),
                new NewFeatureRequest("feature4", "feature4")), "description", new Category("category", null), new User());

        boolean isDecreased = product.decreaseStorage(quantity);

        Assertions.assertEquals(result, isDecreased);
    }
}
