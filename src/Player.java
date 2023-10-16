import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Image implements Moveable, Collidable {
    private int xSpeed, ySpeed;

    public Player(int x, int y, int width, int height/* , PImage sprite, PApplet app */) {
        super(x, y, width, height/* , sprite, app */);
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    public void act(PApplet app) {
        super.act(app);
        this.move();
    }

    public void move() {
        this.x += this.xSpeed;
        this.y += this.ySpeed;
    }

    public boolean collidesWith(List<Collidable> other) {
        return false;
    }

    public void onCollision() {
        return;
    }
}
