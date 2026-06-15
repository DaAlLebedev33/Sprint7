package ScooterPraktikumApi;

import ApiScooter.StepGetListOrders;
import ApiScooter.StepsCreateCourier;
import ApiScooter.StepsLoginCourier;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static constants.Endpoints.BASE_URL;

public class GetOrderTest {
    private final String login = "Alex52399";
    private final String password = "qwer234121";
    private final String firstName = "Андрей";

    private StepsCreateCourier stepsCreateCourier = new StepsCreateCourier();
    private StepGetListOrders stepGetListOrders = new StepGetListOrders();
    private StepsLoginCourier stepsLoginCourier = new StepsLoginCourier();

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

    }

    @Test
    @DisplayName("Получение списка заказов без ID курьера")
    @Description("Проверка, что запрос возвращает список заказов")
    public void getOrdersWithoutCourierIdTest() {
        Response response = stepGetListOrders.requestGetListOrders();

        stepGetListOrders.verifyGetOrdersSuccess(response);

    }

    @Test
    @DisplayName("Получение списка заказов по ID курьера")
    @Description("Проверка, что для существующего курьера возвращаются заказы")
    public void getOrdersByExistingCourierIdTest() {

        stepsCreateCourier.sendCreateCourierRequest(login, password, firstName);

        Response loginResponse = stepsLoginCourier.autorizationCourierRequest(login, password);
        int courierId = loginResponse.jsonPath().getInt("id");

        Response response = stepGetListOrders.getOrdersCourierIdRequest(courierId);

        stepGetListOrders.verifyGetOrdersSuccess(response);

        stepsCreateCourier.deleteCourierRequest(String.valueOf(courierId));
    }

    @Test
    @DisplayName("Получение списка заказов с несуществующим ID курьера")
    @Description("Проверка, что при несуществующем ID возвращается ошибка 404")
    public void getOrdersByNonExistentCourierIdTest() {
        int nonExistentId = 1231212121;

        Response response = stepGetListOrders.getOrdersCourierIdRequest(nonExistentId);

        stepGetListOrders.verifycationGetOrdersCourierNotFound(response, nonExistentId);
    }
}