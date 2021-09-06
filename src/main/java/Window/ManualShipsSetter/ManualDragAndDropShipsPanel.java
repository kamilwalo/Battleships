package Window.ManualShipsSetter;

import Window.Game.ShipFrame;
import Window.Game.ShipPanel;
import game.Ship;
import game.ShipsManual;
import game.ShipsOnBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
* This is the class which is a window, that makes possible to manual set ships
*/

public class ManualDragAndDropShipsPanel extends JPanel {
    public static final int WINDOW_HEIGHT =(Toolkit.getDefaultToolkit().getScreenSize().width/4);
    public static final int SPACE_FOR_SHIPS = WINDOW_HEIGHT/2;
    public static final int WINDOW_WIDTH = (WINDOW_HEIGHT+SPACE_FOR_SHIPS);
    public static final int quantityOfSquares = 10;
    private Point mousePt;
    private List<ShipsManual> ships = new ArrayList<>();
    private ShipsManual shipsManual;
    private final String[][] map = new String[10][10];
    private boolean isItFirstPlayer = true;
    private List<Ship> shipsFirstPlayer;
    private List<Ship> shipsSecondPlayer;
    private final String[][] mapFirstPlayer = new String[10][10];

    private final JButton nextPlayerStartGameButton;

    /*
    * This constructor requires frame to dispose() this.
    * */

    public ManualDragAndDropShipsPanel(ManualDragAndDropShipsFrame frame) {
        setLayout(null);
        int buttonSize = (WINDOW_HEIGHT-ShipPanel.gapForCoordinates)/quantityOfSquares;
        JButton generateRandom = new JButton("random");
        generateRandom.setSize(100,100);
        generateRandom.setBounds(WINDOW_WIDTH-(buttonSize*3),ShipPanel.gapForCoordinates,buttonSize*3,buttonSize);
        add(generateRandom);
        nextPlayerStartGameButton = new JButton("next player");
        nextPlayerStartGameButton.setSize(100, 100);
        nextPlayerStartGameButton.setBounds(WINDOW_WIDTH-(buttonSize*3),ShipPanel.gapForCoordinates*3,buttonSize*3,buttonSize);
        add(nextPlayerStartGameButton);

        /*
        * This button is for accept position of ships and to start play game
        * */
        nextPlayerStartGameButton.addActionListener(e -> {
            if(checkIfEveryShipIsOnTheMap())
            if(isItFirstPlayer){
                for (int X = 0; X < mapFirstPlayer.length; X++) {
                    for (int Y = 0; Y < mapFirstPlayer.length; Y++) {
                        mapFirstPlayer[Y][X] = map[Y][X];
                    }
                }
                shipsFirstPlayer = new ArrayList();
                for (int i = 0; i < ships.size(); i++) {
                    try {
                        shipsFirstPlayer.add(ships.get(i).clone());
                        shipsFirstPlayer.get(i).setMap(mapFirstPlayer);
                    } catch (CloneNotSupportedException ex) {
                        ex.printStackTrace();
                    }
                }
                ships=new ArrayList<>();
                addShips();
                isItFirstPlayer=false;

                clearMap();
                nextPlayerStartGameButton.setText("Start Game");
            }else {
                shipsSecondPlayer = new ArrayList();
                for (int i = 0; i < ships.size(); i++) {
                    try {
                        shipsSecondPlayer.add(ships.get(i).clone());
                        shipsSecondPlayer.get(i).setMap(map);
                    } catch (CloneNotSupportedException ex) {
                        ex.printStackTrace();
                    }
                }
                new ShipFrame(shipsFirstPlayer, shipsSecondPlayer, mapFirstPlayer, map);
                frame.dispose(); // here is disposed
            }
        });
        generateRandom.addActionListener(e -> {
            clearMap();
            for (ShipsManual manual : ships) {
                manual.putShipOnTheStartingPosition(manual.getShipLength());
            }
            for (ShipsManual ship : ships) {
                ship.generateShips();
                repaint();
            }
        });

        for (int i = 0; i < map.length; i++) {
            for (int i1 = 0; i1 < map.length; i1++) {
                map[i1][i] = ".";
            }
        }

        setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));

        this.setFont(new Font("Sans Serif", Font.BOLD, 12));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mousePt = e.getPoint();
                for (ShipsManual ship: ships) {
                   if (ship.select(mousePt.x, mousePt.y)){
                       shipsManual = ship;
                       break;
                   }
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                shipsManual = null;
                for (ShipsManual ship: ships) {
                    if (ship.select(mousePt.x, mousePt.y)){
                        shipsManual = ship;
                        break;
                    }
                }
                if(shipsManual!=null){
                    shipsManual.rotate();
                    repaint();
                }shipsManual=null;
            }

            public void mouseReleased(MouseEvent e) {
                if(shipsManual!=null)shipsManual.unselect();
                shipsManual=null;
                //getMapByAsci();
                repaint();
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                mousePt = e.getPoint();
                for (ShipsManual h: ships) h.move(mousePt.x, mousePt.y);
                repaint();
            }
        });

        addShips();

    }

    private boolean checkIfEveryShipIsOnTheMap() {
    int length=0;
        for (String[] strings : map) {
            for (int y = 0; y < map.length; y++) {
                if (Objects.equals(strings[y], "O")) length++;
            }
        }
        return length == ShipsOnBoard.getLengthWholeShips();
    }

    private void addShips(){
        for (int i = 0; i < ShipsOnBoard.getShip4(); i++) {
            addShip(new ShipsManual(4,map));
        }for (int i = 0; i < ShipsOnBoard.getShip3(); i++) {
            addShip(new ShipsManual(3,map));
        }for (int i = 0; i < ShipsOnBoard.getShip2(); i++) {
            addShip(new ShipsManual(2,map));
        }for (int i = 0; i < ShipsOnBoard.getShip1(); i++) {
            addShip(new ShipsManual(1,map));
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (ShipsManual h: ships) {
            h.paint(g);
        }
        drawBoard(g);
        drawNumbersOnBoard(g);
    }

    private void drawBoard(Graphics g) {
        for (int i = 0; i < quantityOfSquares; i++) {
            g.drawLine(
                    ShipPanel.gapForCoordinates+i*(((WINDOW_WIDTH-SPACE_FOR_SHIPS)-ShipPanel.gapForCoordinates)/quantityOfSquares),
                    0,
                    ShipPanel.gapForCoordinates+i*(((WINDOW_WIDTH-SPACE_FOR_SHIPS)-ShipPanel.gapForCoordinates)/quantityOfSquares),
                    WINDOW_HEIGHT);
        }
        //draw horizontal lines
        for (int i = 0; i < quantityOfSquares; i++) {
            g.drawLine(
                    0,
                    ShipPanel.gapForCoordinates+i*((WINDOW_HEIGHT-ShipPanel.gapForCoordinates)/quantityOfSquares),
                    WINDOW_WIDTH-SPACE_FOR_SHIPS,
                    ShipPanel.gapForCoordinates+i*((WINDOW_HEIGHT-ShipPanel.gapForCoordinates)/quantityOfSquares));
        }
        g.setColor(Color.RED);
        g.fillRect(WINDOW_WIDTH-SPACE_FOR_SHIPS,0, (WINDOW_HEIGHT/100),WINDOW_HEIGHT);
        g.setColor(Color.BLACK);
    }

    private void drawNumbersOnBoard(Graphics g) {
        g.setColor(Color.BLACK);
        int size = ((WINDOW_HEIGHT-ShipPanel.gapForCoordinates)/quantityOfSquares)/2;
        g.setFont(new Font("Times New Roman",Font.BOLD,size));
        FontMetrics fontMetrics = g.getFontMetrics();
        for (int i = 0; i < quantityOfSquares; i++) {
            g.drawString(
                    String.valueOf(i),
                    (ShipPanel.gapForCoordinates-fontMetrics.stringWidth(String.valueOf(i)))/2,
                    ((((WINDOW_HEIGHT-ShipPanel.gapForCoordinates)/quantityOfSquares)/2)+ShipPanel.gapForCoordinates+i*(WINDOW_HEIGHT-ShipPanel.gapForCoordinates)/quantityOfSquares)+size/2) ;
            g.drawString(
                    String.valueOf(i),
                    ShipPanel.gapForCoordinates+((WINDOW_WIDTH-SPACE_FOR_SHIPS)-ShipPanel.gapForCoordinates)/quantityOfSquares*i + (((WINDOW_WIDTH-SPACE_FOR_SHIPS)-ShipPanel.gapForCoordinates)/quantityOfSquares)/2-fontMetrics.stringWidth(String.valueOf(i))/2,
                    ShipPanel.gapForCoordinates-(ShipPanel.gapForCoordinates-size));
        }
    }

    public void addShip(ShipsManual ship) {
        ships.add(ship);
        repaint();
    }

    public void clearMap(){
        for (int X = 0; X < map.length; X++) {
            for (int Y = 0; Y < map.length; Y++) {
                map[X][Y] = ".";
            }
        }
    }
}


