package unsw.dungeon;

public class WithSwordState implements PlayerState {
    private Player player;

    public WithSwordState(Player player) {
        this.player = player;
    }

    @Override
    public void collide(Dungeon dungeon, Enemy enemy) {
        player.useSword();
        enemy.killEnemy(player);
    }

    @Override
    public String toString() {
        return "The player has a sword.";
    }

    @Override
    public void countDown() {
        // do nothing
    }
}