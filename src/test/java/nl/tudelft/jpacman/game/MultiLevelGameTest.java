package nl.tudelft.jpacman.game;


import nl.tudelft.jpacman.MultiLevelLauncher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * the new MultiLevelGame introduces two new game states which LevelWon and LevelLost.
 * in order to reach GameWon state we need to get to the LevelWon state 5 times in a row.
 * <p>
 * to win the game the player needs to win 5 levels after each other.
 * in this class we test that functionality.
 */
public class MultiLevelGameTest extends GameTest {

    private MultiLevelLauncher launcher;
    private Game game;
    private Player player;
    private Level.LevelObserver levelObserver;
    private Level level;


    @Override
    public MultiLevelLauncher getLauncher() {

        this.launcher = new MultiLevelLauncher();
        levelObserver = Mockito.mock(Level.LevelObserver.class);
        return launcher;
    }

    @Override
    @AfterEach
    void dispose() {
        //not needed here
    }

    /**
     * test Running-->LevelWon-->RunningNewLevel.
     * test winning the first level and stating the next level.
     * Expected transition.
     * <p>
     * testing Scenario 5.1
     */
    @Test
    void testLevelWon() {
        launcher.withMapFile("/GameTest.txt");
        launcher.launch();
        launcher.getGame().start();

        //adding the mocked observer to all the levels.
        for (Level level : launcher.getLevels()) {
            level.addObserver(levelObserver);
        }

        game = launcher.getGame();
        level = game.getLevel();

        player = game.getPlayers().get(0);
        game.move(player, Direction.EAST);

        assertThat(level.remainingPellets()).isEqualTo(0);

        Mockito.verify(levelObserver).levelWon();

        Level level2 = game.getLevel();

        assertThat(level2.remainingPellets()).isEqualTo(1);

        //assert that the level has indeed changed.
        assertThat(level != level2);
        assertThat(game.isInProgress());
        assertThat(player.getScore()).isGreaterThan(0);

    }

    /**
     * test FiveLevelsWon-->GameWon-->GameSuspended.
     * test winning 5 levels and winning the hole game.
     * the expected behavior here is that the game just stops.
     * Expected transition.
     * <p>
     * testing Scenario 5.2
     */
    @Test
    @Override
    @SuppressWarnings("checkstyle:magicnumber")
    void testGameWon() {

        launcher.withMapFile("/GameTest.txt");
        launcher.launch();
        launcher.getGame().start();


        game = launcher.getGame();

        player = game.getPlayers().get(0);

        //adding the mocked observer to all the levels.
        for (Level level : launcher.getLevels()) {
            level.addObserver(levelObserver);
        }


        //winning 5 levels in a row.
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver, Mockito.times(5)).levelWon();
        assertThat(player.getScore()).isEqualTo(50);
        assertThat(game.isInProgress()).isFalse();

    }


    /**
     * test GameWon-->Running.
     * test test start after winning the game.
     * UnExpected transition.
     */
    @Test
    void testStartingWonGame() {

        launcher.withMapFile("/GameTest.txt");
        launcher.launch();
        launcher.getGame().start();


        game = launcher.getGame();

        player = game.getPlayers().get(0);

        //winning 5 levels in a row.
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);

        assertThat(game.isInProgress()).isFalse();

        //tying to press Start after winning it.
        game.start();
        assertThat(game.isInProgress()).isFalse();

    }

    /**
     * test LevelWon-->Running-->LevelLost-->GameLost.
     * testing winning some levels and failing after which leads to losing the whole game.
     * Expected transition
     */
    @Test
    void testLosingLevel() {
        launcher.withMapFile("/GameTest3.txt");
        launcher.launch();
        launcher.getGame().start();
        game = launcher.getGame();
        player = game.getPlayers().get(0);
        //adding the mocked observer to all the levels.
        for (Level level : launcher.getLevels()) {
            level.addObserver(levelObserver);
        }

        //winning 2 levels then losing.
        game.move(player, Direction.WEST);
        game.move(player, Direction.WEST);

        assertThat(player.getKiller()).isNull();
        assertThat(game.isInProgress());

        //killing the player and losing the level.
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver, Mockito.times(2)).levelWon();
        Mockito.verify(levelObserver, Mockito.times(1)).levelLost();

        assertThat(player.getKiller()).isNotNull();

        assertThat(game.isInProgress()).isFalse();
    }

    /**
     * test for simple setter.
     */
    @Test
    void testSetNumberOfLevels() {
        launcher.setNumLevels(1);
        launcher.launch();
        assertThat(launcher.getLevels().size()).isEqualTo(1);
    }

}
