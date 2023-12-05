import processing.core.PImage;

public class Button extends Image {
    public Runnable onClick;

    public Button(int x, int y, int width, int height, Runnable onClick, PImage sprite) {
        super(x, y, width, height, sprite);
        this.onClick = onClick;
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
}
