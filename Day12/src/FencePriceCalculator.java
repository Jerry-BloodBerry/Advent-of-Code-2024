import java.util.HashSet;

public class FencePriceCalculator {
    private int area;
    private int perimeter;
    private int sides;
    private final HashSet<FenceBorder> borders = new HashSet<>();

    public FencePriceCalculator() {
        this.area = 0;
        this.perimeter = 0;
        this.sides = 0;
    }

    public void incrementArea() {
        this.area++;
    }

    public int getTotalPrice() {
        return this.area * this.perimeter;
    }

    public void addBorder(FenceBorder border) {
        if (!hasNeighbourBorder(border)) {
            sides++;
        }
        if (isMergeSidesBorder(border)) {
            sides--;
        }
        borders.add(border);
        perimeter++;
    }

    private boolean isMergeSidesBorder(FenceBorder border) {
        return (borders.contains(new FenceBorder(border.side, new Point(border.location.x() + 1, border.location.y()))) && borders.contains(new FenceBorder(border.side, new Point(border.location.x() - 1, border.location.y())))) ||
                (borders.contains(new FenceBorder(border.side, new Point(border.location.x(), border.location.y() + 1))) && borders.contains(new FenceBorder(border.side, new Point(border.location.x(), border.location.y() - 1))));
    }

    private boolean hasNeighbourBorder(FenceBorder border) {
        var bordersToCheck = new HashSet<FenceBorder>();
        switch (border.side) {
            case Left, Right -> {
                bordersToCheck.add(new FenceBorder(border.side, new Point(border.location.x(), border.location.y() - 1)));
                bordersToCheck.add(new FenceBorder(border.side, new Point(border.location.x(), border.location.y() + 1)));
            }
            case Top, Bottom -> {
                bordersToCheck.add(new FenceBorder(border.side, new Point(border.location.x() - 1, border.location.y())));
                bordersToCheck.add(new FenceBorder(border.side, new Point(border.location.x() + 1, border.location.y())));
            }
        }
        for (FenceBorder b : bordersToCheck) {
            if (borders.contains(b)) {
                return true;
            }
        }
        return false;
    }

    public int getDiscountedPrice() {
        return this.area * this.sides;
    }

    public void reset() {
        this.area = 0;
        this.perimeter = 0;
        this.sides = 0;
        borders.clear();
    }
}
