package HW2.Server;
public class User {
    private final String login;
    private final String pass;
    private final String name;

    public User(String ... creds ){
        this.login = creds[0];
        this.pass = creds[1];
        this.name = creds[2];
    }

    public String getLogin() {
        return login;
    }

    public String getPass() {
        return pass;
    }

    public String getName() {
        return name;
    }
}
