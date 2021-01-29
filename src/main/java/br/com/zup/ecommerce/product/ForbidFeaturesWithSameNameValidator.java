package br.com.zup.ecommerce.product;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

public class ForbidFeaturesWithSameNameValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewProductRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors())
            return;

        NewProductRequest request = (NewProductRequest) target;
        Set<String> sameName = request.findSameFeatures();
        if (!sameName.isEmpty())
            errors.rejectValue("features", null, "There are more than one feature with same name." + sameName);
    }
}
