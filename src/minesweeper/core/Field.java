package minesweeper.core;

import java.util.Random;

/**
 * Field represents playing field and game logic.
 */
public class Field {
    /**
     * Playing field tiles.
     */
    private final Tile[][] tiles;

    private final int rowCount;

    private final int columnCount;

    private final int mineCount;

    private GameState state = GameState.PLAYING;

    /**
     * Constructor.
     *
     * @param rowCount    row count
     * @param columnCount column count
     * @param mineCount   mine count
     */
    public Field(int rowCount, int columnCount, int mineCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.mineCount = mineCount;
        tiles = new Tile[rowCount][columnCount];

        //generate the field content
        generate();
    }

    /**
     * Opens tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void openTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.OPEN);
                openAdjacentTiles(row, column);
            if (tile instanceof Mine) {
                state = GameState.FAILED;
                return;
            }

            if (isSolved()) {
                state = GameState.SOLVED;
                return;
            }
        }
    }

    /**
     * Marks tile at specified indeces.
     *
     * @param row    row number
     * @param column column number
     */
    public void markTile(int row, int column) {
        Tile tile = tiles[row][column];
        if (tile.getState() == Tile.State.CLOSED) {
            tile.setState(Tile.State.MARKED);
        } else if (tile.getState() == Tile.State.MARKED) {
            tile.setState(Tile.State.CLOSED);
        }
    }

    /**
     * Generates playing field.
     */
    private void generate() {
        Random randomNumber = new Random();
        int column, row;
        for (int i = 0; i < mineCount; i++) {
            column = randomNumber.nextInt(getColumnCount());
            row = randomNumber.nextInt(getRowCount());
            if (tiles[row][column] == null) {
                tiles[row][column] = new Mine();
            } else {
                i--;
            }
        }

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (tiles[i][j] == null) {
                    tiles[i][j] = new Clue(countAdjacentMines(i, j));
                }
            }

        }


    }

    /**
     * Returns true if game is solved, false otherwise.
     *
     * @return true if game is solved, false otherwise
     */
    private boolean isSolved() {
        return (getColumnCount() * getRowCount() == getNumberOf(Tile.State.OPEN) + getMineCount());
    }

    /**
     * Returns number of adjacent mines for a tile at specified position in the field.
     *
     * @param row    row number.
     * @param column column number.
     * @return number of adjacent mines.
     */
    private int countAdjacentMines(int row, int column) {
        int count = 0;
        for (int rowOffset = -1; rowOffset <= 1; rowOffset++) {
            int actRow = row + rowOffset;
            if (actRow >= 0 && actRow < getRowCount()) {
                for (int columnOffset = -1; columnOffset <= 1; columnOffset++) {
                    int actColumn = column + columnOffset;
                    if (actColumn >= 0 && actColumn < getColumnCount()) {
                        if (tiles[actRow][actColumn] instanceof Mine) {
                            count++;
                        }
                    }
                }
            }
        }

        return count;
    }

    private void openAdjacentTiles(int row, int column) {
        if (getTile(row, column).getState() == Tile.State.OPEN && (getTile(row, column) instanceof Clue && (((Clue) getTile(row, column)).getValue() == 0))) {
            for (int i = row - 1; i <= row + 1; i++) {
                for (int j = column - 1; j <= column + 1; j++) {
                    if (i+1 > getRowCount() || j+1 > getColumnCount() || i < 0 || j < 0) {

                    } else {
                        openTile(i, j);

                    }
                }
            }
        }
    }

    /**
     * Field row count. Rows are indexed from 0 to (rowCount - 1).
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * Column count. Columns are indexed from 0 to (columnCount - 1).
     */
    public int getColumnCount() {
        return columnCount;
    }

    /**
     * Mine count.
     */
    public int getMineCount() {
        return mineCount;
    }

    /**
     * Game state.
     */
    public GameState getState() {
        return state;
    }

    public Tile getTile(int row, int column) {
        return tiles[row][column];
    }

    private int getNumberOf(Tile.State state) {
        int open = 0;
        int marked = 0;
        int closed = 0;

        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                if (getTile(i, j).getState() == Tile.State.OPEN) {
                    open++;
                }
                if (getTile(i, j).getState() == Tile.State.CLOSED) {
                    closed++;
                }
                if (getTile(i, j).getState() == Tile.State.MARKED) {
                    marked++;
                }
            }
        }
        if (state.equals(Tile.State.CLOSED)) {
            return closed;
        }
        if (state.equals(Tile.State.MARKED)) {
            return marked;
        } else
            return open;


    }
}
