package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * Abstract decorator to add functionality to the remove brick strategy, following the decorator pattern.
 * All strategy decorators should inherit from this class.
 */
public abstract class RemoveBrickStrategyDecorator implements CollisionStrategy {
    private final CollisionStrategy toBeDecorated;

    /**
     * Constructor
     * @param toBeDecorated - Collision strategy object to be decorated.
     */
    public RemoveBrickStrategyDecorator(CollisionStrategy toBeDecorated){

        this.toBeDecorated = toBeDecorated;
    }

    /**
     * Should delegate to held Collision strategy object.
     * @param thisObj  this game object.
     * @param otherObj - other game object that collide
     * @param counter - num of bricks in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        toBeDecorated.onCollision(thisObj,otherObj,counter);
    }

    /**
     * return held reference to global game object.
     * Delegate to held object to be decorated
     * @return game objects
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return toBeDecorated.getGameObjectCollection();
    }
}
