package src.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * Display a graphic object on the game window showing a numeric count of lives left.
 */
public class NumericLifeCounter extends GameObject {
    private final Counter liveCounter;
    private final GameObjectCollection gameObjectCollection;
    private int lastLifeNum;
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private GameObject labelObj;

    /**
     * Construct a new GameObject instance.
     * @param livesCounter global lives counter of game.
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param gameObjectCollection- global game object collection
     */
    public NumericLifeCounter(Counter livesCounter,
                              Vector2 topLeftCorner,
                              Vector2 dimensions,
                              GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions,new TextRenderable(""));
        this.liveCounter = livesCounter;
        this.gameObjectCollection = gameObjectCollection;
        this.lastLifeNum = livesCounter.value();
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.labelObj = null;
        createNumericLifeCounter(livesCounter.value());
    }

    /**
     * override update in class danogl.GameObject
     * @param deltaTime
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (liveCounter.value() != lastLifeNum){
            createNumericLifeCounter(liveCounter.value());
            lastLifeNum = liveCounter.value();
        }
    }

    /**
     * private method that helps to create numeric life counter
     * @param value - num of life
     */
    private void createNumericLifeCounter(int value) {
        if(labelObj != null){
            gameObjectCollection.removeGameObject(labelObj, Layer.BACKGROUND);
        }
        Renderable label = new TextRenderable(String.format("life left: %d",value));
        labelObj = new GameObject(topLeftCorner,dimensions,label);
        gameObjectCollection.addGameObject(labelObj, Layer.BACKGROUND);
    }
}
