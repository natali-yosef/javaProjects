package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

public class GraphicLifeCounter extends GameObject {
    private final Counter liveCounter;
    private final GameObjectCollection gameObjects;
    private final int numOfLives;
    private GameObject[] heartsArray;
    private int lastLifeNum;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     */
    public GraphicLifeCounter(Vector2 topLeftCorner,
                              Vector2 dimensions,
                              Counter livesCounter,
                              Renderable renderable,
                              GameObjectCollection gameObjects,
                              int numOfLives) {
        super(topLeftCorner, dimensions, renderable);
        this.liveCounter = livesCounter;
        this.gameObjects = gameObjects;
        this.numOfLives = numOfLives;
        this.heartsArray = new GameObject[numOfLives+1];
        this.lastLifeNum = numOfLives;
        createGraphicLife(topLeftCorner, dimensions,renderable);
    }

    /**
     * private method helps to create the graphic life
     * @param topLeftCorner - top left corner of the graphic life
     * @param dimensions - dimensions for one life
     * @param renderable - image render
     */
    private void createGraphicLife(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable) {

        for (int life = 0; life < numOfLives; life++) {
            Vector2 newLocation = new Vector2(topLeftCorner.x(), topLeftCorner.y() - (life * (5 + dimensions.y())));
            GameObject heart = new GameObject(Vector2.ZERO, dimensions, renderable);
            heart.setTopLeftCorner(newLocation);
            heartsArray[life+1] = heart;
            gameObjects.addGameObject(heart, Layer.BACKGROUND);
        }
    }


    /**
     * override update in class danogl.GameObject
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (liveCounter.value() != lastLifeNum){
            gameObjects.removeGameObject(heartsArray[liveCounter.value()+1], Layer.BACKGROUND);
            lastLifeNum = liveCounter.value();
        }


    }
}
