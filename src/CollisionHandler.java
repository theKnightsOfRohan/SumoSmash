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
                    CollisionInfo info = getCollisionInfo(moveable, collidable);
                    moveable.onCollision(info);
                    collidable.onCollision(info);
                }
            }
        }
    }

    private boolean isCollision(Moveable moveable, Collidable collidable) {
        return moveable.getX() + moveable.getWidth() > collidable.getX() && moveable.getX() < collidable.getX() + collidable.getWidth()
                && moveable.getY() + moveable.getHeight() > collidable.getY() && moveable.getY() < collidable.getY() + collidable.getHeight();
    }

    private CollisionInfo getCollisionInfo(Moveable moveable, Collidable collidable) {
        int leftX = Math.max(moveable.getX(), collidable.getX());
        int rightX = Math.min(moveable.getX() + moveable.getWidth(), collidable.getX() + collidable.getWidth());
        int topY = Math.max(moveable.getY(), collidable.getY());
        int bottomY = Math.min(moveable.getY() + moveable.getHeight(), collidable.getY() + collidable.getHeight());

        int width = rightX - leftX;
        int height = bottomY - topY;

        if (width < height) {
            if (moveable.getX() < collidable.getX()) {
                return new CollisionInfo(moveable, collidable, CollisionInfo.Direction.HORIZONTAL, moveable);
            } else {
                return new CollisionInfo(moveable, collidable, CollisionInfo.Direction.HORIZONTAL, collidable);
            }
        } else {
            if (moveable.getY() < collidable.getY()) {
                return new CollisionInfo(moveable, collidable, CollisionInfo.Direction.VERTICAL, moveable);
            } else {
                return new CollisionInfo(moveable, collidable, CollisionInfo.Direction.VERTICAL, collidable);
            }
        }
    }
}

class CollisionInfo {
    private Collidable a;
    private Collidable b;

    enum Direction {
        HORIZONTAL, VERTICAL
    }

    private Collidable leftOrTopCollidable;

    private Direction direction;

    public CollisionInfo(Collidable a, Collidable b, Direction direction, Collidable leftOrTopCollidable) {
        this.a = a;
        this.b = b;
        this.direction = direction;
        this.leftOrTopCollidable = leftOrTopCollidable;
    }

    public Collidable getA() {
        return a;
    }

    public Collidable getB() {
        return b;
    }

    public Direction getDirection() {
        return direction;
    }

    public Collidable getLeftOrTopCollidable() {
        return leftOrTopCollidable;
    }
}