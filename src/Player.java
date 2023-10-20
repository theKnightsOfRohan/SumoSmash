import java.util.HashSet;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

/**
 * This class represents a player in the SumoSmash game. It extends the Image
 * class and implements the Moveable and Shovable interfaces. It contains fields
 * for the player's speed, acceleration, jump charge, actions, and collision
 * properties. It also contains methods for moving the player, handling
 * collisions, and updating the player's state based on user input.
 */
public class Player extends Image implements Moveable, Shovable {
    private float xSpeed, ySpeed;
    private float chargeYSpeed, chargeYAcceleration, maxChargeYSpeed;
    private float xAcceleration, maxXSpeed;
    private float airAccScaleFactor;
    private boolean canJump;
    private HashSet<String> currentActions;
    private float bounceFactor;
    private float friction;

    /**
     * Constructor for the Player class.
     * 
     * @param x      The x-coordinate of the player.
     * @param y      The y-coordinate of the player.
     * @param width  The width of the player.
     * @param height The height of the player.
     */
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

    /**
     * Moves the player by updating its position based on its current speed and
     * acceleration. Also performs any actions currently assigned to the player and
     * checks for collisions with immovable objects. If the player goes off-screen,
     * it is respawned.
     * 
     * @param app the Main object representing the game
     */
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

    /**
     * Performs actions based on the current set of actions.
     * 
     * @param currentActions a HashSet of Strings representing the current set of
     *                       actions
     */
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

    /**
     * Checks if the player collides with any of the given collidables.
     * 
     * @param others a list of collidables to check against
     * @return the collidable that the player collides with, or null if there is no
     *         collision
     */
    public Collidable collidesWith(List<Collidable> others) {
        for (Collidable block : others) {
            if (this.x + this.width > block.getX() && this.x < block.getX() + block.getWidth() && this.y + this.height > block.getY()
                    && this.y < block.getY() + block.getHeight()) {
                return block;
            }
        }

        return null;
    }

    /**
     * This method is called when the player collides with another Collidable
     * object. If the player is moving downwards with a speed greater than 10 and
     * cannot jump, the player's ySpeed is reversed and multiplied by the
     * bounceFactor. Otherwise, the player's ySpeed is set to 0 and canJump is set
     * to true. The player's y position is set to the top of the other Collidable
     * object.
     * 
     * @param other the Collidable object that the player collided with
     */
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

    /**
     * Increases the player's charge jump speed if the maximum charge jump speed has
     * not been reached and the player can jump.
     */
    public void chargeJump() {
        if (this.chargeYSpeed > this.maxChargeYSpeed && this.canJump)
            this.chargeYSpeed += this.chargeYAcceleration;
    }

    /**
     * Release the jump of the player if they have charged their jump and can jump.
     * Sets the player's ySpeed to the charged ySpeed if they can jump. Sets canJump
     * to false and chargeYSpeed to 0.
     */
    public void releaseJump() {
        if (this.chargeYSpeed != 0 && this.canJump) {
            this.ySpeed = this.chargeYSpeed;
        }
        this.canJump = false;
        this.chargeYSpeed = 0;
    }

    /**
     * Sets the state of a key to pressed or released and updates the current
     * actions accordingly. If the key is pressed and not already in the current
     * actions, it is added. If the key is released, it is removed from the current
     * actions and if it is the jump key, the jump is released.
     * 
     * @param key     the key being pressed or released
     * @param pressed true if the key is pressed, false if it is released
     */
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

    /**
     * Checks if the player is off the screen.
     * 
     * @return true if the player is off the screen, false otherwise.
     */
    public boolean isOffScreen() {
        if (this.y > Settings.SCREEN_HEIGHT || this.x + this.width < 0 || this.x > Settings.SCREEN_WIDTH) {
            return true;
        }
        return false;
    }

    /**
     * Resets the player's position and speed to default values.
     */
    public void respawn() {
        this.x = 250;
        this.y = 250;
        this.ySpeed = 0;
        this.xSpeed = 0;
        this.canJump = false;
        this.chargeYSpeed = 0;
    }

    /**
     * Returns a string representation of the Player object. The string contains the
     * player's x and y coordinates, x and y speeds, charge y speed, and current
     * actions. The x speed is truncated to 4 decimal places.
     *
     * @return a string representation of the Player object
     */
    public String toString() {
        return String.format("Player at (%d, %d) with xSpeed %.4f, ySpeed %f, chargeYSpeed %f, and actions %s", this.x, this.y, this.xSpeed,
                this.ySpeed, this.chargeYSpeed, this.currentActions);
    }
}
