import io.restassured.response.Response;
import org.example.UsedUrl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

public class DeleteCourier {
    @Before
    public void createCourier(){
        CourierApi.createCourier("Polyak", "1234", "Dmitry");
    }
    @Test
    public void deleteCourier(){
        CourierApi.deleteCourierForSecondExercise("Polyak", "1234")
            .then().statusCode(SC_OK).assertThat().body("ok",equalTo(true));
    }
    @Test
    public void deleteCourierWithoutId(){
        CourierApi.deleteCourierWithoutId("Polyak", "1234")
                .then().statusCode(SC_BAD_REQUEST).assertThat().body("message", equalTo("Недосаточно данных для удаления курьера"));
    }
    @Test
    public void deleteCourierWithFalseId(){
        CourierApi.deleteCourierWithFalseId()
                .then().statusCode(SC_NOT_FOUND).assertThat().body("message", equalTo("Курьера с таким id нет"));
    }
    @After
    public void deleteCourierAfterFalseData(){
        CourierApi.deleteCourier("Polyak", "1234");
    }
}
