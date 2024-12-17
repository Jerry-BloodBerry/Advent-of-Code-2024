import java.util.*;

public class Maze {
    private final char[][] grid;
    private final Coordinates start;
    private final Coordinates end;

    public Maze(char[][] grid, Coordinates start, Coordinates end) {
        this.grid = grid;
        this.start = start;
        this.end = end;
    }

    public long findSmallestScore(String part) {
        PriorityQueue<ReindeerState> queue = new PriorityQueue<>(
                Comparator.comparingLong(ReindeerState::cost)
        );

        HashSet<String> visited = new HashSet<>();
        var startingPoint = new Coordinates(start.getX(), start.getY());
        queue.add(new ReindeerState(startingPoint, Direction.East, new ArrayList<>(List.of(startingPoint)), 0));
        long minCost = Long.MAX_VALUE;
        while (!queue.isEmpty()) {
            ReindeerState state = queue.poll();
            Coordinates pos = state.currentCoordinates();
            long cost = state.cost();

            List<Coordinates> updatedPath = new ArrayList<>(state.path());
            updatedPath.add(pos);

            String visitKey = pos.getY() + "-" + pos.getX() + "-" + state.currentDirection();
            visited.add(visitKey);

            // Check if we reached the end
            if (pos.equals(end)) {
                if (Objects.equals(part, "part1")) {
                    return cost;
                }
                if (cost > minCost) {
                    break;
                }
                minCost = cost;
                for (var coord : updatedPath) {
                    grid[coord.getY()][coord.getX()] = 'O'; // Mark the coordinates as a possibly good seat
                }
                continue;
            }

            // Explore neighbors
            for (Direction newDirection : Direction.values()) {
                int dx = getDx(newDirection);
                int dy = getDy(newDirection);
                Coordinates neighbor = new Coordinates(pos.getX() + dx, pos.getY() + dy);

                if (isPathTile(neighbor.getX(), neighbor.getY()) &&
                        !visited.contains(neighbor.getY() + "-" + neighbor.getX() + "-" + newDirection)) {
                    long newCost = cost + (newDirection == state.currentDirection() ? 1 : 1001);
                    queue.add(new ReindeerState(neighbor, newDirection, updatedPath, newCost));
                }
            }
        }

        return -1; // No valid path
    }

    private int getDx(Direction direction) {
        return switch (direction) {
            case East -> 1;
            case West -> -1;
            default -> 0;
        };
    }

    private int getDy(Direction direction) {
        return switch (direction) {
            case North -> -1;
            case South -> 1;
            default -> 0;
        };
    }

    private boolean isPathTile(int x, int y) {
        return isOnGrid(x, y) && (grid[y][x] == '.' || grid[y][x] == 'O');
    }

    private boolean isOnGrid(int x, int y) {
        return x >= 0 && x < grid[0].length && y >= 0 && y < grid.length;
    }

    public long getGoodSeatsCount() {
        long seatsCount = 0;
        for (char[] chars : grid) {
            for (char c : chars) {
                if (c == 'O') {
                    seatsCount++;
                }
            }
        }
        return seatsCount;
    }

    public void printGrid() {
        for (char[] chars : grid) {
            for (char c : chars) {
                System.out.print(c);
            }
            System.out.println();
        }
    }
}
