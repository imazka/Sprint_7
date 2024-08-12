package org.example.courier.tests;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.example.BaseTest;
import org.example.courier.Courier;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistrationCourierTest extends BaseTest {

    //тест регистрации курьера с позитивным сценарием
    @Test
    public void courierRegistration() {

        Courier registrationCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        ValidatableResponse registrationResponse = courierClient.create(registrationCourier);
        assertEquals(HttpStatus.SC_CREATED, registrationResponse.extract().statusCode());
        assertTrue(registrationResponse.extract().body().jsonPath().getBoolean("ok"));

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResponse = courierClient.login(loginCourier);

        assertEquals(HttpStatus.SC_OK, loginResponse.extract().statusCode());
        int id = loginResponse.extract().body().jsonPath().getInt("id");
        assertTrue(id > 0);

        Courier deleteCourier = new Courier(id);
        ValidatableResponse deleteResponse = courierClient.delete(deleteCourier);
        int statusCode = deleteResponse.extract().statusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        assertTrue(deleteResponse.extract().body().jsonPath().getBoolean("ok"));

    }

    //тест регистрации курьера с ранее зарегистрированным логином
    @Test
    public void registerCourierWithExistingLoginTest() {

        Courier registrationCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        ValidatableResponse registrationResponse = courierClient.create(registrationCourier);
        assertEquals(HttpStatus.SC_CREATED, registrationResponse.extract().statusCode());
        assertTrue(registrationResponse.extract().body().jsonPath().getBoolean("ok"));

        Courier secondRegistrationCourier = new Courier(courier.getLogin(), courier.getPassword(), courier.getFirstName());
        ValidatableResponse secondRegistrationResponse = courierClient.create(secondRegistrationCourier);

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResponse = courierClient.login(loginCourier);

        assertEquals(HttpStatus.SC_OK, loginResponse.extract().statusCode());
        int id = loginResponse.extract().body().jsonPath().getInt("id");
        assertTrue(id > 0);

        Courier deleteCourier = new Courier(id);
        ValidatableResponse deleteResponse = courierClient.delete(deleteCourier);
        int statusCode = deleteResponse.extract().statusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        assertTrue(deleteResponse.extract().body().jsonPath().getBoolean("ok"));

        String expectedMassege = "Этот логин уже используется";
        assertEquals(HttpStatus.SC_CONFLICT, secondRegistrationResponse.extract().statusCode());
        assertEquals(expectedMassege, secondRegistrationResponse.extract().body().jsonPath().getString("message"));

    }

    //тест регистрации курьера без пароля
    @Test
    public void registerCourierWithoutLoginTest() {

        Courier registrationCourier = new Courier();
        registrationCourier.setPassword(courier.getPassword() + "456");
        registrationCourier.setFirstName(courier.getFirstName() + "mark");
        ValidatableResponse registrationResponse = courierClient.create(registrationCourier);

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals(HttpStatus.SC_BAD_REQUEST, registrationResponse.extract().statusCode());
        assertEquals(expectedMessage, registrationResponse.extract().body().jsonPath().getString("message"));

    }

    //тест регистрации курьера без имени
    @Test
    public void registerCourierWithoutFirstNameTest() {

        Courier registrationCourier = new Courier();
        registrationCourier.setLogin(courier.getLogin());
        registrationCourier.setPassword(courier.getPassword());

        ValidatableResponse registrationResponse = courierClient.create(registrationCourier);

        Courier loginCourier = new Courier(courier.getLogin(), courier.getPassword());
        ValidatableResponse loginResponse = courierClient.login(loginCourier);

        assertEquals(HttpStatus.SC_OK, loginResponse.extract().statusCode());
        int id = loginResponse.extract().body().jsonPath().getInt("id");
        assertTrue(id > 0);

        Courier deleteCourier = new Courier(id);
        ValidatableResponse deleteResponse = courierClient.delete(deleteCourier);
        int statusCode = deleteResponse.extract().statusCode();
        assertEquals(HttpStatus.SC_OK, statusCode);
        assertTrue(deleteResponse.extract().body().jsonPath().getBoolean("ok"));

        String expectedMessage = "Недостаточно данных для создания учетной записи";
        assertEquals(HttpStatus.SC_BAD_REQUEST, registrationResponse.extract().statusCode());
        assertEquals(expectedMessage, registrationResponse.extract().body().jsonPath().getString("message"));

    }

}
