import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Main {

    public static void part1() throws FileNotFoundException {
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();
        readInput("input.txt", left, right);
        int sumOfDifferences = 0; // for Part 1
        while (!left.isEmpty() && !right.isEmpty()) {
            sumOfDifferences += Math.abs(left.poll() - right.poll());

        }
        System.out.printf("Sum of differences is: %d", sumOfDifferences);
    }

    public static void part2() throws FileNotFoundException {
        PriorityQueue<Integer> left = new PriorityQueue<>();
        PriorityQueue<Integer> right = new PriorityQueue<>();
        readInput("input.txt", left, right);
        int similarityScore = 0;
        int currentLeft = left.poll();
        int currentRight = right.poll();
        int occurences = 0;
        while (!left.isEmpty() && !right.isEmpty()) {
            while(currentLeft == currentRight)
            {
                occurences++;
                currentRight = right.poll();
            }
            similarityScore += (currentLeft * occurences);
            occurences = 0;
            int newLeft = left.poll();
            while (!left.isEmpty() && newLeft == currentLeft) {
                newLeft = left.poll();
            }
            currentLeft = newLeft;
            while (!right.isEmpty() && currentRight < currentLeft)
            {
                currentRight = right.poll();
            }
        }
        System.out.printf("Similarity score is: %d", similarityScore);
    }

    public static void readInput(String fileName, PriorityQueue<Integer> left, PriorityQueue<Integer> right) throws FileNotFoundException {
        URL url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner scanner = new Scanner(new File(url.getPath()));
        while (scanner.hasNextInt())
        {
            left.add(scanner.nextInt());
            right.add(scanner.nextInt());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        part2();
    }
}
