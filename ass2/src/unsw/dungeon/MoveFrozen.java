package unsw.dungeon;

public class MoveFrozen implements MoveStrategy {
    private int duration;

    public MoveFrozen(int duration) {
        this.duration = duration;
    }

    @Override
    public void move(Dungeon dungeon, Player player, Enemy enemy) {
        this.duration--;
        if (this.duration == 0) {
            if (player.isInvincible().get()) {
                enemy.setStrategy(new MoveAway());
            } else {
                enemy.setStrategy(new MoveTowards());
            }
            enemy.defreeze();
        }
    }
}