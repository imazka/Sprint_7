package org.example.orders;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.RestClient;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {

    private static final String ORDERS_PATH = "/api/v1/orders";

    @Step
    public ValidatableResponse createOrder(Order order) {
        return given().spec(getBaseSpec()).body(order).when().post(ORDERS_PATH).then();
    }

    @Step
    public ValidatableResponse getOrder(int id) {
        return given().spec(getBaseSpec()).when().get(ORDERS_PATH + "/track?t=" + id).then();
    }

    @Step
    public ValidatableResponse getOrderList() {
        return given().spec(getBaseSpec()).when().get(ORDERS_PATH).then();
    }

}
