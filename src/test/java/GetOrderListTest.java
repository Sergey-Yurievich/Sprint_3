import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class GetOrderListTest {

    private OrderClient orderClient;
    int randomID = (int) (Math.random() * 30);

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @Description ("Проверка получения списка заказов")
    public void orderListCanBeGetTest () {

        // Получение списка заказов
        ValidatableResponse response = orderClient.getOrderList();
        // Получение статус кода
        int statusCode = response.extract().statusCode();
        // Получение списка заказов
        List<Map<String, Object>> orders = response.extract().jsonPath().getList("orders");

        // Проверка что статус код соответвует ожидаемому
        assertEquals ("Status code is incorrect",200, statusCode);
        // Проверка что размер списка заказов равен 30 (По умолчанию возвращается 30)
        assertThat("Order id list empty", orders, hasSize(30));
        // Проверка что обьекты в массиве Orders содержат значение ID и оно не равно 0
        assertThat("OrdersID incorrect",orders.get(randomID).get("id"), is(not(0)));

    }


}
