public class FlameEffect extends ItemEffect {

    public FlameEffect() {
        t = 20.0f;
    }

    private static final int radiusBoost = 2;

    public void effectBomber(Bomber b) {
        b.bombRadius += radiusBoost;
    }

    public void uneffectBomber(Bomber b) {
        b.bombRadius -= radiusBoost;
    }

}
