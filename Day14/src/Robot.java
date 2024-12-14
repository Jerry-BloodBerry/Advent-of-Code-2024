public class Robot {
    private int x;
    private int y;
    private final VelocityVector velocityVector;
    private final Grid grid;

    public Robot(int initialX, int initialY, VelocityVector velocityVector, Grid grid) {
        this.x = initialX;
        this.y = initialY;
        this.velocityVector = velocityVector;
        this.grid = grid;
    }

    public void move() {
        int newX = x + velocityVector.vX();
        int newY = y + velocityVector.vY();
        // teleportation logic
        if (newX >= grid.getDimensions().width()) {
            newX = newX % grid.getDimensions().width();
        } else if (newX < 0) {
            newX = grid.getDimensions().width() + (newX % grid.getDimensions().width());
        }
        if (newY >= grid.getDimensions().height()) {
            newY = newY % grid.getDimensions().height();
        }
        else if (newY < 0) {
            newY = grid.getDimensions().height() + (newY % grid.getDimensions().height());
        }
        this.x = newX;
        this.y = newY;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
