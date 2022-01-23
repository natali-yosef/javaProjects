package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public abstract class StatusMaker extends GameObject {

    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the object. Can be null, in which case
     * @param gameObjects - game object collection
     * @param windowDimensions - window dimensions
     */
    public StatusMaker(Vector2 topLeftCorner,
                       Vector2 dimensions,
                       Renderable renderable,
                       GameObjectCollection gameObjects,
                       Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
    }

    /**
     * the status should collide only with the paddle
     * @param other - other GameObject instance participating in collision.
     * @return - true if need to collide and false otherwise
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other instanceof Paddle;
    }

    /**
     * override onCollisionEnter from GameObject
     * @param other -other GameObject instance participating in collision.
     * @param collision - Collision object.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        gameObjects.removeGameObject(this);


    }

    /**
     * override update in class danogl.GameObject
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (windowDimensions.y() < getCenter().y()) {
            gameObjects.removeGameObject(this);
        }
    }
}
