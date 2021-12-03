import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class CreateSameCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    // Создание рандомного курьера
    @Before
    public void setUp(){
        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }

    @Test
    @Description ("Проверка не возможности создания двух одинаковых курьеров")
    public void sameCourierCantBeCreatedTest (){

        // Созданный курьер
        courierClient.create(courier);
        // Авторизация созданого курьера
        ValidatableResponse login = courierClient.login(CourierCredentials.from(courier));
        // Получение ID созданого курьера
        courierId =  login.extract().path( "id");
        // Потпытка создание курьера с теми же данными
        ValidatableResponse response = courierClient.create(courier);
        // Получение статус кода при создание такого же курьера
        int statusCode = response.extract().statusCode();
        // Получение тела сообщения
        String isCourierNotCreated = response.extract().path("message");

        // Проверка что статус код соответвует ожидаемому
        assertThat ("Status code is incorrect", statusCode, equalTo(409));
        // Проверка что есть сообщение об ошибке и соответвует требованиям
        assertEquals("Same courier was created ","Этот логин уже используется" , isCourierNotCreated);
    }

    // Удаление созданого курьера
    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

}
