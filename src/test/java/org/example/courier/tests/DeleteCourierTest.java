package org.example.courier.tests;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.BaseTest;
import org.example.courier.Courier;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeleteCourierTest extends BaseTest {

    //удаление зарегистрированного курьера
    @Test
    public void deleteCourierWithCorrectIdTest() {

        Courier registrationCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        ValidatableResponse registrationResponse = courierClient.createCourier(registrationCourier);
        assertEquals(HttpStatus.SC_CREATED, registrationResponse.extract().statusCode());
        assertTrue(registrationResponse.extract().body().jsonPath().getBoolean("ok"));

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResponse = courierClient.loginCourier(loginCourier);

        assertEquals(HttpStatus.SC_OK, loginResponse.extract().statusCode());
        int id = loginResponse.extract().body().jsonPath().getInt("id");
        assertTrue(id > 0);

        Courier deleteCourier = new Courier(id);
        ValidatableResponse validatableResponse = courierClient.deleteCourier(deleteCourier);
        assertEquals(HttpStatus.SC_OK, validatableResponse.extract().statusCode());
        assertTrue(validatableResponse.extract().body().jsonPath().getBoolean("ok"));

        Courier testLoginCourier = new Courier(courier.getLogin(), courier.getPassword());
        ValidatableResponse testLoginResponse = courierClient.loginCourier(testLoginCourier);
        assertEquals(HttpStatus.SC_NOT_FOUND, testLoginResponse.extract().statusCode());
        String expectedMessage = "Учетная запись не найдена";
        assertEquals(expectedMessage, testLoginResponse.extract().body().jsonPath().getString("message"));

    }

}
