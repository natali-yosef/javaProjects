package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

public class SetDimensionsStatus extends StatusMaker {

    private static final int SMALLEST_PADDLE_X = 10 ;

    public enum DimensionsOptions {WIDE, NARROW}

    private final DimensionsOptions dimensionsOption;
    private final Vector2 windowDimensions;



    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public SetDimensionsStatus(Vector2 topLeftCorner,
                               Vector2 dimensions,
                               Renderable renderable,
                               DimensionsOptions dimensionsOption,
                               GameObjectCollection gameObjects,
                               Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable, gameObjects, windowDimensions);
        this.dimensionsOption = dimensionsOption;
        this.windowDimensions = windowDimensions;

    }

    /**
     * override onCollisionEnter from StatusMaker
     *
     * @param other     -other GameObject instance participating in collision.
     * @param collision - Collision object.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 oldDimensions = other.getDimensions();

        if (other instanceof Paddle && dimensionsOption.equals(DimensionsOptions.WIDE)) {
            Vector2 newDimensions = new Vector2(oldDimensions.x() * 2, oldDimensions.y());
            if (newDimensions.x() <= windowDimensions.x()) {
                other.setDimensions(newDimensions);
            }
        }
        if (other instanceof Paddle && dimensionsOption.equals(DimensionsOptions.NARROW)) {
            Vector2 newDimensions = new Vector2(oldDimensions.x() * 0.5F, oldDimensions.y());
            if(SMALLEST_PADDLE_X <= newDimensions.x()){
                other.setDimensions(newDimensions);
            }
        }

    }

}
