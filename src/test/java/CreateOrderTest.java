import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import order.Order;
import order.OrderApi;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static constants.ConstantEndpoints.MAIN_PAGE;
import static constants.ConstantOrder.*;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest{
        private final List<String> color;
        OrderApi orderApi = new OrderApi();

        public CreateOrderTest(List<String> color) {
            this.color = color;
        }

        @Parameterized.Parameters
        public static Object[][] checkCreateOrderWithChoiceColor() {
            return new Object[][]{
                    {List.of("GREY")},
                    {List.of("BLACK")},
                    {List.of("GREY", "BLACK")},
                    {List.of()},
            };
        }

        @Before
        public void setUp() {
            RestAssured.baseURI = MAIN_PAGE;
        }

        @Test
        @DisplayName("Создание заказа с выбором цвета")
        @Description("Проверка, что можно создать заказ на: серый цвет, черный цвет, выбор двух цветов, без выбора цвета")
        public void paramCreateOrderTest() {
            Order order = new Order(FIRST_NAME_ORDERS,
                    lAST_NAME_ORDERS,
                    ADDRESS,
                    METRO_STATION,
                    PHONE,
                    RENT_TIME,
                    DELIVERY_DATE,
                    COMMENT,
                    color);
            ValidatableResponse response = orderApi.createOrder(order);
            response.assertThat().statusCode(SC_CREATED).and().body("track", notNullValue());
        }
}
