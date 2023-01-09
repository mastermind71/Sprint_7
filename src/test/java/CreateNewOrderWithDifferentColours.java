import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.example.DataForCreateOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateNewOrderWithDifferentColours {
    private final String[] colour;

    public CreateNewOrderWithDifferentColours(String[] color) {
        this.colour = color;
    }

    @Parameterized.Parameters
    public static String[][][] getColor() {
        return new String[][][]{
                {{"BLACK"}},
                {{"GREY"}},
                {{"BLACK", "GREY"}},
                {{}},
        };
    }
    @Before
    @Description("Url api scooter")
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Description("Test orders with one color, with two colours, without colours and test for receiving number order")
    public void createNewOrderWithOneColor() {
        DataForCreateOrder order = new DataForCreateOrder("Dmitry", "Kazakov", "Lva tolstovo 16", "Park kulturi", "89029447532", 2, "10.02.2023", "Privezi pobistree pozhaluista", colour);
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders");
        response.then().statusCode(201).extract().path("track");
        int a = response.then().extract().path("track");
        System.out.println(a);
    }
}



