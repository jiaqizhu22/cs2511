package unsw.dungeon;

public class FreezingPotion extends Entity {
    private int duration;

    public FreezingPotion(int x, int y) {
        super(x, y, true);
        this.duration = 10;
    }

    public int getDuration() {
        return duration;
    }
}