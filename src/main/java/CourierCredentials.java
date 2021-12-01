import org.apache.commons.lang3.RandomStringUtils;

public class CourierCredentials {

    public String login;
    public String password;

    public CourierCredentials() {

    }

    public CourierCredentials (String login, String password) {
        this.login = login;
        this.password = password;
    }

    public static CourierCredentials from (Courier courier) {
        return new CourierCredentials(courier.login, courier.password);
    }

    public CourierCredentials setLogin (String login){
        this.login = login;
        return this;
    }

    public CourierCredentials setPassword (String password) {
        this.password = password;
        return this;
    }

    public static CourierCredentials getCourierWithLogin (Courier courier) {
        return new CourierCredentials().setLogin(courier.login);
    }
    public static CourierCredentials getCourierWithPassword (Courier courier) {
        return new CourierCredentials().setPassword(courier.password);
    }
    public static CourierCredentials getCourierWithRandomLoginAndPassword () {
        return new CourierCredentials().setLogin(RandomStringUtils.randomAlphabetic(4))
                .setPassword(RandomStringUtils.randomAlphabetic(6));
    }
}