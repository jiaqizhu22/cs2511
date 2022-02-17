package unsw.dungeon;

public interface PlayerState {
    public void countDown();
    public void collide(Dungeon dungeon, Enemy enemy);
}