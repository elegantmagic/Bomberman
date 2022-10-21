public class SpaceSearch {
    private static Spatial[][] space;
    

    public static void setup(TileMap tm) {
        space = new Spatial[tm.getWidth()][tm.getHeight()];
    }

    public static void reset() {
        for (int i = 0; i < SpaceSearch.space.length; i++) {
            for (int j = 0; j < SpaceSearch.space[0].length; j++) {
                SpaceSearch.space[i][j] = null;
            }
        }
    }

    public static Spatial[] anyAt(int row, int col) {

        return null;
    }

    public static void remove(Spatial spatial) {

    }
}
