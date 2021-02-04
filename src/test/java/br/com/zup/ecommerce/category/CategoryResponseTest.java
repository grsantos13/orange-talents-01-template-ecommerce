package br.com.zup.ecommerce.category;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryResponseTest {

    private CategoryResponse categoryResponse;

    @ParameterizedTest
    @MethodSource("generator")
    @DisplayName("Should test if the mother category name is correctly returned")
    void test1(boolean expected, Category categoryMother){
        categoryResponse = new CategoryResponse(new Category("category", categoryMother));
        assertEquals(expected, categoryResponse.getMotherCategoryName() == null);
    }

    private static Stream<Arguments> generator(){
        return Stream.of(
                Arguments.of(true, null),
                Arguments.of(false, new Category("name", null))
        );
    }
}
