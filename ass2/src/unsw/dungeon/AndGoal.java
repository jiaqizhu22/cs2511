package unsw.dungeon;

import java.util.ArrayList;
import org.json.JSONArray;

/**
 * a couple of goals BOTH goals must be met for this goal to be met
 */
public class AndGoal implements Goal {
    private ArrayList<Goal> goals;
    private boolean complete;

    /**
     * Constructor for the And Goal class
     * 
     * @param goal1 The first goal
     * @param goal2 The second goal
     */
    public AndGoal(Goal goal1, Goal goal2) {
        this.goals = new ArrayList<Goal>();
        if (goal1 instanceof ExitGoal) {
            this.goals.add(goal2);
            this.goals.add(goal1);
        } else {
            this.goals.add(goal1);
            this.goals.add(goal2);
        }
        this.complete = false;
    }

    /**
     * Construct a AndGoal from JSONArray
     * 
     * @param subgoals the JSONArray
     */
    public AndGoal(JSONArray subgoals) {
        this.goals = new ArrayList<Goal>();
        for (int i = 0; i < subgoals.length(); i++) {
            String goal = subgoals.getJSONObject(i).getString("goal");
            switch (goal) {
                case "exit":
                    this.goals.add(new ExitGoal());
                    break;
                case "enemies":
                    this.goals.add(new EnemyGoal());
                    break;
                case "treasure":
                    this.goals.add(new TreasureGoal());
                    break;
                case "boulders":
                    this.goals.add(new SwitchGoal());
                    break;
                case "AND":
                    this.goals.add(new AndGoal(subgoals.getJSONObject(i).getJSONArray("subgoals")));
                    break;
                case "OR":
                    this.goals.add(new OrGoal(subgoals.getJSONObject(i).getJSONArray("subgoals")));
                    break;
            }
        }

    }

    /**
     * if both goals have been met, return true
     */
    @Override
    public boolean hasMetGoal(Dungeon dungeon, Player player) {
        for (Goal goal : this.goals) {
            if (!goal.hasMetGoal(dungeon, player)) {
                return false;
            }
        }
		this.complete = true;
        return true;
    }

    /**
     * Prints out each goal in the class
     */
    @Override
    public String toString() {
        return goals.get(0) + " AND " + goals.get(1) + ".";
    }

}
