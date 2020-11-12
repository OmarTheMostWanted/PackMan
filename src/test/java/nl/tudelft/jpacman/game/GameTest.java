package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * test game state transition.
 * this class has tests for both single player and multilevel game.
 * <p>
 * it tests expected transitions as well as unexpected ones.
 * sepacial maps were uses
 */
abstract class GameTest {


    private Launcher launcher;
    private Game game;
    private Player player;
    private Level.LevelObserver levelObserver;
    private Level level;


    /**
     * @return Either a SinglePlayerGame of a MultiLevelGame.
     */
    public abstract Launcher getLauncher();

    /**
     * create new launcher.
     */
    @BeforeEach
    void setUp() {
        launcher = getLauncher();
        levelObserver = Mockito.mock(Level.LevelObserver.class);

    }

    /**
     * remove the old launcher and mocked object.
     */
    @AfterEach
    void dispose() {
        launcher.dispose();
        levelObserver = null;
    }

    /**
     * test starting the game.
     * test Ready-->Running.
     * Expected transition.
     */
    @Test
    void testStartGame() {
        launcher.withMapFile("/GameTest.txt");
        launcher.launch();
        game = launcher.getGame();

        assertThat(game.isInProgress()).isFalse();

        game.start();

        assertThat(game.isInProgress()).isTrue();

        level = game.getLevel();

        level.addObserver(levelObserver);

        //nothing should happen.
        Mockito.verifyZeroInteractions(levelObserver);


    }

    /**
     * test Ready-->GameWon.
     * test winning a game that has not been started.
     * UnExpected transition.
     */
    @Test
    void testWinningNotStartedGame() {
        launcher.withMapFile("/simplemap.txt");
        launcher.launch();
        game = launcher.getGame();
        level = game.getLevel();

        level.addObserver(levelObserver);

        assertThat(game.isInProgress()).isFalse();
        assertThat(level.remainingPellets()).isEqualTo(0);

        Mockito.verifyZeroInteractions(levelObserver);
    }


    /**
     * test Ready-->GameLost.
     * test losing a game that has not been started.
     * UnExpected transition.
     */
    @Test
    void testLosingNotStartedGame() {
        launcher.withMapFile("/simplemapwithpellets.txt");
        launcher.launch();
        game = launcher.getGame();
        level = game.getLevel();


        level.addObserver(levelObserver);

        player = game.getPlayers().get(0);

        player.setAlive(false);


        Mockito.verifyZeroInteractions(levelObserver);
    }

    /**
     * test Ready-->Paused.
     * test pausing a game that has not been started.
     * UnExpected transition.
     */
    @Test
    void testPausingNotStartedGame() {

        launcher.withMapFile("/simplemapwithpellets.txt");
        launcher.launch();
        game = launcher.getGame();
        level = game.getLevel();


        level.addObserver(levelObserver);

        assertThat(game.isInProgress()).isFalse();
        game.stop();
        assertThat(game.isInProgress()).isFalse();
        Mockito.verifyZeroInteractions(levelObserver);


    }

    /**
     * test Running-->Paused.
     * test pausing the game.
     * Expected transition.
     * <p>
     * the goal is to make sure that resuming the game does not reset the game or players progress.
     */
    @Test
    void testPauseGame() {
        launcher.withMapFile("/simplemapwithpellets.txt");
        launcher.launch();
        game = launcher.getGame();
        player = game.getPlayers().get(0);
        level = game.getLevel();
        level.addObserver(levelObserver);

        game.start();

        assertThat(game.isInProgress()).isTrue();
        game.move(player, Direction.EAST);
        game.stop();
        assertThat(game.isInProgress()).isFalse();

        game.start();
        assertThat(game.isInProgress()).isTrue();
        //test that player has not lost progress.
        assertThat(player.getScore()).isGreaterThan(0);
        //nothing should happen.
        Mockito.verifyZeroInteractions(levelObserver);
    }

    /**
     * test Running-->GameWon.
     * test winning the game.
     * Expected transition.
     */
    @Test
    abstract void testGameWon();


    /**
     * test Running-->GameLost.
     * test losing the game.
     * Expected transition.
     */
    @Test
    void testGameLost() {

        launcher.withMapFile("/GameTest1.txt");
        launcher.launch();
        launcher.getGame().start();
        game = launcher.getGame();
        level = game.getLevel();
        player = game.getPlayers().get(0);


        level.addObserver(levelObserver);


        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);


        Mockito.verify(levelObserver, Mockito.times(1)).levelLost();

    }


    /**
     * test GameLost-->Running.
     * test test start after losing the game.
     * UnExpected transition.
     */
    @Test
    void testStartLostGame() {
        launcher.withMapFile("/GameTest1.txt");
        launcher.launch();
        launcher.getGame().start();
        game = launcher.getGame();
        level = game.getLevel();
        player = game.getPlayers().get(0);


        level.addObserver(levelObserver);


        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);
        game.move(player, Direction.NORTH);

        game.start();
        assertThat(game.isInProgress()).isFalse();

    }


}
