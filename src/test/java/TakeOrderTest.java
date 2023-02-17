import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Parameterized.class)
public class TakeOrderTest {
    private final String[] color;

    public TakeOrderTest(String[] color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Данные для передачи цвета")
    public static String[][][] getColor() {
        return new String[][][]{
                {{"BLACK"}},
        };
    }


    @Test
    public void takeOrder(){
        OrderApi.takeOrder("Dima", "Kazakov", "Lva Tolstovo 12", "Park kulturi", "89028447532", 2, "10.02.2023", "Privozi skoree",color)
                .then().statusCode(SC_OK).assertThat().body("ok", equalTo("true"));
    }

    @Test
    public void takeOrderWithFalseIdCourier(){
        OrderApi.takeOrderWithFalseId("Dima", "Kazakov", "Lva Tolstovo 12", "Park kulturi", "89028447532", 2, "10.02.2023", "Privozi skoree",color)
                .then().statusCode(SC_NOT_FOUND).assertThat().body("message", equalTo("Курьера с таким id не существует"));
    }
    @Test
    public void takeOrderWithouIdCourier(){
        OrderApi.takeOrderWithoutId("dima", "kazakov", "Lva Tolstovo 12", "Park kulturi", "89028447532", 2, "10.02.2023", "Privozi skoree",color)
                .then().statusCode(SC_BAD_REQUEST).assertThat().body("message", equalTo("Недостаточно данных для поиска"));
    }

    @Test
    public void takeOrderWithFalseTrackOrder(){
        OrderApi.takeOrderWithFalseTrack("dima", "kazakov", "Lva Tolstovo 12", "Park kulturi", "89028447532", 2, "10.02.2023", "Privozi skoree",color)
                .then().statusCode(SC_NOT_FOUND).assertThat().body("message", equalTo("Заказа с таким id не существует"));
    }

    @Test
    public void takeOrderWithoutTrackOrder(){
        OrderApi.takeOrderWithoutTrack("dima", "kazakov", "Lva Tolstovo 12", "Park kulturi", "89028447532", 2, "10.02.2023", "Privozi skoree",color)
                .then().statusCode(SC_BAD_REQUEST).assertThat().body("message", equalTo("Недостаточно данных для поиска"));
    }
    @After
    public void deleteCourier(){
        CourierApi.deleteCourier("Lopes", "1234");
    }
}
