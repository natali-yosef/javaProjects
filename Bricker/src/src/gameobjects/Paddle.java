package src.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

/**
 * One of the main game objects. Repels the ball against the bricks.
 */
public class Paddle extends GameObject {
    private static final float MOVEMENT_SPEED = 500;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;
    private int minDistanceFromEdge;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     * @param inputListener - user input render
     * @param windowDimensions - pixel dimensions for game window height x width
     * @param minDistanceFromEdge -  border for paddle movement
     */
    public Paddle(Vector2 topLeftCorner,
                  Vector2 dimensions,
                  Renderable renderable,
                  UserInputListener inputListener,
                  Vector2 windowDimensions,
                  int minDistanceFromEdge) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.minDistanceFromEdge = minDistanceFromEdge;
    }

    /**
     * override from update in class danogl.GameObject
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float checkRightEdge;
        Vector2 topLeftCorner;
        Vector2 movement = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movement = movement.add(Vector2.LEFT);
        }
        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movement = movement.add(Vector2.RIGHT);
        }
        setVelocity(movement.mult(MOVEMENT_SPEED));

        topLeftCorner = getTopLeftCorner();
        checkRightEdge = windowDimensions.x() - minDistanceFromEdge - getDimensions().x();

        if (topLeftCorner.x() < minDistanceFromEdge) {
            setTopLeftCorner(new Vector2(minDistanceFromEdge,topLeftCorner.y()));

        }
        if (checkRightEdge < topLeftCorner.x()) {
            setTopLeftCorner(new Vector2(checkRightEdge, topLeftCorner.y()));

        }

    }
}
