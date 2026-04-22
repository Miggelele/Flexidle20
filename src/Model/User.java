package Model;

/**
 *
 */
public class User {
    private String username;
    private String password;
    private SecurityQuestion securityQuestion;
    private String securityAnswer;

    public User(String username, String password, SecurityQuestion securityQuestion, String securityAnswer) {
        this.username = username;
        this.password = password;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
    }
}
