import processing.core.PApplet;

public class Main extends PApplet {
    static Settings.Stage currentStage;
    static GameState gameState;

    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    public void setup() {
        frameRate(60);
        currentStage = Settings.Stage.STAGE_1;
        gameState = new Stage1(this);
        //gameState = new Replay();
    }

    public void draw() {
        gameState.draw(this);
        text(frameRate, 10, 50);
    }

    public void keyPressed() {
        gameState.handleKey(this, true);
    }

    public void keyReleased() {
        gameState.handleKey(this, false);
    }

    public void mouseReleased() {
        gameState.handleClick(mouseX, mouseY);
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}