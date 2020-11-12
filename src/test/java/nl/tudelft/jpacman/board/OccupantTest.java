package nl.tudelft.jpacman.board;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test suite to confirm that {@link Unit}s correctly (de)occupy squares.
 *
 * @author Jeroen Roosen
 */
class OccupantTest {

    /**
     * The unit under test.
     */
    private Unit unit;

    /**
     * Resets the unit under test.
     */
    @BeforeEach
    void setUp() {
        unit = new BasicUnit();
    }

    /**
     * Asserts that a unit has no square to start with.
     */
    @Test
    void noStartSquare() {
        //in the getSquare method it asserts for null.
        assertThat(unit.hasSquare()).isFalse();
    }

    /**
     * Tests that the unit indeed has the target square as its base after
     * occupation.
     */
    @Test
    void testOccupy() {
        Square target = new BasicSquare();
        unit.occupy(target);

        assertThat(unit.getSquare().equals(target)).isTrue();
        assertThat(target.getOccupants().contains(unit)).isTrue();
    }

    /**
     * Test that the unit indeed has the target square as its base after
     * double occupation.
     */
    @Test
    void testReoccupy() {
        Square target1 = new BasicSquare();
        Square target2 = new BasicSquare();

        //occupy once
        unit.occupy(target1);
        //occupy by another square
        unit.occupy(target2);

        assertThat(unit.getSquare().equals(target1)).isFalse();
        assertThat(target1.getOccupants().contains(unit)).isFalse();

        assertThat(unit.getSquare().equals(target2)).isTrue();
        assertThat(target2.getOccupants().contains(unit)).isTrue();

        //reoccupy by target1
        unit.occupy(target1);
        assertThat(unit.getSquare().equals(target1)).isTrue();
    }
}
