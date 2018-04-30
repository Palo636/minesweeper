package minesweeper;

import java.util.*;

/**
 * Player times.
 */
public class BestTimes implements Iterable<BestTimes.PlayerTime> {
    /** List of best player times. */
    private List<PlayerTime> playerTimes = new ArrayList<PlayerTime>();

    /**
     * Returns an iterator over a set of  best times.
     * @return an iterator
     */
    public Iterator<PlayerTime> iterator() {
        return playerTimes.iterator();
    }

    /**
     * Adds player time to best times.
     * @param name name ot the player
     * @param time player time in seconds
     */
    public void addPlayerTime(String name, int time) {
        this.playerTimes.add(new PlayerTime(name, time));
        Collections.sort(playerTimes);
    }

    public void reset(){
        playerTimes.clear();
    }

    /**
     * Returns a string representation of the object.
     * @return a string representation of the object
     */
    public String toString(){
        Formatter f = new Formatter();
        for(PlayerTime pt: this.playerTimes ){
            f.format("%2d %4d(sec)%s", playerTimes.indexOf(pt)+pt.getTime()+pt.getName() );
        }
        return f.toString();
    }

    /**
     * Player time.
     */
    public static class PlayerTime implements Comparable<PlayerTime> {
        /** Player name. */
        private final String name;

        /** Playing time in seconds. */
        private final int time;

        /**
         * Constructor.
         * @param name player name
         * @param time playing game time in seconds
         */
        public PlayerTime(String name, int time){
            this.name = name;
            this.time = time;
        }

        public String getName() {
            return name;
        }

        public int getTime() {
            return time;
        }

        @Override
        public int compareTo(PlayerTime o) {
            return 0;
        }
    }
}
