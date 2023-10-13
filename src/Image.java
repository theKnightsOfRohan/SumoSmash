import processing.core.PApplet;
import processing.core.PImage;

public class Image implements Drawable {
    protected int x, y, width, height;
    protected PImage sprite;

    public Image(int x, int y, int width, int height, PImage sprite, PApplet app) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
        sprite.resize(width, height);
    }

    public void draw(PApplet app) {
        app.image(sprite, x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
