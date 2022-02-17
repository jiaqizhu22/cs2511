package unsw.dungeon;

public interface Subject {
    public void notifyObserver();
    public void attachObserver(EntityObserver obs);
    public void detachObserver(EntityObserver obs);
}