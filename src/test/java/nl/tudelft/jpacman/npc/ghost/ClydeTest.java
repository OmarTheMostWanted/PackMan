package nl.tudelft.jpacman.npc.ghost;

import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Direction;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.level.PlayerFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * test Clyde.
 */
class ClydeTest {

    private MapParser mapParser;
    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private GhostFactory ghostFactory;
    private PacManSprites pacManSprites;
    private DefaultPointCalculator pointCalculator;
    private PlayerFactory playerFactory;
    private Clyde clyde;

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
     * when Clyde is more than 8 square apart from Pacman
     * it should go after it.
     */
    @Test
    void nextAiMove_good_weather_cases_1() {


        Level level = mapParser.parseMap(Arrays.asList(
            "############",
            "#P        C#",
            "############"));

        level.registerPlayer(playerFactory.createPacMan());

        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());


        assertThat(clyde.nextAiMove()).contains(Direction.WEST);
    }

    /**
     * when Clyde is 8 squares or less apart from Pacman
     * it should run away.
     */
    @Test
    void nextAiMove_good_weather_cases_2() {

        Level level = mapParser.parseMap(Arrays.asList(
            "############",
            "#P       C##",
            "############"));


        level.registerPlayer(playerFactory.createPacMan());

        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());


        assertThat(clyde.nextAiMove()).contains(Direction.EAST);

    }

    /**
     * in the case where there is no player , nextAiMove should return empty.
     */
    @Test
    void nextAiMove_bad_weather_cases_1() {

        Level level = mapParser.parseMap(Arrays.asList(
            "############",
            "#        C##",
            "############"));


        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());


        assertThat(clyde.nextAiMove()).isEmpty();

    }

    /**
     * when Clyde has no possible path to Pacman.
     */
    @Test
    void nextAiMove_bad_weather_cases_2() {

        Level level = mapParser.parseMap(Arrays.asList(
            "############",
            "#      P#C##",
            "############"));


        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());


        assertThat(clyde.nextAiMove()).isEmpty();

    }

    /**
     * when Clyde is completely surrounded.
     */
    @Test
    void nextAiMove_bad_weather_cases_3() {

        Level level = mapParser.parseMap(Arrays.asList(
            "############",
            "#       #C##",
            "############"));


        clyde = Navigation.findUnitInBoard(Clyde.class, level.getBoard());


        assertThat(clyde.nextAiMove()).isEmpty();

    }

}
