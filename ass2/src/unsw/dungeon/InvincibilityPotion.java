package unsw.dungeon;

public class InvincibilityPotion extends Entity {
    private int duration;

    public InvincibilityPotion(int x, int y) {
        super(x, y, true);
        this.duration = 10;
    }

    public int getDuration() {
        return duration;
    }
}