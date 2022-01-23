package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.BrickerGameManager;
import src.gameobjects.Ball;
import src.gameobjects.BallCollisionCountdownAgent;
import src.gameobjects.Puck;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Changes camera focus from ground to
 * ball until ball collides NUM_BALL_COLLISIONS_TO_TURN_OFF times.
 */
public class ChangeCameraStrategy extends RemoveBrickStrategyDecorator{

    private final WindowController windowController;
    private final BrickerGameManager gameManager;
    private BallCollisionCountdownAgent countBall;
    private static final int NUM_BALL_COLLISIONS_TO_TURN_OFF = 4;

    public ChangeCameraStrategy(CollisionStrategy toBeDecorated,
                                WindowController windowController,
                                BrickerGameManager gameManager) {
        super(toBeDecorated);
        this.windowController = windowController;
        this.gameManager = gameManager;
    }

    /**
     * Change camere position on collision and delegate to held CollisionStrategy.
     * @param thisObj  this game object.
     * @param otherObj - other game object that collide
     * @param counter - global brick counter.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if (gameManager.getCamera() == null && otherObj instanceof Ball && !(otherObj instanceof Puck)) {
            countBall = new BallCollisionCountdownAgent((Ball)otherObj,
                    this,NUM_BALL_COLLISIONS_TO_TURN_OFF);
            getGameObjectCollection().addGameObject(countBall);
            gameManager.setCamera(new Camera(otherObj,
                    Vector2.ZERO,
                    windowController.getWindowDimensions().mult(1.2F),
                    windowController.getWindowDimensions()));
        }
    }

    /**
     * Return camera to normal ground position.
     */
    public void turnOffCameraChange(){
        gameManager.setCamera(null);
        getGameObjectCollection().removeGameObject(countBall);

    }
}
