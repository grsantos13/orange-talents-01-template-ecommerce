package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.category.Category;
import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.payment.after.NewPurchaseEvent;
import br.com.zup.ecommerce.purchase.transaction.TransactionStatus;
import br.com.zup.ecommerce.shared.mail.Mailer;
import br.com.zup.ecommerce.user.CleanPassword;
import br.com.zup.ecommerce.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;

import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PaymentProcessingControllerTest {
    private EntityManager manager = Mockito.mock(EntityManager.class);
    private Mailer mailer = Mockito.mock(Mailer.class);
    private NewPurchaseEvent event = Mockito.mock(NewPurchaseEvent.class);
    private PaymentProcessingController controller = new PaymentProcessingController(manager, mailer, event);
    private UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");

    @Test
    @DisplayName("Throws exception when purchase is not found")
    void test1() {
        Mockito.when(manager.find(Purchase.class, 1L)).thenReturn(null);
        PagseguroResponseRequest request = new PagseguroResponseRequest("1", PagseguroPaymentStatus.SUCESSO);
        assertThrows(ResponseStatusException.class,
                () -> controller.pagseguroPaymentProcessing(1L, request, uriComponentsBuilder),
                "Purchase not found");
        Mockito.verify(mailer, Mockito.never()).send(Mockito.any(SuccessPurchaseMail.class));
        Mockito.verify(mailer, Mockito.never()).send(Mockito.any(FailurePurchaseMail.class));
    }

    @ParameterizedTest
    @MethodSource("generator")
    @DisplayName("Should finish purchase successfully")
    void test2(User user, Purchase purchase) {
        Mockito.when(manager.find(Purchase.class, 1L)).thenReturn(purchase);
        PagseguroResponseRequest request = new PagseguroResponseRequest("1", PagseguroPaymentStatus.SUCESSO);

        ResponseEntity<?> responseEntity = controller.pagseguroPaymentProcessing(1L, request, uriComponentsBuilder);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(TransactionStatus.SUCCESS, responseEntity.getBody());
        Mockito.verify(mailer).send(Mockito.any(SuccessPurchaseMail.class));
        Mockito.verify(mailer, Mockito.never()).send(Mockito.any(FailurePurchaseMail.class));
    }

    @ParameterizedTest
    @MethodSource("generator")
    @DisplayName("Should return a transaction error")
    void test3(User user, Purchase purchase) {
        Mockito.when(manager.find(Purchase.class, 1L)).thenReturn(purchase);
        PagseguroResponseRequest request = new PagseguroResponseRequest("1", PagseguroPaymentStatus.ERRO);

        ResponseEntity<?> responseEntity = controller.pagseguroPaymentProcessing(1L, request, uriComponentsBuilder);
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(TransactionStatus.ERROR, responseEntity.getBody());
        Mockito.verify(mailer, Mockito.never()).send(Mockito.any(SuccessPurchaseMail.class));
        Mockito.verify(mailer).send(Mockito.any(FailurePurchaseMail.class));
    }

    private static Stream<Arguments> generator(){
        User user = new User("gsantoset@gmail.com",
                new CleanPassword("123456"));
        Product product = new Product("name",
                BigDecimal.TEN,
                5,
                Set.of(new NewFeatureRequest("feature1", "feature1"),
                        new NewFeatureRequest("feature2", "feature2"),
                        new NewFeatureRequest("feature3", "feature3")),
                "description",
                new Category("name", null),
                user);

        Purchase purchase = new Purchase(product, 5, PaymentGateway.pagseguro, user);

        return Stream.of(
                Arguments.of(user, purchase)
        );
    }
}
