import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Image class implements the Drawable interface and represents an image
 * that can be drawn on the screen.
 */
public class Image implements Drawable {
    protected int x, y, width, height;
    protected PImage sprite;

    /**
     * Constructs a new Image object with the specified position and dimensions.
     * 
     * @param x      the x-coordinate of the top-left corner of the image
     * @param y      the y-coordinate of the top-left corner of the image
     * @param width  the width of the image
     * @param height the height of the image
     */
    public Image(int x, int y, int width, int height/* , PImage sprite, PApplet app */) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        /*
         * this.sprite = sprite; sprite.resize(width, height);
         */
    }

    /**
     * Draws the image on the screen using the specified PApplet object.
     * 
     * @param app the PApplet object to use for drawing
     */
    public void act(PApplet app) {
        /* app.image(sprite, x, y); */
        app.rect(x, y, width, height);
    }

    /**
     * Returns the x-coordinate of the top-left corner of the image.
     * 
     * @return the x-coordinate of the top-left corner of the image
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the top-left corner of the image.
     * 
     * @return the y-coordinate of the top-left corner of the image
     */
    public int getY() {
        return y;
    }

    /**
     * Returns the width of the image.
     * 
     * @return the width of the image
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the image.
     * 
     * @return the height of the image
     */
    public int getHeight() {
        return height;
    }
}
