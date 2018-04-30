package minesweeper.tests;

import minesweeper.core.Clue;
import minesweeper.core.Field;
import minesweeper.core.GameState;
import minesweeper.core.Mine;
import org.junit.Test;

import static org.junit.Assert.*;

public class FieldTest {
    static final int ROWS = 9;
    static final int COLUMNS = 9;
    static final int MINES = 10;

    @Test
    public void openTile() {
    }

    @Test
    public void markTile() {
    }

    @Test
    public void getRowCount() {
    }

    @Test
    public void getColumnCount() {
    }

    @Test
    public void getMineCount() {
    }

    @Test
    public void getState() {
    }

    @Test
    public void getTile() {
    }

    @Test
    public void getRemainingMineCount() {
    }

    @Test
    public void isSolved() {
        Field field = new Field(ROWS, COLUMNS, MINES);

        assertEquals(GameState.PLAYING, field.getState());

        int open = 0;
        for(int row = 0; row < field.getRowCount(); row++) {
            for(int column = 0; column < field.getColumnCount(); column++) {
                if(field.getTile(row, column) instanceof Clue) {
                    field.openTile(row, column);
                    open++;
                }
                if(field.getRowCount() * field.getColumnCount() - open == field.getMineCount()) {
                    assertEquals(GameState.SOLVED, field.getState());
                } else {
                    assertNotSame(GameState.FAILED, field.getState());
                }
            }
        }

        assertEquals(GameState.SOLVED, field.getState());
    }

    @Test
    public void generate(){
        int mineCount=0;
        int clueCount=0;
        Field field = new Field(ROWS, COLUMNS, MINES);
        assertEquals(ROWS, field.getRowCount());
        assertEquals(COLUMNS, field.getColumnCount());
        assertEquals(MINES, field.getMineCount());
        for(int row = 0; row < field.getRowCount(); row++){
            for(int column = 0; column < field.getColumnCount(); column++){
                assertNotNull(field.getTile(row, column));
                if (field.getTile(row, column)instanceof Mine){
                    mineCount++;
                } else if (field.getTile(row, column) instanceof Clue){
                    clueCount++;
                }
            }

        }
        assertEquals(MINES, mineCount);
        assertEquals(ROWS*COLUMNS-MINES, clueCount);
    }
}