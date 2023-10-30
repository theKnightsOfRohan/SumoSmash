import java.util.HashSet;
import processing.core.PApplet;

public class Player extends Image implements Moveable {
    protected float xSpeed, ySpeed;
    protected float dashCooldown;
    protected float dashCooldownIncrement;
    protected float dashSpeedIncrease;
    protected float maxDashSpeed;
    protected float chargeYSpeed, chargeYAcceleration, maxChargeYSpeed;
    protected float xAcceleration, maxXSpeed;
    protected float airAccScaleFactor;
    protected HashSet<String> currentActions;
    protected float bounceFactor;
    protected float friction;
    protected float debugX, debugY;
    protected int spawnX, spawnY;
    protected boolean canDoubleJump;

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
        this.maxXSpeed = 10;
        this.maxDashSpeed = 20;
        this.airAccScaleFactor = 0.9f;
        this.currentActions = new HashSet<String>();
        this.bounceFactor = 0.2f;
        this.friction = 0.8f;
        this.debugX = 10;
        this.debugY = 10;
        this.spawnX = x;
        this.spawnY = y;
        this.dashCooldown = 0;
        this.canDoubleJump = false;
        this.dashCooldownIncrement = 30;
        this.dashSpeedIncrease = 40;
    }

    public void act(PApplet app) {
        this.move(app);
        app.fill(0);
        app.stroke(255);
        super.act(app);
        if (dashCooldown > 0) {
            dashCooldown--;
        }
        app.text("DEBUG: " + this.toString(), this.debugX, this.debugY);
    }

    /**
     * Moves the player by updating its position based on its current speed and
     * acceleration. Also performs any actions currently assigned to the player. If
     * the player goes off-screen, it is respawned.
     *
     * @param app the Main object representing the game
     */
    public void move(PApplet app) {
        this.x += this.xSpeed;
        this.y += this.ySpeed;
        this.ySpeed += Settings.GRAVITY;

        doActions(currentActions);

        if (this.isOffScreen()) {
            this.respawn();
        }
    }

    public void setXSpeed(float xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(float ySpeed) {
        this.ySpeed = ySpeed;
    }

    public float getXSpeed() {
        return this.xSpeed;
    }

    public float getYSpeed() {
        return this.ySpeed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.ySpeed = y;
    }

    public float getBounceFactor() {
        return this.bounceFactor;
    }

    /**
     * Performs actions based on the current set of actions.
     *
     * @param currentActions a HashSet of Strings representing the current set of
     *                       actions
     */
    protected void doActions(HashSet<String> currentActions) {
        if (currentActions.contains("jump"))
            this.chargeJump();
        else if (currentActions.contains("left")) {
            if (Math.abs(this.xSpeed) < this.maxXSpeed) {
                this.xSpeed -= 2 * this.xAcceleration;
            }
        } else if (currentActions.contains("right")) {
            if (Math.abs(this.xSpeed) < this.maxXSpeed) {
                this.xSpeed += 2 * this.xAcceleration;
            }
        }

        if (currentActions.contains("lDash") && this.dashCooldown == 0) {
            if (isOnPlatform()) {
                this.xSpeed -= this.dashSpeedIncrease / 2;
            } else {
                ySpeed = -1;
                this.xSpeed -= this.dashSpeedIncrease;
            }
            this.dashCooldown = this.dashCooldownIncrement;
        } else if (currentActions.contains("rDash") && this.dashCooldown == 0) {
            if (isOnPlatform()) {
                this.xSpeed += this.dashSpeedIncrease / 2;
            } else {
                ySpeed = -1;
                this.xSpeed += this.dashSpeedIncrease;
            }
            this.dashCooldown = this.dashCooldownIncrement;
        } /*else if (currentActions.contains("uDash") && this.dashCooldown == 0) {
            this.ySpeed -= this.dashSpeedIncrease;
            this.dashCooldown = this.dashCooldownIncrement;
        }*/ else if (currentActions.contains("dDash") && this.dashCooldown == 0) {
            this.ySpeed += this.dashSpeedIncrease;
            this.dashCooldown = this.dashCooldownIncrement;
        }

        if (this.canJump()) {
            this.xSpeed *= this.friction;
        } else {
            this.xSpeed *= this.airAccScaleFactor;
        }
        if(!this.isOnPlatform()&&this.canDoubleJump){
            this.ySpeed = -20;
            this.canDoubleJump = false;
        }

        if (Math.abs(this.xSpeed) < 1)
            this.xSpeed = 0;
    }

    /**
     * Increases the player's charge jump speed if the maximum charge jump speed has
     * not been reached and the player can jump.
     */
    public void chargeJump() {
        if (this.chargeYSpeed > this.maxChargeYSpeed && this.canJump())
            this.chargeYSpeed += this.chargeYAcceleration;
    }

    /**
     * Release the jump of the player if they have charged their jump and can jump.
     * Sets the player's ySpeed to the charged ySpeed if they can jump. Sets
     * canJump() to false and chargeYSpeed to 0.
     */
    public void releaseJump() {
        if (this.chargeYSpeed != 0 && this.canJump()) {
            this.ySpeed = this.chargeYSpeed;
        }
        this.chargeYSpeed = 0;
        this.canDoubleJump = true;
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
                this.releaseJump();
            }
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
        this.x = this.spawnX;
        this.y = this.spawnY;
        this.ySpeed = 0;
        this.xSpeed = 0;
        this.chargeYSpeed = 0;
    }

    /**
     * Returns a boolean indicating whether the player can jump or not. A player can
     * jump if their ySpeed is less than 2 and they are on a platform.
     *
     * @return true if the player can jump, false otherwise
     */
    public boolean canJump() {
        return Math.abs(this.ySpeed) < 2 && isOnPlatform();
    }

    /**
     * Checks if the player is currently on a platform in the current stage.
     *
     * @return true if the player is on a platform, false otherwise.
     */
    protected boolean isOnPlatform() {
        switch (Main.currentStage) {
            case STAGE_1 -> {
                for (int[] platform : Stage1.platforms) {
                    if (this.x + this.width > platform[0] && this.x < platform[0] + platform[2] && this.y + this.height >= platform[1]
                            && this.y + this.height <= platform[1] + platform[3]) {
                        return true;
                    }
                }
                return false;
            }
            default -> {
                return false;
            }
        }
    }

    /**
     * Returns a string representation of the Player object.
     *
     * @return a formatted string containing the Player's position, speed, charge
     *         speed, and current actions
     */
    public String toString() {
        return String.format("Player at (%.4f, %.4f) with xSpeed %.4f, ySpeed %.4f, chargeYSpeed %.4f, and actions %s", this.x, this.y, this.xSpeed,
                this.ySpeed, this.chargeYSpeed, this.currentActions);
    }
}
