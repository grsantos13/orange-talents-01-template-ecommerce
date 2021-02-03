package br.com.zup.ecommerce.purchase;

import br.com.zup.ecommerce.purchase.payment.PaymentGatewayResponse;
import br.com.zup.ecommerce.purchase.transaction.Transaction;
import br.com.zup.ecommerce.purchase.transaction.TransactionStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PurchaseTest {
    @Test
    @DisplayName("Should add a transaction")
    void test1(){
        Purchase purchase = new Purchase();
        PaymentGatewayResponse gatewayResponse = (p) -> {
            return new Transaction(TransactionStatus.SUCCESS, "1", p);
        };

        purchase.addTransaction(gatewayResponse);
    }

    @Test
    @DisplayName("Should return an error to add the same transaction twice")
    void test2(){
        Purchase purchase = new Purchase();
        PaymentGatewayResponse gatewayResponse = (p) -> {
            return new Transaction(TransactionStatus.ERROR, "1", p);
        };
        purchase.addTransaction(gatewayResponse);

        assertThrows(IllegalStateException.class, () -> purchase.addTransaction(gatewayResponse));
    }

    @Test
    @DisplayName("Should return an error to add a transaction after being successfully processed")
    void test3(){
        Purchase purchase = new Purchase();
        PaymentGatewayResponse gatewayResponse = (p) -> {
            return new Transaction(TransactionStatus.SUCCESS, "1", p);
        };
        purchase.addTransaction(gatewayResponse);

        PaymentGatewayResponse gatewayResponse2 = (p) -> {
            return new Transaction(TransactionStatus.SUCCESS, "2", p);
        };

        assertThrows(IllegalStateException.class, () -> purchase.addTransaction(gatewayResponse2));
    }

    @ParameterizedTest
    @MethodSource("generatorTest4")
    @DisplayName("Should check if a purchase was successfully processes")
    void test4(boolean expected, List<PaymentGatewayResponse> responses){
        Purchase purchase = new Purchase();
        responses.forEach(purchase::addTransaction);

        assertEquals(expected, purchase.successfullyProcessed());

    }

    private static Stream<Arguments> generatorTest4(){
        PaymentGatewayResponse gatewayResponseSuccess = (p) -> {
            return new Transaction(TransactionStatus.SUCCESS, "1", p);
        };
        PaymentGatewayResponse gatewayResponseFail = (p) -> {
            return new Transaction(TransactionStatus.ERROR, "2", p);
        };
        return Stream.of(
                Arguments.of(true, List.of(gatewayResponseSuccess)),
                Arguments.of(false, List.of(gatewayResponseFail)),
                Arguments.of(true, List.of(gatewayResponseFail, gatewayResponseSuccess)),
                Arguments.of(false, List.of())
        );
    }
}
