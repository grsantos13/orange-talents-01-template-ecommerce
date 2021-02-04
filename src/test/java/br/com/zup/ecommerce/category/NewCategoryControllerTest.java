package br.com.zup.ecommerce.category;

import br.com.zup.ecommerce.shared.security.ActiveUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class NewCategoryControllerTest {

    @Autowired
    protected WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EntityManager manager;

    @Autowired
    private ObjectMapper mapper;

    private ActiveUser activeUser;

    @BeforeEach
    void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
        activeUser = (ActiveUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Test
    @DisplayName("Should create a category successfully")
    @WithUserDetails(value = "gsantoset@gmail.com")
    void test1() throws Exception {
        NewCategoryRequest request = new NewCategoryRequest("Celular", null);
        String json = mapper.writeValueAsString(request);
        mvc.perform(MockMvcRequestBuilders
                .post("/categories")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should not create a category successfully due to duplicated name")
    @WithUserDetails(value = "gsantoset@gmail.com")
    void test2() throws Exception {
        NewCategoryRequest request = new NewCategoryRequest("Celular", null);
        String json = mapper.writeValueAsString(request);
        mvc.perform(MockMvcRequestBuilders
                .post("/categories")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        mvc.perform(MockMvcRequestBuilders
                .post("/categories")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$['fieldErrors'][0]['field']", containsString("name")))
                .andExpect(jsonPath("$['fieldErrors'][0]['message']", containsString("O valor informado já existe")));
    }
}
