import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        int width = 141;
        int height = 141;
        Maze maze = constructMazeFromScannerInput(sc, width, height);
        part1(maze);
        part2(maze);
    }

    private static void part1(Maze maze) {
        long smallestScore = maze.findSmallestScore("part1");
        System.out.printf("Part1: %d\n", smallestScore);
    }

    private static void part2(Maze maze) {
        maze.findSmallestScore("part2");
        System.out.printf("Part2: %d\n", maze.getGoodSeatsCount());
    }

    private static Maze constructMazeFromScannerInput(Scanner sc, int width, int height) {
        char[][] grid = new char[height][width];
        Coordinates startCoordinates = new Coordinates(-1, -1);
        Coordinates endCoordinates = new Coordinates(-1, -1);
        int i = 0;
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            for (int j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '.' || line.charAt(j) == '#') {
                    grid[i][j] = line.charAt(j);
                }
                else if (line.charAt(j) == 'S') {
                    startCoordinates.update(j, i);
                    grid[i][j] = '.';
                }
                else if (line.charAt(j) == 'E') {
                    endCoordinates.update(j, i);
                    grid[i][j] = '.';
                }
            }
            i++;
        }
        return new Maze(grid, startCoordinates, endCoordinates);
    }
}
