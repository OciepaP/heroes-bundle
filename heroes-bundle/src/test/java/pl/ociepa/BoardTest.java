package pl.ociepa;

import javafx.scene.layout.CornerRadii;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private Creature creature;
    @BeforeEach
    void init(){
        board = new Board();
        creature = new Creature();
    }
    @Test
    void shouldAddCreature(){


        board.add(new Point(0,0 ), creature);

        Creature creatureFromBoard = board.get(0,0);

        assertEquals(creature, creatureFromBoard);
    }

    @Test
    void shouldReturnNullWhenFliedIsEmpty() {

        Creature creatureFromBoard = board.get(0,0);

        assertNull(creatureFromBoard);
    }

    @Test
    void shouldXWenYouTryAddCreatureToNotEmptyField() {

        board.add(new Point(0,0), creature);

        /*Creature creature2 = new Creature();
        board.add(new Point(0,0), creature2);*/

        Creature creatureFromBoard = board.get(0,0);

        assertEquals(creature, creatureFromBoard);
    }


    @Test
    void shouldReturnCorrectlocationForByCreature(){
        board.add(new Point(5,5), creature);
        Point resutl = board.get(creature);

        assertEquals(new Point(5,5), resutl);
    }
}