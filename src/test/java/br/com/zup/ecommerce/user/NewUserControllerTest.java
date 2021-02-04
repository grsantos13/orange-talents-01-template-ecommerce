package br.com.zup.ecommerce.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class NewUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EntityManager manager;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @DisplayName("Should create a user successfully")
    void test1() throws Exception {
        NewUserRequest request = new NewUserRequest("teste@email.com", "123456");
        String json = mapper.writeValueAsString(request);
        mvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should not create a user without login")
    void test2() throws Exception {
        NewUserRequest request = new NewUserRequest("", "123456");
        String json = mapper.writeValueAsString(request);
        mvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$['fieldErrors']", hasSize(1)))
                .andExpect(jsonPath("$['fieldErrors'][0]['field']", containsString("login")))
                .andExpect(jsonPath("$['fieldErrors'][0]['message']", containsString("must not be blank")))
                .andExpect(jsonPath("$['numberOfErrors']", is(1)));
    }

    @Test
    @DisplayName("Should not create a user without password")
    void test3() throws Exception {
        NewUserRequest request = new NewUserRequest("teste@email.com", "");
        String json = mapper.writeValueAsString(request);
        mvc.perform(MockMvcRequestBuilders
                .post("/users")
                .content(json)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$['fieldErrors']", hasSize(2)))
                .andExpect(jsonPath("$['fieldErrors'][0]['field']", containsString("password")))
                .andExpect(jsonPath("$['numberOfErrors']", is(2)));
    }
}
