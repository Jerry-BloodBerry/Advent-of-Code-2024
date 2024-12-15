public class Claw {
    Coordinates currentCoordinates;

    public Claw() {
        currentCoordinates = new Coordinates(0, 0);
    }

    public void move(Vector shiftVector) {
        currentCoordinates.update(shiftVector);
    }
}
