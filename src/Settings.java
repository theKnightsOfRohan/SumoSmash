import java.util.List;
import processing.core.PApplet;

public class Settings {
    public static final int SCREEN_WIDTH = 1000;
    public static final int SCREEN_HEIGHT = 1000;
    public static final int GRAVITY = 1;
    public static final int PLATFORM_START_X = 200;
    public static final int PLATFORM_START_Y = 700;
    public static final int PLATFORM_WIDTH = 600;
    public static final int PLATFORM_HEIGHT = 300;
    public static final int PLAYER_1_START_X = 200;
    public static final int PLAYER_1_START_Y = 200;
    public static final int PLAYER_2_START_X = 800;
    public static final int PLAYER_2_START_Y = 200;
}

interface Drawable {
    public void act(PApplet app);

    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();
}

interface Moveable {
    public void move(Main app);
}

interface Shovable {
    public Collidable collidesWith(List<Collidable> others);

    public void onCollision(Collidable other);
}

interface Collidable extends Drawable {

}
