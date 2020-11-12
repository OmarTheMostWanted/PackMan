package nl.tudelft.jpacman.integration;

import nl.tudelft.jpacman.Launcher;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.level.Player;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * testing how the System works with the application and testing scenarios.
 */
public class ScenariosAndSystemTest {

    private Launcher launcher;


    /**
     * set up launcher.
     */
    @BeforeEach
    void setUp() {
        launcher = new Launcher();

    }

    /**
     * close the game and dispose of it.
     */
    @AfterEach
    void dispose() {
        launcher.dispose();
    }

    /**
     * testing what happens when the game is suspended and resumed.
     * Story 4: Suspend the Game.
     */
    @Test
    void suspensionTest() {

        launcher.launch();
        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();

        launcher.getGame().stop();
        assertThat(launcher.getGame().isInProgress()).isFalse();

        launcher.getGame().start();
        assertThat(launcher.getGame().isInProgress()).isTrue();

    }

    /**
     * player consumes a pellet.
     * Scenario_S2_1.
     */
    @Test
    void scenario_S2_1() {

        launcher.launch();
        launcher.getGame().start();

        Player player = launcher.getGame().getPlayers().get(0);


        assertThat(player.getScore()).isEqualTo(0);

        launcher.getGame().move(player, Direction.EAST);

        assertThat(player.getScore()).isGreaterThan(0);



        //make sure that the pellet is collected.
        assertThat(player.getSquare().getOccupants()).containsOnly(player);


    }

    /**
     * The player moves on empty square.
     * Scenario_S2_2.
     */
    @Test
    void scenario_S2_2() {

        launcher.launch();
        launcher.getGame().start();

        Player player = launcher.getGame().getPlayers().get(0);

        Square square1 = player.getSquare();
        Square square2 = square1.getSquareAt(Direction.EAST);

        assertThat(square2.isAccessibleTo(player));

        assertThat(player.getScore()).isEqualTo(0);

        launcher.getGame().move(player, Direction.EAST);

        assertThat(player.getSquare()).isEqualTo(square2);


        assertThat(square2.getOccupants()).contains(player);

    }

    /**
     * trying to move into a wall.
     * Scenario_S2_3.
     */
    @Test
    void scenario_S2_3() {

        launcher.launch();
        launcher.getGame().start();

        Player player = launcher.getGame().getPlayers().get(0);

        Square square1 = player.getSquare();
        Square square2 = square1.getSquareAt(Direction.NORTH);
        assertThat(!square2.isAccessibleTo(player));

        launcher.getGame().move(player, Direction.NORTH);
        assertThat(square2.getOccupants()).doesNotContain(player);
        assertThat(square1.getOccupants()).contains(player);
    }


    /**
     * ghost kills player and game lost.
     * Scenario_S2_4.
     */
    @Test
    void scenario_S2_4() {

        //using custom map to make is easier to test.
        launcher.withMapFile("/Scenario_S2_4.txt");
        launcher.launch();
        launcher.getGame().start();

        Player player = launcher.getGame().getPlayers().get(0);
        launcher.getGame().move(player, Direction.EAST);
        assertThat(!player.isAlive());

        assertThat(launcher.getGame().isInProgress()).isFalse();

    }

    /**
     * player consumes all pellets and game won.
     * Scenario_S2_5.
     */
    @Test
    void scenario_S2_5() {

        launcher.withMapFile("/Scenario_S2_5.txt");
        launcher.launch();
        launcher.getGame().start();

        assertThat(launcher.getGame().getLevel().remainingPellets()
        ).isEqualTo(1);
        Player player = launcher.getGame().getPlayers().get(0);
        launcher.getGame().move(player, Direction.EAST);
        assertThat(launcher.getGame().getLevel().remainingPellets()
        ).isEqualTo(0);

        assertThat(launcher.getGame().isInProgress()).isFalse();

    }
}
