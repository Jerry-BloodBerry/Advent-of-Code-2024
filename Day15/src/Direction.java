public enum Direction {
    Up, Down, Left, Right;

    public static Direction fromChar(char direction) {
        return switch (direction) {
            case '^' -> Up;
            case 'v' -> Down;
            case '>' -> Right;
            case '<' -> Left;
            default -> throw new AssertionError("Invalid direction: " + direction);
        };
    }

    public static Direction Opposite(Direction direction) {
        return switch (direction) {
            case Up -> Down;
            case Down -> Up;
            case Left -> Right;
            case Right -> Left;
        };
    }
}
