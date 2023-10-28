import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;

class OptionsSelect {
    public void setup() {

    }
}

class Stage1 implements Settings.GameState {
    Player player;
    List<Drawable> drawables;
    List<Button> buttons;
    CollisionHandler collisionHandler;

    public static final List<int[]> platforms = new ArrayList<>(Arrays.asList(new int[][] { new int[] { 200, 700, 600, 300 } }));
    public static final int PLAYER_1_START_X = 300;
    public static final int PLAYER_1_START_Y = 200;
    public static final int PLAYER_2_START_X = 600;
    public static final int PLAYER_2_START_Y = 200;
    public static final int MENU_BUTTON_WIDTH = 100;
    public static final int MENU_BUTTON_HEIGHT = 50;
    public static final int MENU_BUTTON_X = Settings.SCREEN_WIDTH - MENU_BUTTON_WIDTH;
    public static final int MENU_BUTTON_Y = 0;

    public Stage1() {
        drawables = new ArrayList<>();
        buttons = new ArrayList<>();
        collisionHandler = new CollisionHandler();

        player = new Player(PLAYER_1_START_X, PLAYER_1_START_Y, 50, 50);
        drawables.add(player);
        collisionHandler.addMoveable(player);
        collisionHandler.addCollidable(player);

        Dummy dummy = new Dummy(PLAYER_2_START_X, PLAYER_2_START_Y, 50, 50);
        drawables.add(dummy);
        collisionHandler.addMoveable(dummy);
        collisionHandler.addCollidable(dummy);

        for (int[] platform : platforms) {
            Platform p = new Platform(platform[0], platform[1], platform[2], platform[3]);
            drawables.add(p);
            collisionHandler.addCollidable(p);
        }

        Button menuButton = new Button(MENU_BUTTON_X, MENU_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, (Void) -> {
            Main.currentStage = Settings.Stage.OPTIONS;
            return null;
        });
        buttons.add(menuButton);
        drawables.add(menuButton);
    }

    public void draw(PApplet app) {
        app.background(200);
        for (Drawable drawable : drawables) {
            drawable.act(app);
        }
        app.fill(0);
        collisionHandler.handleCollisions();
    }

    public void handleKey(PApplet main, boolean pressed) {
        if (pressed) {
            if (main.key == ' ' || main.key == 'w' || main.keyCode == PConstants.UP) {
                player.setKeys("jump", true);
            } else if (main.key == 'a' || main.keyCode == PConstants.LEFT) {
                player.setKeys("left", true);
                player.setKeys("right", false);
            } else if (main.key == 'd' || main.keyCode == PConstants.RIGHT) {
                player.setKeys("right", true);
                player.setKeys("left", false);
            }
        } else {
            if (main.key == ' ' || main.key == 'w' || main.keyCode == PConstants.UP) {
                player.setKeys("jump", false);
            } else if (main.key == 'a' || main.keyCode == PConstants.LEFT) {
                player.setKeys("left", false);
            } else if (main.key == 'd' || main.keyCode == PConstants.RIGHT) {
                player.setKeys("right", false);
            }
        }
    }

    public void handleClick(int mouseX, int mouseY) {
        for (Button button : buttons) {
            if (button.isClicked(mouseX, mouseY)) {
                button.onClick.apply(null);
            }
        }
    }
}