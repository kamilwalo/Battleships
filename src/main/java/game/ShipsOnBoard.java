package game;

public class ShipsOnBoard {
    private static int ship1 =1;
    private static int ship2 =1;
    private static int ship3 =1;
    private static int ship4 =1;

    public static void setShip1(int ship1) {
        ShipsOnBoard.ship1 = ship1;
    }

    public static void setShip2(int ship2) {
        ShipsOnBoard.ship2 = ship2;
    }

    public static void setShip3(int ship3) {
        ShipsOnBoard.ship3 = ship3;
    }

    public static void setShip4(int ship4) {
        ShipsOnBoard.ship4 = ship4;
    }

    public static int getShip1() {
        return ship1;
    }

    public static int getShip2() {
        return ship2;
    }

    public static int getShip3() {
        return ship3;
    }

    public static int getShip4() {
        return ship4;
    }

    public static int getLengthWholeShips(){ return ship1 +ship2*2+ship3*3+ship4*4;}
}
