package ScooterPraktikumApi;

import ApiScooter.StepCreateOrder;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static constants.Endpoints.BASE_URL;

public class CreateOrderParametrizeTest {

    private StepCreateOrder stepCreateOrder = new StepCreateOrder();

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of("BLACK цвет", "Naruto", "Uchiha", "Konoha, 142 apt.", 4,
                        "+7 800 355 35 35", 5, "2025-06-06", "Saske, come back to Konoha", Arrays.asList("BLACK")),
                Arguments.of("GREY цвет", "Sakura", "Haruno", "Konoha, 15 apt.", 3,
                        "+7 800 555 35 35", 3, "2025-06-10", "I love Sasuke", Arrays.asList("GREY")),
                Arguments.of("Оба цвета", "Sasuke", "Uchiha", "Konoha, 7 apt.", 7,
                        "+7 800 777 35 35", 7, "2025-06-15", "I will restore my clan", Arrays.asList("BLACK", "GREY")),
                Arguments.of("Без цвета", "Kakashi", "Hatake", "Konoha, 1 apt.", 1, "+7 800 111 35 35", 2,
                        "2025-06-20", "I'm late", Collections.emptyList())
        );
    }

    @ParameterizedTest
    @MethodSource("orderData")
    @DisplayName("Создание заказа")
    @Description("Проверка создания заказа с разными данными и проверка успешного ответа")
    public void createOrderWithDifferentData(String testName,
                                             String firstName,
                                             String lastName,
                                             String address,
                                             int metroStation,
                                             String phone,
                                             int rentTime,
                                             String deliveryDate,
                                             String comment,
                                             List<String> colors) {

        Response response = stepCreateOrder.createOrderRequest(firstName, lastName, address,
                metroStation, phone, rentTime,
                deliveryDate, comment, colors);

        int track = stepCreateOrder.createOrderSuccess(response);
        System.out.println("Тест: " + testName + " - Track: " + track);
    }
}