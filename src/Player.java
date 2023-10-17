import java.util.List;
import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Image implements Moveable, Shovable {
    private float xSpeed, ySpeed;
    private float chargeYSpeed, chargeYAcceleration, maxChargeYSpeed;

    public Player(int x, int y, int width, int height/* , PImage sprite, PApplet app */) {
        super(x, y, width, height/* , sprite, app */);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.chargeYSpeed = 0;
        this.chargeYAcceleration = -5;
        this.maxChargeYSpeed = -25;
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
            if (this.x + this.width > block.getX() && this.x < block.getX() + block.getWidth() && this.y + this.height > block.getY()
                    && this.y < block.getY() + block.getHeight()) {
                return block;
            }
        }

        return null;
    }

    public void onCollision(Collidable other) {
        if (other == null)
            return;

        this.ySpeed = 0;
        this.y = (other).getY() - this.height;
    }

    public void chargeJump() {
        if (this.chargeYSpeed > this.maxChargeYSpeed && this.ySpeed == 0)
            this.chargeYSpeed += this.chargeYAcceleration;
    }

    public void releaseJump() {
        this.ySpeed = this.chargeYSpeed;
        this.chargeYSpeed = 0;
    }

    public String toString() {
        return "x: " + this.x + ", y: " + this.y + ", xSpeed: " + this.xSpeed + ", ySpeed: " + this.ySpeed + ", chargeYSpeed: " + this.chargeYSpeed;
    }
}
