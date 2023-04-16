package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import static constants.ConstantEndpoints.MAIN_PAGE;

public class ScooterRent {
    public static RequestSpecification requestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(MAIN_PAGE)
                .setContentType(ContentType.JSON)
                .build();
    }
}
