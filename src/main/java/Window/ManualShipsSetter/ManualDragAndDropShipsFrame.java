package Window.ManualShipsSetter;

import javax.swing.*;
import java.util.Objects;

public class ManualDragAndDropShipsFrame extends JFrame {
    public ManualDragAndDropShipsFrame(){
        add(new ManualDragAndDropShipsPanel(this));
        setTitle("Manual ship setter");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("shipIcon.png")));
        setIconImage(imageIcon.getImage());
        setVisible(true);
    }
}
