public class Coordinates {
    private int x;
    private int y;

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void update(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void incrementInDirection(Direction direction) {
        switch (direction) {
            case Up -> y--;
            case Down -> y++;
            case Left -> x--;
            case Right -> x++;
        }
    }
}
