import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        List<List<Character>> tilesList = new ArrayList<>();
        Coordinates robotCoordinates = new Coordinates(-1, -1);
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.isEmpty()) break;
            var lineList = new ArrayList<Character>();
            for (char c : line.toCharArray()) {
                if (c == '@') {
                    robotCoordinates = new Coordinates(line.indexOf('@'), tilesList.size());
                    lineList.add('.');

                } else {
                    lineList.add(c);
                }
            }
            tilesList.add(lineList);
        }
        WarehouseMap warehouseMap = new WarehouseMap(tilesList, robotCoordinates);
        while (sc.hasNextLine()) {
            String robotMoveSequence = sc.nextLine();
            for (int i = 0; i < robotMoveSequence.length(); i++) {
                warehouseMap.moveRobot(Direction.fromChar(robotMoveSequence.charAt(i)));
            }
        }
        sc.close();
        System.out.printf("Part1: %d", warehouseMap.getSumOfBoxesCoordinates());
    }
}
