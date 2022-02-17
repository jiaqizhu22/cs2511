package unsw.dungeon;

import java.util.ArrayList;
import org.json.JSONArray;

/**
 * a couple of goals AT LEAST one goal must be met for this goal to be met
 */
public class OrGoal implements Goal {
    private ArrayList<Goal> goals;
    private boolean complete;

    /**
     * Constructor for the Or Goal class
     * 
     * @param goal1 The first goal
     * @param goal2 The second goal
     */
    public OrGoal(Goal goal1, Goal goal2) {
        this.goals = new ArrayList<>();
        this.goals.add(goal1);
        this.goals.add(goal2);
        this.complete = false;
    }

    /**
     * Construct an OrGoal from JSON array
     * 
     * @param subgoals
     */
    public OrGoal(JSONArray subgoals) {
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
     * If either goal has been met, return true
     */
    @Override
    public boolean hasMetGoal(Dungeon dungeon, Player player) {
        for (Goal goal : this.goals) {
            if (goal.hasMetGoal(dungeon, player)) {
                this.complete = true;
                return true;
            }
        }
        return false;
    }

    /**
     * Print out the goal
     */
    @Override
    public String toString() {
        return goals.get(0) + " OR " + goals.get(1) + ".";
    }

}
