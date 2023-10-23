import java.util.List;

import processing.core.PApplet;

public class Settings {
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 1000;
    public static final int GRAVITY = 1;
    public static final int PLATFORM_START_X = 200;
    public static final int PLATFORM_START_Y = 700;
    public static final int PLATFORM_WIDTH = 600;
    public static final int PLATFORM_HEIGHT = 300;
    public static final int PLAYER_1_START_X = 200;
    public static final int PLAYER_1_START_Y = 200;
    public static final int PLAYER_2_START_X = 800;
    public static final int PLAYER_2_START_Y = 200;
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
    public int getX();

    /**
     * This method returns the y-coordinate of the drawable object.
     *
     * @return the y-coordinate of the object
     */
    public int getY();

    /**
     * This method returns the width of the drawable object.
     *
     * @return the width of the object
     */
    public int getWidth();

    /**
     * This method returns the height of the drawable object.
     *
     * @return the height of the object
     */
    public int getHeight();
}

/**
 * This interface represents a Moveable object that can move within the game.
 */
interface Moveable extends Collidable {
    /**
     * Moves the Moveable object within the game.
     *
     * @param app the PApplet object representing the game
     */
    public void move(PApplet app);

    public float getXSpeed();

    public float getYSpeed();

    public void setXSpeed(float xSpeed);

    public void setYSpeed(float ySpeed);
}

/**
 * An interface that represents an object that can be collided with. Extends the
 * Drawable interface.
 */
interface Collidable extends Drawable {
    public void onCollision(CollisionInfo info);
}
