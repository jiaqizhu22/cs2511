package unsw.dungeon;

public class Switch extends Entity {
    private Boulder boulder;

    public Switch(int x, int y) {
        super(x, y, false);
        this.boulder = null;
    }

    public void addBoulder(Boulder b) {
        this.boulder = b;
    }

    public void removeBoulder() {
        this.boulder = null;
    }

    public boolean isActivated() {
        return this.boulder != null;
    }

    @Override
    public void update(Dungeon dungeon, Player player) {
        if (this.getX() == player.getX() && this.getY() == player.getY()) {
            if (this.isActivated()) {
                this.removeBoulder();
            }
        }
    }
}