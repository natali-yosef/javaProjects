package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class MockPaddle extends Paddle{

    public static boolean isInstantiated;
    private final Counter countNumCollisions;
    private final GameObjectCollection gameObjectCollection;
    private int minDistanceFromEdge;
    private final int numCollisionsToDisappear;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     * @param inputListener - listener object for user input.
     * @param windowDimensions dimensions of game window.
     * @param gameObjectCollection the game object collection
     * @param minDistanceFromEdge  border for paddle movement.
     * @param numCollisionsToDisappear - the number of collision to disappear
     */
    public MockPaddle(Vector2 topLeftCorner,
                      Vector2 dimensions,
                      Renderable renderable,
                      UserInputListener inputListener,
                      Vector2 windowDimensions,
                      GameObjectCollection gameObjectCollection,
                      int minDistanceFromEdge,
                      int numCollisionsToDisappear) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions, minDistanceFromEdge);
        this.gameObjectCollection = gameObjectCollection;
        this.minDistanceFromEdge = minDistanceFromEdge;
        this.numCollisionsToDisappear = numCollisionsToDisappear;
        this.countNumCollisions = new Counter(0);
        isInstantiated = true;
    }

    /**
     * override onCollisionEnter in class danogl.GameObject
     * @param other -other GameObject instance participating in collision.
     * @param collision - Collision object.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        countNumCollisions.increment();
        if(countNumCollisions.value() == numCollisionsToDisappear){
            isInstantiated = false;
            gameObjectCollection.removeGameObject(this);
        }

    }
}
