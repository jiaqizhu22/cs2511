package unsw.dungeon;

/**
 * the goal to collect all treasure
 *
 */
public class TreasureGoal implements Goal {
    private boolean complete;

    public TreasureGoal() {
        this.complete = false;
    }

	/**
	 * If there's no treasure on the dungeon map
	 */
	@Override
	public boolean hasMetGoal(Dungeon dungeon, Player player) {
		if (dungeon.hasTreasureLeft()) {
			return false;
		}
		this.complete = true;
		return true;
	}

	/**
	 * Prints the class
	 */
	@Override
	public String toString() {
		return "to collect all the treasure";
	}
}
