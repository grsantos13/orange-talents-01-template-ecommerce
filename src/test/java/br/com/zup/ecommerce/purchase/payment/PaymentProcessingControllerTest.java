package br.com.zup.ecommerce.purchase.payment;

import br.com.zup.ecommerce.purchase.Purchase;
import br.com.zup.ecommerce.purchase.payment.after.NewPurchaseEvent;
import br.com.zup.ecommerce.shared.mail.Mailer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;

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
    }
}
