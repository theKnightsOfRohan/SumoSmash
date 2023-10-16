import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

public class Platform extends Image implements Collidable {
    public Platform(int x, int y, int width, int height/* , PImage sprite, PApplet app */) {
        super(x, y, width, height/* , sprite, app */);
    }

    public void act(PApplet app) {
        super.act(app);
    }

    public boolean collidesWith(List<Collidable> other) {
        return false;
    }

    public void onCollision() {
        return;
    }
}
