import processing.core.PApplet;

public class Main extends PApplet {
    Platform mainPlatform;
    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    public void setup() {
        mainPlatform = new Platform(250, 250, 100, 100/*, loadImage("platform.png"), this*/);
        //Player player = new Player(0, 0, 50, 50 /* , loadImage("player.png"), this */);
    }

    public void draw() {
        mainPlatform.act(this);
    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}