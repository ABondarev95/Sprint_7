package order;

import client.ScooterRent;
import io.restassured.response.ValidatableResponse;

import static constants.ConstantEndpoints.ORDERS_API;
import static io.restassured.RestAssured.given;

public class OrderApi extends ScooterRent {
    public ValidatableResponse createOrder(Order order) {
        return given()
                .spec(ScooterRent.requestSpecification())
                .and()
                .body(order)
                .when()
                .post(ORDERS_API)
                .then();

    }
}
