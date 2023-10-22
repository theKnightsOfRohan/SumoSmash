import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

/**
 * This class represents the main class of the SumoSmash game. It extends the
 * PApplet class and contains the setup, draw, keyPressed, keyReleased and main
 * methods. It also contains a Player object and a List of Collidable objects
 * representing the immovable objects in the game. The setup method initializes
 * the immovables list and the player object. The draw method updates the player
 * and immovables objects and displays the debug information. The keyPressed and
 * keyReleased methods handle the player's movement. The main method is the
 * entry point of the game.
 * 
 * @author theKnightsOfRohan, https://github.com/theKnightsOfRohan
 * @author HitmanNook, https://github.com/HitmanNook
 */
public class Main extends PApplet {
    Player player;
    List<Drawable> drawables;
    CollisionHandler collisionHandler;

    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    /**
     * Sets up the game by initializing the immovables list, creating a new player
     * object, and adding a platform to the immovables list.
     */
    public void setup() {
        collisionHandler = new CollisionHandler();

        drawables = new ArrayList<Drawable>();

        player = new Player(Settings.PLAYER_1_START_X, Settings.PLAYER_1_START_Y, 50, 50);
        drawables.add(player);
        collisionHandler.addMoveable(player);
        collisionHandler.addCollidable(player);

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

    /**
     * This method is called whenever a key is pressed. It sets the corresponding
     * keys in the player object to true based on the key pressed. If the spacebar,
     * 'w' key or up arrow key is pressed, the "jump" key is set to true. If the 'a'
     * key or left arrow key is pressed, the "left" key is set to true and the
     * "right" key is set to false. If the 'd' key or right arrow key is pressed,
     * the "right" key is set to true and the "left" key is set to false.
     */
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

    /**
     * Called when a key is released. Updates the player's movement actions based on
     * the released key.
     */
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