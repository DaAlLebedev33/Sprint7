package ScooterPraktikumApi;

import ApiScooter.StepsCreateCourier;
import ApiScooter.StepsLoginCourier;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static constants.Endpoints.BASE_URL;

public class CourierLoginTest {
    private StepsCreateCourier stepsCreateCourier = new StepsCreateCourier();
    private StepsLoginCourier stepsLoginCourier = new StepsLoginCourier();

    private final String login = "Alex523457";
    private final String password = "qwer123412";
    private final String firstName = "Алексей";
    private final String incorrectedLogin = "Alex523451222";
    private String id;

    @BeforeEach
    public void setUp() {
        RestAssured.baseURI = BASE_URL;

    }

    @Test
    @DisplayName("Авторизация курьера со всеми обязательными полями")
    @Description("Проверка успешного создания курьера с валидными данными и возвращением id")
    public void createCourierSuccess() {
        stepsCreateCourier.sendCreateCourierRequest(login, password, firstName);

        Response response = stepsLoginCourier.autorizationCourierRequest(login, password);

        id = stepsLoginCourier.autorizationCourierSuccess(response);
    }

    @Test
    @DisplayName("Система вернёт ошибку, если неправильно указать логин или пароль;")
    @Description("Проверка ошибки если ввести некорректные данные")
    public void createCourierIncorrectedData() {
        stepsCreateCourier.sendCreateCourierRequest(login, password, firstName);

        Response response = stepsLoginCourier.autorizationCourierRequest(incorrectedLogin, password);
        stepsLoginCourier.getResponseErrorCodeAutorization(response);

        id = stepsCreateCourier.getCourierId(login, password);
    }

    @Test
    @DisplayName("Если какого-то поля нет, запрос возвращает ошибку")
    @Description("Система вернёт ошибку если нет обязательного поля")
    public void createCourierRequiredFieldMissing() {
        stepsCreateCourier.sendCreateCourierRequest(login, password, firstName);

        Response response = stepsLoginCourier.autorizationCourierRequiredFieldMissing(password);
        stepsLoginCourier.getResponseErrorCodeRequiredFieldMissing(response);

        id = stepsCreateCourier.getCourierId(login, password);
    }

    @AfterEach
    public void deleteCourier() {
            stepsCreateCourier.deleteCourierRequest(id);
    }
}
