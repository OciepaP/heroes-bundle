package pl.ociepa;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

class CreatureTurnQueue {
    private final Collection<Creature> creatures;
    private final Queue<Creature> creatureQueue;
    private Creature activeCreature;

    public CreatureTurnQueue(Collection<Creature> aCreatureList) {
        creatures = aCreatureList;
        creatureQueue = new LinkedList<>();
        initQueue();
        next();
    }

    private void initQueue() {
        creatureQueue.addAll(creatures);
    }

    Creature getActiveCreature() {
        return activeCreature;
    }

    void next() {

        if(creatureQueue.isEmpty()){
            initQueue();
        }
        activeCreature = creatureQueue.poll();
    }
}
