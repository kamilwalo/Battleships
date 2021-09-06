package game;

/*
* The class contain map
* @param map - is the String which contains map.
* */
public class Map {
    private final String[][] map = new String[10][10];

    public Map() {
        for (int wspX = 0; wspX < map.length; wspX++) {
            for (int wspY = 0; wspY < map.length; wspY++) {
                map[wspX][wspY] = ".";
            }
        }
    }

    public String[][] getMap() {
        return map;
    }

}