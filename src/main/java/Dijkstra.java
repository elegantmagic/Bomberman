import java.util.HashSet;

public class Dijkstra extends LogicMap {
    public Dijkstra(TileMap tm) {
        super(tm, Integer.MAX_VALUE);
        for (int i = 0; i < tm.getWidth(); i++) {
            for (int j = 0; j < tm.getHeight(); j++) {
                map[i][j] = tm.map[i][j] == 0 ? Integer.MAX_VALUE : -1;
            }
        }
    }
    
    public void update() {
        

    }

    public void update(TileMap.Pair start) {
        HashSet<TileMap.Pair> frontier = new HashSet<TileMap.Pair>(); // Must not contain a point with value = -1
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = Global.tilemap.map[i][j] != 0 ? -1 : Integer.MAX_VALUE;
            }
        }
        map[start.x][start.y] = 0;
        frontier.add(start);
        while (!frontier.isEmpty()) {
            int min = Integer.MAX_VALUE;
            TileMap.Pair k = null;
            for (TileMap.Pair cand : frontier) {
                if (min > map[cand.x][cand.y]) {
                    min = map[cand.x][cand.y];
                    k = cand;
                }
            }
            frontier.remove(k);
            TileMap.Pair[] neighbors = {new TileMap.Pair(k.x + 1, k.y), 
                                        new TileMap.Pair(k.x - 1, k.y),
                                        new TileMap.Pair(k.x, k.y + 1),
                                        new TileMap.Pair(k.x, k.y - 1)};
            for (TileMap.Pair n : neighbors) {
                if (map[n.x][n.y] == -1) continue;
                if (Global.bombBlast.map[n.x][n.y] < 0) {
                    map[n.x][n.y] = Integer.MAX_VALUE;
                    continue;
                }
                if (map[n.x][n.y] > min + 1) {
                    map[n.x][n.y] = min + 1;
                    frontier.add(n);
                }
            }
        }
    }
}
