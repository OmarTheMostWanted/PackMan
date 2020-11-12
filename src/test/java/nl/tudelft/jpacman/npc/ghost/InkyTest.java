package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.Player;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * test Inky.
 */
class InkyTest {

    private MapParser mapParser;
    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private GhostFactory ghostFactory;
    private PacManSprites pacManSprites;
    private DefaultPointCalculator pointCalculator;
    private PlayerFactory playerFactory;
    private Inky inky;

    /**
     * setup before each test.
     */
    @BeforeEach
    void setUp() {


        pointCalculator = new DefaultPointCalculator();
        pacManSprites = new PacManSprites();
        ghostFactory = new GhostFactory(pacManSprites);
        boardFactory = new BoardFactory(pacManSprites);
        levelFactory = new LevelFactory(pacManSprites, ghostFactory, pointCalculator);
        mapParser = new GhostMapParser(levelFactory, boardFactory, ghostFactory);
        playerFactory = new PlayerFactory(pacManSprites);

    }

    /**
     * Inkys Path is clear two squares behind pacman.
     */
    @Test
    void nextAiMove_good_weather_cases_1() {

        Level level = mapParser.parseMap(Arrays.asList(
            "##############",
            "#   P    B  I#",
            "##############"));

        Player player = playerFactory.createPacMan();
        player.setDirection(Direction.EAST);

        level.registerPlayer(player);


        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).contains(Direction.WEST);
    }

    /**
     * both Blinky and Pacman are there and
     * Inky is not obstructed by anything but it has no path to its
     * destination.
     */
    @Test
    void nextAiMove_good_weather_cases_2() {

        Level level = mapParser.parseMap(Arrays.asList(
            "####                  #############",
            "#                    I P       B  #",
            "#######   #########               #"));

        Player player = playerFactory.createPacMan();
        player.setDirection(Direction.WEST);

        level.registerPlayer(player);


        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).contains(Direction.WEST);
    }

    /**
     * No Pacman.
     */
    @Test
    void nextAiMove_bad_weather_cases_1() {

        Level level = mapParser.parseMap(Arrays.asList(
            "##############",
            "#    B  I    #",
            "##############"));

        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEmpty();
    }

    /**
     * no Blinky.
     */
    @Test
    void nextAiMove_bad_weather_cases_2() {

        Level level = mapParser.parseMap(Arrays.asList(
            "##############",
            "#    P  I    #",
            "##############"));

        Player player = playerFactory.createPacMan();
        player.setDirection(Direction.EAST);

        level.registerPlayer(player);


        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEmpty();
    }

    /**
     * Inky is blocked.
     */
    @Test
    void nextAiMove_bad_weather_cases_3() {

        Level level = mapParser.parseMap(Arrays.asList(
            "##############",
            "#   P    B #I#",
            "##############"));

        Player player = playerFactory.createPacMan();
        player.setDirection(Direction.EAST);

        level.registerPlayer(player);


        inky = Navigation.findUnitInBoard(Inky.class, level.getBoard());

        assertThat(inky.nextAiMove()).isEmpty();
    }
}
