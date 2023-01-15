import io.restassured.response.Response;
import org.example.DataForCreateOrder;
import org.example.Orders;
import org.example.UsedUrl;
import static io.restassured.RestAssured.given;

public class OrderApi extends BaseApi {

    static public Orders takeOrderList() {
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .get(UsedUrl.getOrderUrl)
                .body().as(Orders.class);
    }
    static public Response createOrder(String firstName, String lastName, String address, String metroStation,String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        DataForCreateOrder dataForCreateOrder   = new DataForCreateOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .body(dataForCreateOrder)
                .when()
                .post(UsedUrl.getOrderUrl);
    }
}
