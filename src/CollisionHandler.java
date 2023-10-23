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
                    if (collidable instanceof Moveable) {
                        handlePhysicsCollisions(info);
                        moveable.onCollision(info);
                    } else {
                        moveable.onCollision(info);
                        collidable.onCollision(info);
                    }
                }
            }
        }
    }


    public void handlePhysicsCollisions(CollisionInfo info){
       Moveable Left = (Moveable) info.getLeftOrTop();
       Moveable Right = (Moveable) info.getRightOrBottom();
       float XSpeedR = Right.getXSpeed();
       float XSpeedL = Left.getXSpeed();
        if(Math.abs(XSpeedL)>=Math.abs(XSpeedR)){
           Right.setXSpeed(XSpeedL+XSpeedR);
           Left.setXSpeed(0f);
       }else if(Math.abs(XSpeedR)>=Math.abs(XSpeedL)){
            Left.setXSpeed(XSpeedR+XSpeedL);
            Right.setXSpeed(0f);
        }
    }

    private boolean isCollision(Collidable a, Collidable b) {
        return a.getX() + a.getWidth() > b.getX() && a.getX() < b.getX() + b.getWidth() && a.getY() + a.getHeight() > b.getY()
                && a.getY() < b.getY() + b.getHeight();
    }

    private CollisionInfo getCollisionInfo(Collidable a, Collidable b) {
        int leftX = Math.max(a.getX(), b.getX());
        int rightX = Math.min(a.getX() + a.getWidth(), b.getX() + b.getWidth());
        int topY = Math.max(a.getY(), b.getY());
        int bottomY = Math.min(a.getY() + a.getHeight(), b.getY() + b.getHeight());

        int width = rightX - leftX;
        int height = bottomY - topY;

        if (width < height) {
            if (a.getX() < b.getX()) {
                return new CollisionInfo(a, b, CollisionInfo.Direction.HORIZONTAL, a);
            } else {
                return new CollisionInfo(a, b, CollisionInfo.Direction.HORIZONTAL, b);
            }
        } else {
            if (a.getY() < b.getY()) {
                return new CollisionInfo(a, b, CollisionInfo.Direction.VERTICAL, a);
            } else {
                return new CollisionInfo(a, b, CollisionInfo.Direction.VERTICAL, b);
            }
        }
    }
}

class CollisionInfo {
    private Collidable leftOrTop;
    private Collidable rightOrBottom;

    enum Direction {
        HORIZONTAL, VERTICAL
    }

    private Direction direction;

    public CollisionInfo(Collidable first, Collidable second, Direction direction, Collidable leftOrTop) {
        this.direction = direction;
        this.leftOrTop = leftOrTop;
        this.rightOrBottom = first == leftOrTop ? second : first;
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