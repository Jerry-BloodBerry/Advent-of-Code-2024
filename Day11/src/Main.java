import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        var url = Main.class.getResource("input.txt");
        assert url != null;
        Scanner sc = new Scanner(new File(url.getPath()));
        var stonesList = (ArrayList<BigInteger>) Arrays.stream(sc.nextLine().split(" ")).map(BigInteger::new).collect(Collectors.toList());
        sc.close();
        Stones stones = new Stones(stonesList);
        for (int i = 0; i < 75; i++) {
            System.out.println("Currently at iteration " + (i + 1));
            System.out.println("Stones size " + stones.count());
            stones.blink();
        }
        System.out.printf("Stones count: %d", stones.count());
    }
}

class Stones {
    private HashMap<BigInteger, Long> stones;

    public Stones(List<BigInteger> stones) {
        this.stones = new HashMap<>();
        for (var stone : stones) {
            this.stones.put(stone, this.stones.getOrDefault(stone, 0L) + 1);
        }
    }

    public void blink() {
        HashMap<BigInteger, Long> newStones = new HashMap<>();
        for (var stone : this.stones.entrySet()) {
            if (stone.getKey().equals(BigInteger.ZERO)) {
                newStones.put(BigInteger.ONE, newStones.getOrDefault(BigInteger.ONE, 0L) + stone.getValue());
            } else {
                var digitCount = calculateDigitCount(stone.getKey());
                if (digitCount % 2 == 0) {
                    // Split the stone by dividing it into two parts
                    BigInteger digitDivider = BigInteger.TEN.pow(digitCount / 2);
                    BigInteger left = stone.getKey().divide(digitDivider);
                    BigInteger right = stone.getKey().mod(digitDivider);
                    newStones.put(left, newStones.getOrDefault(left, 0L) + stone.getValue());
                    newStones.put(right, newStones.getOrDefault(right, 0L) + stone.getValue());
                } else {
                    BigInteger newStone = stone.getKey().multiply(new BigInteger("2024"));
                    newStones.put(newStone, newStones.getOrDefault(newStone, 0L) + stone.getValue());
                }
            }
        }
        this.stones = newStones;
    }

    private int calculateDigitCount(BigInteger number) {
        return number.toString().length();
    }

    public long count() {
        return stones.values().stream().reduce(0L, Long::sum);
    }
}