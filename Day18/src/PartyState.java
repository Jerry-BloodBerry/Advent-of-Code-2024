public class PartyState {
    Coordinates coordinates;
    int currentCost;

    public PartyState(Coordinates coordinates, int currentCost) {
        this.coordinates = coordinates;
        this.currentCost = currentCost;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public int getCurrentCost() {
        return currentCost;
    }
}
