package minesweeper.consoleui;

import minesweeper.UserInterface;
import minesweeper.core.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Console user interface.
 */
public class ConsoleUI implements UserInterface {
    /**
     * Playing field.
     */
    private Field field;

    /**
     * Input reader.
     */
    private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    /**
     * Reads line of text from the reader.
     *
     * @return line as a string
     */
    private String readLine() {
        try {
            return input.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Starts the game.
     *
     * @param field field of mines and clues
     */
    @Override
    public void newGameStarted(Field field) {
        this.field = field;
        do {
            update();
            processInput();
            if (field.getState() == GameState.SOLVED) {
                System.out.println("gratulujem vyhral si ");
                System.exit(0);
            }
            if (field.getState() == GameState.FAILED) {
                System.out.println("mozno nabuduce");
                update();
                System.exit(0);
            }
        } while (true);
    }

    /**
     * Updates user interface - prints the field.
     */
    @Override
    public void update() {
        System.out.printf("  ");
        for (int i = 1; i < field.getColumnCount() + 1; i++) {
            System.out.printf("%d", i);
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < field.getRowCount(); i++) {
            System.out.printf("%c", i + 'A');
            System.out.print(" ");
            for (int j = 0; j < field.getColumnCount(); j++) {
                if ((field.getTile(i, j).getState().equals(Tile.State.OPEN)) && ((field.getTile(i, j) instanceof Mine))) {
                    System.out.print("X ");
                }
                if (field.getTile(i, j).getState().equals(Tile.State.OPEN) && field.getTile(i, j) instanceof Clue) {
                    System.out.print(((Clue) field.getTile(i, j)).getValue() + " ");
                }
                if (field.getTile(i, j).getState().equals((Tile.State.MARKED))) {
                    System.out.print("M ");
                }
                if (field.getTile(i, j).getState().equals(Tile.State.CLOSED)) {
                    System.out.print("- ");
                }

            }
            System.out.println();
        }

        System.out.println("pocet zostavajucich min je: " + field.getRemainingMineCount());

    }

    /**
     * Processes user input.
     * Reads line from console and does the action on a playing field according to input string.
     */
    private void processInput() {
        System.out.println("Nazdar " + System.getProperty("user.name"));

        System.out.println("X – ukončenie hry, MA1 – označenie dlaždice v riadku A a stĺpci 1, OB4 – odkrytie dlaždice v riadku B a stĺpci 4.");
        String input = readLine().toUpperCase();
        try {
            handleInput(input);
        } catch (WrongFormatException e) {
            e.getMessage();
        }
    }

    private void handleInput(String input) throws WrongFormatException {

        Pattern pattern = Pattern.compile("([O,M])([A-I])([0-9])");
        Matcher matcher = pattern.matcher(input);
        if (input.equals("X")) {
            System.exit(0);
        } else if (matcher.matches()) {
            int row = matcher.group(2).charAt(0) - 'A';
            int column = Integer.parseInt(matcher.group(3));
            if (input.charAt(0) == 'M') {
                field.markTile(row, column - 1);
            }
            if (input.charAt(0) == 'O') {
                field.openTile(row, column - 1);
            }

        } else {
            System.exit(1);
        }
    }
}


