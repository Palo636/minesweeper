package minesweeper;

import minesweeper.consoleui.ConsoleUI;
import minesweeper.core.Field;

/**
 * Main application class.
 */
public class Minesweeper {
    private static long startMillis;
    private static BestTimes bestTimes = new BestTimes();
    private static Minesweeper instance;

    /**
     * User interface.
     */
    private UserInterface userInterface;

    /**
     * Constructor.
     */
    private Minesweeper() {
        instance = this;
        userInterface = new ConsoleUI();

        Field field = new Field(9, 9, 0);
        userInterface.newGameStarted(field);
    }

    /**
     * Main method.
     *
     * @param args arguments
     */
    public static void main(String[] args) {
        startMillis = System.currentTimeMillis();
        new Minesweeper();


    }

    public static BestTimes getBestTimes() {
        return bestTimes;
    }

    public int getPlayingSeconds() {
        int playingTime;
        playingTime = (int) ((System.currentTimeMillis() - startMillis) * 0.001);
        return playingTime;
    }

    public static Minesweeper getInstance(){
        return Minesweeper.instance;
    }
}
