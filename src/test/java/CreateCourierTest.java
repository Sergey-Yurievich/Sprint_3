import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertTrue;

public class CreateCourierTest {

    private Courier courier;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp(){
        courier = Courier.getRandom();
        courierClient = new CourierClient();
    }

    @Test
    @Description ("Проверка что курьера можно создать, статус код соответвует ожидаемому " +
            "при создании курьера присваивает ID")
    public void courierCanBeCreatedTest (){

        // Создание курьера
        ValidatableResponse response = courierClient.create(courier);
        // Получение статус кода с тела создания курьера
        int statusCode = response.extract().statusCode();
        // Получение тела ответа при создании курьера
        boolean isCourierCreated = response.extract().path("ok");
        // Авторизация созданого курьера
        ValidatableResponse login = courierClient.login(CourierCredentials.from(courier));
        // Получение ID авторизованого курьера
        courierId = login.extract().path("id");
        System.out.println(courierId);

        // Проверка что курьер создался
        assertTrue ("Courier is not creates", isCourierCreated);
        // Проверка что статус код соответсвует ожиданиям
        assertThat ("Status code is incorrect", statusCode, equalTo(201));
        // Проверка что ID курьера не 0
        assertThat("Courier ID is incorrect", courierId, is(not(0)));
    }

    @After //Удаляем созданого курьера
    public void tearDown() {
        courierClient.delete(courierId);
    }
}
