package Window.ChooseModeGame;

import Window.Game.ShipFrame;
import Window.ManualShipsSetter.ManualDragAndDropShipsFrame;
import Window.MenuWindow.Menu;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/*
* it is window for choose which mode of game users want. Random or manual.
* */

public class ModeGame extends JFrame {
    private JPanel panel;
    private JButton randomButton;
    private JButton manualButton;
    private JButton backButton;

    private final int WINDOWS_WIDTH = 400;
    private final int WINDOWS_HEIGHT = 500;

    public ModeGame(){
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("shipIcon.png")));
        setIconImage(imageIcon.getImage());
        setContentPane(panel);
        setSize(new Dimension(
                WINDOWS_WIDTH,
                WINDOWS_HEIGHT
        ));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setVisible(true);

        backButton.addActionListener(e -> {
            new Menu();
            dispose();
        });
        randomButton.addActionListener(e -> {
            new ShipFrame();
            dispose();
        });
        manualButton.addActionListener(e -> {
            new ManualDragAndDropShipsFrame();
            dispose();
        });
    }
}
