package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * Concrete brick strategy implmenting CollisionStrategy interface.
 * Removes holding brick on collision.
 */
public class RemoveBrickStrategy implements CollisionStrategy{
    private final GameObjectCollection gameObjects;

    public RemoveBrickStrategy(GameObjectCollection gameObjects) {
        this.gameObjects = gameObjects;
    }

    /**
     * Removes brick from game object collection on collision.
     * @param thisObj  this game object.
     * @param otherObj - other game object that collide
     * @param counter - num of bricks in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        if(gameObjects.removeGameObject(thisObj, Layer.STATIC_OBJECTS)) {
            counter.decrement();
        }
    }

    /**
     * All collision strategy objects should hold a reference to the
     * global game object collection and be able to return it.
     * @return game objects
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return gameObjects;
    }
}
