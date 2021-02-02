package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

public class NewProductRequestTest {

    @ParameterizedTest
    @MethodSource("generator")
    @DisplayName("It should create a product with every feature")
    void test1(int expected, Set<NewFeatureRequest> features){
        NewProductRequest request = new NewProductRequest("name", BigDecimal.TEN, 5, features, "description", 1L);

        Assertions.assertEquals(expected, request.findSameFeatures().size());
    }

    private static Stream<Arguments> generator(){
        return Stream.of(
                Arguments.of(0, Set.of()),
                Arguments.of(0, Set.of(new NewFeatureRequest("feature1", "feature1"))),
                Arguments.of(0, Set.of(new NewFeatureRequest("feature1", "feature1"), new NewFeatureRequest("feature2", "feature2"))),
                Arguments.of(1, Set.of(new NewFeatureRequest("feature1", "feature1"), new NewFeatureRequest("feature1", "feature1")))
        );
    }
}
