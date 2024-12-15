import java.util.HashSet;

public class Map
{
    private final char[][] fields;
    private int visitedSpots = 0;
    private final HashSet<Coordinates> visitedCoordinates = new HashSet<>();

    public Map(char[][] fields)
    {
        this.fields = fields;
    }

    public void markVisited(int x, int y)
    {
        if (!isVisited(x, y))
        {
            fields[y][x] = 'X';
            visitedSpots++;
            visitedCoordinates.add(new Coordinates(x, y));
        }
    }

    private boolean isVisited(int x, int y) {
        return fields[y][x] == 'X';
    }

    public boolean isObstacle(int x, int y) {
        return fields[y][x] == '#';
    }

    public int getVisitedSpots() {
        return visitedSpots;
    }

    public void placeObstacle(int x, int y) {
        fields[y][x] = '#';
    }

    public boolean hasCoordinates(int x, int y)
    {
        return x >= 0 && y >= 0 && x < fields[0].length && y < fields.length;
    }

    public void removeObstacle(int x, int y) {
        fields[y][x] = '.';
    }

    public HashSet<Coordinates> getVisitedCoordinates() {
        return visitedCoordinates;
    }
}