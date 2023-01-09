import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AutorizationCourierTest {
    @Before
    @Description("Before everyone test we create courier for autorization")
    public void createCourierPlusAutorization() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
        File json = new File("src/test/resources/createCourierForAutorization.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        if(response.statusCode() == 200) {
            String id = response.then().statusCode(200).extract().path("id").toString();
            String path = String.format("/api/v1/courier/%s", id);
            given()
                    .header("Content-type", "application/json")
                    .and()
                    .when()
                    .delete(path);
        }
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);
    }
    @After
    @Description("After everyone tests we delete created courier")
    public void deleteCourierAfterAutorization() {
        File json = new File("src/test/resources/createCourierForAutorization.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        String id = response.then().statusCode(200).extract().path("id").toString();
        String path = String.format("/api/v1/courier/%s", id);
        given()
                .header("Content-type", "application/json")
                .and()
                .when()
                .delete(path)
                .then().statusCode(200);
    }

    @Test
    @Description("Test for autorization courier")
    public void courierCanAutorization() {
        File json = new File("src/test/resources/courierForAutorization.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(200).assertThat().body("id", notNullValue());
    }

    @Test
    @Description("Test for autorization courier with empty login")
    public void courierCantAutorizationIfLoginEmpty() {
        File json = new File("src/test/resources/courierForAutorizationWithoutLogin.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа" ));
    }

    @Test
    @Description("test for aurorization courier with empty password")
    public void courierCantAutorizationIfPasswordEmpty() {
        File json = new File("src/test/resources/courierForAutorizationWithoutPassword.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400).assertThat().body("message", equalTo("Недостаточно данных для входа" ));
    }
    @Test
    @Description("Test for autorization with false password")
    public void courierCantAutorizationIfPasswordFalse() {
        File json = new File("src/test/resources/courierForAutorizationWithFalsePassword.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404).assertThat().body("message", equalTo("Учетная запись не найдена" ));
    }
    @Test
    @Description("test for autorization with false login")
    public void courierCantAutorizationIfLoginFalse() {
        File json = new File("src/test/resources/courierForAutorizationWithFalseLogin.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404).assertThat().body("message", equalTo("Учетная запись не найдена" ));
    }
}

