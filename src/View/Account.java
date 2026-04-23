package View;
import Model.SecurityQuestion;

import javax.swing.*;
import java.awt.*;

public class Account extends JPanel {

    private GUI gui;
    private int width;
    private int height;

    JTextField usernameTextField;
    JPasswordField passwordTextField;

    JComboBox<SecurityQuestion> questionDropdown;
    JTextField answerTextField;

    public Account (GUI gui, int width, int height) {
        super(null);
        this.gui = gui;
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        this.setBackground(Color.black);
        this.setVisible(true);
        setup();
    }

    private void setup() {
        JLabel title = new JLabel("Account");
        title.setLocation(10,10);
        title.setSize(200,50);
        title.setForeground(Color.white);
        add(title);

        int[] buttonSize = {500,50};
        int startPositionY = 50;
        int spacingY = 75;
        int labelH = 25;

        Font labelFont = new Font("Arial", Font.BOLD, 16);
        Font buttonFont = new Font("Dialog", Font.BOLD, 24);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setLocation(width/2-buttonSize[0]/2, startPositionY);
        usernameLabel.setSize(buttonSize[0], labelH);
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(Color.white);
        add(usernameLabel);

        usernameTextField = new JTextField();
        usernameTextField.setLocation(width/2-buttonSize[0]/2, startPositionY + labelH);
        usernameTextField.setSize(buttonSize[0], buttonSize[1]);
        usernameTextField.setFont(buttonFont);
        usernameTextField.setTransferHandler(null);
        add(usernameTextField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY);
        passwordLabel.setSize(buttonSize[0], labelH);
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(Color.white);
        add(passwordLabel);

        passwordTextField = new JPasswordField();
        passwordTextField.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY + labelH);
        passwordTextField.setSize(buttonSize[0], buttonSize[1]);
        passwordTextField.setFont(buttonFont);
        passwordTextField.setTransferHandler(null);
        add(passwordTextField);

        JButton logInButton = new JButton("LOG IN");
        logInButton.setEnabled(true);
        logInButton.setBackground(Color.DARK_GRAY);
        logInButton.setForeground(Color.white);
        logInButton.setFont(buttonFont);
        logInButton.setSize(buttonSize[0], buttonSize[1]);
        logInButton.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*2 + labelH);
        logInButton.addActionListener(l -> gui.logInButtonPressed(logInButton.getText()));
        add(logInButton);

        JSeparator separator = new JSeparator();
        separator.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*3 + labelH + 10);
        separator.setSize(buttonSize[0], 2);
        separator.setForeground(Color.DARK_GRAY);
        add(separator);

        JLabel createAccountLabel = new JLabel("First time here? Create an account:");
        createAccountLabel.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*3 + labelH + 20);
        createAccountLabel.setSize(buttonSize[0], labelH);
        createAccountLabel.setFont(labelFont);
        createAccountLabel.setForeground(Color.white);
        add(createAccountLabel);

        JLabel securityQLabel = new JLabel("Select a security question:");
        securityQLabel.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*4 + labelH);
        securityQLabel.setSize(buttonSize[0], labelH);
        securityQLabel.setFont(labelFont);
        securityQLabel.setForeground(Color.white);
        add(securityQLabel);

        questionDropdown = new JComboBox<>(SecurityQuestion.values());
        questionDropdown.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*4 + labelH*2);
        questionDropdown.setSize(buttonSize[0], buttonSize[1]);
        questionDropdown.setFont(buttonFont);
        questionDropdown.setMaximumRowCount(SecurityQuestion.values().length);
        add(questionDropdown);

        JLabel answerLabel = new JLabel("Your answer:");
        answerLabel.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*5 + labelH);
        answerLabel.setSize(buttonSize[0], labelH);
        answerLabel.setFont(labelFont);
        answerLabel.setForeground(Color.white);
        add(answerLabel);

        answerTextField = new JTextField();
        answerTextField.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*5 + labelH*2);
        answerTextField.setSize(buttonSize[0], buttonSize[1]);
        answerTextField.setFont(buttonFont);
        answerTextField.setTransferHandler(null);
        add(answerTextField);

        JButton createAccount = new JButton("CREATE ACCOUNT");
        createAccount.setEnabled(true);
        createAccount.setBackground(Color.DARK_GRAY);
        createAccount.setForeground(Color.white);
        createAccount.setFont(buttonFont);
        createAccount.setSize(buttonSize[0], buttonSize[1]);
        createAccount.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*6 + labelH*2);
        createAccount.addActionListener(l -> gui.createAccountButtonPressed(createAccount.getText()));
        add(createAccount);

        JButton backToMainMenuButton = new JButton("BACK TO MAIN MENU");
        backToMainMenuButton.setEnabled(true);
        backToMainMenuButton.setBackground(Color.DARK_GRAY);
        backToMainMenuButton.setForeground(Color.white);
        backToMainMenuButton.setFont(buttonFont);
        backToMainMenuButton.setSize(buttonSize[0], buttonSize[1]);
        backToMainMenuButton.setLocation(width/2-buttonSize[0]/2, startPositionY + spacingY*7 + labelH*2);
        backToMainMenuButton.addActionListener(l -> gui.gameMenuButtonPressed(backToMainMenuButton.getText()));
        add(backToMainMenuButton);
    }

    public String getUsername() {
        return usernameTextField.getText();
    }

    public String getPassword() {
        return passwordTextField.getText();
    }

    public SecurityQuestion getSelectedQuestion() {
        return (SecurityQuestion) questionDropdown.getSelectedItem();
    }

    public String getAnswer() {
        return answerTextField.getText();
    }
}
