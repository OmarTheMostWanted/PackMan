package nl.tudelft.jpacman.game;

import com.google.common.collect.ImmutableList;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;

import java.util.List;

/**
 * the new MultiLevelGame introduces two new game states which LevelWon and LevelLost.
 * in order to reach GameWon state we need to get to the LevelWon state 5 times in a row.
 * <p>
 * to win the game the player needs to win 5 levels after each other.
 */
public class MultiLevelGame extends Game {


    /**
     * The List of levels.
     */
    private List<Level> levels;

    /**
     * Keeps track of how many levels are won.
     */
    private int stage;


    /**
     * The player of this game.
     */
    private final Player player;

    /**
     * The current level being played.
     */
    private Level level;

    /**
     * Create a new MultiLevelGame for a provided list of levels and a player.
     *
     * @param player          The player.
     * @param levels          The set of levels.
     * @param pointCalculator The way to calculate points upon collisions.
     */
    protected MultiLevelGame(Player player, List<Level> levels, PointCalculator pointCalculator) {
        super(pointCalculator);


        this.stage = 0;
        this.levels = levels;
        this.player = player;
        this.level = levels.get(stage);
        this.level.registerPlayer(player);

    }

    /**
     * simple getter for player.
     *
     * @return current player.
     */
    @Override
    public List<Player> getPlayers() {
        return ImmutableList.of(player);
    }

    /**
     * simple getter.
     *
     * @return current level that is being played.
     */
    @Override
    public Level getLevel() {
        return level;
    }


    /**
     * win a level is won, check if stage 5 is reached.
     * if not then load the next level.
     * if 5 levels are won in a row Game Won and then suspended.
     */
    @Override
    public void levelWon() {

        //level.removeObserver(this);

        this.stage++;

        if (this.stage > levels.size() - 1) {
            level.stop();
            stop();
            return;
        }

        level.stop();
        stop();


        this.level = levels.get(stage);

        this.level.registerPlayer(player);

        //level.addObserver(this);

        level.start();
        start();

    }

}



