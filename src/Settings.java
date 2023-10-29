import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;

public class Settings {
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 1000;
    public static final int GRAVITY = 1;

    public static enum Stage {
        OPTIONS, STAGE_1
    };

}

interface GameState {
    public void draw(PApplet app);

    public void handleKey(PApplet app, boolean pressed);

    public void handleClick(int x, int y);
}

/**
 * This interface represents a drawable object that can be displayed on a
 * PApplet.
 */
interface Drawable {
    /**
     * This method is called to update the state of the drawable object.
     *
     * @param app the PApplet on which the object is being displayed
     */
    public void act(PApplet app);

    /**
     * This method returns the x-coordinate of the drawable object.
     *
     * @return the x-coordinate of the object
     */
    public float getX();

    /**
     * This method returns the y-coordinate of the drawable object.
     *
     * @return the y-coordinate of the object
     */
    public float getY();

    public void setX(float x);

    public void setY(float y);

    /**
     * This method returns the width of the drawable object.
     *
     * @return the width of the object
     */
    public float getWidth();

    /**
     * This method returns the height of the drawable object.
     *
     * @return the height of the object
     */
    public float getHeight();
}

/**
 * This interface represents a Moveable object that can move within the game.
 */
interface Moveable extends Collidable {
    public void move(PApplet app);

    public float getXSpeed();

    public float getYSpeed();

    public void setXSpeed(float xSpeed);

    public void setYSpeed(float ySpeed);

    public float getBounceFactor();
}

/**
 * An interface that represents an object that can be collided with. Extends the
 * Drawable interface.
 */
interface Collidable extends Drawable {
}
