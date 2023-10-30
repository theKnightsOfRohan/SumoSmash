import processing.core.PApplet;

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
