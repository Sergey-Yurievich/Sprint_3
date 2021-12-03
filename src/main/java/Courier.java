
import com.github.javafaker.Faker;

public class Courier {


    public static Faker faker = new Faker();
    public String login;
    public String password;
    public String firstName;

    public Courier () {

    }

    public Courier (String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    public static Courier getRandom() {
        final String login = faker.internet().domainName();
        final String password = faker.internet().password();
        final String firstName = faker.name().firstName();
        return new Courier(login, password, firstName);
    }

    public Courier setLogin (String login){
        this.login = login;
        return this;
    }

    public Courier setPassword(String password) {
        this.password = password;
        return this;
    }

    public Courier setFirstName (String firstName) {
        this.firstName = firstName;
        return this;
    }

    public static Courier getCourierWithOnlyLogin () {
        return new Courier().setLogin(faker.internet().domainName());
    }
    public static Courier getCourierWithOnlyPassword () {
        return new Courier().setPassword(faker.internet().password());
    }
    public static Courier getCourierWithOnlyFirstName () {
        return new Courier().setFirstName(faker.name().firstName());
    }
}
