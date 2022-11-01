public class BomberDijkstra extends Dijkstra {
    public BomberDijkstra (TileMap tm) {
        super(tm);
    }

    public void update() {
        super.update(new TileMap.Pair(Global.bomber.x / Global.scaledSize, Global.bomber.y / Global.scaledSize));
    }

}
