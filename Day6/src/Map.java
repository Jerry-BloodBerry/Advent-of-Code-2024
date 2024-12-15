import java.util.HashSet;

class Map
{
    private final char[][] fields;
    private int visitedSpots = 0;
    private HashSet<Coordinates> visitedCoordinates = new HashSet<>();

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

    public void print()
    {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                System.out.print(fields[i][j]);
            }
            System.out.println();
        }
    }

    public boolean hasCoordinates(int x, int y)
    {
        return x >= 0 && y >= 0 && x < fields[0].length && y < fields.length;
    }

    public void removeObstacle(int x, int y) {
        fields[y][x] = '.';
    }

    public void printWithMarker(int x, int y) {
        for (int i = 0; i < fields.length; i++) {
            for (int j = 0; j < fields[i].length; j++) {
                if (i == y && j == x) {
                    System.out.print("M");
                } else {
                    System.out.print(fields[i][j]);
                }
            }
            System.out.println();
        }
    }

    public HashSet<Coordinates> getVisitedCoordinates() {
        return visitedCoordinates;
    }
}