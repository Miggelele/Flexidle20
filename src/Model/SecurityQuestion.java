package Model;

/**
 * An enum class that is used when creating an account.
 * The values are shown in a dropdown box
 *
 * @author Elin Piho
 */
public enum SecurityQuestion {
    MOM("What is your mothers name?"),
    MAU("Favorite lecture-hall at MAU?"),
    FLEXI("Is Flexidle better than Wordle yes or no?"),
    TWILIGHT("Are you team Edward or team Jacob?");

    private final String question;

    SecurityQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return question;
    }
}