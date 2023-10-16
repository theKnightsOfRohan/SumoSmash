import java.util.List;
import processing.core.PApplet;

public class Settings {
    public static final int SCREEN_WIDTH = 500;
    public static final int SCREEN_HEIGHT = 500;
    public static final int GRAVITY = 1;
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
