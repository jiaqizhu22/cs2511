package unsw.dungeon;

/**
 * the goal to kill all enemies
 */
public class EnemyGoal implements Goal {
    private boolean complete;

    public EnemyGoal() {
        this.complete = false;
    }

    /**
     * If all enemies are dead, then goal has been met
     */
    @Override
    public boolean hasMetGoal(Dungeon dungeon, Player player) {
        if (dungeon.hasEnemy()) {
            return false;
        }
		this.complete = true;
        return true;
    }

    @Override
    public String toString() {
        return "to kill all enemies";
    }

}
