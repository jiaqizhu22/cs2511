package unsw.dungeon;

/**
 * the goal that all floor switches are activated
 */
public class SwitchGoal implements Goal {
	private boolean complete;

    public SwitchGoal() {
        this.complete = false;
    }

	/**
	 * If all switches have been activated, return true
	 */
	@Override
	public boolean hasMetGoal(Dungeon dungeon, Player player) {
		for (Entity e: dungeon.getEntities()) {
			if (e instanceof Switch && !((Switch) e).isActivated()) {
				return false;
			}
		}
		this.complete = true;
		return true;
	}
	
	@Override
	public String toString() {
		return "to activate all floor switches with boulders";
	}
}
