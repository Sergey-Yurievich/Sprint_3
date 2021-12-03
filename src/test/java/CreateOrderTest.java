import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderTest {

    private Order order;
    private OrderClient orderClient = new OrderClient();
    private final List<String> color;
    private final List<String> expectedColor;
    private int trackID;


    public CreateOrderTest(List<String> color, List<String> expectedColor) {
        this.color = color;
        this.expectedColor = expectedColor;
    }

    @Parameterized.Parameters
    public static Object[][] getColorType() {
        return new Object[][]{
                {List.of("BLACK"), List.of("BLACK")},
                {List.of("GREY"), List.of("GREY")},
                {List.of("BLACK", "GREY"), List.of("BLACK", "GREY")},
                {List.of(), List.of()}

        };
    }

    @Test
    @Description("Проверка что заказ можно создать " +
            "1.Со значением ключа color: BLACK " +
            "2. Со значение ключа color: GREY " +
            "3. Со значение ключа color: BLACK,GREY " +
            "4. С пустым значение ключа color ")
    public void orderCanBeCreatedTest() {

        order = Order.getRandomOrder(color);
        // Создание заказа
        ValidatableResponse response = orderClient.create(order);
        // Получение статус кода с тела создания заказа
        int statusCode = response.extract().statusCode();
        // Получение значения ключа "Track"
        trackID = response.extract().path("track");
        // Запрашиваем информацию о созданных заказах
        ValidatableResponse orderDate = orderClient.getOrder(trackID);
        // Получаем значение ключа Color в теле соданаго заказа
        List<Object> actualColor = orderDate.extract().jsonPath().getList("order.color");

        // Проверка что статус код соответвует ожиданиям
        assertEquals("Status code is incorrect", 201, statusCode);
        // Проверка что значения ключа "Track" не 0
        assertThat("Track number not created", trackID, is(not(0)));
        // Проверяем что переданное значение Color соответствует фактическуму
        assertEquals("Value color different", expectedColor, actualColor);
    }

    @After // Отменяем созданный заказ
    public void tearDown() {
        orderClient.cancel(trackID);
    }
}
