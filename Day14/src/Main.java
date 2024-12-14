import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        Grid grid = new Grid(new GridDimensions(101, 103));
        while (sc.hasNextLine()) {
            var pv = sc.nextLine().split(" ");
            String p = pv[0];
            String v = pv[1];
            var coordinates = Arrays.stream(p.substring(2).split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            var velocityVector = Arrays.stream(v.substring(2).split(",")).map(Integer::parseInt).toArray(Integer[]::new);
            grid.addRobot(coordinates[0], coordinates[1], new VelocityVector(velocityVector[0], velocityVector[1]));
        }
        sc.close();
        for (int i = 0; i < 10000; i++) {
            grid.moveAllRobots();
            if (i == 99) {
                System.out.printf("Part1: %d", grid.getQuadrantScoresMultiplied());
            }
            if (grid.getCountInCenter() > grid.getCountInQuadrants()) {
                grid.printGrid(i+1);
                // check the outputImages folder for part 2 result
            }
        }
    }
}
