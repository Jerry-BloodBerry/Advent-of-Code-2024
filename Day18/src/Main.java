import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        char[][] grid = new char[71][71];
//        part1(grid, sc, 1024);
        part2(grid, sc);
        sc.close();
    }

    public static void part1(char[][] grid, Scanner sc, int bytesFallen) {
        for (char[] chars : grid) {
            Arrays.fill(chars, '.');
        }
        var i = 0;
        while (sc.hasNextLine() && i < bytesFallen) {
            String[] lineCoords = sc.nextLine().split(",");
            grid[Integer.parseInt(lineCoords[1])][Integer.parseInt(lineCoords[0])] = '#';
            i++;
        }
        var memorySpace = new MemorySpace(grid, new Coordinates(0,0), new Coordinates(grid.length - 1, grid[grid.length - 1].length - 1));
        var pathLength = memorySpace.findShortestPathToExit();
        System.out.printf("Part1: %d\n", pathLength);
    }

    public static void part2(char[][] grid, Scanner sc) {
        for (char[] chars : grid) {
            Arrays.fill(chars, '#');
        }
        LinkedHashSet<Coordinates> corruptedMemoryLocations = new LinkedHashSet<>();
        while (sc.hasNextLine()) {
            String[] lineCoords = sc.nextLine().split(",");
            corruptedMemoryLocations.add(new Coordinates(Integer.parseInt(lineCoords[0]), Integer.parseInt(lineCoords[1])));
        }
        var uf = new WeightedQuickUnion(grid.length * grid.length);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                var coord = new Coordinates(i, j);
                if (!corruptedMemoryLocations.contains(coord)) {
                    uf.markOpen(coord);
                }
            }
        }
        var start = new Coordinates(0, 0);
        var end = new Coordinates(grid.length - 1, grid[grid.length - 1].length - 1);
        if (uf.connected(start, end)) {
            System.out.println("Paths were still connected after all corruptions");
            return;
        }
        var list = corruptedMemoryLocations.stream().toList();
        for (int i = list.size() - 1; i >= 0; i--) {
            uf.markOpen(list.get(i));
            if (uf.connected(start, end)) {
                System.out.printf("Part2: %s\n", list.get(i).toString());
                return;
            }
        }
    }
}
