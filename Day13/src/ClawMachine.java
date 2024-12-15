import java.util.HashSet;

public class ClawMachine {
    private Button a;
    private Button b;
    private final Claw claw;
    private final Coordinates prizeCoordinates;
    private long minTokensSpent;
    HashSet<ButtonPair> visitedPairs = new HashSet<>();

    public ClawMachine(Button a, Button b, Coordinates prizeCoordinates) {
        this.a = a;
        this.b = b;
        this.prizeCoordinates = prizeCoordinates;
        this.claw = new Claw();
        this.minTokensSpent = -1;
    }

    public boolean foundThePrize()
    {
        return claw.currentCoordinates.equals(prizeCoordinates);
    }

    public void runSimulation() {
        if (a.getTimesPressed() > 100 || b.getTimesPressed() > 100) {
            return;
        }
        visitedPairs.add(new ButtonPair(a.getTimesPressed(), b.getTimesPressed()));
        if (foundThePrize()) {
            if (getTokensSpent() < minTokensSpent || minTokensSpent == -1) {
                minTokensSpent = getTokensSpent();
            }
            return;
        }
        if (claw.currentCoordinates.getX() + a.getShiftVector().getdX() <= prizeCoordinates.getX() && claw.currentCoordinates.getY() + a.getShiftVector().getdY() <= prizeCoordinates.getY() && !visitedPairs.contains(new ButtonPair(a.getTimesPressed() + 1, b.getTimesPressed()))) {
            a.press(claw);
            runSimulation();
            a.rollbackPress(claw);
        }
        if (claw.currentCoordinates.getX() + b.getShiftVector().getdX() <= prizeCoordinates.getX() && claw.currentCoordinates.getY() + b.getShiftVector().getdY() <= prizeCoordinates.getY() && !visitedPairs.contains(new ButtonPair(a.getTimesPressed(), b.getTimesPressed() + 1))) {
            b.press(claw);
            runSimulation();
            b.rollbackPress(claw);
        }
    }

    public void runSimulation2() {
        if (a.getShiftVector().isColinearWith(b.getShiftVector())) {
            runSimulationForColinear();
        }
        else {
            runSimulationForNonColinear();
        }
    }

    private void runSimulationForColinear() {
        if (a.getShiftVector().equals(Vector.zero) && b.getShiftVector().equals(Vector.zero)) {
            minTokensSpent = prizeCoordinates.getX() == 0 && prizeCoordinates.getY() == 0 ? 0 : -1;
            return;
        }
        else if(a.getShiftVector().equals(Vector.zero)) {
            handleZeroVectorForButton(a);
            return;
        }
        else if(b.getShiftVector().equals(Vector.zero)) {
            handleZeroVectorForButton(b);
            return;
        }
        long k;
        if(a.getShiftVector().getdX() != 0) {
            if (a.getShiftVector().getdX() > b.getShiftVector().getdX()) {
                swapButtons();
            }
            k = b.getShiftVector().getdX() / a.getShiftVector().getdX();
        } else {
            if (a.getShiftVector().getdY() > b.getShiftVector().getdY()) {
                swapButtons();
            }
            k = b.getShiftVector().getdY() / a.getShiftVector().getdY();
        } // at this point b's coordinate shift is greater than or equal to a's and k is >= 1
        var x = a.getShiftVector().getdX() == 0 ? prizeCoordinates.getY() / a.getShiftVector().getdY() : prizeCoordinates.getX() / a.getShiftVector().getdX();
        var lim = x / k;
        for (long i = lim; i >= 0; i--) {
            var bTemp = i;
            var aTemp = x - i * k;
            System.out.printf("a: %d, b: %d\n", aTemp, bTemp);
            if (aTemp * a.getShiftVector().getdX() + bTemp * b.getShiftVector().getdX() == prizeCoordinates.getX() && aTemp * a.getShiftVector().getdY() + bTemp * b.getShiftVector().getdY() == prizeCoordinates.getY()) {
                var newPrice = aTemp * a.getPricePerPress() + bTemp * b.getPricePerPress();
                if (newPrice < minTokensSpent) {
                    minTokensSpent = newPrice;
                }
            }
        }
    }

    private void swapButtons() {
        var temp = a;
        a = b;
        b = temp;
    }

    private void handleZeroVectorForButton(Button b) {
        var temp = b.equals(this.a) ? this.b : this.a;
        if ((temp.getShiftVector().getdX() == 0 && prizeCoordinates.getX() != 0) || (temp.getShiftVector().getdY() == 0 && prizeCoordinates.getY() != 0)) {
            minTokensSpent = -1;
            return;
        }
        if (temp.getShiftVector().getdX() == 0 && temp.getShiftVector().getdY() == 0) {
            minTokensSpent = 0;
        } else if (temp.getShiftVector().getdX() == 0) {
            if (prizeCoordinates.getY() % temp.getShiftVector().getdY() != 0) {
                minTokensSpent = -1;
                return;
            }
            minTokensSpent = temp.getPricePerPress() * (prizeCoordinates.getY() / temp.getShiftVector().getdY());
        } else if (temp.getShiftVector().getdY() == 0) {
            if (prizeCoordinates.getX() % temp.getShiftVector().getdX() != 0) {
                minTokensSpent = -1;
                return;
            }
            minTokensSpent = temp.getPricePerPress() * (prizeCoordinates.getX() / temp.getShiftVector().getdX());
        } else {
            if (prizeCoordinates.getY() % temp.getShiftVector().getdY() != 0 || prizeCoordinates.getX() % temp.getShiftVector().getdX() != 0) {
                minTokensSpent = -1;
                return;
            }
            minTokensSpent = temp.getPricePerPress() * (prizeCoordinates.getY() / temp.getShiftVector().getdY());
        }
    }

    private void runSimulationForNonColinear() {
        long bCount = (a.getShiftVector().getdX()* prizeCoordinates.getY() - prizeCoordinates.getX() * a.getShiftVector().getdY()) / (b.getShiftVector().getdY() * a.getShiftVector().getdX() - b.getShiftVector().getdX() * a.getShiftVector().getdY());
        long aCount = (prizeCoordinates.getX() - bCount * b.getShiftVector().getdX()) / a.getShiftVector().getdX();
        if (aCount < 0 || bCount < 0) {
            minTokensSpent = -1;
            return;
        }
        var result = a.getShiftVector().multiplyByScalar(aCount).add(b.getShiftVector().multiplyByScalar(bCount));
        var cVector = new Vector(prizeCoordinates.getX(), prizeCoordinates.getY());
        if (!result.equals(cVector)) {
            minTokensSpent = -1;
            return;
        }
        minTokensSpent = a.getPricePerPress() * aCount + b.getPricePerPress() * bCount;
    }

    public long getMinTokensSpent() {
        return minTokensSpent;
    }

    public int getTokensSpent() {
        return a.getTokensSpent() + b.getTokensSpent();
    }
}
