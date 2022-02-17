package unsw.dungeon;

public class Sword extends Entity {
    private int turns;

    public Sword(int x, int y) {
        super(x, y, true);
        this.turns = 5;
    }

    public int getTurns() {
        return turns;
    }

    public void useSword() {
        if (turns > 0) {
            turns -= 1;
        }
    }

}