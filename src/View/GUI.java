package View;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {

    private Controller controller;
    private GameBoard gameBoard;
    private GameMenu gameMenu;
    private Statistics statistics;
    private MainMenu mainMenu;
    private int width;
    private int height;


    public GUI(Controller controller, int width, int height) {
        super("FLEXIDLE");

        //Finds the location that centers the frame in the screen.
        int locationWidth =  (int) (( Toolkit.getDefaultToolkit().getScreenSize().getWidth()  - width ) / 2);
        int locationHeight = (int) (( Toolkit.getDefaultToolkit().getScreenSize().getHeight() - height ) / 2 - 20);
        this.setLocation(locationWidth, locationHeight);

        this.controller = controller;
        this.width = width;
        this.height = height;
        this.setSize(width, height);
        this.mainMenu = new MainMenu(this, width, height);
        this.setContentPane(mainMenu);
        this.setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    protected void mainMenuButtonPressed(String buttonName) {
        controller.mainMenuButtonPressed(buttonName);
    }

    protected void statisticsButtonPressed(String buttonName) {
        controller.statisticsButtonPressed(buttonName);
    }

    protected void gameMenuButtonPressed(String buttonName) {
        controller.gameMenuButtonPressed(buttonName);
    }

    protected void gameBoardButtonPressed(String buttonName) {
        controller.gameBoardButtonPressed(buttonName);
    }

    public void setPanel(String panelName) {
        switch (panelName) {
            case "GameBoard":
                //hårdkodade just nu, ska implementeras att hämta från de valda alternativen
                int chosenMaxGuesses = 7;
                int chosenWordlength = 5;
                String chosenLanguage = "SWEDISH";
                this.gameBoard = new GameBoard(this, width, height, chosenMaxGuesses, chosenWordlength, chosenLanguage);
                setContentPane(gameBoard);
                break;
            case "GameMenu":
                this.gameMenu = new GameMenu(this, width, height);
                setContentPane(gameMenu);
                break;
            case "Statistics":
                this.statistics =  new Statistics(this, width, height);
                setContentPane(statistics);
                break;
            case "MainMenu":
                this.mainMenu = new MainMenu(this, width, height);
                setContentPane(mainMenu);
                break;
            default:
                setContentPane(mainMenu);
                System.out.println("Invalid panel name, back to main menu you go.");
        }
    }

    public void changeKeyBoardButtonColor(String keyboardButton, String colour) {
        JButton[][] keyboardButtons = gameBoard.getKeyBoardButtons();
        for (int  i = 0; i < keyboardButtons.length; i++) {
            for (int j = 0; j < keyboardButtons[i].length; j++) {
                if (keyboardButtons[i][j].getText().equals(keyboardButton)) {
                    if (colour.equals("GREEN")) {
                        keyboardButtons[i][j].setBackground(new Color(37, 175, 92, 255) );
                    } else if(colour.equals("YELLOW")) {
                        keyboardButtons[i][j].setBackground(new Color(255, 194, 23, 255) );
//                        keyboardButtons[i][j].setBackground(new Color(255, 237, 67, 255) );
                    } else if(colour.equals("GRAY")) {
                        keyboardButtons[i][j].setBackground( new Color(43, 33, 6, 255) );
//                        keyboardButtons[i][j].setBackground( new Color(67, 66, 74, 255) );
                    }
                    return;
                }

            }
        }
    }

}
