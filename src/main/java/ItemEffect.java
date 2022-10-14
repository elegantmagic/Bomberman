public abstract class ItemEffect {
    protected float t;
    public abstract void effectBomber(Bomber b);
    public abstract void uneffectBomber(Bomber b);

    public boolean checkTime(Bomber b, float delta) {
        t -= delta;
        if (t < 0.0f) {
            uneffectBomber(b);
            return true;
        }
        return false;
    }
}
