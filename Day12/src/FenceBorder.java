import java.util.Objects;

public class FenceBorder {
    public enum Side {
        Top,
        Bottom,
        Left,
        Right
    }
    Side side;
    Point location;

    public FenceBorder(Side side, Point location) {
        this.side = side;
        this.location = location;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FenceBorder other) {
            return this.side == other.side && this.location.equals(other.location);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(side, location);
    }
}
