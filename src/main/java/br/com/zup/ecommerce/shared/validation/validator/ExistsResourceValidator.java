package br.com.zup.ecommerce.shared.validation.validator;

import br.com.zup.ecommerce.shared.validation.annotation.ExistsResource;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistsResourceValidator implements ConstraintValidator<ExistsResource, Object> {

    private String field;
    private Class<?> aClass;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(ExistsResource params) {
        this.field = params.field();
        this.aClass = params.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null)
            return true;

        Assert.state(manager != null, "O EntityManager está nulo. Verificar se a anotação foi colocada dentro do contexto do Spring.");

        Query query = manager.createQuery("select 1 from " + aClass.getName() + " where " + field + " = :value");
        query.setParameter("value", value);
        List<?> resultList = query.getResultList();

        return !resultList.isEmpty();
    }
}
