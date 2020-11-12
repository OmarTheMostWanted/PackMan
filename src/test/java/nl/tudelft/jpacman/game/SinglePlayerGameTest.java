package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * test class for Single player game.
 */
public class SinglePlayerGameTest extends GameTest {

    private Launcher launcher;
    private Game game;
    private Player player;
    private Level.LevelObserver levelObserver;
    private Level level;


    @Override
    public Launcher getLauncher() {

        this.launcher = new Launcher();
        levelObserver = Mockito.mock(Level.LevelObserver.class);
        return launcher;
    }

    /**
     * test Running-->GameWon.
     * test winning the game.
     * Expected transition.
     */
    @Test
    @Override
    void testGameWon() {

        launcher.withMapFile("/GameTest.txt");
        launcher.launch();
        launcher.getGame().start();
        game = launcher.getGame();
        level = game.getLevel();


        level.addObserver(levelObserver);
        player = game.getPlayers().get(0);
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelWon();

        assertThat(game.isInProgress()).isFalse();

    }

    /**
     * test GameWon-->Running.
     * test test start after winning the game.
     * Expected transition.
     */
    @Test
    void testStartingWonGame() {

        launcher.withMapFile("/GameTest.txt");
        launcher.launch();
        launcher.getGame().start();
        game = launcher.getGame();
        level = game.getLevel();


        level.addObserver(levelObserver);
        player = game.getPlayers().get(0);
        game.move(player, Direction.EAST);

        Mockito.verify(levelObserver).levelWon();
        game.start();
        assertThat(game.isInProgress()).isFalse();

    }


}
