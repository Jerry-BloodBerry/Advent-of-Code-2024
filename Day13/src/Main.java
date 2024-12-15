import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        part1();
        part2();
    }

    public static void part1() throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        long sumTokens = 0;
        var i = 0;
        while (sc.hasNextLine()) {
            String aLine = sc.nextLine();
            String bLine = sc.nextLine();
            String prizeLine = sc.nextLine();
            if (sc.hasNextLine()) sc.nextLine();
            var aButton = parseFromInputLine(aLine, 3);
            var bButton = parseFromInputLine(bLine, 1);
            var prizeCoordinates = parsePrizeCoordinates(prizeLine);
            var clawMachine = new ClawMachine(aButton, bButton, prizeCoordinates);
            clawMachine.runSimulation();
            if (clawMachine.getMinTokensSpent() != -1) {
                sumTokens += clawMachine.getMinTokensSpent();
                System.out.printf("Possible to win prize on machine %d\n", ++i);
            } else {
                ++i;
            }
        }
        System.out.println("Part1: " + sumTokens);
    }

    public static void part2() throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        long sumTokens = 0;
        var i = 0;
        while (sc.hasNextLine()) {
            String aLine = sc.nextLine();
            String bLine = sc.nextLine();
            String prizeLine = sc.nextLine();
            if (sc.hasNextLine()) sc.nextLine();
            var aButton = parseFromInputLine(aLine, 3);
            var bButton = parseFromInputLine(bLine, 1);
            var prizeCoordinates = parsePrizeCoordinates(prizeLine, 10000000000000L);
            var clawMachine = new ClawMachine(aButton, bButton, prizeCoordinates);
            clawMachine.runSimulation2();
            if (clawMachine.getMinTokensSpent() != -1) {
                sumTokens += clawMachine.getMinTokensSpent();
                System.out.printf("Possible to win prize on machine %d\n", ++i);
            } else {
                ++i;
            }
        }
        System.out.println("Part2: " + sumTokens);
    }

    public static Button parseFromInputLine(String line, int pricePerPress) {
        var stringCoords = line.split(": ")[1].split(", ");
        return new Button(new Vector(Long.parseLong(stringCoords[0].split("\\+")[1]), Long.parseLong(stringCoords[1].split("\\+")[1])), pricePerPress);
    }

    public static Coordinates parsePrizeCoordinates(String line) {
        var stringCoords = line.split(": ")[1].split(", ");
        return new Coordinates(Long.parseLong(stringCoords[0].split("=")[1]), Long.parseLong(stringCoords[1].split("=")[1]));
    }

    public static Coordinates parsePrizeCoordinates(String line, long conversionError) {
        var stringCoords = line.split(": ")[1].split(", ");
        return new Coordinates(Long.parseLong(stringCoords[0].split("=")[1]) + conversionError, Long.parseLong(stringCoords[1].split("=")[1]) + conversionError);
    }
}
