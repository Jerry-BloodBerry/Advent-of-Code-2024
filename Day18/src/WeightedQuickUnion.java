import java.util.ArrayList;
import java.util.List;

public class WeightedQuickUnion {
    private final int[] id;
    private final boolean[] open;
    private final int[] size;
    private final int gridSide;
    private final List<Vector> unionVectorsToCheck = new ArrayList<>(List.of(
            new Vector(1, 0),
            new Vector(-1, 0),
            new Vector(0, 1),
            new Vector(0, -1)
    ));

    public WeightedQuickUnion(int n) {
        id = new int[n];
        size = new int[n];
        open = new boolean[n];
        gridSide = (int)Math.sqrt(n);
        for (int i = 0; i < gridSide; i++) {
            for (int j = 0; j < gridSide; j++) {
                id[j * gridSide + i] = j * gridSide + i;
                size[j * gridSide + i] = 1;
            }
        }
    }

    private int root(Coordinates p) {
        int temp = id[index(p)];
        while (temp != id[temp]) {
            id[temp] = id[id[temp]];
            temp = id[temp];
        }
        return temp;
    }

    public void markOpen(Coordinates c) {
        open[index(c)] = true;
        for (var v : unionVectorsToCheck) {
            var coord = c.add(v);
            if (isOnGrid(coord) && isOpen(coord)) {
                union(c, coord);
            }
        }
    }

    public boolean connected(Coordinates p, Coordinates q) {
        return root(p) == root(q);
    }

    private boolean isOpen(Coordinates coord) {
        return open[index(coord)];
    }

    private boolean isOnGrid(Coordinates coordinates) {
        return isOnGrid(coordinates.getX(), coordinates.getY());
    }

    private boolean isOnGrid(int x, int y) {
        return x >= 0 && x < gridSide && y >= 0 && y < gridSide;
    }

    private int index(Coordinates c) {
        return c.getX() * gridSide + c.getY();
    }

    private void union(Coordinates p, Coordinates q) {
        var p1 = root(p);
        var p2 = root(q);
        if (size[p1] < size[p2]) {
            id[p1] = id[p2];
            size[p2] += size[p1];
        } else {
            id[p2] = id[p1];
            size[p1] += size[p2];
        }
    }
}
