import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Image implements Moveable, Collidable {
    public Player(int x, int y, int width, int height, PImage sprite, PApplet app) {
        super(x, y, width, height, sprite, app);
    }

    public void draw(PApplet app) {
        super.draw(app);
    }

    public void move() {
        return;
    }

    public boolean collidesWith(List<Collidable> other) {
        return false;
    }

    public void onCollision() {
        return;
    }
}
