package br.com.zup.ecommerce.product.opinion;

import br.com.zup.ecommerce.product.Opinions;
import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import br.com.zup.ecommerce.user.CleanPassword;
import br.com.zup.ecommerce.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;

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
    @DisplayName("Should add an opinion successfully")
    void test2(){
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(new Product());
        User user = new User("gsantoset@gmail.com", new CleanPassword("123456"));
        NewOpinionRequest request = new NewOpinionRequest(1, "title", "description");

        ResponseEntity<?> responseEntity = controller.addOpinion(1L, request, new ActiveUser(user));
        Opinions body = (Opinions) responseEntity.getBody();
        assertEquals(200, responseEntity.getStatusCode().value());
    }
}
