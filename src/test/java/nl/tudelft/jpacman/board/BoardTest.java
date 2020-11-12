package nl.tudelft.jpacman.board;

import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.level.LevelFactory;
import nl.tudelft.jpacman.level.MapParser;
import nl.tudelft.jpacman.npc.ghost.GhostFactory;
import nl.tudelft.jpacman.points.DefaultPointCalculator;
import nl.tudelft.jpacman.sprite.PacManSprites;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * test the assertions in Board class.
 */
public class BoardTest {

    private MapParser mapParser;
    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private GhostFactory ghostFactory;
    private PacManSprites pacManSprites;
    private Level level;
    private Board board;
    private DefaultPointCalculator pointCalculator;


    /**
     * test a valid board where it has no Null value in its grid.
     */
    @Test
    void testValidBoard() {
        Square[][] grid = new Square[1][1];

        for (Square[] row : grid) {
            Arrays.fill(row, new BasicSquare());
        }

        Board br = new Board(grid);
        assertThat(br.invariant());
    }

    /**
     * SetUp a 4X4 board filled to blank squares to test withinBorders() method.
     */
    @BeforeEach
    void setUpBoard() {

        pointCalculator = new DefaultPointCalculator();
        pacManSprites = new PacManSprites();
        ghostFactory = new GhostFactory(pacManSprites);
        boardFactory = new BoardFactory(pacManSprites);
        levelFactory = new LevelFactory(pacManSprites, ghostFactory, pointCalculator);
        mapParser = new MapParser(levelFactory, boardFactory);
        level = mapParser.parseMap(Arrays.asList(
            "    ",
            "    ",
            "    ",
            "    "));
        board = level.getBoard();
    }

    /**
     * running the test at every boundary point for both X and Y.
     *
     * @param x   x cor.
     * @param y   y cor.
     * @param res result.
     */
    @ParameterizedTest
    @CsvSource({
        "-1, 2, false",
        "0, 2, true",
        "1, 2, true",
        "3, 2, true",
        "4, 2 ,false",
        "2, -1, false",
        "2, 0, true",
        "2, 1, true",
        "2, 3, true",
        "2, 4 ,false"
    })
    void withinBorders(int x, int y, boolean res) {

        assertThat(board.withinBorders(x, y)).isEqualTo(res);

    }
}
