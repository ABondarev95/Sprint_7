package TestData;

import com.github.javafaker.Faker;
import courier.Courier;


public class TestData {
    private static Courier courier = null;

    public static Courier getRandomCourier() {
        if (courier == null) {
            Faker faker = new Faker();
            courier = new Courier(
                    faker.name().username(),
                    faker.internet().password(),
                    faker.name().firstName()
            );
        }
        return courier;
    }
}