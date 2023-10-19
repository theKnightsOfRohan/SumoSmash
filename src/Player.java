import java.util.HashSet;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

public class Player extends Image implements Moveable, Shovable {
    private float xSpeed, ySpeed;
    private float chargeYSpeed, chargeYAcceleration, maxChargeYSpeed;
    private float xAcceleration, maxXSpeed;
    private float airAccScaleFactor;
    private boolean canJump;
    private HashSet<String> currentActions;
    private float bounceFactor;
    private float friction;

    public Player(int x, int y, int width, int height/* , PImage sprite, PApplet app */) {
        super(x, y, width, height/* , sprite, app */);
        this.xSpeed = 0;
        this.ySpeed = 0;
        this.chargeYSpeed = 0;
        this.chargeYAcceleration = -1;
        this.maxChargeYSpeed = -25;
        this.xAcceleration = 1;
        this.maxXSpeed = 5;
        this.airAccScaleFactor = 0.9f;
        this.canJump = true;
        this.currentActions = new HashSet<String>();
        this.bounceFactor = 0.2f;
        this.friction = 0.8f;
    }

    public void act(Main app) {
        this.move(app);
        app.fill(0);
        app.stroke(255);
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
        if (currentActions.contains("jump"))
            this.chargeJump();
        else if (currentActions.contains("left")) {
            if (Math.abs(this.xSpeed) < this.maxXSpeed) {
                this.xSpeed -= 2 * this.xAcceleration;
            }
        } else if (currentActions.contains("right")) {
            if (this.xSpeed < this.maxXSpeed) {
                this.xSpeed += 2 * this.xAcceleration;
            }
        }

        if (this.canJump) {
            this.xSpeed *= this.friction;
        } else {
            this.xSpeed *= this.airAccScaleFactor;
        }

        if (Math.abs(this.xSpeed) < 1)
            this.xSpeed = 0;
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
        if (this.ySpeed > 10 && !this.canJump) {
            this.ySpeed = -this.ySpeed * bounceFactor;
        } else {
            this.ySpeed = 0;
            this.canJump = true;
        }
        this.y = other.getY() - this.height;
    }

    public void chargeJump() {
        if (this.chargeYSpeed > this.maxChargeYSpeed && this.canJump)
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
        if (pressed) {
            if (!currentActions.contains(key))
                this.currentActions.add(key);
        } else {
            this.currentActions.remove(key);
            if (key.equals("jump")) {
            }
            this.releaseJump();
        }
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
        return "x: " + this.x + ", y: " + this.y + ", xSpeed: "
                + Float.toString(this.xSpeed).substring(0, (int) (Math.min(Float.toString(this.xSpeed).length(), 4))) + ", ySpeed: " + this.ySpeed
                + ", chargeYSpeed: " + this.chargeYSpeed + ", keys: " + this.currentActions.toString();
    }
}
