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
        player = new Player(0, 0, 50, 50 /* , loadImage("player.png"), this */);
        immovables.add(new Platform(0, 500, 500, 25/* , loadImage("platform.png"), this */));
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
            player.chargeJump();
        } else if (key == 'a' || keyCode == LEFT) {
            player.moveSideways("left");
        } else if (key == 'd' || keyCode == RIGHT) {
            player.moveSideways("right");
        }
    }

    public void keyReleased() {
        if (key == ' ' || key == 'w' || keyCode == UP) {
            player.releaseJump();
        } else if (key == 'a' || keyCode == LEFT) {
            player.moveSideways("");
        } else if (key == 'd' || keyCode == RIGHT) {
            player.moveSideways("");
        }
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}