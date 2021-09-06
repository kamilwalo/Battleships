package Window.MenuWindow;

import Window.ChooseModeGame.ModeGame;
import Window.Settings.Settings;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;


/*
* It is class which is main menu of game
* */
public class Menu extends JFrame {

    private JPanel panel;
    private JButton startGameButton;
    private JButton settingsButton;
    private JButton exitButton;
    private final int WINDOW_HEIGHT = 500;
    private final int WINDOW_WIDTH = 425;

    public Menu(){
        setContentPane(panel);
        setSize(new Dimension(
                WINDOW_WIDTH,
                WINDOW_HEIGHT
        ));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("shipIcon.png")));
        setIconImage(imageIcon.getImage());

        setLocationRelativeTo(null);
        startGameButton.addActionListener(e -> {
            new ModeGame();
            dispose();
        });
        settingsButton.addActionListener(e -> {
            dispose();
            new Settings();
        });
        setVisible(true);
        exitButton.addActionListener(e -> {
           System.exit(0);
        });
    }
}