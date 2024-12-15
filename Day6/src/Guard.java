import java.util.HashSet;

public class Guard {

    private int x;
    private int y;
    private Direction direction;
    private final Map map;
    private boolean isOnTheMap;

    public Guard(int x, int y, Direction direction, Map map) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.map = map;
        this.isOnTheMap = map.hasCoordinates(x, y);
        if (isOnTheMap)
        {
            this.map.markVisited(x, y);
        }
    }

    public void walk() {
        int[] newCoordinates = getNextFieldCoordinates();
        if (!map.hasCoordinates(newCoordinates[0], newCoordinates[1])) {
            isOnTheMap = false;
            return; // indicates that the guard left the map
        }
        if (map.isObstacle(newCoordinates[0], newCoordinates[1])) {
            switchDirection();
            return;
        }
        visit(newCoordinates[0], newCoordinates[1]);
    }

    public int[] getNextFieldCoordinates()
    {
        int tempX = x;
        int tempY = y;
        switch (direction) {
            case Up -> tempY--;
            case Down -> tempY++;
            case Right -> tempX++;
            case Left -> tempX--;
        }
        return new int[] {tempX, tempY};
    }

    private void visit(int x, int y) {
        map.markVisited(x, y);
        this.x = x;
        this.y = y;
    }

    public void switchDirection() {
        direction = switch (direction) {
            case Up -> Direction.Right;
            case Down -> Direction.Left;
            case Right -> Direction.Down;
            case Left -> Direction.Up;
        };
    }

    public Map getMap() {
        return map;
    }

    public boolean isOnTheMap() {
        return isOnTheMap;
    }

    public boolean isStuckInLoop() {
        int initialX = this.x;
        int initialY = this.y;
        Direction initialDirection = direction;
        boolean isLoop = false;
        HashSet<DirectedCoordinates> visitedCoordinates = new HashSet<>();
        visitedCoordinates.add(new DirectedCoordinates(this.x, this.y, initialDirection));
        while (this.isOnTheMap()) {
            this.walk();
            var newCoordinates = new DirectedCoordinates(this.x, this.y, this.direction);
            if (this.isOnTheMap && !visitedCoordinates.add(newCoordinates)) {
                isLoop = true;
                break;
            }
        }
        resetPositionTo(initialX, initialY, initialDirection);
        return isLoop;
    }

    private void resetPositionTo(int x, int y, Direction direction)
    {
        this.x = x;
        this.y = y;
        this.isOnTheMap = true;
        this.direction = direction;
    }
}
