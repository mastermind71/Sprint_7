import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.example.Orders;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.*;

public class GetOrderDeskTest {

    @Before
    @Description("Url api scooter")
    public void setUp(){
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Description("Test for receiving list of orders")
    public void getListOrders(){
        Orders orders = given()
                .header("Content-type", "application/json")
                .get("/api/v1/orders")
                .body().as(Orders.class);
    }
}

