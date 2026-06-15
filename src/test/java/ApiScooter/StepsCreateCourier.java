package ApiScooter;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static constants.Endpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


public class StepsCreateCourier {

    @Step("Ввести обязательны поля для регистрации")
    public Response sendCreateCourierRequest(String login, String password, String firstName) {
        CreateCourier createCourier = new CreateCourier(login, password, firstName);

        return given()
                .header("Content-type", "application/json")
                .body(createCourier)
                .post(CREATE_COURIER);
    }

    @Step("Получить ID курьера")
    public String getCourierId(String login, String password) {
        LoginRequest loginRequest = new LoginRequest(login, password);

        Response response = given()
                .header("Content-type", "application/json")
                .body(loginRequest)
                .post(GET_ID_COURIER);

        if (response.statusCode() == 200) {
            return response.jsonPath().getString("id");
        }
        return null;
    }

    @Step("Удалить курьера")
    public void deleteCourierRequest(String id) {
        given()
                .header("Content-type", "application/json")
                .delete(DELETE_COURIER + id)
                .then()
                .statusCode(200)
                .body("ok", equalTo(true));
    }

    @Step("Проверка положительно создания курьера")
    public void getResponseSucsses(Response response) {
        response.then()
                .statusCode(201)
                .body("ok", equalTo(true));
    }

    @Step("Проверка если курьер с таким логином уже создан")
    public void getResponseErrorCode(Response response) {
        response.then()
                .statusCode(409)
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Создание абонента без обязательного поля")
    public Response sendCreateCourierInsufficientData(String password, String firstName) {
        CreateCourierInsufficientData createCourierInsufficientData = new CreateCourierInsufficientData(password, firstName);

        return given()
                .header("Content-type", "application/json")
                .body(createCourierInsufficientData)
                .post(CREATE_COURIER);
    }

    @Step("Проверка если курьер с таким логином уже создан")
    public void getResponseErrorCodeInsufficientData(Response response) {
        response.then()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
}