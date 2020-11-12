//package nl.tudelft.jpacman.level;
//
//import nl.tudelft.jpacman.PacmanConfigurationException;
//import nl.tudelft.jpacman.board.Board;
//import nl.tudelft.jpacman.board.BoardFactory;
//import nl.tudelft.jpacman.board.Square;
//import nl.tudelft.jpacman.npc.ghost.GhostFactory;
//import nl.tudelft.jpacman.points.DefaultPointCalculator;
//import nl.tudelft.jpacman.sprite.PacManSprites;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.ArgumentMatchers.anyList;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
///**
// * mapParser test.
// */
//public class OldMapParserTest {
//
//    private Board board;
//    private Level level;
//    private MapParser mapParser;
//    private LevelFactory levelFactory;
//    private BoardFactory boardFactory;
//    private GhostFactory ghostFactory;
//    private PacManSprites pacManSprites;
//    private DefaultPointCalculator pointCalculator;
//    private PlayerFactory playerFactory;
//    private BoardFactory boardFactoryMocked;
//    private LevelFactory levelFactoryMocked;
//    private MapParser mapParserTest;
//    private List<String> map;
//
//    /**
//     * set up before each test.
//     */
//    @BeforeEach
//    void setUp() {
//
//        pointCalculator = new DefaultPointCalculator();
//        pacManSprites = new PacManSprites();
//        ghostFactory = new GhostFactory(pacManSprites);
//        boardFactory = new BoardFactory(pacManSprites);
//        levelFactory = new LevelFactory(pacManSprites, ghostFactory, pointCalculator);
//        mapParser = new MapParser(levelFactory, boardFactory);
//        playerFactory = new PlayerFactory(pacManSprites);
//
//        boardFactoryMocked = Mockito.mock(BoardFactory.class);
//        levelFactoryMocked = Mockito.mock(LevelFactory.class);
//        mapParserTest = new MapParser(levelFactoryMocked, boardFactoryMocked);
//
//
//    }
//
//    /**
//     * good weather case.
//     */
//    @Test
//    void mapParserGoodWeatherCase() {
//        map = Arrays.asList(
//            "############",
//            "#P      . G#",
//            "############");
//
//        level = mapParser.parseMap(map);
//        level.registerPlayer(playerFactory.createPacMan());
//        board = level.getBoard();
//        when(boardFactoryMocked.createBoard(any(Square[][].class))).thenReturn(board);
//        when(levelFactoryMocked.createLevel(any(Board.class),
//            anyList(), anyList())).thenReturn(level);
//        //int size = 5;
//        int size = 2;
//        char[][] chars = new char[size][size];
//        for (int i = 0; i < size; i++) {
//            for (int k = 0; k < size; k++) {
//
//                chars[i][k] = ' ';
//            }
//        }
//        mapParserTest.parseMap(chars);
//        verify(boardFactoryMocked).createBoard(any(Square[][].class));
//        verify(levelFactoryMocked).createLevel(any(Board.class), anyList(), anyList());
//
//        assertThat(mapParserTest.getBoardCreator()).isEqualTo(boardFactoryMocked);
//    }
//
//    /**
//     * when the map String array is null.
//     */
//    @Test
//    void checkMapFormatNotNull() {
//        map = null;
//        Assertions.assertThrows(
//            PacmanConfigurationException.class, () -> {
//                mapParserTest.parseMap(map);
//            });
//    }
//
//    /**
//     * when the array is empty.
//     */
//    @Test
//    void checkMapFormatNotEmpty() {
//        map = new ArrayList<>();
//        assertThat(map.isEmpty());
//        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
//            mapParserTest.parseMap(map);
//        });
//    }
//
//    /**
//     * when the map array has empty strings.
//     */
//    @Test
//    void checkMapFormatNotEmptyString() {
//        map = new ArrayList<>();
//        map.add("");
//        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
//            mapParserTest.parseMap(map);
//        });
//    }
//
//    /**
//     * when the array has strings that are not equal width.
//     */
//    @Test
//    void checkMapFormatNotEqualWidth() {
//        map = new ArrayList<>(Arrays.asList("##", "#"));
//        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
//            mapParserTest.parseMap(map);
//        });
//    }
//
//    /**
//     * test invalid characters.
//     */
//    @Test
//    void checkMapFormatInvalidCharacter() {
//        map = new ArrayList<>(Arrays.asList("##", "#@"));
//        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
//            mapParser.parseMap(map);
//        });
//    }
//
//    /**
//     * when the map text file is not found.
//     */
//    @Test
//    void mapParserCouldNotGetResource() {
//
//        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
//            mapParserTest.parseMap("wrong file");
//        });
//    }
//
//    /**
//     * testing parsing map form a file.
//     *
//     * @throws IOException exception expected if a file is not found.
//     */
//    @Test
//    void mapParserInputStreamTestGoodWeatherCase() throws IOException {
//
//        String path = "/simplemap.txt";
//
//
//        level = mapParser.parseMap(path);
//
//        level.registerPlayer(playerFactory.createPacMan());
//
//        board = level.getBoard();
//
//        when(boardFactoryMocked.createBoard(any(Square[][].class))).thenReturn(board);
//        when(levelFactoryMocked.createLevel(any(Board.class),
//            anyList(), anyList())).thenReturn(level);
//
//
//        mapParserTest.parseMap(path);
//        verify(boardFactoryMocked).createBoard(any(Square[][].class));
//        verify(levelFactoryMocked).createLevel(any(Board.class), anyList(), anyList());
//
//
//    }
//
//    /**
//     * testing invalid char exception.
//     */
//    @Test
//    void addSquare() {
//        Assertions.assertThrows(PacmanConfigurationException.class, () -> {
//            mapParserTest.addSquare(null, null, null, 0, 0, 'q');
//        });
//    }
//
//}
