package unsw.dungeon;

public class DeadState implements PlayerState {
    private Player player;

    public DeadState(Player player) {
        this.player = player;
    }

    @Override
    public void collide(Dungeon dungeon, Enemy enemy) {
    }

    @Override
    public void countDown() {
    }
}