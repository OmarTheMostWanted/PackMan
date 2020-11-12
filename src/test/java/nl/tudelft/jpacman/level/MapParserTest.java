package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.PacmanConfigurationException;
import nl.tudelft.jpacman.board.Board;
import nl.tudelft.jpacman.board.BoardFactory;
import nl.tudelft.jpacman.board.Square;
import nl.tudelft.jpacman.npc.Ghost;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * testing the mapParser class only using mocked objects.
 * there was a false positive PMD that needed to be suppressed.
 */
public class MapParserTest {

    private MapParser mapParser;
    private LevelFactory levelFactory;
    private BoardFactory boardFactory;
    private Board board;
    private List<String> map;
    private Level level;
    private Pellet pellet;
    private Ghost ghost;

    /**
     * set up new mocked objects before each test.
     */
    @BeforeEach
    void setUp() {
        board = Mockito.mock(Board.class);
        level = Mockito.mock(Level.class);
        levelFactory = Mockito.mock(LevelFactory.class);
        boardFactory = Mockito.mock(BoardFactory.class);

        mapParser = new MapParser(levelFactory, boardFactory);

        when(boardFactory.createBoard(any())).thenReturn(board);

        pellet = Mockito.mock(Pellet.class);
        ghost = Mockito.mock(Ghost.class);

        when(levelFactory.createPellet()).thenReturn(pellet);
        when(levelFactory.createGhost()).thenReturn(ghost);
        when(levelFactory.createLevel(any(Board.class), anyList(), anyList())).thenReturn(level);

    }

    /**
     * parsing an expected map, verifying that all the correct methods have been called.
     */
    @Test
    void parseMap() {

        map = Arrays.asList(
            "############",
            "#P      . G#",
            "############");
        level = mapParser.parseMap(map);

        verify(boardFactory).createBoard(any(Square[][].class));
        verify(levelFactory).createLevel(any(Board.class), anyList(), anyList());

        assertThat(mapParser.getBoardCreator()).isEqualTo(boardFactory);


    }

    /**
     * when the map String array is null.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void checkMapFormatNotNull() {
        map = null;
        Assertions.assertThrows(
            PacmanConfigurationException.class, () -> {
                mapParser.parseMap(map);
            });
    }

    /**
     * when the array is empty.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void checkMapFormatNotEmpty() {
        map = new ArrayList<>();
        assertThat(map.isEmpty());
        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mapParser.parseMap(map);
        });
    }

    /**
     * when the map array has empty strings.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void checkMapFormatNotEmptyString() {
        map = new ArrayList<>();
        map.add("");
        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mapParser.parseMap(map);
        });
    }

    /**
     * when the array has strings that are not equal width.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void checkMapFormatNotEqualWidth() {
        map = new ArrayList<>(Arrays.asList("##", "#"));
        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mapParser.parseMap(map);
        });
    }

    /**
     * test invalid characters.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void checkMapFormatInvalidCharacter() {
        map = new ArrayList<>(Arrays.asList("##", "#@"));
        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mapParser.parseMap(map);
        });
    }

    /**
     * when the map text file is not found.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void mapParserCouldNotGetResource() {

        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mapParser.parseMap("wrong file");
        });
    }


    /**
     * testing invalid char exception.
     */
    @Test
    @SuppressWarnings("PMD.JUnitTestsShouldIncludeAssert")
    void addSquare() {
        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
            mapParser.addSquare(null, null, null, 0, 0, 'q');
        });
    }
}
