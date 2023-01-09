import io.restassured.RestAssured;
import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateNewCourierTest {
    @Before
    @Description("Url api scooter")
    public void setUp() {
        RestAssured.baseURI = "http://qa-scooter.praktikum-services.ru/";
    }

    @Test
    @Description("Test for create courier with all positive parameters")
    public void weCanCreateNewCourierWithPositiveParameters() {
        File json = new File("src/test/resources/createNewCourier.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);


    }
    @After
    @Description("After everyone tests, delete courier with parameters")
    public void deleteThisCourier() {
        File json = new File("src/test/resources/fileForDeleteNewCourier.json");
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login");
        if(response.statusCode() != 200) {
            return;
        }
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
    @Description("Test for create courier twice attempt")
    public void tryingCreateDoubleCouriersWithSameNames() {
        File json = new File("src/test/resources/createNewCourier.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(409);
    }
    @Test
    @Description("Test for comparison body response")
    public void checkOkTrue(){
        File json = new File("src/test/resources/createNewCourier.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(201).assertThat().body("ok",equalTo(true));

    }

    @Test
    @Description("Trying create courier without login")
    public void weCantCreateCourierWithMissingLogin(){
        File json = new File("src/test/resources/courierWithoutLogin.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400);

    }
    @Test
    @Description("Test for trying create courier without password")
    public void weCantCreateCourierWithMissingPassword(){
        File json = new File("src/test/resources/courierWithoutPassword.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400);

    }
    @Test
    @Description("Test for trying create courier without First Name")
    public void weCantCreateCourierWithMissingFirstName(){
        File json = new File("src/test/resources/courierWithoutFirstName.json");
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().statusCode(400);

    }
}


