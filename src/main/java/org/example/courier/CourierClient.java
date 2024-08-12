package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.example.RestClient;
import static org.hamcrest.Matchers.*;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    private static final String COURIER_PATH = "api/v1/courier/";
    private static final String COURIER_LOGIN_PATH = "api/v1/courier/login";

    @Step
    public ValidatableResponse create(Courier courier) {
        return given().spec(getBaseSpec()).body(courier).when().post(COURIER_PATH).then();
    }

    @Step
    public ValidatableResponse login(Courier courier) {
        return given().spec(getBaseSpec()).body(courier).when().post(COURIER_LOGIN_PATH).then();
    }

    @Step
    public ValidatableResponse delete(Courier courier) {
        return given().spec(getBaseSpec()).body(courier).when().delete(COURIER_PATH + courier.getId()).then().time(lessThan(3000L));
    }
}
