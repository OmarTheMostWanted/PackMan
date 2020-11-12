package nl.tudelft.jpacman.level;

/**
 * create an instance of PlayerCollisions to be tested.
 */
public class PlayerCollisionsTest extends CollisionsTesting {


    @Override
    public CollisionMap getCollisionMap() {
        return new PlayerCollisions(getPointCalculator());
    }
}
