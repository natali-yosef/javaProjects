package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Ball is the main game object. It's positioned in game window as part of game initialization and
 * given initial velocity.
 * On collision, it's velocity is updated to be reflected about the normal vector of
 * the surface it collides with.
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private final Counter collisionCounter;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = new Counter(0);
    }

    /**
     * On collision, object velocity is reflected
     * about the normal vector of the surface it collides with.
     * @param other -other GameObject instance participating in collision.
     * @param collision - Collision object.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(!(other instanceof Ball)) {
            super.onCollisionEnter(other, collision);
            setVelocity(getVelocity().flipped(collision.getNormal()));
            collisionSound.play();
            collisionCounter.increment();
        }

    }

    /**
     * Ball object maintains a counter which keeps count of collisions from start of game.
     * This getter method allows access to the collision count in case any behavior should need to
     * be based on number of ball collisions.
     * @return - number of times ball collided with an object since start of game.
     */
    public int getCollisionCount(){
        return collisionCounter.value();
    }
}
