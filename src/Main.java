import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Main extends PApplet {
    Player player;
    List<Collidable> immovables;

    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    public void setup() {
        immovables = new ArrayList<Collidable>();
        player = new Player(Settings.PLAYER_1_START_X, Settings.PLAYER_1_START_Y, 50, 50 /* , loadImage("player.png"), this */);
        immovables.add(new Platform(Settings.PLATFORM_START_X, Settings.PLATFORM_START_Y, Settings.PLATFORM_WIDTH,
                Settings.PLATFORM_HEIGHT/* , loadImage("platform.png"), this */));
    }

    public void draw() {
        background(200);
        player.act(this);
        for (Collidable block : immovables) {
            block.act(this);
        }

        fill(0);
        text("DEBUG: " + player.toString(), 10, 10);
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