package pl.ociepa;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

public class GameEngine {

    public static final String CURRENT_CREATURE_CHANGED = "CURRENT_CREATURE_CHANGED";
    public static final String CREATURE_MOVED = "CREATURE_MOVED";
    public static final String CREATURE_ATTACKED = "CREATURE_ATTACKED";
    public static final String END_OF_TURN = "END_OF_TURN";

    private final Board board;
    private final CreatureTurnQueue queue;
    private final PropertyChangeSupport observerSupport;
    private boolean blockAttacking;
    private boolean blockMoving;

    public GameEngine(List<Creature> aCreatures1, List<Creature> aCreatures2) {
        board = new Board();
        putCreaturesToBoard(aCreatures1, aCreatures2);
        List<Creature> twoSidesCreatures = new ArrayList<>();
        twoSidesCreatures.addAll(aCreatures1);
        twoSidesCreatures.addAll(aCreatures2);
        queue = new CreatureTurnQueue(twoSidesCreatures);

        twoSidesCreatures.forEach(queue::addObserver);
        observerSupport = new PropertyChangeSupport(this);
    }

    public void addObserver(String aEventType, PropertyChangeListener aObs){
        if (END_OF_TURN.equals(aEventType)){
            queue.addObserver(aObs);
        }
        observerSupport.addPropertyChangeListener(aEventType, aObs);
    }

    public void removeObserver(PropertyChangeListener aObs){
        observerSupport.removePropertyChangeListener(aObs);
    }

    public void notifyObservers(PropertyChangeEvent aEvent){
        observerSupport.firePropertyChange(aEvent);
    }



    public void move(Point aTargetPoint){
        if (blockMoving){
            return;
        }
        Point oldPosition = board.get(queue.getActiveCreature());
        board.move(queue.getActiveCreature(), aTargetPoint);
        blockMoving = true;
        notifyObservers(new PropertyChangeEvent(this, CREATURE_MOVED,oldPosition, aTargetPoint));

    }

    public void pass(){
        Creature oldActiveCreature = queue.getActiveCreature();
        queue.next();
        blockAttacking = false;
        blockMoving = false;
        Creature newActiveCreature = queue.getActiveCreature();
        notifyObservers(new PropertyChangeEvent(this, CURRENT_CREATURE_CHANGED, oldActiveCreature, newActiveCreature));
    }

    public void attack(int x, int y){
        if (blockAttacking) {
            return;
        }
        queue.getActiveCreature().attack(board.get(x,y));
        blockAttacking = true;
        blockMoving = true;
        notifyObservers(new PropertyChangeEvent(this, CREATURE_ATTACKED, null, null));

    }

    private void putCreaturesToBoard(List<Creature> aCreatures1, List<Creature> aCreatures2) {

        putCreaturesFromOneSideToBoard(aCreatures1, 0);

        putCreaturesFromOneSideToBoard(aCreatures2, board.WIDTH-1);


    }

    private void putCreaturesFromOneSideToBoard(List<Creature> aCreatures1, int aAI) {
        for (int i = 0; i < aCreatures1.size(); i++) {
            board.add(new Point(aAI, i * 2), (aCreatures1.get(i)));
        }
    }

    public Creature get(int aX, int aY) {
        return board.get(aX,aY);
    }

    public Creature getActiveCreatures() {
        return queue.getActiveCreature();
    }

    public boolean canMove(int aX, int aY) {
        return board.canMove(getActiveCreatures(),  aX, aY);

    }

    public boolean canAttack(int aX, int aY) {
        return board.get(getActiveCreatures()).distance(new Point(aX,aY)) <= 1.8;
    }
}
