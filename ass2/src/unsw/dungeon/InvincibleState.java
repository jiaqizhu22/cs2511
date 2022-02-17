package unsw.dungeon;

public class InvincibleState implements PlayerState {
    private Player player;
    private int duration;

    public InvincibleState(Player player, int duration) {
        this.player = player;
        this.duration = duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getDuration() {
        return this.duration;
    }

    @Override
    public void countDown() {
        this.duration--;
        if (this.duration == 0) {
            if (player.hasSword()) {
                player.setPlayerStateSword();
            } else {
                player.setPlayerStateNormal();
            }
        }
    }

    @Override
    public void collide(Dungeon dungeon, Enemy enemy) {
        enemy.killEnemy(player);
    }

    @Override
    public String toString() {
        return "The player is invincible.";
    }

}