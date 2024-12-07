import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        HashMap<Integer, HashSet<Integer>> beforeRules = new HashMap<>();
        var sumOfMiddleElements = 0;
        var sumOfCorrectedUpdatesMiddleElements = 0;
        while (sc.hasNextLine())
        {
            var line = sc.nextLine();
            if (line.isEmpty()) break;
            var splitLine = line.split("\\|");
            var key = Integer.parseInt(splitLine[1]);
            beforeRules.putIfAbsent(key, new HashSet<>());
            beforeRules.get(key).add(Integer.parseInt(splitLine[0]));
        }
        while (sc.hasNextLine())
        {
            var splitLine = sc.nextLine().split(",");
            var nums = Arrays.stream(splitLine).map(Integer::parseInt).toList();
            int[] updateResult = isCorrectlyOrderedUpdate(nums, beforeRules);
            boolean isCorrect = updateResult[0] == -1;
            sumOfMiddleElements += isCorrect ? nums.get(nums.size() / 2) : 0;
            if (!isCorrect) {
                var validUpdate = makeUpdateValid(new ArrayList<>(nums), beforeRules);
                sumOfCorrectedUpdatesMiddleElements += validUpdate.get(validUpdate.size() / 2);
            }
        }
        System.out.printf("Part 1: %d%n", sumOfMiddleElements);
        System.out.printf("Part 2: %d%n", sumOfCorrectedUpdatesMiddleElements);
    }

    /**
     * Checks if update pages are ordered correctly. Returns [-1, -1] if they are ordered correctly.
     * Otherwise, returns the index of the first failing element and the index at which the violated rule was added.
     */
    private static int[] isCorrectlyOrderedUpdate(List<Integer> nums, HashMap<Integer, HashSet<Integer>> beforeRules) {
        // tracks the numbers that cannot appear at and after the current number for update to be valid
        HashMap<Integer, Integer> illegalNums = new HashMap<>();
        for (int i = 0; i < nums.size(); i++) {
            if (illegalNums.containsKey(nums.get(i))) {
                return new int[] {i, illegalNums.get(nums.get(i))};
            } else {
                var nowIllegal = beforeRules.get(nums.get(i));
                for (var illegal : nowIllegal)
                {
                    illegalNums.put(illegal, i);
                }
            }
        }
        return new int[] {-1, -1};
    }

    private static List<Integer> makeUpdateValid(ArrayList<Integer> nums, HashMap<Integer, HashSet<Integer>> beforeRules) {
        var updateResult = isCorrectlyOrderedUpdate(nums, beforeRules);
        while (updateResult[0] != -1)
        {
            int failingValue = nums.get(updateResult[0]);
            nums.remove(updateResult[0]);
            nums.add(updateResult[1] > 0 ? updateResult[1] - 1 : 0, failingValue);
            updateResult = isCorrectlyOrderedUpdate(nums, beforeRules);
        }
        return nums;
    }
}
