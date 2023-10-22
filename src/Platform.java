import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class represents a platform in the SumoSmash game. It extends the Image
 * class and implements the Collidable interface. A platform is a rectangular
 * object that can be collided with by other objects in the game. The act method
 * is responsible for updating the platform's position and rendering it on the
 * screen.
 *
 * @param x      The x-coordinate of the platform's top-left corner.
 * @param y      The y-coordinate of the platform's top-left corner.
 * @param width  The width of the platform.
 * @param height The height of the platform.
 */
public class Platform extends Image implements Collidable {
    public Platform(int x, int y, int width, int height/* , PImage sprite, PApplet app */) {
        super(x, y, width, height/* , sprite, app */);
    }

    public void act(PApplet app) {
        super.act(app);
        app.fill(100);
        app.stroke(0);
        app.rect(x, y, width, height);
    }

    public void onCollision(CollisionInfo info) {
        return;
    }
}
