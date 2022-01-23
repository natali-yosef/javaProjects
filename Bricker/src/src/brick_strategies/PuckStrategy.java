package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.Ball;
import src.gameobjects.Puck;

import java.util.Random;

/**
 * Introduces several pucks instead of brick once removed.
 */
public class PuckStrategy extends RemoveBrickStrategyDecorator{

    private static final float BALL_SPEED = 250;
    private static final int NUM_PUCK = 3;



    public PuckStrategy(CollisionStrategy toBeDecorated,
                        ImageReader imageReader,
                        SoundReader soundReader) {
        super(toBeDecorated);
    }

    /**
     * Add pucks to game on collision and delegate to held CollisionStrategy.
     * @param thisObj  this game object.
     * @param otherObj - other game object that collide
     * @param counter - num of bricks in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        createBalls(thisObj.getCenter());
    }

    /**
     * create puck balls for every collision
     * @param centerBrick - center of the brick
     */
    private void createBalls(Vector2 centerBrick) {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();

        Renderable ballImage = BrickStrategyFactory.puckImage;

        Sound collisionSound = BrickStrategyFactory.ballSound;

        for (int i = 0; i < NUM_PUCK; i++) {
            GameObject puck = new Puck(centerBrick,
                    new Vector2(20, 20),
                    ballImage,
                    collisionSound);

            if (rand.nextBoolean()) {
                ballVelX *= -1;
            }
            if (rand.nextBoolean()) {
                ballVelY *= -1;
            }

            puck.setVelocity(new Vector2(ballVelX-(i*5), ballVelY-(i*5)));

            getGameObjectCollection().addGameObject(puck);

        }
    }
}
