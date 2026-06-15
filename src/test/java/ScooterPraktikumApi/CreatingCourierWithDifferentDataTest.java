package ScooterPraktikumApi;

import ApiScooter.StepsCreateCourier;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static constants.Endpoints.BASE_URL;

public class CreatingCourierWithDifferentDataTest extends StepsCreateCourier {
    private final String login = "Alex229945";
    private final String password = "qwer1299341";
    private final String firstName = "Алексей";


    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
    }

    @Test
    @DisplayName("Курьера можно создать")
    @Description("Проверка успешного создания курьера с валидными данными")
    public void createCourierSuccess() {

        Response response = sendCreateCourierRequest(login, password, firstName);

        getResponseSucsses(response);

        String id = getCourierId(login, password);

        deleteCourierRequest(id);
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Ошибка при создании двух одинаковых курьеров")
    public void createCourierTwoIndentical() {
        Response response = sendCreateCourierRequest(login, password, firstName);

        getResponseSucsses(response);

        Response response1 = sendCreateCourierRequest(login, password, firstName);

        getResponseErrorCode(response1);

        String id = getCourierId(login, password);

        deleteCourierRequest(id);
    }

    @Test
    @DisplayName("Если одного из полей нет, запрос возвращает ошибку")
    @Description("Ошибка при создании курьера без указания обязательного поля для регистрации")
    public void createCouriergetInsufficientData() {
        Response response = sendCreateCourierInsufficientData(password, firstName);

        getResponseErrorCodeInsufficientData(response);
    }
}