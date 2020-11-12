package nl.tudelft.jpacman.level;


/**
 * create an instance of DefaultPlayerInteractionMap to be tested.
 */
class DefaultPlayerInteractionMapTest extends CollisionsTesting {
    @Override
    public CollisionMap getCollisionMap() {
        return new DefaultPlayerInteractionMap(getPointCalculator());
    }
}
