package unsw.dungeon;

public class NormalState implements PlayerState {
    private Player player;

    public NormalState(Player player) {
        this.player = player;
    }

    @Override
    public void collide(Dungeon dungeon, Enemy enemy) {
        player.setPlayerStateDead();
        player.hideFromMap();
        // TODO change javaFX to the dead page 
    }

    @Override
    public void countDown() {
        // do nothing
    }
}