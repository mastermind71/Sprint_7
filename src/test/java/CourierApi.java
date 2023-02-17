import io.restassured.response.Response;
import org.example.UsedUrl;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;

public class CourierApi extends BaseApi {
    static public Response createCourier(String login, String password, String firstName) {
        Courier courier = new Courier(login, password, firstName);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(UsedUrl.createCourier);
    }

    static public Response loginCourier(String login, String password) {
        Courier courier = new Courier();
        courier.setLogin(login)
                .setPassword(password);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(UsedUrl.loginCourier);
    }

    public static Response deleteCourier(String login, String password) {
        Response response = loginCourier(login, password);
        if (response.statusCode() != SC_OK) {
            return response;
        }
        String id = response.then().statusCode(SC_OK).extract().path("id").toString();
        String path = String.format(UsedUrl.createCourier + "/%s", id);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(path);
    }

    public static Response deleteCourierForSecondExercise(String login, String password) {
        Response response = loginCourier(login, password);
        String id = response.then().extract().path("id").toString();
        String path = String.format(UsedUrl.createCourier + "/%s", id);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(path);
    }

    public static Response deleteCourierWithoutId(String login, String password) {
        Response response = loginCourier(login, password);
        String id = response.then().extract().path("id").toString();
        String path = String.format(UsedUrl.createCourier);
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(path);
    }

    public static Response deleteCourierWithFalseId() {
        return given()
                .spec(baseApi())
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(UsedUrl.falseIdUrlForDelete);
    }
}
