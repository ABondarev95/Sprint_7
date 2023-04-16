import client.ScooterRent;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static constants.ConstantEndpoints.MAIN_PAGE;
import static constants.ConstantEndpoints.ORDERS_API;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = MAIN_PAGE;
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что в тело ответа возвращается список заказов")
    public void getOrderListTest() {
        given()
                .spec(ScooterRent.requestSpecification())
                .when()
                .get(ORDERS_API)
                .then()
                .statusCode(SC_OK).assertThat().body("orders", notNullValue());

    }
}
