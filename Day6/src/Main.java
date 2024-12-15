import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

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
        var labClone = new char[labMap.length][];
        for (int j = 0; j < labMap.length; j++) {
            labClone[j] = Arrays.copyOf(labMap[j], labMap[j].length);
        }
        Guard guard = new Guard(67, 89, Direction.Up, new Map(labMap));
        part1(guard); // 67, 89
        part2(new Guard(67, 89, Direction.Up, new Map(labClone)), guard.getMap().getVisitedCoordinates());
    }

    public static void part1(Guard guard) {
        while (guard.isOnTheMap()) {
            guard.walk();
        }
        System.out.printf("Part 1: %d%n", guard.getMap().getVisitedSpots());
    }

    public static void part2(Guard guard, HashSet<Coordinates> coordinatesToCheck) {
        var map = guard.getMap();
        var loopPlacementPositions = 0;
        for (var coord : coordinatesToCheck) {
            map.placeObstacle(coord.x(), coord.y());
            if (guard.isStuckInLoop()) {
                loopPlacementPositions++;
            }
            map.removeObstacle(coord.x(), coord.y());
        }
        System.out.printf("Part 2: %d%n", loopPlacementPositions);
    }
}
