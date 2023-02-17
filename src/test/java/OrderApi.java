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

    static public Response createOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        DataForCreateOrder dataForCreateOrder = new DataForCreateOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .body(dataForCreateOrder)
                .when()
                .post(UsedUrl.getOrderUrl);
    }


    static public Response takeOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        CourierApi.createCourier("Lopes", "1234", "dmitry");
        Response response1 = CourierApi.loginCourier("Lopes", "1234");
        int id = response1.then().extract().path("id");
        System.out.println(id);
        Response response = createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        int track = response.then().extract().path("track");
        System.out.println(track);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .put("api/v1/orders/accept/" + track + "?courierId=" + id);
    }

    static public Response takeOrderWithoutId(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        CourierApi.createCourier("Lopes", "1234", "dmitry");
        Response response1 = CourierApi.loginCourier("Lopes", "1234");
        int id = response1.then().extract().path("id");
        System.out.println(id);
        Response response = createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        int track = response.then().extract().path("track");
        System.out.println(track);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .put("api/v1/orders/accept/" + track + "?courierId=");
    }

    static public Response takeOrderWithFalseId(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        CourierApi.createCourier("Lopes", "1234", "dmitry");
        Response response1 = CourierApi.loginCourier("Lopes", "1234");
        int id = response1.then().extract().path("id");
        System.out.println(id);
        Response response = createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        int track = response.then().extract().path("track");
        System.out.println(track);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .put("api/v1/orders/accept/" + track + "?courierId=" + 31831838);
    }
    static public Response takeOrderWithFalseTrack(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        CourierApi.createCourier("Lopes", "1234", "dmitry");
        Response response1 = CourierApi.loginCourier("Lopes", "1234");
        int id = response1.then().extract().path("id");
        System.out.println(id);
        Response response = createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        int track = response.then().extract().path("track");
        System.out.println(track);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .put("api/v1/orders/accept/" + 402123 + "?courierId=" + id);
    }
    static public Response takeOrderWithoutTrack(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        CourierApi.createCourier("Lopes", "1234", "dmitry");
        Response response1 = CourierApi.loginCourier("Lopes", "1234");
        int id = response1.then().extract().path("id");
        System.out.println(id);
        Response response = createOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        int track = response.then().extract().path("track");
        System.out.println(track);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .put("api/v1/orders/accept/?courierId=" + 31831838);
    }
}
