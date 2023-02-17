import io.restassured.RestAssured;
import jdk.jfr.Description;
import org.example.UsedUrl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class CreateNewCourierTest {
    @Before
    @Description("Url api scooter")
    public void setUp() {
        RestAssured.baseURI = UsedUrl.scooterUrl;
    }

    @Test
    @Description("Test for create courier with all positive parameters")
    public void weCanCreateNewCourierWithPositiveParameters() {
        CourierApi.createCourier("DimDimich", "1234", "saske")
                .then()
                .statusCode(SC_CREATED);
    }
    @After
    @Description("After everyone tests, delete courier with parameters")
    public void deleteThisCourier() {
        CourierApi.deleteCourier("DimDimich", "1234");
    }

    @Test
    @Description("Test for create courier twice attempt")
    public void tryingCreateDoubleCouriersWithSameNames() {
        CourierApi.createCourier("DimDimich", "1234", "saske")
                .then()
                .statusCode(SC_CREATED);
        CourierApi.createCourier("DimDimich", "1234", "saske")
                .then()
                .statusCode(SC_CONFLICT);
    }
    @Test
    @Description("Test for comparison body response")
    public void checkOkTrue(){
        CourierApi.createCourier("DimDimich", "1234", "saske")
                .then()
                .statusCode(SC_CREATED).assertThat().body("ok",equalTo(true));

    }

    @Test
    @Description("Trying create courier without login")
    public void weCantCreateCourierWithMissingLogin(){
        CourierApi.createCourier("", "1234", "saske")
                .then()
                .statusCode(SC_BAD_REQUEST);

    }
    @Test
    @Description("Test for trying create courier without password")
    public void weCantCreateCourierWithMissingPassword(){
        CourierApi.createCourier("DimDimich", "", "saske")
                .then()
                .statusCode(SC_BAD_REQUEST);

    }
    @Test
    @Description("Test for trying create courier without First Name")
    public void weCantCreateCourierWithMissingFirstName(){
        CourierApi.createCourier("DimDimich", "1234", "")
                .then()
                .statusCode(SC_BAD_REQUEST);
    }
}


