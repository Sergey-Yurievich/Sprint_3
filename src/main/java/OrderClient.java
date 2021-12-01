import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class OrderClient extends RestAssuredClient {

    private static final String ORDER_PATH = "/api/v1/orders";
    private static final String GET_ORDER_PATH = "/api/v1/orders/track?t=";

    @Step ("Создание заказа")
    public ValidatableResponse create (Order order){
        return given()
                .spec(getBaseSpec())
                .body(order)
                .when()
                .post(ORDER_PATH)
                .then();
    }
    @Step ("Получение списка заказов")
    public ValidatableResponse getOrderList (){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }

    @Step ("Получение информации о заказе")
    public ValidatableResponse getOrder (int trackID){
        return given()
                .spec(getBaseSpec())
                .when()
                .get(GET_ORDER_PATH + trackID)
                .then();
    }

    @Step ("Отмена не принятого заказа")
    public ValidatableResponse cancel (int trackId){
        return given()
                .spec(getBaseSpec())
                .body(trackId)
                .when()
                .put(ORDER_PATH + "cancel/")
                .then();
    }
}
