import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        URL url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        char[][] grid = new char[140][140];
        for (int i = 0; i < grid.length; i++) {
            var line = sc.nextLine();
            for (int j = 0; j < grid[i].length; j++) {
                grid[i][j] = line.charAt(j);
            }
        }
        part1(grid);
        part2(grid);
    }

    private static void part1(char[][] grid)
    {
        String searchWord = "XMAS";
        int totalWords = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == searchWord.charAt(0))
                {
                    totalWords += checkToTheRight(grid, i, j, searchWord) ? 1 : 0;
                    totalWords += checkToTheLeft(grid, i, j, searchWord) ? 1 : 0;
                    totalWords += checkTopToBottom(grid, i, j, searchWord) ? 1 : 0;
                    totalWords += checkBottomToTop(grid, i, j, searchWord) ? 1 : 0;
                    totalWords += checkDiagonalBottomLeft(grid, i , j, searchWord) ? 1 : 0;
                    totalWords += checkDiagonalBottomRight(grid, i , j, searchWord) ? 1 : 0;
                    totalWords += checkDiagonalTopLeft(grid, i , j, searchWord) ? 1 : 0;
                    totalWords += checkDiagonalTopRight(grid, i , j, searchWord) ? 1 : 0;
                }
            }
        }
        System.out.printf("Part 1: %d%n", totalWords);
    }

    private static void part2(char[][] grid)
    {
        String searchWord = "MAS";
        String reverseSearchWord = "SAM";
        int totalWords = 0;
        for (int i = 1; i < grid.length - 1; i++) {
            for (int j = 1; j < grid[i].length - 1; j++) {
                if (isXShape(grid, i, j, searchWord) || isXShape(grid, i, j, reverseSearchWord)) {
                    totalWords++;
                }
            }
        }
        System.out.printf("Part 2: %d%n", totalWords);
    }

    private static boolean isXShape(char[][] grid, int i, int j, String word) {
        return grid[i - 1][j - 1] == word.charAt(0) &&  // Top-left
                grid[i][j] == word.charAt(1) &&          // Center
                grid[i + 1][j + 1] == word.charAt(2) &&  // Bottom-right
                // The other diagonal
                ((grid[i - 1][j + 1] == word.charAt(2) &&  grid[i + 1][j - 1] == word.charAt(0)) || (grid[i - 1][j + 1] == word.charAt(0) &&  grid[i + 1][j - 1] == word.charAt(2)));
    }


    private static boolean checkToTheRight(char[][] grid, int i, int j, String searchWord) {
        int currentChar = 0;
        for (int k = j; k < grid[i].length && currentChar < searchWord.length(); k++) {
            if (grid[i][k] != searchWord.charAt(currentChar)) break;
            currentChar++;
        }
        return currentChar == searchWord.length();
    }

    private static boolean checkToTheLeft(char[][] grid, int i, int j, String searchWord) {
        int currentChar = 0;
        for (int k = j; k >= 0 && currentChar < searchWord.length(); k--) {
            if (grid[i][k] != searchWord.charAt(currentChar)) break;
            currentChar++;
        }

        return currentChar == searchWord.length();
    }

    private static boolean checkTopToBottom(char[][] grid, int i, int j, String searchWord) {
        int currentChar = 0;
        for (int k = i; k < grid.length && currentChar < searchWord.length(); k++) {
            if (grid[k][j] != searchWord.charAt(currentChar)) break;
            currentChar++;
        }
        return currentChar == searchWord.length();
    }

    private static boolean checkBottomToTop(char[][] grid, int i, int j, String searchWord) {
        int currentChar = 0;
        for (int k = i; k >= 0 && currentChar < searchWord.length(); k--) {
            if (grid[k][j] != searchWord.charAt(currentChar)) break;
            currentChar++;
        }
        return currentChar == searchWord.length();
    }

    private static boolean checkDiagonalBottomRight(char[][] grid, int i, int j, String searchWord) {
        int currentChar = 0;
        while (i < grid.length && j < grid[0].length && currentChar < searchWord.length())
        {
            if (grid[i][j] != searchWord.charAt(currentChar)) break;
            i++;
            j++;
            currentChar++;
        }
        return currentChar == searchWord.length();
    }

    private static boolean checkDiagonalBottomLeft(char[][] grid, int i, int j, String searchWord) {
        int currentChar = 0;
        while (i < grid.length && j >= 0 && currentChar < searchWord.length())
        {
            if (grid[i][j] != searchWord.charAt(currentChar)) break;
            i++;
            j--;
            currentChar++;
        }
        return currentChar == searchWord.length();
    }

    private static boolean checkDiagonalTopRight(char[][] grid, int i, int j, String searchWord) {
        int currentChar = 0;
        while (i >= 0 && j < grid[0].length && currentChar < searchWord.length())
        {
            if (grid[i][j] != searchWord.charAt(currentChar)) break;
            i--;
            j++;
            currentChar++;
        }
        return currentChar == searchWord.length();
    }

    private static boolean checkDiagonalTopLeft(char[][] grid, int i, int j, String searchWord) {
        int currentChar = 0;
        while (i >= 0 && j >= 0 && currentChar < searchWord.length())
        {
            if (grid[i][j] != searchWord.charAt(currentChar)) break;
            i--;
            j--;
            currentChar++;
        }
        return currentChar == searchWord.length();
    }
}
