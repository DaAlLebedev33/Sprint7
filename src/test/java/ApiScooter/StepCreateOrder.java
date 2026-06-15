package ApiScooter;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static constants.Endpoints.CREATE_ORDER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class StepCreateOrder {
    @Step("Создание заказа с разными цветами")
    public Response createOrderRequest(String firstName, String lastName, String address,
                                       int metroStation, String phone, int rentTime,
                                       String deliveryDate, String comment, List<String> colors) {
        OrderRequest orderRequest = new OrderRequest(firstName, lastName, address,
                metroStation, phone, rentTime,
                deliveryDate, comment, colors);

        return given()
                .header("Content-type", "application/json")
                .body(orderRequest)
                .post(CREATE_ORDER);
    }

    @Step("Проверка успешного создания заказа")
    public int createOrderSuccess(Response response) {
        response.then()
                .statusCode(201)
                .body("track", notNullValue());

        return response.jsonPath().getInt("track");
    }
}