package nl.tudelft.jpacman.game;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * testing the Start Game method.
 */
class GameUnitTest {

    private SinglePlayerGame singlePlayerGame;
    private Player player;
    private Level level;

    /**
     * set  up singlePlayerGame object with mocked player and level.
     */
    @BeforeEach
    void setUp() {

        player = Mockito.mock(Player.class);

        level = Mockito.mock(Level.class);

        singlePlayerGame = new SinglePlayerGame(player, level, Mockito.mock(PointCalculator.class));


    }

    /**
     * make sure that the game start method has the expected behavior.
     */
    @Test
    void startGoodWeatherCase() {

        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(1);
        //game did not start yet
        assertThat(singlePlayerGame.isInProgress()).isFalse();

        singlePlayerGame.start();

        assertThat(singlePlayerGame.isInProgress()).isTrue();

        //starting the game for the second time (nothing should happen).
        singlePlayerGame.start();
        //make sure that the method calls happened only once.
        verify(level, Mockito.times(1)).addObserver(any());
        verify(level, Mockito.times(1)).start();

    }

    /**
     * make sure that does not start with no alive players.
     */
    @Test
    void startNoAlivePLayers() {

        when(level.isAnyPlayerAlive()).thenReturn(false);
        when(level.remainingPellets()).thenReturn(1);

        assertThat(singlePlayerGame.isInProgress()).isFalse();

        singlePlayerGame.start();

        assertThat(singlePlayerGame.isInProgress()).isFalse();

        verify(level, Mockito.times(0)).addObserver(any());
        verify(level, Mockito.times(0)).start();

    }

    /**
     * starting the game with no pellets.
     */
    @Test
    void startWithNOPellets() {

        when(level.isAnyPlayerAlive()).thenReturn(true);
        when(level.remainingPellets()).thenReturn(0);

        assertThat(singlePlayerGame.isInProgress()).isFalse();

        singlePlayerGame.start();

        assertThat(singlePlayerGame.isInProgress()).isFalse();

        verify(level, Mockito.times(0)).addObserver(any());
        verify(level, Mockito.times(0)).start();

    }


}
