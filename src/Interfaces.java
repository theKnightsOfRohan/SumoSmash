import java.util.List;
import processing.core.PApplet;

interface Drawable {
    public void draw(PApplet app);

    public int getX();

    public int getY();

    public int getWidth();

    public int getHeight();
}

interface Moveable {
    public void move();
}

interface Collidable {
    public boolean collidesWith(List<Collidable> others);

    public void onCollision();
}
