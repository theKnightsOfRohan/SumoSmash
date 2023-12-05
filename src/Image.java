import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Image class implements the Drawable interface and represents an image
 * that can be drawn on the screen.
 */
public class Image implements Drawable {
    protected float x, y, width, height;
    protected PImage sprite;

    public Image(int x, int y, int width, int height, PImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        sprite.resize(width, height);
    }

    public void act(PApplet app) {
        app.image(sprite, x, y);
        //app.rect(x, y, width, height);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}
