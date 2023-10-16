import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Image implements Moveable, Shovable {
    private int xSpeed, ySpeed;

    public Player(int x, int y, int width, int height/* , PImage sprite, PApplet app */) {
        super(x, y, width, height/* , sprite, app */);
        this.xSpeed = 0;
        this.ySpeed = 0;
    }

    public void act(Main app) {
        this.move(app);
        super.act(app);
    }

    public void move(Main app) {
        this.x += this.xSpeed;
        this.y += this.ySpeed;
        this.ySpeed += Settings.GRAVITY;

        this.onCollision(this.collidesWith(app.immovables));
    }

    public Collidable collidesWith(List<Collidable> others) {
        for (Collidable block : others) {
            if (this.x + this.width > ((Drawable) block).getX() && this.x < ((Drawable) block).getX() + ((Drawable) block).getWidth()
                    && this.y + this.height > ((Drawable) block).getY() && this.y < ((Drawable) block).getY() + ((Drawable) block).getHeight()) {
                return block;
            }
        }

        return null;
    }

    public void onCollision(Collidable other) {
        if (other == null)
            return;

        this.ySpeed = 0;
        this.y = ((Drawable) other).getY() - this.height;
    }
}
