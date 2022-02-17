package unsw.dungeon;

public class Key extends Entity {
    private int id;

    public Key(int x, int y, int id) {
        super(x, y, true);
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public boolean matchID(int doorID) {
        if (this.getID() == doorID) {
            return true;
        }
        return false;
    }
}