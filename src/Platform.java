import processing.core.PApplet;
import processing.core.PImage;

public class Platform extends Image implements Collidable {
    public Platform(int x, int y, int width, int height, PImage sprite) {
        super(x, y, width, height , sprite);
    }

    public void act(PApplet app) {
        super.act(app);
        //app.fill(100);
        //app.stroke(0);
        //app.rect(x, y, width, height);
        //app.image(sprite, x, y);
    }

    public void onCollision(CollisionInfo info) {
        return;
    }
}
