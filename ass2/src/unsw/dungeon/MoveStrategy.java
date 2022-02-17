package unsw.dungeon;

public interface MoveStrategy {
    public void move(Dungeon dungeon, Player player, Enemy enemy);
}