import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        int[][] topographicMap = new int[45][45];
        Scanner sc = new Scanner(new File(url.getPath()));
        int n = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            for (int i = 0; i < line.length(); i++) {
                topographicMap[n][i] = line.charAt(i) - '0';
            }
            n++;
        }
        sc.close();
        part1(topographicMap);
        part2(topographicMap);
    }

    public static void part1(int[][] topographicMap) {
        var counter = new Counter();
        for (int i = 0; i < topographicMap.length; i++) {
            for (int j = 0; j < topographicMap[i].length; j++) {
                if (topographicMap[i][j] != 0) continue;
                exploreTrails(topographicMap, j, i, 0, new HashSet<>(), counter);
            }
        }
        System.out.printf("Part1: %d\n", counter.getValue());
    }

    public static void part2(int[][] topographicMap) {
        var counter = new Counter();
        for (int i = 0; i < topographicMap.length; i++) {
            for (int j = 0; j < topographicMap[i].length; j++) {
                if (topographicMap[i][j] != 0) continue;
                exploreTrails2(topographicMap, j, i, 0, counter);
            }
        }
        System.out.printf("Part2: %d\n", counter.getValue());
    }

    private static void exploreTrails(int[][] topographicMap, int x, int y, int currentHeight, HashSet<Point> visited, Counter scoreCounter) {
        if (currentHeight == 9 && !visited.contains(new Point(x, y))) {
            scoreCounter.increment();
            visited.add(new Point(x, y));
            return;
        }
        if (x - 1 >= 0 && topographicMap[y][x - 1] == currentHeight + 1) {
            exploreTrails(topographicMap, x - 1, y, currentHeight + 1, visited, scoreCounter);
        }
        if (x + 1 < topographicMap[0].length && topographicMap[y][x + 1] == currentHeight + 1) {
            exploreTrails(topographicMap, x + 1, y, currentHeight + 1, visited, scoreCounter);
        }
        if (y - 1 >= 0 && topographicMap[y - 1][x] == currentHeight + 1) {
            exploreTrails(topographicMap, x, y - 1, currentHeight + 1, visited, scoreCounter);
        }
        if (y + 1 < topographicMap.length && topographicMap[y + 1][x] == currentHeight + 1) {
            exploreTrails(topographicMap, x, y + 1, currentHeight + 1, visited, scoreCounter);
        }
    }

    private static void exploreTrails2(int[][] topographicMap, int x, int y, int currentHeight, Counter scoreCounter) {
        if (currentHeight == 9) {
            scoreCounter.increment();
            return;
        }
        if (x - 1 >= 0 && topographicMap[y][x - 1] == currentHeight + 1) {
            exploreTrails2(topographicMap, x - 1, y, currentHeight + 1, scoreCounter);
        }
        if (x + 1 < topographicMap[0].length && topographicMap[y][x + 1] == currentHeight + 1) {
            exploreTrails2(topographicMap, x + 1, y, currentHeight + 1, scoreCounter);
        }
        if (y - 1 >= 0 && topographicMap[y - 1][x] == currentHeight + 1) {
            exploreTrails2(topographicMap, x, y - 1, currentHeight + 1, scoreCounter);
        }
        if (y + 1 < topographicMap.length && topographicMap[y + 1][x] == currentHeight + 1) {
            exploreTrails2(topographicMap, x, y + 1, currentHeight + 1, scoreCounter);
        }
    }


}
