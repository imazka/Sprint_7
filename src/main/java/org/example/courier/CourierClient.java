package org.example.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.RestClient;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class CourierClient extends RestClient {

    private static final String COURIER_PATH = "api/v1/courier/";
    private static final String COURIER_LOGIN_PATH = "api/v1/courier/login";

    @Step
    public ValidatableResponse createCourier(Courier courier) {
        return given().spec(getBaseSpec()).body(courier).when().post(COURIER_PATH).then();
    }

    @Step
    public ValidatableResponse loginCourier(Courier courier) {
        return given().spec(getBaseSpec()).body(courier).when().post(COURIER_LOGIN_PATH).then();
    }

    @Step
    public ValidatableResponse deleteCourier(Courier courier) {
        return given().spec(getBaseSpec()).body(courier).when().delete(COURIER_PATH + courier.getId()).then().time(lessThan(3000L));
    }
}
