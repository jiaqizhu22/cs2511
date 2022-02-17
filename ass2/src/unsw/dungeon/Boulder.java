package unsw.dungeon;

import java.util.ArrayList;

public class Boulder extends Entity {

    public Boulder(int x, int y) {
        super(x, y, false);
    }

    @Override
    public void update(Dungeon dungeon, Player player) {
        if (player.getX() == this.getX() && player.getY() == this.getY()) {
            int direction = player.getLastPressed();
            int newX = this.getX();
            int newY = this.getY();
            switch (direction) {
                case 0:
                    newY--;
                    break;
                case 1:
                    newY++;
                    break;
                case 2:
                    newX--;
                    break;
                case 3:
                    newX++;
                    break;
            }
            this.updateCoordinates(newX, newY);
            ArrayList<Entity> square = dungeon.getEntityFromCoordinates(newX, newY);
            if (square.size() != 0) {
                if (square.get(0) instanceof Switch) {
                    Switch fswicth = (Switch) square.get(0);
                    fswicth.addBoulder(this);
                } else if (square.size() > 1 && square.get(1) instanceof Switch) {
                    Switch fswicth = (Switch) square.get(1);
                    fswicth.addBoulder(this);
                }
            }
        }
    }
}