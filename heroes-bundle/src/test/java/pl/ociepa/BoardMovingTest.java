package pl.ociepa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardMovingTest {

    private Board board;
    private Creature creature;

    @BeforeEach
    void init() {
        board = new Board();
        creature = new Creature();
        board.add(new Point(0, 0), creature);
    }

    @Test
    void creatureShouldMoveCorrectly(){

        board.move(new Point(0,0), new Point(0,1));

        Creature creatureFromBoard = board.get(0,1);

        assertEquals(creature, creatureFromBoard);
        assertNull(board.get(0,0));
        }


    @Test
    void shouldThrowExceptionWhenCreatureTryingToMoveToNotEmpty(){

        board.add(new Point(0,1), new Creature());
        assertThrows(IllegalArgumentException.class,() ->board.move(new Point(0,0), new Point(0,1)));

        Creature creatureFromBoard = board.get(0,0);

        assertEquals(creature, creatureFromBoard);

    }

}