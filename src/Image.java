import processing.core.PImage;

public class Image {
    protected int x,y,width,height;
    protected PImage sprite;
    public Image(int x, int y, int width, int height, PImage sprite) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.sprite = sprite;
    }
}
