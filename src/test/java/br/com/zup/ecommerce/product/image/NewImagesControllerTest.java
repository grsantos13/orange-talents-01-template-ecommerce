package br.com.zup.ecommerce.product.image;

import br.com.zup.ecommerce.category.Category;
import br.com.zup.ecommerce.product.Product;
import br.com.zup.ecommerce.product.feature.NewFeatureRequest;
import br.com.zup.ecommerce.shared.mail.Mailer;
import br.com.zup.ecommerce.shared.security.ActiveUser;
import br.com.zup.ecommerce.user.CleanPassword;
import br.com.zup.ecommerce.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

public class NewImagesControllerTest {
    private Uploader uploader = Mockito.mock(Uploader.class);
    private EntityManager manager = Mockito.mock(EntityManager.class);
    private Mailer mailer = Mockito.mock(Mailer.class);
    private NewImagesController controller = new NewImagesController(manager, uploader);
    private UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080");

    @ParameterizedTest
    @MethodSource("imageGenerator")
    @DisplayName("It should return an error due to product not being found")
    void test1(NewImageRequest request){

        Mockito.when(manager.find(Product.class, 1L)).thenReturn(null);
        User user = new User("gsantoset@gmail.com", new CleanPassword("123456"));

        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.addImages(1L, request, new ActiveUser(user)),
                "Product not found");
    }

    @ParameterizedTest
    @MethodSource("imageGenerator")
    @DisplayName("It should return an error due to product not being requester property")
    void test2( NewImageRequest request){
        User user = new User("gsantoset@gmail.com", new CleanPassword("123456"));
        Product product = new Product("name", BigDecimal.TEN, 5, Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature2", "feature2"),
                new NewFeatureRequest("feature3", "feature3")), "description", new Category("name", null), user);
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(product);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.addImages(1L, request, new ActiveUser(new User("gsantoset2@gmail.com", new CleanPassword("123456")))),
                "Product with id " + 1L + " is not your property");
    }

    private static Stream<Arguments> imageGenerator(){
        MockMultipartFile file = new MockMultipartFile("image", "image.png", "application/png", "image".getBytes());
        NewImageRequest request = new NewImageRequest();
        request.setImages(Arrays.asList(file));
        return Stream.of(
                Arguments.of(request)
        );
    }

    @ParameterizedTest
    @MethodSource("wrongImageGenerator")
    @DisplayName("It should return an error due to images not being valid")
    void test3( NewImageRequest request){
        User user = new User("gsantoset@gmail.com", new CleanPassword("123456"));
        Product product = new Product("name", BigDecimal.TEN, 5, Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature2", "feature2"),
                new NewFeatureRequest("feature3", "feature3")), "description", new Category("name", null), user);
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(product);

        Assertions.assertThrows(ResponseStatusException.class,
                () -> controller.addImages(1L, request, new ActiveUser(new User("gsantoset2@gmail.com", new CleanPassword("123456")))),
                "Images must be JPG, JPEG or PNG");
    }



    private static Stream<Arguments> wrongImageGenerator(){
        MockMultipartFile file1 = new MockMultipartFile("file", "file.pdf", "application/pdf", "file".getBytes());
        MockMultipartFile file2 = new MockMultipartFile("image", "image.png", "application/png", "image".getBytes());
        NewImageRequest request1 = new NewImageRequest();
        request1.setImages(Arrays.asList(file1));
        NewImageRequest request2 = new NewImageRequest();
        request2.setImages(Arrays.asList(file1, file2));
        return Stream.of(
                Arguments.of(request1),
                Arguments.of(request2)
        );
    }

    @ParameterizedTest
    @MethodSource("imageGenerator")
    @DisplayName("It should return an error due to product not being requester property")
    void test4( NewImageRequest request){
        User user = new User("gsantoset@gmail.com", new CleanPassword("123456"));
        Product product = new Product("name", BigDecimal.TEN, 5, Set.of(new NewFeatureRequest("feature1", "feature1"),
                new NewFeatureRequest("feature2", "feature2"),
                new NewFeatureRequest("feature3", "feature3")), "description", new Category("name", null), user);
        Mockito.when(manager.find(Product.class, 1L)).thenReturn(product);

        Set<String> links = Set.of(uriComponentsBuilder.path("/image1.png").toUriString());
        Mockito.when(uploader.upload(request.getImages())).thenReturn(links);

        ResponseEntity<?> responseEntity = controller.addImages(1L, request, new ActiveUser(user));
        Assertions.assertEquals(200, responseEntity.getStatusCode().value());
        Assertions.assertEquals(links, responseEntity.getBody());
    }
}
