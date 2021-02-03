package br.com.zup.ecommerce.product.question;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.shared.mail.Mailer;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import br.com.zup.ecommerce.user.CleanPassword;
import br.com.zup.ecommerce.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewQuestionControllerTest {

    private EntityManager manager = Mockito.mock(EntityManager.class);
    private Mailer mailer = Mockito.mock(Mailer.class);
    private NewQuestionController controller = new NewQuestionController(manager, mailer);
    private UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");

    @Test
    @DisplayName("Throws error when product not found")
    void test1(){
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(null);
        User user = new User("grodrigueset@gmail.com", new CleanPassword("123456"));

        assertThrows(ResponseStatusException.class,
                () -> controller.addQuestion(1L, new NewQuestionRequest("name"), new ActiveUser(user), uriBuilder),
                "Product not found");

    }

    @Test
    @DisplayName("Should save a new question and send an email to owner")
    void test2(){
        Product product = new Product();
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(product);
        User user = new User("grodrigueset@gmail.com", new CleanPassword("123456"));

        Mockito.doAnswer(invocation -> {
            Question question = invocation.<Question>getArgument(0);
            ReflectionTestUtils.setField(question, "id", 1L);
            return null;
        }).when(manager).persist(Mockito.any(Question.class));

        NewQuestionRequest request = new NewQuestionRequest("question");

        ResponseEntity<?> responseEntity = controller.addQuestion(1L, request, new ActiveUser(user), uriBuilder);
        assertEquals(200, responseEntity.getStatusCode().value());
        Mockito.verify(mailer).send(Mockito.any(QuestionMail.class));
    }

}
