import jdk.jfr.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AutorizationCourierTest {
    @Before
    @Description("Before everyone test we create courier for autorization")
    public void createCourierPlusAutorization() {
        CourierApi.deleteCourier("Kazak", "1234");
        CourierApi.createCourier("Kazak", "1234", "Dmitry");
        }
    @After
    @Description("After everyone tests we delete created courier")
    public void deleteCourierAfterAutorization() {
        CourierApi.deleteCourier("Kazak", "1234");
    }

    @Test
    @Description("Test for autorization courier")
    public void courierCanAutorization() {
CourierApi.loginCourier("Kazak", "1234")
                .then().statusCode(SC_OK).assertThat().body("id", notNullValue());
    }

    @Test
    @Description("Test for autorization courier with empty login")
    public void courierCantAutorizationIfLoginEmpty() {
        CourierApi.loginCourier("", "1234")
                .then().statusCode(SC_BAD_REQUEST).assertThat().body("message", equalTo("Недостаточно данных для входа" ));
    }

    @Test
    @Description("test for aurorization courier with empty password")
    public void courierCantAutorizationIfPasswordEmpty() {
        CourierApi.loginCourier("Kazak", "")
                .then().statusCode(SC_BAD_REQUEST).assertThat().body("message", equalTo("Недостаточно данных для входа" ));
    }
    @Test
    @Description("Test for autorization with false password")
    public void courierCantAutorizationIfPasswordFalse() {
                CourierApi.loginCourier("Kazak", "1030")
                .then().statusCode(SC_NOT_FOUND).assertThat().body("message", equalTo("Учетная запись не найдена" ));
    }
    @Test
    @Description("test for autorization with false login")
    public void courierCantAutorizationIfLoginFalse() {
        CourierApi.loginCourier("kazki", "1234")
                .then().statusCode(SC_NOT_FOUND).assertThat().body("message", equalTo("Учетная запись не найдена" ));
    }
}

