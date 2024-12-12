import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        int side = 140;
        char[][] gardenBeds = new char[side][side];
        boolean[][] visited = new boolean[side][side];
        int i = 0;
        while (sc.hasNextLine()) {
            var line = sc.nextLine();
            for (int j = 0; j < line.length(); j++) {
                gardenBeds[i][j] = line.charAt(j);
            }
            i++;
        }
        sc.close();
        var calculator = new FencePriceCalculator();
        int sumOfPrices = 0;
        int sumOfDiscountedPrices = 0;
        for (int k = 0; k < gardenBeds.length; k++) {
            for (int j = 0; j < gardenBeds[k].length; j++) {
                if (!visited[k][j]) {
                    visit(gardenBeds, visited, j, k, calculator, gardenBeds[k][j]);
                    sumOfPrices += calculator.getTotalPrice();
                    sumOfDiscountedPrices += calculator.getDiscountedPrice();
                    calculator.reset();
                }
            }
        }
        System.out.printf("Part1: %d\n", sumOfPrices);
        System.out.printf("Part2: %d\n", sumOfDiscountedPrices);
    }

    private static void visit(char[][] gardenBeds, boolean[][] visited, int x, int y, FencePriceCalculator calculator, Character bedType) {
        visited[y][x] = true;
        calculator.incrementArea();
        if (x - 1 >= 0 && !visited[y][x - 1] && gardenBeds[y][x - 1] == bedType) {
            visit(gardenBeds, visited, x - 1, y, calculator, bedType);
        } else if (x - 1 < 0 || gardenBeds[y][x - 1] != bedType) {
            calculator.addBorder(new FenceBorder(FenceBorder.Side.Left, new Point(x, y)));
        }
        if (x + 1 < gardenBeds[0].length && !visited[y][x + 1] && gardenBeds[y][x + 1] == bedType) {
            visit(gardenBeds, visited, x + 1, y, calculator, bedType);
        } else if (x + 1 >= gardenBeds[0].length || gardenBeds[y][x + 1] != bedType) {
            calculator.addBorder(new FenceBorder(FenceBorder.Side.Right, new Point(x, y)));
        }
        if (y - 1 >= 0 && !visited[y - 1][x] && gardenBeds[y - 1][x] == bedType) {
            visit(gardenBeds, visited, x, y - 1, calculator, bedType);
        } else if (y - 1 < 0 || gardenBeds[y - 1][x] != bedType) {
            calculator.addBorder(new FenceBorder(FenceBorder.Side.Top, new Point(x, y)));
        }
        if (y + 1 < gardenBeds.length && !visited[y + 1][x] && gardenBeds[y + 1][x] == bedType) {
            visit(gardenBeds, visited, x, y + 1, calculator, bedType);
        } else if (y + 1 >= gardenBeds.length || gardenBeds[y + 1][x] != bedType) {
            calculator.addBorder(new FenceBorder(FenceBorder.Side.Bottom, new Point(x, y)));
        }
    }
}
