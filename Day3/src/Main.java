import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        var sc = new Scanner(new File(url.getPath()));
        var sb = new StringBuilder();
        while (sc.hasNextLine())
        {
            sb.append(sc.nextLine().strip());
        }
        var joinedString = sb.toString();
        part1(joinedString);
        part2(joinedString);
    }

    public static void part1(String input)
    {
        Matcher regexMatcher = Pattern.compile("mul\\(\\d+,\\d+\\)").matcher(input);
        int sumOfMulPart1 = 0;
        while (regexMatcher.find())
        {
            var match = regexMatcher.group();
            var stringNumbers = (match.substring(4, match.length() - 1)).split(",");
            sumOfMulPart1 += Integer.parseInt(stringNumbers[0]) * Integer.parseInt(stringNumbers[1]);
        }

        System.out.printf("Part 1: %d%n", sumOfMulPart1);
    }

    public static void part2(String input)
    {
        Matcher regexMatcher = Pattern.compile("mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\)").matcher(input);
        int sumOfMulPart2 = 0;
        boolean enabled = true;
        while (regexMatcher.find())
        {
            var match = regexMatcher.group();
            switch (match) {
                case "do()":
                    enabled = true;
                    break;
                case "don't()":
                    enabled = false;
                    break;
                default:
                    if (enabled)
                    {
                        var stringNumbers = (match.substring(4, match.length() - 1)).split(",");
                        sumOfMulPart2 += Integer.parseInt(stringNumbers[0]) * Integer.parseInt(stringNumbers[1]);
                    }
                    break;
            }
        }

        System.out.printf("Part 2: %d%n", sumOfMulPart2);
    }
}
