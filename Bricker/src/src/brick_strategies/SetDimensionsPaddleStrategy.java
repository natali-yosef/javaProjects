package src.brick_strategies;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.rendering.ImageRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import src.gameobjects.SetDimensionsStatus;

import java.awt.*;
import java.util.Random;

/**
 * introduce status that wide or narrow the paddle
 */
public class SetDimensionsPaddleStrategy extends RemoveBrickStrategyDecorator {
    private static final String WIDE_IMAGE_PATH = "assets/buffWiden.png";
    private static final String NARROW_IMAGE_PATH = "assets/buffNarrow.png";
    private static final float SPEED_STATUS = 250;
    private final ImageReader imageReader;
    private final Vector2 windowDimensions;

    public SetDimensionsPaddleStrategy(CollisionStrategy collisionStrategy,
                                       ImageReader imageReader,
                                       Vector2 windowDimensions) {
        super(collisionStrategy);

        this.imageReader = imageReader;
        this.windowDimensions = windowDimensions;

    }

    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj, Counter counter) {

        super.onCollision(thisObj, otherObj, counter);

        boolean rand = new Random().nextBoolean();
        SetDimensionsStatus dimensionsStatus;
        // choosing randomly wide or narrow
        if(rand){
            Renderable imageSetDimensions = imageReader.readImage(WIDE_IMAGE_PATH,true);
            dimensionsStatus = new SetDimensionsStatus(Vector2.ZERO,
                                    thisObj.getDimensions(),
                                    imageSetDimensions,
                                    SetDimensionsStatus.DimensionsOptions.WIDE,
                                    getGameObjectCollection(),windowDimensions);
        }
        else {
            Renderable imageSetDimensions =imageReader.readImage(NARROW_IMAGE_PATH,true);
            dimensionsStatus = new SetDimensionsStatus(Vector2.ZERO,
                                    thisObj.getDimensions(),
                                    imageSetDimensions,
                                    SetDimensionsStatus.DimensionsOptions.NARROW,
                                    getGameObjectCollection(),windowDimensions);
        }

        dimensionsStatus.setCenter(thisObj.getCenter());

        dimensionsStatus.setVelocity(Vector2.DOWN.mult(SPEED_STATUS));

        getGameObjectCollection().addGameObject(dimensionsStatus);


    }
}
