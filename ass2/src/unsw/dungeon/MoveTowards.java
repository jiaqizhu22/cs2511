package unsw.dungeon;

public class MoveTowards implements MoveStrategy {

    public MoveTowards() {
    }

    @Override
    public void move(Dungeon dungeon, Player player, Enemy enemy) {
        int eX = enemy.getX();
        int eY = enemy.getY();
        boolean moved = false;

        while (moved == false) {
            if (eX > player.getX()) {
                if (dungeon.checkEnemyMovability(eX - 1, eY)) {
                    enemy.x().set(eX - 1);
                    break;
                }
            } else if (eX < player.getX()) {
                if (dungeon.checkEnemyMovability(eX + 1, eY)) {
                    enemy.x().set(eX + 1);
                    break;
                }
            }

            if (eY > player.getY()) {
                if (dungeon.checkEnemyMovability(eX, eY - 1)) {
                    enemy.y().set(eY - 1);
                    break;
                }
            } else if (eY < player.getY()) {
                if (dungeon.checkEnemyMovability(eX, eY + 1)) {
                    enemy.y().set(eY + 1);
                    break;
                }
            }

            moved = true;
        }
    }
}