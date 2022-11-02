public class LogicMap {
    public int map[][];

    public LogicMap(int width, int height, int initValue) {
        map = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                map[i][j] = initValue;
            }
        }
        
    }

    public LogicMap(TileMap tm, int initValue) {
        map = new int[tm.getWidth()][tm.getHeight()];
        for (int i = 0; i < tm.getWidth(); i++) {
            for (int j = 0; j < tm.getHeight(); j++) {
                map[i][j] = initValue;
            }
        }
    }
    
    public void setup(int initValue) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = initValue;
            }
        }
    }
    public void refresh(int initValue) {
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = initValue;
            }
        }
    }

    public void update() {

    }
}
