package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.category.Category;
import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.purchase.payment.PaymentGateway;
import br.com.zup.ecommerce.shared.mail.Mailer;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import br.com.zup.ecommerce.user.CleanPassword;
import br.com.zup.ecommerce.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Set;

public class NewPurchaseControllerTest {

    private EntityManager manager = Mockito.mock(EntityManager.class);
    private Mailer mailer = Mockito.mock(Mailer.class);
    private NewPurchaseController controller = new NewPurchaseController(manager, mailer);
    private UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");

    @Test
    @DisplayName("Redirect to gateway when the available amount is decreased")
    void test1() throws Exception{
        Product product = new Product("name", new BigDecimal(50), 5, getFeatureRequests(),
                "description", getCategory(), new User("gsantoset@gmail.com", new CleanPassword("123456")));
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(product);

        Mockito.doAnswer(invocation -> {
            Purchase purchase = invocation.<Purchase>getArgument(0);
            ReflectionTestUtils.setField(purchase, "id", 1L);
            return null;
        }).when(manager).persist(Mockito.any(Purchase.class));

        NewPurchaseRequest request = new NewPurchaseRequest(1L, 1, PaymentGateway.paypal);

        ResponseEntity<?> responseEntity = controller.create(request, new ActiveUser(new User("gsantoset@gmail.com", new CleanPassword("13102013"))), uriComponentsBuilder);
        Assertions.assertEquals(302,responseEntity.getStatusCode().value());
        Assertions.assertEquals("paypal.com/1?redirectUrl=http://localhost:8080/paypal-response/1", responseEntity.getBody());

    }

    @Test
    @DisplayName("Throws exception for not finding the product")
    void test2() throws Exception{
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(null);

        NewPurchaseRequest request = new NewPurchaseRequest(1L, 1, PaymentGateway.paypal);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.create(request, new ActiveUser(new User("gsantoset@gmail.com", new CleanPassword("13102013"))), uriComponentsBuilder),
                "Product not found");

    }

    @Test
    @DisplayName("Throws exception when the available amount isn't enough")
    void test3() throws Exception{
        Product product = new Product("name", new BigDecimal(50), 5, getFeatureRequests(),
                "description", getCategory(), new User("gsantoset@gmail.com", new CleanPassword("123456")));
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(product);

        Mockito.doAnswer(invocation -> {
            Purchase purchase = invocation.<Purchase>getArgument(0);
            ReflectionTestUtils.setField(purchase, "id", 1L);
            return null;
        }).when(manager).persist(Mockito.any(Purchase.class));

        NewPurchaseRequest request = new NewPurchaseRequest(1L, 50, PaymentGateway.paypal);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.create(request, new ActiveUser(new User("gsantoset@gmail.com", new CleanPassword("13102013"))), uriComponentsBuilder),
                "There isn't enough product available.");

    }

    private Category getCategory() {
        Category category = new Category("category", null);
        return category;
    }

    private Set<NewFeatureRequest> getFeatureRequests() {
        return Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature2", "feature2"),
                new NewFeatureRequest("feature3", "feature3"));
    }
}
