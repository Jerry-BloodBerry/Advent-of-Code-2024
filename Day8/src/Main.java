import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        int mapSideLength = 50;
        var map = new AntennaMap(new char[mapSideLength][mapSideLength]);
        int i = 0;
        while (sc.hasNextLine())
        {
            var line = sc.nextLine();
            for (int j = 0; j < line.length(); j++) {
                map.place(j, i, line.charAt(j));
            }
            i++;
        }
        part2(map);
    }

    public static void part1(AntennaMap map)
    {
        map.placeAntinodes();
        System.out.printf("Part 1: %d", map.getAntinodeCount());
    }

    public static void part2(AntennaMap map)
    {
        map.placeAntinodesWithResonantHarmonics();
        System.out.printf("Part 2: %d", map.getAntinodeCount());
    }
}
