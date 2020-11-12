package nl.tudelft.jpacman;


import nl.tudelft.jpacman.game.GameFactory;
import nl.tudelft.jpacman.game.MultiLevelGame;
import nl.tudelft.jpacman.level.Level;
import nl.tudelft.jpacman.points.PointCalculatorLoader;
import nl.tudelft.jpacman.ui.PacManUiBuilder;

import java.util.ArrayList;
import java.util.List;


/**
 * Creates and launches the JPacMan UI.
 * Also creates a given number of maps and uses them to create a multi level game.
 *
 * @author Jeroen Roosen
 */
public class MultiLevelLauncher extends Launcher {


    private MultiLevelGame multiLevelGame;
    private List<Level> levels;

    private String levelMap = "/easyBoard.txt";


    /**
     * number of levels of the game.
     */
    @SuppressWarnings("checkstyle:magicnumber")
    private int numLevels = 4;

    /**
     * Creates a given number of levels using the makesGame method.
     * Those are used to create the game class.
     *
     * @return The game object this launcher will start when {@link #launch()}
     * is called.
     */
    @Override
    public MultiLevelGame makeGame() {

        levels = new ArrayList<>();

        int i;

        for (i = 0; i <= numLevels; i++) {

            this.levels.add(this.makeLevel());

        }

        GameFactory gf = getGameFactory();

        this.multiLevelGame = gf.createMultiLevelGame(
            this.levels, new PointCalculatorLoader().load());
        return multiLevelGame;

    }

    /**
     * getter.
     *
     * @return The MultiLevelGame.
     */
    @Override
    public MultiLevelGame getGame() {
        return this.multiLevelGame;
    }

    /**
     * Creates and starts a JPac-Man game.
     */
    @Override
    public void launch() {
        multiLevelGame = makeGame();
        PacManUiBuilder builder = new PacManUiBuilder().withDefaultButtons();
        addSinglePlayerKeys(builder);
        builder.build(getGame()).start();
    }

    /**
     * standard getter.
     *
     * @return all levels.
     */
    public List<Level> getLevels() {
        return this.levels;
    }



    /**
     * @param numLevels set the number of levels to create.
     */
    public void setNumLevels(int numLevels) {
        this.numLevels = numLevels - 1;
    }

    /**
     * Used for testing.
     * used to make a multi level game that is easy to win/lose.
     *
     * @param args args.
     */
    public static void main(String[] args) {

        MultiLevelLauncher multiLevelLauncher = new MultiLevelLauncher();
        multiLevelLauncher.withMapFile(multiLevelLauncher.levelMap);
        multiLevelLauncher.launch();

    }


}
