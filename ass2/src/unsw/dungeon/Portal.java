package unsw.dungeon;


public class Portal extends Entity {
    private int id;
    private boolean stage;

    public Portal(int x, int y, int id) {
        super(x, y, false);
        this.id = id;
        this.stage = false;
    }

    public int getID() {
        return id;
    }

    public boolean getStage() {
        return stage;
    }

    public void teleport(Dungeon dungeon, Player player) {
        Portal pair = null;
        for (Entity e : dungeon.getEntities()) {
            if (e instanceof Portal) {
                Portal des = (Portal) e;
                if (des.getID() == this.getID() && (des.getX() != this.getX() || des.getY() != this.getY())) {
                    pair = des;
                    break;
                }
            }
        }
        if (pair != null) {
            int newX = pair.getX();
            int newY = pair.getY();
            player.updateCoordinates(newX, newY);
        }
    }

}