package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.Set;

public class ForbidFeaturesWithSameNameValidatorTest {

    private Validator forbidFeaturesWithSameNameValidator = new ForbidFeaturesWithSameNameValidator();

    @Test
    @DisplayName("It should return a validation error")
    void test1(){
        Set<NewFeatureRequest> features = Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature1", "feature1"));
        NewProductRequest request = new NewProductRequest("name", BigDecimal.TEN, 5, features , "description", 1L);
        BindException exception = new BindException(request, "features");
        ValidationUtils.invokeValidator(forbidFeaturesWithSameNameValidator, request, exception);
        Assertions.assertTrue(exception.hasErrors());
    }

    @Test
    @DisplayName("It should pass successfully through validator")
    void test2(){
        Set<NewFeatureRequest> features = Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature2", "feature2"));
        NewProductRequest request = new NewProductRequest("name", BigDecimal.TEN, 5, features , "description", 1L);
        BindException exception = new BindException(request, "features");
        ValidationUtils.invokeValidator(forbidFeaturesWithSameNameValidator, request, exception);
        Assertions.assertFalse(exception.hasErrors());
    }
}
