import TestData.TestData;
import courier.Courier;
import courier.CourierApi;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static constants.ConstantEndpoints.*;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;

public class CreateCourierTest {

    static String id;
    Courier courier;
    CourierApi courierApi = new CourierApi();

    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_PAGE;
        courier = TestData.getRandomCourier();
    }

    @After
    public void tearDown() {
        courierApi.courierDelete(id);
        courierApi.checkCourierDeleted(id);
    }

    @Test
    @DisplayName("Регистрации нового курьера")
    @Description("Проверка, что можно создать нового курьера с валидными значениями")
    public void newCourierTest() {
        ValidatableResponse response = courierApi.courierReg(courier);
        response.assertThat().statusCode(SC_CREATED).body("ok", is(true)).log().all();
        ValidatableResponse loginResponse = courierApi.courierLogin(courier);
        id = loginResponse.extract().path("id").toString();
    }

    @Test
    @DisplayName("Регистрация двух одинаковых курьеров")
    @Description("Проверка, что нельзя создать нового курьера, если вводимый логин уже есть в системе")
    public void duplicateCourierTest() {
        ValidatableResponse response = courierApi.courierReg(courier);
        response.statusCode(SC_CREATED);
        ValidatableResponse loginResponse = courierApi.courierLogin(courier);
        id = loginResponse.extract().path("id").toString();
        ValidatableResponse response2 = courierApi.courierReg(courier);
        response2.statusCode(SC_CONFLICT)
                .and().assertThat().body("message", is("Этот логин уже используется. Попробуйте другой."));

    }

    @Test
    @DisplayName("Регистрация курьера без логина")
    @Description("Проверка, что появится ошибка при попытке создания курьера без заполнения логина")
    public void courierWithoutLoginTest() {
        courier = new Courier("", courier.getPassword(), courier.getFirstName());
        ValidatableResponse response = courierApi.courierReg(courier);
        response.statusCode(SC_BAD_REQUEST)
                .and().assertThat().body("message", is("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Регистрация курьера без пароля")
    @Description("Проверка, что появится ошибка при попытке создания курьера без заполнения пароля")
    public void courierWithoutPasswordTest() {
        courier = new Courier(courier.getLogin(), "", courier.getFirstName());
        ValidatableResponse response = courierApi.courierReg(courier);
        response.statusCode(SC_BAD_REQUEST)
                .and().assertThat().body("message", is("Недостаточно данных для создания учетной записи"));
    }
}