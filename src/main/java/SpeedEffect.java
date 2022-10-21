public class SpeedEffect extends ItemEffect {

    public SpeedEffect() {
        t = 12.0f;
    }

    private static final int speedBoost = 12;
    
    public void effectBomber(Bomber b) {
        b.runSpeed += speedBoost;
    }

    public void uneffectBomber(Bomber b) {
        b.runSpeed -= speedBoost;
    }


}

