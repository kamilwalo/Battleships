package Window.Game;


import game.Map;
import game.Ship;
import game.ShipsOnBoard;
import game.Shooting;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*
* This is class which contains the window where users playing.
* @parm quantityOfShip -means how much squares is on the board (^2)
* @parm gapForCoordinates - it is gap for number which are on the board (on top and left of maps)
* */
public class ShipPanel extends JPanel {
    private static final int WINDOW_HEIGHT =  (Toolkit.getDefaultToolkit().getScreenSize().width/4);
    private static final int WINDOW_WIDTH =  (WINDOW_HEIGHT*2+WINDOW_HEIGHT/100);
    private static final int quantityOfShips = 10;
    public  static int gapForCoordinates=  (WINDOW_HEIGHT/20);
    private Shooting shootingFirstPlayer;
    private Shooting shootingSecondPlayer;
    private String[][] mapFirstPlayer = new String[10][10];
    private String[][] mapSecondPlayer = new String[10][10];
    private boolean turnFirstPlayer=true;
    private boolean isGameStillGoing = true;

    /*
    * Constructor for random genrator ships.
    * */
    public ShipPanel() {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        addMouseListener(new MouseEventDemo());
        setFocusable(true);

        startRandomGeneratorShip();
    }

    /*
    * Constructor for manual setting ship
    */
    public ShipPanel(List ships1,List ships2, String[][] map1,String[][] map2 ) {
        setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        addMouseListener(new MouseEventDemo());
        setFocusable(true);

        startGame(ships1,ships2, map1,map2);
    }


    private void startGame(List<Ship> ships1, List<Ship> ships2, String[][] map1, String[][] map2) {

        mapFirstPlayer = map1;
        mapSecondPlayer=map2;
        shootingFirstPlayer = new Shooting(ships1);
        shootingSecondPlayer = new Shooting(ships2);

        addManualPlayer(shootingFirstPlayer,ships1);
        addManualPlayer(shootingSecondPlayer,ships2);

        repaint();
    }

    private void addManualPlayer(Shooting shootingPlayer, List<Ship> ships) {
        byte lengthShips=0;
        for (Ship ship : ships) lengthShips += ship.getLengthShip();
        shootingPlayer.setLengthsShips(lengthShips);
    }


    public void startRandomGeneratorShip() {
        byte lenghtsShipsFirstPlayer=0;
        byte lenghtsShipsSecondPlayer=0;

        Map mapFirstPlayer = new Map();
        this.mapFirstPlayer =mapFirstPlayer.getMap();
        List<Ship> shipsFirstPlayer = new ArrayList<>();
        addShips(shipsFirstPlayer,mapFirstPlayer);
        shootingFirstPlayer = new Shooting(shipsFirstPlayer);
        for (Ship value : shipsFirstPlayer) lenghtsShipsFirstPlayer += value.getLengthShip();
        shootingFirstPlayer.setLengthsShips(lenghtsShipsFirstPlayer);

        Map mapSecondPlayer = new Map();
        this.mapSecondPlayer =mapSecondPlayer.getMap();
        List<Ship> shipsSecondPlayer = new ArrayList<>();
        addShips(shipsSecondPlayer,mapSecondPlayer);
        shootingSecondPlayer = new Shooting(shipsSecondPlayer);
        for (Ship ship : shipsSecondPlayer) lenghtsShipsSecondPlayer += ship.getLengthShip();
        shootingSecondPlayer.setLengthsShips(lenghtsShipsSecondPlayer);


        repaint();
    }



    private void addShips(List<Ship> ships, Map map) {
        for (int i = 0; i < ShipsOnBoard.getShip4(); i++)
            ships.add(new Ship(map.getMap(),(byte) 4,true));
        for (int i = 0; i < ShipsOnBoard.getShip3(); i++)
            ships.add(new Ship(map.getMap(),(byte) 3,true));
        for (int i = 0; i < ShipsOnBoard.getShip2(); i++)
            ships.add(new Ship(map.getMap(),(byte) 2,true));
        for (int i = 0; i < ShipsOnBoard.getShip1(); i++)
            ships.add(new Ship(map.getMap(),(byte) 1,true));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBoard(g);
        drawNumbersOnBoard(g);
        try {
            drawField(g);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(!isGameStillGoing) drawEndGame(g);
    }

    private void drawEndGame(Graphics g) {
        g.setColor(Color.blue);
        g.setFont(new Font("Times New Roman",Font.BOLD,35));
        String playerWhosWon;
        if(shootingFirstPlayer.getLengthsShips()==0) playerWhosWon="second player";
        else playerWhosWon = "first player";
        g.drawString("Game Over",g.getFontMetrics().stringWidth("Game Over")/2,WINDOW_HEIGHT/2);
        g.drawString(
                "Game Won "+ playerWhosWon,
                g.getFontMetrics().stringWidth("Game Won "+ playerWhosWon)/2,
                WINDOW_HEIGHT/2+35);
    }


    /*
    * This method draw X on the map (whats mean that player shot here)
    * draw T on the map (whats mean that player hit there ship)
    * */
    private void drawField(Graphics g) throws IOException {
        Image xIcon = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("X.png")));
        Image tIcon = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("T.png")));
        for (int X = 0; X < mapFirstPlayer.length; X++) {
            for (int Y = 0; Y < mapFirstPlayer.length; Y++) {
                if(Objects.equals(mapFirstPlayer[X][Y], "X")){
                    g.drawImage(
                            xIcon.getScaledInstance(
                                    (((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips),(WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips,5),
                            gapForCoordinates+X*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips),
                            gapForCoordinates+Y*((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips),
                            this);
                }
                if(Objects.equals(mapFirstPlayer[X][Y], "T")){
                    g.drawImage(
                            tIcon.getScaledInstance(
                             (((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips),(WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips,5),
                            gapForCoordinates+X*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips),
                            gapForCoordinates+Y*((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips),
                            this);
                }
            }
            for (int Y = 0; Y < mapSecondPlayer.length; Y++) {
                if(Objects.equals(mapSecondPlayer[X][Y], "X")){
                    g.drawImage(
                            xIcon.getScaledInstance(
                            ((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips+WINDOW_HEIGHT/50,(WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips,5),
                            gapForCoordinates+10*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips)+gapForCoordinates+X*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips),
                            gapForCoordinates+Y*((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips),
                            this);
                }
                if(Objects.equals(mapSecondPlayer[X][Y], "T")){
                    g.drawImage(
                            tIcon.getScaledInstance(
                            ((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips+WINDOW_HEIGHT/100,(WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips,5),
                            gapForCoordinates+10*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips)+gapForCoordinates+X*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips),
                            gapForCoordinates+Y*((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips),
                            this);
                }
            }
        }
    }

    private void drawNumbersOnBoard(Graphics g) {
        g.setColor(Color.BLACK);
        int size = ((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips)/2;
        g.setFont(new Font("Times New Roman",Font.BOLD,size));
        FontMetrics fontMetrics = g.getFontMetrics();
        for (int i = 0; i < quantityOfShips; i++) {
            //String - first player
            g.drawString(
                    String.valueOf(i),
                    (gapForCoordinates-fontMetrics.stringWidth(String.valueOf(i)))/2,
                    ((((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips)/2)+gapForCoordinates+i*(WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips)+size/2) ;
            g.drawString(
                    String.valueOf(i),
                    gapForCoordinates+((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips*i + (((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips)/2-fontMetrics.stringWidth(String.valueOf(i))/2,
                    gapForCoordinates-(gapForCoordinates-size));

            //String second player
            g.drawString(
                    String.valueOf(i),
                     ((WINDOW_WIDTH+gapForCoordinates-fontMetrics.stringWidth(String.valueOf(i)))/2),
                    ((((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips)/2)+gapForCoordinates+i*(WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips)+size/2) ;

            g.drawString(
                    String.valueOf(i),
                    WINDOW_WIDTH/2+gapForCoordinates+((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips*i + (((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips)/2-fontMetrics.stringWidth(String.valueOf(i))/2,
                    gapForCoordinates-(gapForCoordinates-size));
        }
    }

    private void drawBoard(Graphics g) {
        //draw vertical lines
        for (int i = 0; i < quantityOfShips; i++) {
            g.drawLine(gapForCoordinates+i*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips),0,
                    gapForCoordinates+i*((WINDOW_WIDTH/2-gapForCoordinates)/quantityOfShips),WINDOW_HEIGHT);
        }
        //draw horizontal lines
        for (int i = 0; i < quantityOfShips; i++) {
            g.drawLine(0,gapForCoordinates+i*((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips),
                    WINDOW_WIDTH,gapForCoordinates+i*((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips));
        }
        g.setColor(Color.RED);
        g.fillRect(WINDOW_WIDTH/2,0, (WINDOW_HEIGHT/100),WINDOW_HEIGHT);
        g.setColor(Color.BLACK);

        //draw vertical lines - opponent
        for (int i = 0; i < quantityOfShips; i++) {
            g.drawLine(
                    gapForCoordinates+10*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips)+ (WINDOW_HEIGHT/100)+gapForCoordinates+i*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips),
                    0,
                    gapForCoordinates+10*(((WINDOW_WIDTH/2)-gapForCoordinates)/quantityOfShips)+ (WINDOW_HEIGHT/100)+gapForCoordinates+i*((WINDOW_WIDTH/2-gapForCoordinates)/quantityOfShips),
                    WINDOW_HEIGHT);
        }
        //draw horizontal lines - opponent
        for (int i = 0; i < quantityOfShips; i++) {
            g.drawLine(0,gapForCoordinates+i*((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips),
                    (WINDOW_WIDTH/2),gapForCoordinates+i*((WINDOW_HEIGHT-gapForCoordinates)/quantityOfShips));
        }
    }

    private void clickedField(Point point) {
        if(turnFirstPlayer)
            point = new Point(point.x - (WINDOW_WIDTH / 2)+(WINDOW_HEIGHT/50), point.y );
        float gapForCoordinatesButFloat= (float) (WINDOW_HEIGHT*0.05);
        float X =  ((point.x-gapForCoordinatesButFloat)/((WINDOW_WIDTH/2-gapForCoordinatesButFloat)/quantityOfShips));
        float Y =  ((point.y-gapForCoordinatesButFloat)/((WINDOW_HEIGHT-gapForCoordinatesButFloat)/quantityOfShips));

        Point clickedPoint = new Point((int) X,(int) Y);
        makeActionOnField(clickedPoint);
    }

    private void makeActionOnField(Point clickedPoint) {
        if(turnFirstPlayer)
            shoot(shootingSecondPlayer,clickedPoint,mapSecondPlayer); else
                shoot(shootingFirstPlayer,clickedPoint,mapFirstPlayer);
    }

    private void shoot(Shooting shooting, Point clickedPoint, String[][] map) {
        if(clickedPoint.x>=0 && clickedPoint.x<=9 && clickedPoint.y>=0 && clickedPoint.y <=9)
        if(!Objects.equals(map[clickedPoint.x][clickedPoint.y], "X")){
            shooting.shoot(
                    clickedPoint.x,
                    clickedPoint.y,
                    map
            );
            if (!Objects.equals(map[clickedPoint.x][clickedPoint.y], "T"))
                turnFirstPlayer = !turnFirstPlayer;
        }
    }


    private class MouseEventDemo implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            if(isGameStillGoing){
                clickedField(e.getPoint());
                if (shootingSecondPlayer.getLengthsShips() == 0) isGameStillGoing = false;
                if (shootingFirstPlayer.getLengthsShips() == 0) isGameStillGoing = false;
            }
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if(isGameStillGoing){
                clickedField(e.getPoint());
                if (shootingSecondPlayer.getLengthsShips() == 0) isGameStillGoing = false;
                if (shootingFirstPlayer.getLengthsShips() == 0) isGameStillGoing = false;
            }
            repaint();
        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}
