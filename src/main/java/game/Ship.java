package game;

/*
* Ship is the class which contains information about ship on the board such as: place, length etc.
* @param shipCord - Contains coordinates of ship.
* @param map - contains every coordinate of ships.
* @param wspXOfImage - its contain coordinate of image on board.
* @param wspYOfImage - its contain coordinate of image on board.
* */

import java.util.Objects;

public class Ship {
    protected String[][] shipCord = new String[10][10];
    protected String[][] map;
    private final byte lengthShip;
    private byte hits;
    public boolean isItVertical=true;
    protected int wspXOfImage;
    protected int wspYOfImage;

    public void setHits(byte hits) {
        this.hits = hits;
    }

    public byte getHits() {
        return hits;
    }

    public byte getLengthShip() {
        return lengthShip;
    }

    public String[][] getShipCord() {
        return shipCord;
    }

    /*
    * This constructor adding "." to ship cord and can generate
    * random coordinates of ship.
    * */

    public Ship(String[][] map, byte lengthShip,boolean shouldItGenerateShipsRandomly) {
        for (int X = 0; X < shipCord.length; X++) {
            for (int Y = 0; Y < shipCord.length; Y++) {
                shipCord[X][Y] =".";
            }
        }
        hits=lengthShip;
        this.map = map;
        this.lengthShip=lengthShip;

        if(shouldItGenerateShipsRandomly){
            generate(lengthShip);
        }
    }

    /*
    * This constructor is using when user decided to make on his own coordinates of ship (class ShipManual)
    * */

    public Ship(String[][] shipCord, String[][] map, byte lengthShip, byte hits, boolean isItVertical) {
        this.shipCord = shipCord;
        this.map = map;
        this.lengthShip = lengthShip;
        this.hits = hits;
        this.isItVertical = isItVertical;
    }

    /*
    * Method generate is generating random wsp of ship,
    * direction of ship (Vertical or horizontally)
    * @param wspX - generate random, starting point, coordinate on X
    * @param wspY - generate random, starting point, coordinate on Y
    * */

    public void generate(byte lengthShip){
        float direction = (float) Math.random(); //it is generated vertical pr horizontal ships

        byte wspX;
        byte wspY;
        if(direction<=0.5){
            wspX = (byte) (Math.random() * 10);
            wspY = (byte) (Math.random() * (11 - lengthShip));
        }else {
            wspY = (byte) (Math.random() * 10);
            wspX = (byte) (Math.random() * (11 - lengthShip));
        }

        wspXOfImage= wspX;
        wspYOfImage= wspY;

        if(direction>=0.5)
        {
            isItVertical=true;
            generateX(wspX,wspY,lengthShip);
        }
        else
        {
            isItVertical=false;
            generateY(wspX,wspY,lengthShip);
        }
    }

    /*
    * Method generateX is generating ship on X
    * */

    private void generateX(byte wspX,byte wspY, byte lengthShip){
        boolean checking = true;
        for (byte i=0;i<lengthShip;i++){
            if(Objects.equals(map[wspX + i][wspY], "O") || checking((byte) (wspX+i),wspY)){
                checking=false;
                generate(lengthShip);
                break;
            }
        }

        if(checking){
            for (int i=0;i<lengthShip;i++){
                map[wspX+i][wspY] = "O";
                shipCord[wspX+i][wspY]="O";
            }
        }
    }

    /*
     * Method generateY is generating ship on Y
     * */

    private void generateY(byte wspX,byte wspY, byte lengthShip){
        boolean checking = true;
        for (byte i=0;i<lengthShip;i++){
            if(Objects.equals(map[wspX][wspY + i], "O") || checking(wspX, (byte) (wspY+i))){
                checking=false;
                generate(lengthShip);
                break;
            }
        }

        if (checking)
        for (byte i=0;i<lengthShip;i++){
            map[wspX][wspY+i] = "O";
            shipCord[wspX][wspY+i]="O";
        }
    }

    /*
    * This method is checking if the ship didn't generate near of other ship
    * */

    protected boolean checking(byte wspX, byte wspY){
        if(wspX-1 != -1) if(wspY-1 !=-1) if (Objects.equals(map[wspX - 1][wspY - 1], "O")) return true; // [-1][-1]
        if(wspX-1 != -1)                 if (Objects.equals(map[wspX - 1][wspY], "O")) return true; // [-1][0]
        if(wspX-1 != -1) if(wspY+1 !=10) if (Objects.equals(map[wspX - 1][wspY + 1], "O")) return true; // [-1][1]
        if(wspY+1 !=10)                  if (Objects.equals(map[wspX][wspY + 1], "O")) return true; // [0][1]
        if(wspX+1 != 10) if(wspY+1 !=10) if (Objects.equals(map[wspX + 1][wspY + 1], "O")) return true; // [1][1]
        if(wspX+1 != 10)                 if (Objects.equals(map[wspX + 1][wspY], "O")) return true; // [1][0]
        if(wspX+1 != 10) if(wspY-1 !=-1) if (Objects.equals(map[wspX + 1][wspY - 1], "O")) return true; // [1][-1]
        if(wspY-1 !=-1)                  if (Objects.equals(map[wspX][wspY - 1], "O")) return true; // [0][-1]
        return false;
    }

    /*
    * The point of this method is to make X, when the ship is sunken
    * */

    public void makeX(){
        for (int wspX = 0; wspX < shipCord.length; wspX++) {
            for (int wspY = 0; wspY < shipCord.length; wspY++) {
                if(Objects.equals(shipCord[wspX][wspY], "I")){
                    map[wspX][wspY]="T";
                if(wspX-1 != -1) if(wspY-1 !=-1 && !Objects.equals(shipCord[wspX - 1][wspY - 1], "I"))   map[wspX-1][wspY-1]="X";
                if(wspX-1 != -1&& !Objects.equals(shipCord[wspX - 1][wspY], "I"))                      map[wspX-1][wspY]="X";
                if(wspX-1 != -1) if(wspY+1 !=10&& !Objects.equals(shipCord[wspX - 1][wspY + 1], "I"))    map[wspX-1][wspY+1]="X";
                if(wspY+1 !=10&& !Objects.equals(shipCord[wspX][wspY + 1], "I"))                       map[wspX][wspY+1]="X";
                if(wspX+1 != 10) if(wspY+1 !=10&& !Objects.equals(shipCord[wspX + 1][wspY + 1], "I"))    map[wspX+1][wspY+1]="X";
                if(wspX+1 != 10&& !Objects.equals(shipCord[wspX + 1][wspY], "I"))                      map[wspX+1][wspY]="X";
                if(wspX+1 != 10) if(wspY-1 !=-1&& !Objects.equals(shipCord[wspX + 1][wspY - 1], "I"))    map[wspX+1][wspY-1]="X";
                if(wspY-1 !=-1&& !Objects.equals(shipCord[wspX][wspY - 1], "I"))                       map[wspX][wspY-1]="X";}
            }
        }
    }

    public void setMap(String[][] map) {
        this.map = map;
    }
}