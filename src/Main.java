import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Main extends PApplet {
    Player player;
    List<Drawable> drawables;
    CollisionHandler collisionHandler;

    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    public void setup() {
        collisionHandler = new CollisionHandler();

        drawables = new ArrayList<Drawable>();

        player = new Player(Settings.PLAYER_1_START_X, Settings.PLAYER_1_START_Y, 50, 50);
        drawables.add(player);
        collisionHandler.addMoveable(player);
        collisionHandler.addCollidable(player);

        Dummy dummy = new Dummy(Settings.PLAYER_2_START_X, Settings.PLAYER_2_START_Y, 50, 50);
        drawables.add(dummy);
        collisionHandler.addMoveable(dummy);
        collisionHandler.addCollidable(dummy);

        Platform platform = new Platform(Settings.PLATFORM_START_X, Settings.PLATFORM_START_Y, Settings.PLATFORM_WIDTH, Settings.PLATFORM_HEIGHT);
        drawables.add(platform);
        collisionHandler.addCollidable(platform);
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