package src.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.MockPaddle;
import src.gameobjects.Paddle;

/**
 * Concrete class extending abstract RemoveBrickStrategyDecorator. Introduces extra paddle to game
 * window which remains until colliding NUM_COLLISION_TO_DISAPPEAR with other game objects.
 */
public class AddPaddleStrategy extends RemoveBrickStrategyDecorator {
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final int MIN_DISTANCE_SCREEN_EDGE = 20;
    private static final int NUM_COLLISION_TO_DISAPPEAR = 3;
    private static final Vector2 PADDLE_SIZE =  new Vector2(100, 15);
    private static Vector2 PADDLE_PLACE = null;


    private final ImageReader imageReader;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;


    public AddPaddleStrategy(CollisionStrategy toBeDecorated,
                             ImageReader imageReader,
                             UserInputListener inputListener,
                             Vector2 windowDimensions) {
        super(toBeDecorated);
        this.imageReader = imageReader;
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        if(PADDLE_PLACE == null){
            PADDLE_PLACE = new Vector2(windowDimensions.x() / 2, windowDimensions.y() / 2);
        }
    }

    /**
     * Adds additional paddle to game and delegates to held object.
     * @param thisObj  this game object.
     * @param otherObj - other game object that collide
     * @param counter - num of bricks in the game
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {
        super.onCollision(thisObj, otherObj, counter);
        if(!MockPaddle.isInstantiated) {
            Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);
            MockPaddle mockPaddle = new MockPaddle(PADDLE_PLACE,
                    PADDLE_SIZE,
                    paddleImage,
                    inputListener,
                    windowDimensions, getGameObjectCollection(),
                    MIN_DISTANCE_SCREEN_EDGE, NUM_COLLISION_TO_DISAPPEAR);
            getGameObjectCollection().addGameObject(mockPaddle);
        }

    }

    /**
     * return held reference to global game object.
     * @return game objects
     */
    @Override
    public GameObjectCollection getGameObjectCollection() {
        return super.getGameObjectCollection();
    }
}
