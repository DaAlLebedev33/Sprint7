package ApiScooter;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.Endpoints.LOGIN_COURIER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class StepsLoginCourier {

    @Step("Авторизация курьера")
    public Response autorizationCourierRequest(String login, String password) {
        LoginRequest loginRequest = new LoginRequest(login, password);

        return given()
                .header("Content-type", "application/json")
                .body(loginRequest)
                .post(LOGIN_COURIER);
    }

    @Step("Успешный логин")
    public String autorizationCourierSuccess(Response response) {
        response.then()
                .statusCode(200)
                .body("id", notNullValue());

        return response.jsonPath().getString("id");
    }

    @Step("Проверка ошибки на несуществующие данные")
    public void getResponseErrorCodeAutorization(Response response) {
        response.then()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Step("Авторизация с отсутствующим полем")
    public Response autorizationCourierRequiredFieldMissing(String password) {
        AutorizationIncorrected autorizationIncorrected = new AutorizationIncorrected(password);

        return given()
                .header("Content-type", "application/json")
                .body(autorizationIncorrected)
                .post(LOGIN_COURIER);
    }

    @Step("Проверка ошибки если не указать одно из полей")
    public void getResponseErrorCodeRequiredFieldMissing(Response response) {
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
}