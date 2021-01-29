package br.com.zup.ecommerce.product.question;

import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
public class NewQuestionController {

    @PersistenceContext
    private EntityManager manager;

    @Autowired
    private Mailer sender;

    @PostMapping("/products/{id:\\d+}/questions")
    @Transactional
    public ResponseEntity<?> addQuestion(@PathVariable("id") Long id,
                                         @RequestBody @Valid NewQuestionRequest request,
                                         @AuthenticationPrincipal ActiveUser user,
                                         UriComponentsBuilder uriBuilder){
        Product product = manager.find(Product.class, id);
        if (product == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");

        Question question = request.toModel(product, user.getUser());
        manager.persist(question);
        URI location = uriBuilder.path("/products/{id:\\d+}/questions/{questionId:\\d+}")
                .buildAndExpand(product.getId(), question.getId())
                .toUri();

        sender.newQuestion(question, location);

        Set<QuestionResponse> questionResponses = product.getQuestions().stream()
                .map(q -> new QuestionResponse(q))
                .collect(Collectors.toSet());
        return ResponseEntity.ok(questionResponses);
    }
}
