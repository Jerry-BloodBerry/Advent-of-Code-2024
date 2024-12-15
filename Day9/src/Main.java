import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        String diskMap = sc.nextLine();
        sc.close();
        var rearrangedDiskMap = rearrange(diskMap);
        System.out.printf("Part 1: %d%n", checksum(rearrangedDiskMap));
    }

    public static long checksum(List<Integer> rearrangedDisk) {
        long result = 0;
        int i = 0;
        for (var num : rearrangedDisk) {
            result += (long) i * num;
            i++;
        }
        return result;
    }

    public static List<Integer> rearrange(String diskMap) {
        int left = 0;
        int right = diskMap.length() % 2 == 0 ? diskMap.length() - 2 : diskMap.length() - 1;
        int numberOfIds = (int) Math.ceil(diskMap.length() / 2.0d);
        List<Integer> result = new ArrayList<>();
        int currentLeftId = 0;
        int currentRightId = numberOfIds - 1;
        int blocksLeftAtCurrentRight = diskMap.charAt(right) - '0';
        while (left < right) {
            for (int i = 0; i < diskMap.charAt(left) - '0'; i++) {
                result.add(currentLeftId);
            }
            left++;
            for (int i = 0; i < diskMap.charAt(left) - '0'; i++) {
                if (blocksLeftAtCurrentRight > 0) {
                    result.add(currentRightId);
                    blocksLeftAtCurrentRight--;
                } else {
                    right -= 2;
                    if (right < left) break;
                    currentRightId--;
                    blocksLeftAtCurrentRight = diskMap.charAt(right) - '0';
                    i--;
                }
            }
            left++;
            currentLeftId++;
        }
        return result;
    }
}
