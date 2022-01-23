package src.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.*;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import src.BrickerGameManager;

import java.util.Random;

/**
 * Factory class for creating Collision strategies
 */
public class BrickStrategyFactory {
    public static Sound ballSound = null;
    public static Renderable puckImage = null;
    private static final int NUM_STRATEGY = 6;
    private static final int DOUBLE_STRATEGY_INDEX = 5;
    private static final String COLLISION_SOUND_PATH = "assets/blop_cut_silenced.wav";
    private static final String PUCK_IMAGE_PATH = "assets/mockBall.png";
//    private final CollisionStrategy[] strategiesArray;
    private final GameObjectCollection gameObjects;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final BrickerGameManager gameManager;
    private final UserInputListener inputListener;
    private final WindowController windowController;
    private final Vector2 windowDimensions;


    public BrickStrategyFactory(GameObjectCollection gameObjects,
                                BrickerGameManager gameManager,
                                ImageReader imageReader,
                                SoundReader soundReader,
                                UserInputListener inputListener,
                                WindowController windowController,
                                Vector2 windowDimensions) {
        this.gameObjects = gameObjects;
        this.gameManager = gameManager;
        this.inputListener = inputListener;
        this.windowController = windowController;
        this.windowDimensions = windowDimensions;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        if (ballSound == null) {
            ballSound = soundReader.readSound(COLLISION_SOUND_PATH);
        }
        if (puckImage == null) {
            puckImage = imageReader.readImage(PUCK_IMAGE_PATH, true);
        }





//        strategiesArray = new CollisionStrategy[NUM_STRATEGY];
//        strategiesArray[0] = new RemoveBrickStrategy(gameObjects);
//        strategiesArray[1] = new AddPaddleStrategy(strategiesArray[0],
//                imageReader,
//                inputListener,
//                windowDimensions);
//        strategiesArray[2] = new PuckStrategy(strategiesArray[0],
//                imageReader,
//                soundReader);
//        strategiesArray[3] = new ChangeCameraStrategy(strategiesArray[0],
//                windowController,
//                gameManager);
//        strategiesArray[4] = new SetDimensionsPaddleStrategy(strategiesArray[0],
//                imageReader,
//                windowDimensions);

    }

    private CollisionStrategy decorateStrategy(int numStrategy, CollisionStrategy toDecorate){
        if(toDecorate == null){
            toDecorate = new RemoveBrickStrategy(gameObjects);
        }
        switch (numStrategy){
            case 0:
                return new RemoveBrickStrategy(gameObjects);
            case 1:
                return new AddPaddleStrategy(toDecorate,
                        imageReader,
                        inputListener,
                        windowDimensions);
            case 2:
                return new PuckStrategy(toDecorate,
                        imageReader,
                        soundReader);
            case 3:
                return new ChangeCameraStrategy(toDecorate,
                        windowController,
                        gameManager);
            case 4:
                return new SetDimensionsPaddleStrategy(toDecorate,
                        imageReader,
                        windowDimensions);
        }
        return null;
    }

//    /**
//     * method randomly selects between 5 strategies and returns one CollisionStrategy object which
//     * is a RemoveBrickStrategy decorated by one of the decorator strategies, or decorated by two randomly
//     * selected strategies, or decorated by one of the decorator strategies and a pair of additional two
//     * decorator strategies.
//     * @return CollisionStrategy object.
//     */
//    public CollisionStrategy getStrategy() {
//
//        int random;
//        random = new Random().nextInt(NUM_STRATEGY);
//
//        // if not double behavior
//        if (random != DOUBLE_STRATEGY_INDEX) {
//            return strategiesArray[random];
//        }
//
//        // if the first is double behavior so random the first
//        random = new Random().nextInt(NUM_STRATEGY-1) +1;
//
//        // if double again
//        if(random == DOUBLE_STRATEGY_INDEX){
//            // random three behavior different from double
//            random = new Random().nextInt(NUM_STRATEGY-2)+1;
//            int secondRandom = new Random().nextInt(NUM_STRATEGY-2) +1;
//            int thirdRandom = new Random().nextInt(NUM_STRATEGY-2) + 1;
//            return new DoubleStrategies(gameObjects,strategiesArray[random],
//                    strategiesArray[secondRandom],
//                    strategiesArray[thirdRandom]);
//        }
//        // random second behavior
//        int secondRandom = new Random().nextInt(NUM_STRATEGY-1) + 1;
//
//        // if the second is double
//        if(secondRandom == DOUBLE_STRATEGY_INDEX){
//            // random two more behaviors
//            secondRandom = new Random().nextInt(NUM_STRATEGY-2) + 1;
//            int thirdRandom = new Random().nextInt(NUM_STRATEGY-2) + 1;
//            return new DoubleStrategies(gameObjects,strategiesArray[random],
//                    strategiesArray[secondRandom],
//                    strategiesArray[thirdRandom]);
//        }
//
//        return new DoubleStrategies(gameObjects,
//                strategiesArray[random],
//                strategiesArray[secondRandom],
//                null);
//    }

    public CollisionStrategy getStrategy() {
        int firstRandom, secondRandom, thirdRandom ;
        CollisionStrategy firstStrategy, secondStrategy;
        // random 1/6
        firstRandom = new Random().nextInt(6);

        // if not double, done
        if(firstRandom != 5){
            return decorateStrategy(firstRandom,null);
        }

        // if double, random the first
        firstRandom = new Random().nextInt(5) + 1;
        // if not double
        if(firstRandom != 5){
            firstStrategy = decorateStrategy(firstRandom,null);
            secondRandom =  new Random().nextInt(5) + 1;
            // if second not double
            if(secondRandom != 5){
                return decorateStrategy(secondRandom, firstStrategy);
            }
            // if second double
            secondRandom =  new Random().nextInt(4) + 1;
            thirdRandom = new Random().nextInt(4) + 1;
            secondStrategy = decorateStrategy(secondRandom,firstStrategy);
            return decorateStrategy(thirdRandom, secondStrategy);
        }
        // if first double
        firstRandom = new Random().nextInt(4) + 1;
        secondRandom =  new Random().nextInt(4) + 1;
        thirdRandom = new Random().nextInt(4) + 1;
        firstStrategy = decorateStrategy(firstRandom,null);
        secondStrategy = decorateStrategy(secondRandom,firstStrategy);
        return decorateStrategy(thirdRandom, secondStrategy);
    }

}
