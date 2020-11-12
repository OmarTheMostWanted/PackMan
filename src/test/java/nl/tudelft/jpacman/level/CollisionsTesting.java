package nl.tudelft.jpacman.level;

import nl.tudelft.jpacman.board.Unit;
import nl.tudelft.jpacman.npc.Ghost;
import nl.tudelft.jpacman.points.PointCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;


/**
 * test PlayerCollisions Class.
 */
abstract class CollisionsTesting {


    private CollisionMap collisionMap;

    private Player player;
    private Ghost ghost;
    private Pellet pellet;

    private PointCalculator pointCalculator;


    /**
     * get collisionMap.
     * @return the collision  Map.
     */
    public abstract CollisionMap getCollisionMap();

    /**
     * set up new mocked unit objects to test collisions.
     */
    @BeforeEach
    void setUp() {
        pointCalculator = mock(PointCalculator.class);
        player = mock(Player.class);
        ghost = mock(Ghost.class);
        pellet = mock(Pellet.class);
        collisionMap = getCollisionMap();
    }

    /**
     * player consumes a pellet.
     */
    @Test
    void collidePlayerAndPlayer() {

        Player player1 = mock(Player.class);
        collisionMap.collide(player, player1);
        verify(player, Mockito.times(0)).setAlive(anyBoolean());

    }

    /**
     * player gets killed by a ghost.
     */
    @Test
    void collidePlayerAndGhost() {

        collisionMap.collide(player, ghost);

        //verify(collisionMap., times(1)).collidedWithAGhost(player, ghost);
        verify(player, times(1)).setAlive(false);
        verify(player, times(1)).setKiller(ghost);
    }

    /**
     * test when a player consumes a pellets.
     */
    @Test
    void collidePlayerAndPellet() {
        collisionMap.collide(player, pellet);
        //verify(pointCalculator, times(1)).consumedAPellet(player, pellet);
        verify(pellet, times(1)).leaveSquare();

    }

    /**
     * test when a ghost collide with a player. player dies.
     */
    @Test
    void collideGhostAndPlayer() {
        collisionMap.collide(ghost, player);
        //verify(pointCalculator, times(1)).collidedWithAGhost(player, ghost);
        verify(player, times(1)).setAlive(false);
        verify(player, times(1)).setKiller(ghost);
    }

    /**
     * test when a pellet and a player collide.
     */
    @Test
    void collidePelletAndPlayer() {
        collisionMap.collide(pellet, player);
        //verify(pointCalculator, times(1)).consumedAPellet(player, pellet);
        verify(pellet, times(1)).leaveSquare();

    }

    /**
     * test when a ghost and a pellet collide, nothing should happen.
     */
    @Test
    void collidePelletAndGhost() {
        collisionMap.collide(pellet, ghost);

        verify(pellet, times(0)).leaveSquare();

    }

    /**
     * pellet on pellet, nothing should happen.
     */
    @Test
    void collidePelletAndPellet() {
        Pellet pellet1 = mock(Pellet.class);
        collisionMap.collide(pellet, pellet1);
        verify(pellet, times(0)).leaveSquare();
    }

    /**
     * ghost on ghost nothing should happen.
     */
    @Test
    void collideGhostAndGhost() {
        Ghost ghost1 = mock(Ghost.class);
        collisionMap.collide(ghost, ghost1);
        //verify(pointCalculator, times(0)).collidedWithAGhost(any(), any());
        verify(player, Mockito.times(0)).setAlive(anyBoolean());
    }

    /**
     * Unit on pellet, nothing should happen.
     */
    @Test
    void collideUnitAndPellet() {
        Unit unit = Mockito.mock(Unit.class);
        collisionMap.collide(unit, pellet);
        verify(pellet, times(0)).leaveSquare();
    }

    /**
     * Unit on player, nothing should happen.
     */
    @Test
    void collideUnitAndPlayer() {
        Unit unit = Mockito.mock(Unit.class);
        collisionMap.collide(unit, player);
        verify(player, Mockito.times(0)).setAlive(anyBoolean());
    }

    /**
     * pointcalculator.
     * @return pointcal.
     */
    public PointCalculator getPointCalculator() {
        return pointCalculator;
    }

}
