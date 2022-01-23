package src;

import danogl.collisions.GameObjectCollection;
import danogl.components.CoordinateSpace;
import danogl.components.GameObjectPhysics;
import src.brick_strategies.BrickStrategyFactory;
import src.brick_strategies.CollisionStrategy;
import danogl.util.Counter;
import src.brick_strategies.RemoveBrickStrategy;
import src.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.*;
import danogl.util.Vector2;
import danogl.gui.rendering.Renderable;

import java.util.Random;

/**
 * this class is responsible for game initialization, holding references for game objects and calling
 * update methods for every update iteration. Entry point for code should be in a main method in this class
 */
public class BrickerGameManager extends GameManager {


    private static final int BORDER_WIDTH = 10;
    private static final String BALL_IMAGE_PATH = "assets/ball.png";
    private static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";
    private static final String PADDLE_IMAGE_PATH = "assets/paddle.png";
    private static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final float BALL_SPEED = 250;
    private static final String BRICK_IMAGE_PATH = "assets/brick.png";
    private static final int NUM_ROWS = 5;
    private static final int NUM_COLS = 8;
    private static final float HEIGHT_BRICK = 20;
    private static final int EXTRA_FROM_BORDERS = 5;
    private static final int EXTRA_FROM_BRICKS = 5;
    private static final int NUM_LIVES = 4;
    private static final String HEART_IMAGE_PATH = "assets/heart.png";
    private Vector2 windowDimensions;
    private WindowController windowController;
    private GameObject ball;
    private Counter liveLeft;
    private ImageReader imageReader;
    private SoundReader soundReader;
    private Counter numOfBricksInGame;
    private UserInputListener inputListener;


    /**
     * constructor for BrickerGameManager
     * @param windowTitle - window title
     * @param windowDimensions - pixel dimensions for game window height x width
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        super(windowTitle, windowDimensions);
    }

    /**
     * game initializer
     * @param imageReader - image render
     * @param soundReader - sound render
     * @param inputListener - user input render
     * @param windowController -
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {

        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.windowDimensions = windowController.getWindowDimensions();
        this.liveLeft = new Counter(NUM_LIVES);
        this.numOfBricksInGame = new Counter(0);

        createBorder(Vector2.ZERO, new Vector2(BORDER_WIDTH, windowDimensions.y()));
        createBorder(new Vector2(windowDimensions.x(), 0), new Vector2(BORDER_WIDTH, windowDimensions.y()));
        createBorder(Vector2.ZERO, new Vector2(windowDimensions.x(), BORDER_WIDTH));

        GameObject background = new GameObject(Vector2.ZERO, windowController.getWindowDimensions(),
                imageReader.readImage(BACKGROUND_PATH, false));
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);

        gameObjects().addGameObject(background, Layer.BACKGROUND);


        createBall();

        createBricks();

        createPaddle();

        Renderable heartImage = imageReader.readImage(HEART_IMAGE_PATH, true);
        GameObject graphicLifeCounter = new GraphicLifeCounter(new Vector2(BORDER_WIDTH,
                windowDimensions.y() - (BORDER_WIDTH * 5)),
                new Vector2(40, 40),
                liveLeft,
                heartImage,
                gameObjects(),
                NUM_LIVES);

        gameObjects().addGameObject(graphicLifeCounter);

    }

    /**
     * creating the paddle in the game
     */
    private void createPaddle() {

        Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE_PATH, true);

        GameObject paddle = new Paddle(Vector2.ZERO,
                new Vector2(100, 15),
                paddleImage,
                inputListener,
                windowDimensions, BORDER_WIDTH);

        paddle.setCenter(new Vector2(windowDimensions.x() / 2, windowDimensions.y() - 30));
        gameObjects().addGameObject(paddle);

    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // remove from the gameObject the pucks that no longer in the game
        for (GameObject gameObject : gameObjects()) {
            if (gameObject instanceof Puck && (windowDimensions.y() < gameObject.getCenter().y())) {
                gameObjects().removeGameObject(gameObject);

            }
        }
        endedGame();

    }

    /**
     * checking if the game is over
     */
    private void endedGame() {
        String prompt = "";
        if (numOfBricksInGame.value() == 0) {
            prompt = "you won! play again?";
            if (windowController.openYesNoDialog(prompt)) {
                windowController.resetGame();
            } else {
                windowController.closeWindow();
            }
        } else {
            if (windowDimensions.y() < ball.getCenter().y()) {
                if (liveLeft.value() == 1) {
                    prompt = "you lost! play again?";
                    if (windowController.openYesNoDialog(prompt)) {
                        windowController.resetGame();
                    } else {
                        windowController.closeWindow();
                    }
                } else {
                    liveLeft.decrement();
                    ball.setCenter(windowDimensions.mult(0.5F));
                }
            }
        }


    }

    /**
     * private method for creating the borders
     *
     * @param place      - the top left corner of the border
     * @param dimensions - the bord dimensions
     */
    private void createBorder(Vector2 place, Vector2 dimensions) {

        GameObject border = new GameObject(place, dimensions, null);
        gameObjects().addGameObject(border);

    }

    /**
     * private method for creating the ball
     */
    private void createBall() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random rand = new Random();

        Renderable ballImage = imageReader.readImage(BALL_IMAGE_PATH, true);

        Sound collisionSound = soundReader.readSound(COLLISION_SOUND_PATH);

        GameObject ball = new Ball(windowDimensions.mult(0.5F),
                new Vector2(20, 20),
                ballImage,
                collisionSound);

        // randomly choose the ball velocity
        if (rand.nextBoolean()) {
            ballVelX *= -1;
        }
        if (rand.nextBoolean()) {
            ballVelY *= -1;
        }

        ball.setVelocity(new Vector2(ballVelX, ballVelY));

        gameObjects().addGameObject(ball);

        this.ball = ball;

    }


    /**
     * creating the bricks in the game
     */
    private void createBricks() {

        float lenBrick, heightBrick;

        Renderable brickImage = imageReader.readImage(BRICK_IMAGE_PATH, true);

        // reload the strategy factory
        BrickStrategyFactory strategyFactory = new BrickStrategyFactory(gameObjects(),
                this,
                imageReader,
                soundReader,
                inputListener,
                windowController,
                windowDimensions);

        // calculate the brick len based on the window dimensions
        lenBrick = (windowDimensions.x() - (2 * BORDER_WIDTH) - (2 * EXTRA_FROM_BORDERS) -
                (NUM_COLS * EXTRA_FROM_BRICKS)) / NUM_COLS;

        heightBrick = HEIGHT_BRICK;

        Vector2 brickDimensions = new Vector2(lenBrick, heightBrick);

        for (int row = 0; row < NUM_ROWS; row++) {

            for (int col = 0; col < NUM_COLS; col++) {

                // getting random strategy
                GameObject brick = new Brick(Vector2.ZERO,
                        brickDimensions,
                        brickImage,
                        strategyFactory.getStrategy(),
                        numOfBricksInGame);

                float xTopCorner = BORDER_WIDTH + EXTRA_FROM_BORDERS + (col * (lenBrick + EXTRA_FROM_BRICKS));
                float yTopCorner = BORDER_WIDTH + EXTRA_FROM_BORDERS +
                        (row * (heightBrick + EXTRA_FROM_BRICKS));

                brick.setTopLeftCorner(new Vector2(xTopCorner, yTopCorner));

                gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);

                numOfBricksInGame.increment();
            }

        }

    }

    public static void main(String[] args) {
        new BrickerGameManager("Bricker", new Vector2(700, 500)).run();
    }


}
