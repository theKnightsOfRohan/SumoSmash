import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import processing.core.PApplet;
import processing.core.PConstants;

class OptionsSelect implements GameState {
    List<Button> buttons;

    public static final int BUTTON_WIDTH = 200;
    public static final int BUTTON_HEIGHT = 50;
    public static final int BUTTON_X = 400;
    public static final int BUTTON_Y = 400;

    public OptionsSelect() {
        buttons = new ArrayList<>();
        Button button = new Button(BUTTON_X, BUTTON_Y, BUTTON_WIDTH, BUTTON_HEIGHT, () -> {
            Main.currentStage = Settings.Stage.STAGE_1;
            Main.gameState = new Stage1();
        });
        buttons.add(button);
    }

    public void draw(PApplet app) {
        app.background(200);
        for (Button button : buttons) {
            button.act(app);
        }
    }

    public void handleKey(PApplet main, boolean pressed) {
    }

    public void handleClick(int mouseX, int mouseY) {
        for (Button button : buttons) {
            if (button.isClicked(mouseX, mouseY)) {
                button.onClick.run();
            }
        }
    }
}

class Stage1 implements GameState {
    Player player;
    List<Drawable> drawables;
    List<Button> buttons;
    CollisionHandler collisionHandler;
    boolean paused;

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
        paused = false;

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

        Button menuButton = new Button(MENU_BUTTON_X, MENU_BUTTON_Y, MENU_BUTTON_WIDTH, MENU_BUTTON_HEIGHT, () -> {
            Main.currentStage = Settings.Stage.OPTIONS;
            Main.gameState = new OptionsSelect();
        });
        buttons.add(menuButton);
        drawables.add(menuButton);
    }

    public void draw(PApplet app) {
        if (paused)
            return;
        app.background(200);
        for (Drawable drawable : drawables) {
            drawable.act(app);
        }
        app.fill(0);
        collisionHandler.handleCollisions();
    }

    public void handleKey(PApplet main, boolean pressed) {
        if (pressed) {
            if (main.key == ' ' || main.key == 'w') {
                player.setKeys("jump", true);
            } else if (main.key == 'a') {
                player.setKeys("left", true);
                player.setKeys("right", false);
            } else if (main.key == 'd') {
                player.setKeys("right", true);
                player.setKeys("left", false);
            } else if (main.keyCode == PConstants.LEFT) {
                player.setKeys("lDash", true);
            } else if (main.keyCode == PConstants.RIGHT) {
                player.setKeys("rDash", true);
            } else if (main.keyCode == PConstants.UP) {
                player.setKeys("uDash", true);
            } else if (main.keyCode == PConstants.DOWN) {
                player.setKeys("dDash", true);
            }
        } else {
            if (main.key == ' ' || main.key == 'w') {
                player.setKeys("jump", false);
            } else if (main.key == 'a') {
                player.setKeys("left", false);
            } else if (main.key == 'd') {
                player.setKeys("right", false);
            } else if (main.keyCode == PConstants.LEFT) {
                player.setKeys("lDash", false);
            } else if (main.keyCode == PConstants.RIGHT) {
                player.setKeys("rDash", false);
            } else if (main.keyCode == PConstants.UP) {
                player.setKeys("uDash", false);
            } else if (main.keyCode == PConstants.DOWN) {
                player.setKeys("dDash", false);
            } else if (main.key == 'p') {
                paused = !paused;
            }
        }
    }

    public void handleClick(int mouseX, int mouseY) {
        for (Button button : buttons) {
            if (button.isClicked(mouseX, mouseY)) {
                button.onClick.run();
            }
        }
    }
}

class Replay extends Stage1 {
    ArrayList<String> file;
    ArrayList<ArrayList<String>> allActions;
    HashSet<String> possibleActions;
    int frameCount;

    public Replay() {
        super();
        file = readFile("replayFiles/replay01");
        int x = Integer.parseInt(file.get(0));
        int y = Integer.parseInt(file.get(1));
        // player = new Player(x, y, 50, 50);
        frameCount = 0;
        allActions = parseReplayFile(file);
        possibleActions = new HashSet<String>(Arrays.asList("jump", "left", "right", "lDash", "rDash"));
    }

    private ArrayList<ArrayList<String>> parseReplayFile(ArrayList<String> file) {
        ArrayList<ArrayList<String>> allActions = new ArrayList<>();
        for (int i = 2; i < file.size(); i++) {
            String[] actions = file.get(i).split(",");
            ArrayList<String> actionsList = new ArrayList<String>(Arrays.asList(actions));
            allActions.add(actionsList);
        }
        return allActions;
    }

    public void draw(PApplet app) {
        if (paused)
            return;
        app.background(200);

        try {
            playInputs(allActions.get(frameCount));
        } catch (ArrayIndexOutOfBoundsException e) {
            System.exit(0);
        }

        for (Drawable drawable : drawables) {
            drawable.act(app);
        }

        app.fill(0);
        collisionHandler.handleCollisions();
        frameCount++;
        app.text("Frame count: " + frameCount, 600, 10);
    }

    public void handleKey(PApplet main, boolean pressed) {
    }

    public void playInputs(ArrayList<String> actions) {
        if (actions.contains("jump")) {
            player.setKeys("jump", true);
        } else {
            player.setKeys("jump", false);
        }
        if (actions.contains("left")) {
            player.setKeys("left", true);
        } else {
            player.setKeys("left", false);
        }
        if (actions.contains("right")) {
            player.setKeys("right", true);
        } else {
            player.setKeys("right", false);
        }
        if (actions.contains("lDash")) {
            player.setKeys("lDash", true);
        } else {
            player.setKeys("lDash", false);
        }
        if (actions.contains("rDash")) {
            player.setKeys("rDash", true);
        } else {
            player.setKeys("rDash", false);
        }
    }

    private static ArrayList<String> readFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            ArrayList<String> fileLines = new ArrayList<>();
            String line = reader.readLine();
            while (line != null) {
                fileLines.add(line);
                line = reader.readLine();
            }
            return fileLines;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}