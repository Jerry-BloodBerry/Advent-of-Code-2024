import java.util.Objects;

public class Vector {

    public static Vector zero = new Vector(0, 0);

    private long dX;
    private long dY;

    public Vector(long dX, long dY) {
        this.dX = dX;
        this.dY = dY;
    }

    public boolean isColinearWith(Vector v) {
        return this.dX * v.dY - this.dY * v.dX == 0;
    }

    public long getdX() {
        return dX;
    }

    public long getdY() {
        return dY;
    }

    public Vector multiplyByScalar(long scalar) {
        return new Vector(this.dX * scalar, this.dY * scalar);
    }

    public Vector add(Vector v) {
        return new Vector(this.dX + v.dX, this.dY + v.dY);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Vector vector)) return false;
        return getdX() == vector.getdX() && getdY() == vector.getdY();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getdX(), getdY());
    }

    public Vector oppositeVector() {
        return new Vector(-dX, -dY);
    }
}
