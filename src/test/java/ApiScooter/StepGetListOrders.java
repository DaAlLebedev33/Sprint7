package ApiScooter;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static constants.Endpoints.GET_LIST_ORDERS;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class StepGetListOrders {

    @Step("Получить список заказов")
    public Response requestGetListOrders() {
        return given()
                .header("Content-type", "application/json")
                .get(GET_LIST_ORDERS);
    }

    @Step("Получить список заказов по ID курьера: {courierId}")
    public Response getOrdersCourierIdRequest(int courierId) {
        return given()
                .header("Content-type", "application/json")
                .queryParam("courierId", courierId)
                .get(GET_LIST_ORDERS);
    }

    @Step("Проверка успешного получения списка заказов")
    public void verifyGetOrdersSuccess(Response response) {
        response.then()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders", instanceOf(List.class));
    }

    @Step("Проверка ошибки при получении заказов с несуществующим ID курьера")
    public void verifycationGetOrdersCourierNotFound(Response response, int courierId) {
        response.then()
                .statusCode(404)
                .body("message", equalTo("Курьер с идентификатором " + courierId + " не найден"));
    }
}