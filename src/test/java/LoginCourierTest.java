import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

public class LoginCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    //Создаем нового рандомного курьера
    @Before
    public void setUp(){
        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }

    @Test
    @Description ("Проверка что существующий курьер может авторизоваться")
    public void courierCanBeCreatedTest (){

        // Создание курьера
        courierClient.create(courier);
        // Авторизация созданого курьера
        ValidatableResponse login = courierClient.login(CourierCredentials.from(courier));
        // Получение статус кода с тела авторизации курьера
        int statusCode = login.extract().statusCode();
        // Получение ID авторизованого курьера
        courierId = login.extract().path("id");

        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(200));
        // Проверка что ID курьера не 0
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    // Удаляем созданого курьера
    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

}
