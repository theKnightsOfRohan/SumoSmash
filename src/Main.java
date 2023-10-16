import processing.core.PApplet;

public class Main extends PApplet {
    public void settings() {
        size(Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
    }

    public void setup() {
        background(0);
        Player player = new Player(0, 0, 50, 50 /* , loadImage("player.png"), this */);
    }

    public void draw() {

    }

    public static void main(String[] args) {
        PApplet.main("Main");
    }
}