import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


class Guard {
    public enum Direction {
        Up,
        Down,
        Right,
        Left
    }

    private int x;
    private int y;
    private Direction direction;
    private final char[][] labMap;
    private boolean isOnTheMap;
    private int visitedSpots;

    public Guard(int x, int y, Direction direction, char[][] labMap) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.labMap = labMap;
        this.isOnTheMap = wouldStillBeOnTheMap(x, y);
        visitedSpots = isOnTheMap ? 1 : 0;
        if (isOnTheMap)
        {
            this.labMap[y][x] = 'X';
        }
    }

    public void walk() {
        int tempX = x;
        int tempY = y;
        switch (direction) {
            case Up -> tempY--;
            case Down -> tempY++;
            case Right -> tempX++;
            case Left -> tempX--;
        }
        if (!wouldStillBeOnTheMap(tempX, tempY)) {
            isOnTheMap = false;
            return; // indicates that the guard left the map
        }
        if (isObstacle(tempX, tempY)) {
            switchDirection();
            return;
        }
        visit(tempX, tempY);
    }

    private void visit(int x, int y) {
        if (!isVisited(x, y)) {
            labMap[y][x] = 'X';
            visitedSpots++;
        }
        this.x = x;
        this.y = y;
    }

    private boolean isVisited(int x, int y) {
        return labMap[y][x] == 'X';
    }

    private boolean isObstacle(int x, int y) {
        return labMap[y][x] == '#';
    }

    private boolean wouldStillBeOnTheMap(int x, int y) {
        return x >= 0 && y >= 0 && x < labMap[0].length && y < labMap.length;
    }

    public void switchDirection() {
        direction = switch (direction) {
            case Up -> Direction.Right;
            case Down -> Direction.Left;
            case Right -> Direction.Down;
            case Left -> Direction.Up;
        };
    }

    public boolean isOnTheMap() {
        return isOnTheMap;
    }

    public int getVisitedSpots() {
        return visitedSpots;
    }

    public void printMap()
    {
        for (int i = 0; i < labMap.length; i++) {
            for (int j = 0; j < labMap[i].length; j++) {
                System.out.print(labMap[i][j]);
            }
            System.out.println();
        }
    }
}

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        char[][] labMap = new char[130][130];
        int i = 0;
        while (sc.hasNextLine()) {
            var line = sc.nextLine();
            for (int j = 0; j < line.length(); j++) {
                labMap[i][j] = line.charAt(j);
            }
            i++;
        }
        Guard guard = new Guard(67, 89, Guard.Direction.Up, labMap);
        while (guard.isOnTheMap()) {
            guard.walk();
        }
        System.out.printf("Part 1: %d", guard.getVisitedSpots());
    }
}
