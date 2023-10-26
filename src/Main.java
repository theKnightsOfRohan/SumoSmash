import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Main extends PApplet {
    Player player;
    List<Drawable> drawables;
    CollisionHandler collisionHandler;
    static Settings.StageNumber currentStage;

    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    public void setup() {
        collisionHandler = new CollisionHandler();
        currentStage = Settings.StageNumber.STAGE_1;

        drawables = new ArrayList<Drawable>();

        player = new Player(Settings.Stage1.PLAYER_1_START_X, Settings.Stage1.PLAYER_1_START_Y, 50, 50);
        drawables.add(player);
        collisionHandler.addMoveable(player);
        collisionHandler.addCollidable(player);

        Dummy dummy = new Dummy(Settings.Stage1.PLAYER_2_START_X, Settings.Stage1.PLAYER_2_START_Y, 50, 50);
        drawables.add(dummy);
        collisionHandler.addMoveable(dummy);
        collisionHandler.addCollidable(dummy);

        for (int[] platform : Settings.Stage1.platforms) {
            Platform p = new Platform(platform[0], platform[1], platform[2], platform[3]);
            drawables.add(p);
            collisionHandler.addCollidable(p);
        }
    }

    public void draw() {
        background(200);
        for (Drawable drawable : drawables) {
            drawable.act(this);
        }

        collisionHandler.handleCollisions();
        fill(0);
    }

    public void keyPressed() {
        if (key == ' ' || key == 'w' || keyCode == UP) {
            player.setKeys("jump", true);
        } else if (key == 'a' || keyCode == LEFT) {
            player.setKeys("left", true);
            player.setKeys("right", false);
        } else if (key == 'd' || keyCode == RIGHT) {
            player.setKeys("right", true);
            player.setKeys("left", false);
        }
    }

    public void keyReleased() {
        if (key == ' ' || key == 'w' || keyCode == UP) {
            player.setKeys("jump", false);
        } else if (key == 'a' || keyCode == LEFT) {
            player.setKeys("left", false);
        } else if (key == 'd' || keyCode == RIGHT) {
            player.setKeys("right", false);
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}