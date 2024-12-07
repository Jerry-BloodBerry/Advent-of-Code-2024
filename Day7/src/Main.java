import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class Equation {
    private final long testValue;
    private final long[] components;

    public enum Operator {
        Add,
        Times,
        Concat
    }

    public Equation(long testValue, long[] components) {
        this.testValue = testValue;
        this.components = components;
    }

    public long getTestValue() {
        return testValue;
    }

    public boolean evaluate(Operator[] allowedOperators) {
        return evaluate(components[0], 0, allowedOperators);
    }

    private boolean evaluate(long currentValue, int index, Operator[] allOperators) {
        if (currentValue == testValue && index == components.length - 1) {
            return true;
        } else if (index == components.length - 1) {
            return false;
        }

        for (var operator : allOperators) {
            long newValue = apply(operator, currentValue, components[index + 1]);
            if (evaluate(newValue, index + 1, allOperators)) {
                return true;
            }
        }
        return false;
    }

    private long apply(Operator operator, long x, long y) {
        return switch (operator) {
            case Add -> x + y;
            case Times -> x * y;
            case Concat -> concatNumbers(x, y);
        };
    }

    private static long concatNumbers(long x, long y) {
        long multiplier = 1;
        while (y >= multiplier) {
            multiplier *= 10;
        }
        return x * multiplier + y;
    }
}

class EquationParser {
    static Equation parse(String equation) {
        var splitEquation = equation.split(": ");
        var testValue = Long.parseLong(splitEquation[0]);
        var splitComponents = splitEquation[1].split(" ");
        long[] components = new long[splitComponents.length];
        int i = 0;
        for (String component : splitComponents) {
            components[i] = Long.parseLong(component);
            i++;
        }
        return new Equation(testValue, components);
    }
}

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        part2(sc);
    }

    public static void part1(Scanner sc) {
        long calibrationSumPart1 = 0;
        while (sc.hasNextLine()) {
            var equation = EquationParser.parse(sc.nextLine());
            if (equation.evaluate(new Equation.Operator[]{Equation.Operator.Add, Equation.Operator.Times})) {
                calibrationSumPart1 += equation.getTestValue();
            }
        }
        System.out.printf("Part 1: %d%n", calibrationSumPart1);
    }

    private static void part2(Scanner sc) {
        long calibrationSumPart2 = 0;
        while (sc.hasNextLine()) {
            var equation = EquationParser.parse(sc.nextLine());
            if (equation.evaluate(new Equation.Operator[]{Equation.Operator.Add, Equation.Operator.Times, Equation.Operator.Concat})) {
                calibrationSumPart2 += equation.getTestValue();
            }
        }
        System.out.printf("Part 2: %d%n", calibrationSumPart2);
    }
}
