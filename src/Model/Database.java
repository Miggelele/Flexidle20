package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Database {
    private Random random;
    private Connection conn;

    private String url;
    private String username;
    private String password;


    /**
     * The constructor for the database where the objects in the class are instantiated
     *
     * @author Mikael Szalai
     */
    public Database() {
        random = new Random();
    }

    /**
     * Filters and fetches a wordlist from the database based on the selected options from the game menu.
     * @param selectedLanguage A selected menu option for the word language
     * @param selectedWordLength The selected menu option for the word length
     *
     * @author Mikael Szalai
     */
    public List<String> getMatchingWordList(String selectedLanguage, int selectedWordLength) {
        List<String> wordList = new ArrayList<>();
        String selectQuery =
                "SELECT word " +
                "FROM flexidle.game_word " +
                "WHERE language = ? " +
                "AND CHAR_LENGTH(word) = ?";

        try (PreparedStatement stmt = conn.prepareStatement(selectQuery)) {
            stmt.setString(1, selectedLanguage);
            stmt.setInt(2, selectedWordLength);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String word = rs.getString("word");
                wordList.add(word);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return wordList;
    }

    /**
     * Returns a word randomly from the filtered wordlist
     * @return A filtered word
     *
     * @author Mikael Szalai
     */
    public String getRandomWord(String language, int wordLength) {
        List<String> wordList = getMatchingWordList(language, wordLength);
        int randomIndex = random.nextInt(wordList.size());

        return wordList.get(randomIndex);
    }

    public void connect() {
        try {
            conn = DriverManager.getConnection(url, username, password);
        }
        catch (SQLException e) {
            System.out.println("Connection failed to database");
            System.out.println("Failed to run program");
            System.exit(0);
        }
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
