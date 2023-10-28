import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Main extends PApplet {
    Player player;
    List<Drawable> drawables;
    CollisionHandler collisionHandler;
    static Settings.Stage currentStage;
    Settings.GameState gameState;

    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    public void setup() {
        currentStage = Settings.Stage.STAGE_1;
        gameState = new Stage1();
    }

    public void draw() {
        gameState.draw(this);
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