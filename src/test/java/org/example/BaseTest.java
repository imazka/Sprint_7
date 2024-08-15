package org.example;

import com.google.gson.Gson;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.courier.Courier;
import org.example.courier.CourierClient;
import org.example.orders.Order;
import org.example.orders.OrderClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BaseTest {
    protected CourierClient courierClient;
    protected OrderClient orderClient;
    protected Courier courier;
    protected JsonFileClient jsonFileClient;
    protected Gson gson;
    protected Order order;
    protected Integer r = new Random().nextInt(90000);

    @BeforeEach
    public void setUp() {

        courierClient = new CourierClient();
        jsonFileClient = new JsonFileClient();
        orderClient = new OrderClient();
        gson = new Gson();
        courier = gson.fromJson(jsonFileClient.dataTestFileRead(JsonFileClient.COURIER_DATA_JSON_FILE_PATH), Courier.class);
        order = gson.fromJson(jsonFileClient.dataTestFileRead(JsonFileClient.NEW_ORDER_DATA_JSON_FILE_PATH), Order.class);

    }

    @AfterEach
    public void deleteCourier() {

        try {

            Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword());
            ValidatableResponse loginResponse = courierClient.loginCourier(loginCourier);
            int id = loginResponse.extract().body().jsonPath().getInt("id");

            Courier deleteCourier = new Courier(id);
            courierClient.deleteCourier(deleteCourier);

        } catch (Exception ignored) {}

    }

}
