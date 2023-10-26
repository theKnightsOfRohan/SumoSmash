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
            if (this.chargeYSpeed < this.maxChargeYSpeed)
                this.setKeys("jump", false);
        }
    }
}
