import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class LoginCourierWithoutNecessaryFieldTest {

    private static final CourierClient courierClient = new CourierClient ();
    private static final Courier courier = Courier.getRandom ();
    private final int expectedStatus;
    private final String expectedErrorMessage;
    private final CourierCredentials courierCredentials;
    private int courierId;

    public LoginCourierWithoutNecessaryFieldTest (CourierCredentials courierCredentials, int expectedStatus, String expectedErrorMessage) {
        this.courierCredentials = courierCredentials;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData () {
            return new Object[][] {
                    {CourierCredentials.getCourierWithLogin(courier), 400, "Недостаточно данных для входа"},
                    {CourierCredentials.getCourierWithPassword(courier), 400, "Недостаточно данных для входа"},
                    {CourierCredentials.getCourierWithRandomLoginAndPassword(), 404, "Учетная запись не найдена"}
            };
        }

        @Test
        @Description ("Проверка что курьер не может авторизоваться " +
                "1. Только с логином " +
                "2. Только с паролем " +
                "3. С рандомным логином и паролем ")
        public void courierLoginWithoutNecessaryField () {

            // Создаем курьера
            courierClient.create(courier);
            // Логинимся под созданым курьером
            ValidatableResponse successLogin = courierClient.login(CourierCredentials.from(courier));
            // Получаем ID созданого курьера
            courierId = successLogin.extract().path("id");
            // Пытаемся авторизоваться с данными из условия
            ValidatableResponse login = new CourierClient().login(courierCredentials);
            // Получение статус кода из тела ответа
            int ActualStatusCode = login.extract ().statusCode();
            // Проверяем что статус код соответствует ожидаемому
            assertEquals ("Status code is incorrect",expectedStatus, ActualStatusCode);
            // Получение сообщения об ошибке из тела ответа
            String errorMessage = login.extract ().path ("message");
            // Проверяем что сообщение об ошибке соответствует ожидаемому
            assertEquals ("Error message is  incorrect", expectedErrorMessage, errorMessage);

        }

    // Удаляем созданого курьера
    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }
}
