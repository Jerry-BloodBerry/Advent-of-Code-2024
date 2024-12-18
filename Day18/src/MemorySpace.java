import java.util.*;

public class MemorySpace {
    private final char[][] grid;
    private final Coordinates start;
    private final Coordinates end;

    public MemorySpace(char[][] grid, Coordinates start, Coordinates end) {
        this.grid = grid;
        this.start = start;
        this.end = end;
    }

    public int findShortestPathToExit()
    {
        PriorityQueue<PartyState> queue = new PriorityQueue<>(Comparator.comparingInt(PartyState::getCurrentCost));
        HashSet<Coordinates> visited = new HashSet<>();
        queue.add(new PartyState(start, 0));
        List<Vector> vectorsToCheck = new ArrayList<>(List.of(
                new Vector(1, 0),
                new Vector(-1, 0),
                new Vector(0, 1),
                new Vector(0, -1)
        ));
        while (!queue.isEmpty()) {
            PartyState state = queue.poll();
            if (visited.contains(state.getCoordinates())) {
                continue;
            }
            if (state.coordinates.equals(end)) {
                return state.currentCost;
            }
            for (Vector vector : vectorsToCheck) {
                var coords = state.coordinates.add(vector);
                if (isPathTile(coords.getX(), coords.getY()) && !visited.contains(coords)) {
                    queue.add(new PartyState(coords, state.currentCost + 1));
                }
            }
            visited.add(state.coordinates);
        }
        return -1;
    }

    private boolean isPathTile(int x, int y) {
        return isOnGrid(x, y) && grid[y][x] == '.';
    }

    private boolean isOnGrid(int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        for (char[] chars : grid) {
            for (var j = 0; j < grid[0].length; j++) {
                sb.append(chars[j]);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
