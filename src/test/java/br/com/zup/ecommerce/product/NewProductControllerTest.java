package br.com.zup.ecommerce.product;

import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.net.URI;
import java.util.Set;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
public class NewProductControllerTest {

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    private ActiveUser loggedUser;


    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        loggedUser = (ActiveUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @ParameterizedTest
    @MethodSource("featureGenerator")
    @DisplayName("It should not create a product due to duplicated feature")
    @WithUserDetails(value = "gsantoset@gmail.com")
    void test1(Set<NewFeatureRequest> features) throws Exception {

        URI uri = new URI("/products");
        NewProductRequest request = new NewProductRequest("name", BigDecimal.TEN, 5, features, "description", 1L);

        String content = mapper.writeValueAsString(request);
        mvc.perform(MockMvcRequestBuilders.post(uri)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));

    }

    private static Stream<Arguments> featureGenerator() {
        return Stream.of(
                Arguments.of(Set.of(new NewFeatureRequest("feature1", "feature1"),
                        new NewFeatureRequest("feature2", "feature2"),
                        new NewFeatureRequest("feature3", "feature3"),
                        new NewFeatureRequest("feature3", "feature3")))
        );
    }
}
