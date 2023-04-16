package courier;

import client.ScooterRent;
import io.restassured.response.ValidatableResponse;

import static constants.ConstantEndpoints.*;
import static io.restassured.RestAssured.given;

public class CourierApi extends ScooterRent {

    private Integer id;

    public ValidatableResponse courierReg(Courier courier) {
        return given()
                .spec(ScooterRent.requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(COURIER_API)
                .then();
    }

    public ValidatableResponse courierLogin(Courier courier) {
        return given()
                .spec(ScooterRent.requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(LOGIN_API)
                .then();
    }

    public ValidatableResponse courierDelete(String id) {
        return given()
                .spec(ScooterRent.requestSpecification())
                .delete(COURIER_API + id)
                .then();
    }

    //Проверка, что курьер удален и возникнет ошибка, если обратиться к его id
    public ValidatableResponse checkCourierDeleted(String id) {
        return given()
                .spec(ScooterRent.requestSpecification())
                .when()
                .get("/api/v1/courier/" + id + "/ordersCount")
                .then()
                .statusCode(404);
    }

    public void courierDeleteWithoutPassword(Courier courier) {
        Integer id = given()
                .spec(ScooterRent.requestSpecification())
                .body(courier)
                .when()
                .post("/api/v1/courier/login")
                .then().log().all().extract().body().<Integer>path("id");
        if (id != null) {
            given()
                    .spec(ScooterRent.requestSpecification())
                    .when()
                    .delete("/api/v1/courier/" + id)
                    .then().statusCode(200).log().all();
        }
    }

    public ValidatableResponse checkCourierDeletedWithoutPassword(Integer id) {
        return given()
                .spec(ScooterRent.requestSpecification())
                .when()
                .get("/api/v1/courier/" + id + "/ordersCount")
                .then()
                .statusCode(404);
    }

}