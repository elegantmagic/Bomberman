public abstract class Enemy extends Animation implements Mortal, BombPlanter {
    protected boolean hasBomb = true;
    
    


    public void die() {
        Global.nEnemy--;
    }
    
    public void allowPlantingBomb() {
        hasBomb = true;
    }

    protected abstract TileMap.Pair pathing();
}
