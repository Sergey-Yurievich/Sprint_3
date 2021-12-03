import io.qameta.allure.Description;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateCourierWithoutNecessaryFieldTest {

    private final Courier courier;
    private final int expectedStatus;
    private final String expectedErrorMessage;


    public CreateCourierWithoutNecessaryFieldTest (Courier courier,int expectedStatus, String expectedErrorMessage) {
        this.courier = courier;
        this.expectedStatus = expectedStatus;
        this.expectedErrorMessage = expectedErrorMessage;
    }
    // Параметризация условий авторизации
    @Parameterized.Parameters
    public static Object[][] getTestData () {
        return new Object[][] {
                {Courier.getCourierWithOnlyLogin(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithOnlyPassword(), 400, "Недостаточно данных для создания учетной записи"},
                {Courier.getCourierWithOnlyFirstName(), 400, "Недостаточно данных для создания учетной записи"}
        };
    }

    @Test
    @Description ("Проверка что курьер не может авторизоваться " +
            "1. Только с полем логин " +
            "2. Только с полем пароль " +
            "3. С не существуещем в системе логине и пароле")
    public void courierNotCreatedWithoutNecessaryField () {

        // Создание курьера
        ValidatableResponse response = new CourierClient().create(courier);
        // Получение статус кода запроса
        int statusCode = response.extract().statusCode();
        // Получение значения ключа "Message"
        String errorMessage = response.extract().path("message");
        // Получение ID авторизованого курьера

        // Проверка что статус код соответвует ожиданиям
        assertEquals("Status code is incorrect", expectedStatus, statusCode);
        // Проверка что ссобщение об ошибке соответвует ожиданимя
        assertEquals("Error message is incorrect", expectedErrorMessage, errorMessage);
    }

}
