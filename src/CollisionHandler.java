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

    /**
     * Handles collisions between moveable and collidable objects. Loops through all
     * moveables and collidables and checks for collisions. If a collision is
     * detected, it creates a CollisionInfo object and calls the appropriate
     * collision handling method.
     */
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

    /**
     * Handles simple collisions between two objects by updating their positions and
     * speeds based on their bounce factors.
     * 
     * @param info the CollisionInfo object containing information about the
     *             collision
     */
    private void handleSimpleCollisions(CollisionInfo info) {
        if (info.getDirection() == CollisionInfo.Direction.HORIZONTAL) {
            if (!(info.getLeftOrTop() instanceof Moveable)) {
                Moveable right = (Moveable) info.getRightOrBottom();
                right.setX(info.getLeftOrTop().getX() + info.getLeftOrTop().getWidth());
                float newSpeed = -right.getXSpeed() * right.getBounceFactor();
                if (Math.abs(newSpeed) < 2)
                    newSpeed = 0;
                right.setXSpeed(newSpeed);
            } else {
                Moveable left = (Moveable) info.getLeftOrTop();
                left.setX(info.getRightOrBottom().getX() - left.getWidth());
                float newSpeed = -left.getXSpeed() * left.getBounceFactor();
                if (Math.abs(newSpeed) < 2)
                    newSpeed = 0;
                left.setXSpeed(newSpeed);
            }
        } else if (info.getDirection() == CollisionInfo.Direction.VERTICAL) {
            if (!(info.getLeftOrTop() instanceof Moveable)) {
                Moveable bottom = (Moveable) info.getRightOrBottom();
                bottom.setY(info.getLeftOrTop().getY() + info.getLeftOrTop().getHeight());
                float newSpeed = -bottom.getYSpeed() * bottom.getBounceFactor();
                if (Math.abs(newSpeed) < 2)
                    newSpeed = 0;
                bottom.setYSpeed(newSpeed);
            } else {
                Moveable top = (Moveable) info.getLeftOrTop();
                top.setY(info.getRightOrBottom().getY() - top.getHeight());
                float newSpeed = -top.getYSpeed() * top.getBounceFactor();
                if (Math.abs(newSpeed) < 2)
                    newSpeed = 0;
                top.setYSpeed(newSpeed);
            }
        }
    }

    /**
     * Handles physics collisions between moveable objects.
     * 
     * @param info the collision information
     */
    public void handlePhysicsCollisions(CollisionInfo info) {
        if (info.getDirection() == CollisionInfo.Direction.HORIZONTAL) {
            Moveable left = (Moveable) info.getLeftOrTop();
            Moveable right = (Moveable) info.getRightOrBottom();
            float xSpeedR = right.getXSpeed();
            float xSpeedL = left.getXSpeed();
            float sum = xSpeedL + xSpeedR;

            if (Math.abs(xSpeedL) > Math.abs(xSpeedR)) {
                right.setXSpeed(sum / 2);
                left.setXSpeed(-sum / 2);
            } else {
                right.setXSpeed(-sum / 2);
                left.setXSpeed(sum / 2);
            }

            left.setX(right.getX() - left.getWidth());
            right.setX(left.getX() + left.getWidth());
        } else if (info.getDirection() == CollisionInfo.Direction.VERTICAL) {
            Moveable top = (Moveable) info.getLeftOrTop();
            Moveable bottom = (Moveable) info.getRightOrBottom();
            float ySpeedT = top.getYSpeed();
            float ySpeedB = bottom.getYSpeed();
            float sum = ySpeedT + ySpeedB;

            if (Math.abs(ySpeedT) > Math.abs(ySpeedB)) {
                bottom.setYSpeed(sum / 2);
                top.setYSpeed(-sum / 2);
            } else {
                bottom.setYSpeed(-sum / 2);
                top.setYSpeed(sum / 2);
            }

            top.setY(bottom.getY() - top.getHeight());
            bottom.setY(top.getY() + top.getHeight());
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

    /**
     * Constructs a CollisionInfo object that stores information about the collision
     * between two Collidable objects.
     * 
     * @param first  the first Collidable object involved in the collision
     * @param second the second Collidable object involved in the collision
     */
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