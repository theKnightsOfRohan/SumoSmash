import processing.core.PApplet;

public class Main extends PApplet {
    Platform mainPlatform;
    Player player;

    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    public void setup() {
        player = new Player(0, 0, 50, 50 /* , loadImage("player.png"), this */);
        mainPlatform = new Platform(250, 250, 100, 100/* , loadImage("platform.png"), this */);
    }

    public void draw() {
        background(200);
        player.act(this);
        mainPlatform.act(this);
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}