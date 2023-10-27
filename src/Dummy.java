import processing.core.PApplet;

public class Dummy extends Player {
    Dummy(int x, int y, int width, int height) {
        super(x, y, width, height);
        this.debugX = 10;
        this.debugY = 30;
    }

    public void act(PApplet app) {
        determineInput();
        super.act(app);
    }

    /**
     * Determines the input for the dummy character based on its position. If the
     * dummy is to the left of 300, it moves right and stops moving left and
     * jumping. If the dummy is to the right of 700, it moves left and stops moving
     * right and jumping. Otherwise, the character stops moving left and right and
     * jumps if it can. If the character is charging its jump, it stops jumping if
     * it has reached the maximum charge speed.
     */
    public void determineInput() {
        if (this.x < 300) {
            this.setKeys("right", true);
            this.setKeys("left", false);
            this.setKeys("jump", false);
        } else if (this.x > 700) {
            this.setKeys("left", true);
            this.setKeys("right", false);
            this.setKeys("jump", false);
        } else {
            this.setKeys("right", false);
            this.setKeys("left", false);
            if (this.canJump())
                this.setKeys("jump", true);
            if (this.chargeYSpeed < this.maxChargeYSpeed + 1)
                this.setKeys("jump", false);
        }
    }
}
