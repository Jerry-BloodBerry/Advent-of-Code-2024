public class Button
{
    private final Vector shiftVector;
    private int timesPressed;
    private int tokensSpent;
    private final int pricePerPress;

    public Button(Vector shiftVector, int pricePerPress) {
        timesPressed = 0;
        tokensSpent = 0;
        this.shiftVector = shiftVector;
        this.pricePerPress = pricePerPress;
    }

    public Vector getShiftVector() {
        return shiftVector;
    }

    public void press(Claw claw) {
        timesPressed++;
        claw.move(shiftVector);
        tokensSpent += pricePerPress;
    }

    public void rollbackPress(Claw claw) {
        timesPressed--;
        claw.move(shiftVector.oppositeVector());
        tokensSpent -= pricePerPress;
    }

    public int getTimesPressed() {
        return timesPressed;
    }

    public int getTokensSpent() {
        return tokensSpent;
    }

    public int getPricePerPress() {
        return pricePerPress;
    }
}
