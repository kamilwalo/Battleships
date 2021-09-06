package game;

import Window.Game.ShipPanel;
import Window.ManualShipsSetter.ManualDragAndDropShipsPanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

/*
* This class is for users, whos want put ships on their own
* This class is extended by class Ship.
*
* @parm tX - it is delta X of image. Means that is u click on image, the cursor woudn't be on left-top corner,
*               but right there where u cilck.
* @parm tY - look up
* @parm isSelected - means, that ship is selected by user. (Click an moving it).
* */

public class ShipsManual extends Ship {
        private int tX,tY;
        private boolean isSelected;
        private Image image;
        private int widthImage=(ManualDragAndDropShipsPanel.WINDOW_HEIGHT-ShipPanel.gapForCoordinates)/ManualDragAndDropShipsPanel.quantityOfSquares;
        private int heightImage;
        private final int sizeOfSquare =( ManualDragAndDropShipsPanel.WINDOW_HEIGHT-ShipPanel.gapForCoordinates)/ManualDragAndDropShipsPanel.quantityOfSquares;
        private final int shipLength;


        /*
        * Same as class Ship it's want lengthSip and map
        * it is put the ship on the starting position (on the right window horizontally)
        * */
    public ShipsManual(int lengthShip, String[][] map) {
        super(map,(byte)lengthShip,false);

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getClassLoader().getResource("ship.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.heightImage = lengthShip*widthImage;

            putShipOnTheStartingPosition(lengthShip);

            move(wspXOfImage, wspYOfImage);
            isSelected = false;
            this.shipLength=lengthShip;

    }

    public int getShipLength() {
        return shipLength;
    }

    public void putShipOnTheStartingPosition(int lengthShip) {
        isSelected=false;
        int gapForCoordinates = ShipPanel.gapForCoordinates;
        deleteDataFromPositionOfShip();
        switch (lengthShip) {
            case 1 -> {
                wspXOfImage = gapForCoordinates + 11 * (((ManualDragAndDropShipsPanel.WINDOW_WIDTH - ManualDragAndDropShipsPanel.SPACE_FOR_SHIPS) - ShipPanel.gapForCoordinates) / ManualDragAndDropShipsPanel.quantityOfSquares);
                wspYOfImage = gapForCoordinates + 2 * ((ManualDragAndDropShipsPanel.WINDOW_HEIGHT - ShipPanel.gapForCoordinates) / ManualDragAndDropShipsPanel.quantityOfSquares);
            }
            case 2 -> {
                wspXOfImage = gapForCoordinates + 11 * (((ManualDragAndDropShipsPanel.WINDOW_WIDTH - ManualDragAndDropShipsPanel.SPACE_FOR_SHIPS) - ShipPanel.gapForCoordinates) / ManualDragAndDropShipsPanel.quantityOfSquares);
                wspYOfImage = gapForCoordinates + 4 * (((ManualDragAndDropShipsPanel.WINDOW_WIDTH - ManualDragAndDropShipsPanel.SPACE_FOR_SHIPS) - ShipPanel.gapForCoordinates) / ManualDragAndDropShipsPanel.quantityOfSquares);
            }
            case 3 -> {
                wspXOfImage = gapForCoordinates + 11 * (((ManualDragAndDropShipsPanel.WINDOW_WIDTH - ManualDragAndDropShipsPanel.SPACE_FOR_SHIPS) - ShipPanel.gapForCoordinates) / ManualDragAndDropShipsPanel.quantityOfSquares);
                wspYOfImage = gapForCoordinates + 6 * (((ManualDragAndDropShipsPanel.WINDOW_WIDTH - ManualDragAndDropShipsPanel.SPACE_FOR_SHIPS) - ShipPanel.gapForCoordinates) / ManualDragAndDropShipsPanel.quantityOfSquares);
            }
            case 4 -> {
                wspXOfImage = gapForCoordinates + 11 * (((ManualDragAndDropShipsPanel.WINDOW_WIDTH - ManualDragAndDropShipsPanel.SPACE_FOR_SHIPS) - ShipPanel.gapForCoordinates) / ManualDragAndDropShipsPanel.quantityOfSquares);
                wspYOfImage = gapForCoordinates + 8 * ((ManualDragAndDropShipsPanel.WINDOW_HEIGHT - ShipPanel.gapForCoordinates) / ManualDragAndDropShipsPanel.quantityOfSquares);
            }
        }
        }

        /*
        * when user click randomButton ships make random shipCoordinates.
        * @parm temp - it is checking if ship should rotate or not.
        * */
    public void generateShips(){
        deleteDataFromMap();
        deleteDataFromPositionOfShip();
        boolean temp = isItVertical;
        generate((byte) shipLength);
        wspXOfImage =(wspXOfImage*sizeOfSquare)+ShipPanel.gapForCoordinates;
        wspYOfImage =(wspYOfImage*sizeOfSquare)+ShipPanel.gapForCoordinates;
        if(temp!=isItVertical)
            rotateImage(isItVertical);
    }

    /*
    * It is checking if user click on the ship
    * */
    public boolean select(int x, int y){
            if(x>this.wspXOfImage && x<=this.wspXOfImage +heightImage && y>this.wspYOfImage && y<=this.wspYOfImage + widthImage) {
                isSelected = true;
                deleteDataFromMap();
                tX = x - this.wspXOfImage;
                tY = y - this.wspYOfImage;
                return true;
            }return false;
        }

        /*
        * it is deleting "O" (whats means ship) from main map.
        * */
    private void deleteDataFromMap() {
        for (int X = 0; X < shipCord.length; X++) {
            for (int Y = 0; Y < shipCord.length; Y++) {
                if(Objects.equals(shipCord[Y][X], "O")) map[Y][X]=".";  //odwrotnie wsp nie wiem czemu...
            }
        }
    }

    /*
    * It is adding "O" (whats mean ship) to main map
    * */
    private void addCoordinatesToMap() {
        for (int X = 0; X < shipCord.length; X++) {
            for (int Y = 0; Y < shipCord.length; Y++) {
                if(Objects.equals(shipCord[Y][X], "O")) map[Y][X]="O";  //odwrotnie wsp nie wiem czemu...
            }
        }
    }

    /*
    * it is deleting from shipCord position of ship.
    * */
    private void deleteDataFromPositionOfShip() {
        for (int i = 0; i < shipCord.length; i++) {
            for (int i1 = 0; i1 < shipCord.length; i1++) {
                shipCord[i1][i] = ".";
            }
        }
    }


    /*
    * when user release LMB ship shoud be unselect.
    * */
    public void unselect(){
            isSelected = false;
            if(((wspXOfImage - ShipPanel.gapForCoordinates) / sizeOfSquare)>=0 && ((wspXOfImage - ShipPanel.gapForCoordinates) / sizeOfSquare) <10
            && ((wspYOfImage - ShipPanel.gapForCoordinates) / sizeOfSquare)>=0 && ((wspYOfImage - ShipPanel.gapForCoordinates) / sizeOfSquare)<10 ){
                correctShipPosition();
                if(checkIfUserTryingPutShipOutsideMap()) {
                    addCoordinatesToPositionOfShips();
                    if (checking() && checkIfSomeonePutShipOnTheShip()) {
                        addCoordinatesToMap();
                    } else {
                        deleteDataFromMap();
                        deleteDataFromPositionOfShip();
                        putShipOnTheStartingPosition(shipLength);
                    }
                }else {
                    putShipOnTheStartingPosition(shipLength);
                }
            }else{
                putShipOnTheStartingPosition(shipLength);
            }
        }

    private boolean checkIfUserTryingPutShipOutsideMap() {
        if(!isItVertical){
            return ((wspYOfImage - ShipPanel.gapForCoordinates) / sizeOfSquare) + shipLength < 11;
        }else {
            return ((wspXOfImage - ShipPanel.gapForCoordinates) / sizeOfSquare) + shipLength < 11;
        }
    }

    private boolean checkIfSomeonePutShipOnTheShip() {
        for (int X = 0; X < shipCord.length; X++) {
            for (int Y = 0; Y < shipCord.length; Y++) {
                if(Objects.equals(shipCord[Y][X], "O") && Objects.equals(map[Y][X], "O")){
                    return false;
                }
            }
        }return true;
    }

    /*
    * it is checing from method checking (from class Ship) if the ship is near of other ship.
    * */

    private boolean checking() {
        for (byte X = 0; X < shipCord.length; X++) {
            for (byte Y = 0; Y < shipCord.length; Y++) {
                if(Objects.equals(shipCord[Y][X], "O"))
                    if( checking(Y,X)){
                    return false;
                }
            }
        }return true;
    }

    /*
    * it is adding coordinates of ship right there where user put ship.
    * */
    private void addCoordinatesToPositionOfShips() {
        deleteDataFromPositionOfShip();
        for (int i = 0; i < shipLength; i++) {
            if(isItVertical){
                shipCord[((wspXOfImage-ShipPanel.gapForCoordinates)/sizeOfSquare)+i][((wspYOfImage-ShipPanel.gapForCoordinates)/sizeOfSquare)]="O";
            }else {
                shipCord[((wspXOfImage-ShipPanel.gapForCoordinates)/sizeOfSquare)][((wspYOfImage-ShipPanel.gapForCoordinates)/sizeOfSquare)+i]="O";
                }
        }
    }


    /*
    * when user put ship on the board it should correct the position. That because ship could be
    * lay on the grid
    * */
    private void correctShipPosition() {
            wspXOfImage = ((wspXOfImage - ShipPanel.gapForCoordinates) / sizeOfSquare) * sizeOfSquare + ShipPanel.gapForCoordinates;
            wspYOfImage = ((wspYOfImage - ShipPanel.gapForCoordinates) / sizeOfSquare) * sizeOfSquare + ShipPanel.gapForCoordinates;
    }

    public void move(int x, int y) {
            if(isSelected) {
                this.wspXOfImage = x - tX;
                this.wspYOfImage = y - tY;
            }
        }

    public void paint(Graphics g) {
            g.drawImage(
                    image.getScaledInstance(heightImage,widthImage,5),
                    wspXOfImage,
                    wspYOfImage,
                    null
            );
        }

        /*
        * it is changing ship by rotate it by 90 degree.
        * */
    public void rotate(){
            if(!isItVertical){
                if (wspXOfImage / sizeOfSquare + shipLength <= 10 && wspXOfImage / sizeOfSquare + shipLength >= 0) {
                    rotateCoordinatesOfShipAndImage(true);
                } else {
                    putShipOnTheStartingPosition(shipLength);
                }
            }else {
                if(wspYOfImage / sizeOfSquare + shipLength <= 10 && wspYOfImage / sizeOfSquare + shipLength >= 0){
                    rotateCoordinatesOfShipAndImage(false);
                }else {
                    putShipOnTheStartingPosition(shipLength);
                }
            }
    }

    private void rotateCoordinatesOfShipAndImage(boolean isItVertical) {
        rotateImage(isItVertical);
        if(isItVertical){
            if(((wspYOfImage)/sizeOfSquare)+shipLength <=10) unselect();
            else putShipOnTheStartingPosition(shipLength);
        }else {
            if(((wspXOfImage)/sizeOfSquare)+shipLength<=10)  unselect();
            else putShipOnTheStartingPosition(shipLength);
        }
    }
    private void rotateImage(boolean isItVertical) {
        int temp = widthImage;
        widthImage = heightImage;
        heightImage=temp;
        this.isItVertical=isItVertical;
    }

    @Override
    public Ship clone() throws CloneNotSupportedException {
        String[][] aShipCord = new String[10][10];
        for (int x = 0; x < aShipCord.length; x++) {
            System.arraycopy(shipCord[x], 0, aShipCord[x], 0, aShipCord.length);
        }
        String[][] aMap = new String[10][10];
        for (int x = 0; x < aMap.length; x++) {
            System.arraycopy(map[x], 0, aMap[x], 0, aMap.length);
        }
        int aHits = getHits();
        boolean aIsItVertical = isItVertical;

        return new Ship(aShipCord,aMap, (byte) shipLength,(byte) aHits,aIsItVertical);

    }
}
