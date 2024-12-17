import java.util.List;

public record ReindeerState(Coordinates currentCoordinates, Direction currentDirection, List<Coordinates> path, long cost) {
}
