package game;

import java.util.List;
import java.util.Objects;

/*
* The point of this class is to shoot in the opponent.
* @param ships - it is list which contains ships.
* */
public class Shooting {
    private int lengthsShips;
    private List<Ship> ships;

    public Shooting(List<Ship> ships ) {
        for (int i = 0; i < ships.size(); i++) {
            this.ships=ships;
        }
    }

    public void shoot(int wspX, int wspY, String[][] map) {
        if(!Objects.equals(map[wspX][wspY], "T") && !Objects.equals(map[wspX][wspY], "X")) {
            if (Objects.equals(map[wspX][wspY], "O")) {
                for (Ship ship : ships) {
                    if (Objects.equals(ship.getShipCord()[wspX][wspY], "O")) {
                        ship.getShipCord()[wspX][wspY] = "I";
                        ship.setHits((byte) (ship.getHits() - 1));
                        lengthsShips--;
                        map[wspX][wspY] = "T";
                        if (ship.getHits() == 0) {
                            ship.makeX();
                        }
                    }

                }
            } else {
                map[wspX][wspY] = "X";
            }
        }
    }

    public int getLengthsShips() {
        return lengthsShips;
    }

    public void setLengthsShips(int lengthsShips) {
        this.lengthsShips = lengthsShips;
    }
}
