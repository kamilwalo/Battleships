package Window.Settings;

import Window.MenuWindow.Menu;
import game.ShipsOnBoard;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class Settings extends JFrame {
    private JPanel panel;
    private JTextField textFieldSingle;
    private JTextField textFieldDouble;
    private JTextField textFieldTriple;
    private JTextField textFieldQuadruple;
    private JButton saveChangesAndBackButton;
    private JButton donTSaveAndButton;
    private final int WINDOWS_WIDTH = 400;
    private final int WINDOWS_HEIGHT = 500;

    public Settings(){
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("shipIcon.png")));
        setIconImage(imageIcon.getImage());
        setContentPane(panel);
        setSize(new Dimension(
                WINDOWS_WIDTH,
                WINDOWS_HEIGHT
        ));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        textFieldSingle.setText(ShipsOnBoard.getShip1()+"");
        textFieldDouble.setText(ShipsOnBoard.getShip2()+"");
        textFieldTriple.setText(ShipsOnBoard.getShip3()+"");
        textFieldQuadruple.setText(ShipsOnBoard.getShip4()+"");
        saveChangesAndBackButton.addActionListener(e -> {
            if(!textFieldDouble.getText().equals(""))ShipsOnBoard.setShip1(Integer.parseInt(textFieldSingle.getText()));
            if(!textFieldDouble.getText().equals(""))ShipsOnBoard.setShip2(Integer.parseInt(textFieldDouble.getText()));
            if(!textFieldTriple.getText().equals(""))ShipsOnBoard.setShip3(Integer.parseInt(textFieldTriple.getText()));
            if(!textFieldQuadruple.getText().equals(""))ShipsOnBoard.setShip4(Integer.parseInt(textFieldQuadruple.getText()));
            dispose();
            new Menu();
        });
        setVisible(true);
        donTSaveAndButton.addActionListener(e -> {
            new Menu();
            dispose();
        });
    }
}