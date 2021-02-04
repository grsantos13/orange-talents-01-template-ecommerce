package br.com.zup.ecommerce.product.opinion;

import br.com.zup.ecommerce.category.Category;
import br.com.zup.ecommerce.product.Opinions;
import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import br.com.zup.ecommerce.user.CleanPassword;
import br.com.zup.ecommerce.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewOpinionControllerTest {

    private EntityManager manager = Mockito.mock(EntityManager.class);
    private NewOpinionController controller = new NewOpinionController(manager);

    @Test
    @DisplayName("Should return an error for not finding the product")
    void test1(){
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(null);
        User user = new User("gsantoset@gmail.com", new CleanPassword("123456"));
        NewOpinionRequest request = new NewOpinionRequest(1, "title", "description");

        assertThrows(ResponseStatusException.class,
                () -> controller.addOpinion(1L, request, new ActiveUser(user)),
                "Product not found");
    }

    @Test
    @DisplayName("Should return an error for the person who sends the opinion being the owner of the product")
    void test2(){
        User user = new User("gsantoset@gmail.com", new CleanPassword("123456"));
        Product product = getProduct(user);
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(product);
        NewOpinionRequest request = new NewOpinionRequest(1, "title", "description");

        assertThrows(ResponseStatusException.class,
                () -> controller.addOpinion(1L, request, new ActiveUser(user)),
                "The owner cannot evaluate their own product");
    }


    @Test
    @DisplayName("Should add an opinion successfully")
    void test3(){
        User user = new User("gsantoset@gmail.com", new CleanPassword("123456"));
        User owner = new User("gsantoset2@gmail.com", new CleanPassword("123456"));
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(getProduct(owner));
        NewOpinionRequest request = new NewOpinionRequest(1, "title", "description");

        ResponseEntity<?> responseEntity = controller.addOpinion(1L, request, new ActiveUser(user));
        Opinions body = (Opinions) responseEntity.getBody();
        assertEquals(200, responseEntity.getStatusCode().value());
    }

    private Product getProduct(User user) {
        return new Product("name",
                BigDecimal.TEN,
                5,
                Set.of(new NewFeatureRequest("feature1", "feature1"), new NewFeatureRequest("feature2", "feature2"), new NewFeatureRequest("feature3", "feature3")),
                "description",
                new Category("name", null),
                user);
    }
}
