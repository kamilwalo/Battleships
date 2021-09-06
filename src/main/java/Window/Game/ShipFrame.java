package Window.Game;


import javax.swing.*;
import java.util.List;
import java.util.Objects;

public class ShipFrame extends JFrame {

    public ShipFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ShipPanel shipPanel = new ShipPanel();
        this.add(shipPanel);
        pack();
        setTitle("ShipWar!");
        setResizable(false);

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("shipIcon.png")));
        setIconImage(imageIcon.getImage());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public ShipFrame(List ships1,List ships2, String[][] map1,String[][] map2) {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        ShipPanel shipPanel = new ShipPanel(ships1,ships2,map1,map2);
        this.add(shipPanel);
        pack();
        setTitle("ShipWar!");
        setResizable(false);

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("shipIcon.png")));
        setIconImage(imageIcon.getImage());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
