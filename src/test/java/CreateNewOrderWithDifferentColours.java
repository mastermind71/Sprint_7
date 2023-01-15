import io.restassured.response.Response;
import jdk.jfr.Description;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


@RunWith(Parameterized.class)
public class CreateNewOrderWithDifferentColours {
    private final String[] colour;

    public CreateNewOrderWithDifferentColours(String[] color) {
        this.colour = color;
    }

    @Parameterized.Parameters(name = "Данные для передачи цвета")
    public static String[][][] getColor() {
        return new String[][][]{
                {{"BLACK"}},
                {{"GREY"}},
                {{"BLACK", "GREY"}},
                {{}},
        };
    }

    @Test
    @Description("Test orders with one color, with two colours, without colours and test for receiving number order")
    public void createNewOrderWithOneColor() {
        Response response = OrderApi.createOrder("Dmitry", "Kazakov", "Lva tolstovo 16", "Park kulturi", "89029447532", 2, "10.02.2023", "Privezi pobistree pozhaluista", colour);
        int a = response.then().extract().path("track");
        System.out.println(a);
    }
}



