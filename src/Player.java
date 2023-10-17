import java.util.HashSet;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Image implements Moveable, Shovable {
    private float xSpeed, ySpeed;
    private float chargeYSpeed, chargeYAcceleration, maxChargeYSpeed;
    private boolean canJump;
    private HashSet<String> currentActions;

    public Player(int x, int y, int width, int height/* , PImage sprite, PApplet app */) {
        super(x, y, width, height/* , sprite, app */);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.chargeYSpeed = 0;
        this.chargeYAcceleration = -1;
        this.maxChargeYSpeed = -25;
        this.canJump = true;
        currentActions = new HashSet<String>();
    }

    public void act(Main app) {
        this.move(app);
        super.act(app);
    }

    public void move(Main app) {
        this.x += this.xSpeed;
        this.y += this.ySpeed;
        this.ySpeed += Settings.GRAVITY;

        doActions(currentActions);

        if (this.isOffScreen()) {
            this.respawn();
        }

        this.onCollision(this.collidesWith(app.immovables));
    }

    private void doActions(HashSet<String> currentActions) {
        this.xSpeed = 0;
        if (currentActions.contains("left"))
            this.xSpeed -= 5;

        if (currentActions.contains("right"))
            this.xSpeed += 5;

        if (currentActions.contains("jump"))
            this.chargeJump();
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

        this.canJump = true;
    }

    public void chargeJump() {
        if (this.chargeYSpeed > this.maxChargeYSpeed)
            this.chargeYSpeed += this.chargeYAcceleration;
    }

    public void releaseJump() {
        if (this.chargeYSpeed != 0 && this.canJump) {
            this.ySpeed = this.chargeYSpeed;

        }

        this.canJump = false;
        this.chargeYSpeed = 0;
    }

    public void setKeys(String key, boolean pressed) {
        // System.out.println("Initial: " + currentActions.toString());
        if (pressed) {
            if (!currentActions.contains(key))
                this.currentActions.add(key);
        } else {
            this.currentActions.remove(key);
            if (key.equals("jump")) {
            }
            this.releaseJump();
        }

        //System.out.println("Final: " + currentActions.toString());
    }

    public boolean isOffScreen() {
        if (this.y > Settings.SCREEN_HEIGHT || this.x + this.width < 0 || this.x > Settings.SCREEN_WIDTH) {
            return true;
        }
        return false;
    }

    public void respawn() {
        this.x = 250;
        this.y = 250;
        this.ySpeed = 0;
        this.xSpeed = 0;
        this.canJump = false;
        this.chargeYSpeed = 0;
    }

    public String toString() {
        return "x: " + this.x + ", y: " + this.y + ", xSpeed: " + this.xSpeed + ", ySpeed: " + this.ySpeed + ", chargeYSpeed: " + this.chargeYSpeed
                + ", keys: " + this.currentActions.toString();
    }
}
