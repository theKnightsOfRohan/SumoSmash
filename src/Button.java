import java.util.function.Function;

import processing.core.PApplet;

public class Button extends Image {
    public Function<Void, Void> onClick;

    public Button(int x, int y, int width, int height, Function<Void, Void> onClick) {
        super(x, y, width, height);
        this.onClick = onClick;
    }

    public boolean isClicked(int mouseX, int mouseY) {
        return mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && mouseY <= this.y + this.height;
    }
}
