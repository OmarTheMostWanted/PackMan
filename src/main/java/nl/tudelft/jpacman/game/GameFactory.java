package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.PointCalculator;

import java.util.List;

/**
 * Factory that provides Game objects.
 *
 * @author Jeroen Roosen
 */
public class GameFactory {

    /**
     * The factory providing the player objects.
     */
    private final PlayerFactory playerFactory;

    /**
     * Creates a new game factory.
     *
     * @param playerFactory The factory providing the player objects.
     */
    public GameFactory(PlayerFactory playerFactory) {
        this.playerFactory = playerFactory;
    }

    /**
     * Creates a game for a single level with one player.
     *
     * @param level           The level to create a game for.
     * @param pointCalculator The way to calculate points upon collisions.
     * @return A new single player game.
     */
    public Game createSinglePlayerGame(Level level, PointCalculator pointCalculator) {
        return new SinglePlayerGame(playerFactory.createPacMan(), level, pointCalculator);
    }

    /**
     * Creates a game those more than one level.
     *
     * @param levels          The list of levels used to create the game with.
     * @param calculator The way to calculate points upon collisions.
     * @return A new multi level game.
     */
    public MultiLevelGame createMultiLevelGame(List<Level> levels, PointCalculator calculator) {
        return new MultiLevelGame(playerFactory.createPacMan(), levels, calculator);
    }

    /**
     * Returns the player factory associated with this game factory.
     *
     * @return The player factory associated with this game factory.
     */
    protected PlayerFactory getPlayerFactory() {
        return playerFactory;
    }
}
