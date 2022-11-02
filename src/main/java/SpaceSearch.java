import java.util.HashSet;
import java.util.Set;

public class SpaceSearch {
    public static Set<Spatial>[][] space;

    @SuppressWarnings("unchecked")
    public static void setup(TileMap tm) {
        space = (Set<Spatial>[][]) new Set[tm.getWidth()][tm.getHeight()];
        SpaceSearch.reset();
    }

    public static void reset() {
        for (int i = 0; i < SpaceSearch.space.length; i++) {
            for (int j = 0; j < SpaceSearch.space[0].length; j++) {
                SpaceSearch.space[i][j] = new HashSet<Spatial>();

            }
        }
    }

    public static Set<Spatial> anyAt(int row, int col) {
        return space[col][row];
    }

    public static void remove(Spatial spatial) {
        int left = spatial.x / Global.scaledSize;
        int right = (spatial.x + Global.scaledSize - 1) / Global.scaledSize;

        int up = spatial.y / Global.scaledSize;
        int low = (spatial.y + Global.scaledSize - 1) / Global.scaledSize;

        space[left][up].remove(spatial);
        space[left][low].remove(spatial);
        space[right][up].remove(spatial);
        space[right][low].remove(spatial);
    }

    public static void add(Spatial spatial) {
        int left = spatial.x / Global.scaledSize;
        int right = (spatial.x + Global.scaledSize - 1) / Global.scaledSize;

        int up = spatial.y / Global.scaledSize;
        int low = (spatial.y + Global.scaledSize - 1) / Global.scaledSize;

        space[left][up].add(spatial);
        space[left][low].add(spatial);
        space[right][up].add(spatial);
        space[right][low].add(spatial);
    }
}


