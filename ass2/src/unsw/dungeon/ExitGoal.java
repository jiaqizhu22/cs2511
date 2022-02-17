package unsw.dungeon;

import static java.lang.Math.abs;
import java.util.ArrayList;

/**
 * the goal of reaching the exit
 */
public class ExitGoal implements Goal {
    private boolean complete;

    public ExitGoal() {
        this.complete = false;
    }

    public boolean isAdjacent(int eX, int eY, int pX, int pY) {
        if ((abs(eX - pX) == 1 && eY == pY) || (abs(eY - pY) == 1 && eX == pX)) {
            return true;
        }
        return false;
    }

    /**
     * If Player is at Exit Goal has been met
     */
    @Override
    public boolean hasMetGoal(Dungeon dungeon, Player player) {
        int pX = player.getX();
        int pY = player.getY();
        ArrayList<Integer> exitCoord = dungeon.getExitCoordinates();
        if (exitCoord.get(0) == pX && exitCoord.get(1) == pY) {
            this.complete = true;
            return true;
        }
        return false;

    }

    /**
     * For printing out this goal
     */
    @Override
    public String toString() {
        return "to reach the exit";
    }

}
