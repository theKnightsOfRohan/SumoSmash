import java.util.List;
import java.util.ArrayList;

public class CollisionHandler {
    List<Collidable> collidables;
    List<Moveable> moveables;

    public CollisionHandler() {
        this.collidables = new ArrayList<Collidable>();
        this.moveables = new ArrayList<Moveable>();
    }

    public void addCollidable(Collidable collidable) {
        this.collidables.add(collidable);
    }

    public void addMoveable(Moveable moveable) {
        this.moveables.add(moveable);
    }

    public void handleCollisions() {
        for (Moveable moveable : this.moveables) {
            for (Collidable collidable : this.collidables) {
                if (moveable == collidable)
                    continue;
                if (isCollision(moveable, collidable)) {
                    CollisionInfo info = new CollisionInfo(moveable, collidable);
                    if (collidable instanceof Moveable) {
                        handlePhysicsCollisions(info);
                    } else {
                        handleSimpleCollisions(info);
                    }
                }
            }
        }
    }

    private void handleSimpleCollisions(CollisionInfo info) {
        if (info.getDirection() == CollisionInfo.Direction.HORIZONTAL) {
            if (!(info.getLeftOrTop() instanceof Moveable)) {
                Moveable Right = (Moveable) info.getRightOrBottom();
                Right.setX(info.getLeftOrTop().getX() + info.getLeftOrTop().getWidth());
                float newSpeed = -Right.getXSpeed() * Right.getBounceFactor();
                if (Math.abs(newSpeed) < 2)
                    newSpeed = 0;
                Right.setXSpeed(newSpeed);
            } else {
                Moveable Left = (Moveable) info.getLeftOrTop();
                Left.setX(info.getRightOrBottom().getX() - Left.getWidth());
                float newSpeed = -Left.getXSpeed() * Left.getBounceFactor();
                if (Math.abs(newSpeed) < 2)
                    newSpeed = 0;
                Left.setXSpeed(newSpeed);
            }
        } else if (info.getDirection() == CollisionInfo.Direction.VERTICAL) {
            if (!(info.getLeftOrTop() instanceof Moveable)) {
                Moveable Bottom = (Moveable) info.getRightOrBottom();
                Bottom.setY(info.getLeftOrTop().getY() + info.getLeftOrTop().getHeight());
                float newSpeed = -Bottom.getYSpeed() * Bottom.getBounceFactor();
                if (Math.abs(newSpeed) < 2)
                    newSpeed = 0;
                Bottom.setYSpeed(newSpeed);
            } else {
                Moveable Top = (Moveable) info.getLeftOrTop();
                Top.setY(info.getRightOrBottom().getY() - Top.getHeight());
                float newSpeed = -Top.getYSpeed() * Top.getBounceFactor();
                if (Math.abs(newSpeed) < 2)
                    newSpeed = 0;
                Top.setYSpeed(newSpeed);
            }
        }
    }

    public void handlePhysicsCollisions(CollisionInfo info) {
        if (info.getDirection() == CollisionInfo.Direction.HORIZONTAL) {
            Moveable Left = (Moveable) info.getLeftOrTop();
            Moveable Right = (Moveable) info.getRightOrBottom();
            float xSpeedR = Right.getXSpeed();
            float xSpeedL = Left.getXSpeed();
            float sum = xSpeedL + xSpeedR;

            if (Math.abs(xSpeedL) > Math.abs(xSpeedR)) {
                Right.setXSpeed(sum / 2);
                Left.setXSpeed(-sum / 2);
            } else {
                Right.setXSpeed(-sum / 2);
                Left.setXSpeed(sum / 2);
            }

            Left.setX(Right.getX() - Left.getWidth());
            Right.setX(Left.getX() + Left.getWidth());
        } else if (info.getDirection() == CollisionInfo.Direction.VERTICAL) {
            Moveable Top = (Moveable) info.getLeftOrTop();
            Moveable Bottom = (Moveable) info.getRightOrBottom();
            float ySpeedT = Top.getYSpeed();
            float ySpeedB = Bottom.getYSpeed();
            float sum = ySpeedT + ySpeedB;

            if (Math.abs(ySpeedT) > Math.abs(ySpeedB)) {
                Bottom.setYSpeed(sum / 2);
                Top.setYSpeed(-sum / 2);
            } else {
                Bottom.setYSpeed(-sum / 2);
                Top.setYSpeed(sum / 2);
            }

            Top.setY(Bottom.getY() - Top.getHeight());
            Bottom.setY(Top.getY() + Top.getHeight());
        }
    }

    private boolean isCollision(Collidable a, Collidable b) {
        return a.getX() + a.getWidth() > b.getX() && a.getX() < b.getX() + b.getWidth() && a.getY() + a.getHeight() > b.getY()
                && a.getY() < b.getY() + b.getHeight();
    }
}

class CollisionInfo {
    private Collidable leftOrTop;
    private Collidable rightOrBottom;
    private Direction direction;

    enum Direction {
        HORIZONTAL, VERTICAL
    }

    public CollisionInfo(Collidable first, Collidable second) {
        float leftX = Math.max(first.getX(), second.getX());
        float rightX = Math.min(first.getX() + first.getWidth(), second.getX() + second.getWidth());
        float topY = Math.max(first.getY(), second.getY());
        float bottomY = Math.min(first.getY() + first.getHeight(), second.getY() + second.getHeight());

        float width = rightX - leftX;
        float height = bottomY - topY;

        if (width < height) {
            if (first.getX() < second.getX()) {
                this.direction = Direction.HORIZONTAL;
                this.leftOrTop = first;
                this.rightOrBottom = second;
            } else {
                this.direction = Direction.HORIZONTAL;
                this.leftOrTop = second;
                this.rightOrBottom = first;
            }
        } else {
            if (first.getY() < second.getY()) {
                this.direction = Direction.VERTICAL;
                this.leftOrTop = first;
                this.rightOrBottom = second;
            } else {
                this.direction = Direction.VERTICAL;
                this.leftOrTop = second;
                this.rightOrBottom = first;
            }
        }
    }

    public Collidable getLeftOrTop() {
        return leftOrTop;
    }

    public Collidable getRightOrBottom() {
        return rightOrBottom;
    }

    public Direction getDirection() {
        return direction;
    }
}