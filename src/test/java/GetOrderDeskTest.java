import jdk.jfr.Description;
import org.junit.Test;

public class GetOrderDeskTest {
    @Test
    @Description("Test for receiving list of orders")
    public void getListOrders(){
  OrderApi.takeOrderList();
    }
}

