import java.util.List;

public class WarehouseMap {
    private final List<List<Character>> tiles;
    Coordinates robotCoordinates;

    public WarehouseMap(List<List<Character>> tiles, Coordinates robotCoordinates) {
        this.tiles = tiles;
        this.robotCoordinates = robotCoordinates;
    }

    public void moveRobot(Direction direction) {
        Coordinates newCoordinates = switch (direction) {
            case Up -> new Coordinates(robotCoordinates.getX(), robotCoordinates.getY() - 1);
            case Down -> new Coordinates(robotCoordinates.getX(), robotCoordinates.getY() + 1);
            case Right -> new Coordinates(robotCoordinates.getX() + 1, robotCoordinates.getY());
            case Left -> new Coordinates(robotCoordinates.getX() - 1, robotCoordinates.getY());
        };
        var tileObj = getCharAtTileCoordinates(newCoordinates);
        if (tileObj == '.') {
            robotCoordinates = newCoordinates;
        } else if (tileObj == 'O') {
            if (tryShiftBoxes(newCoordinates, direction)) {
                robotCoordinates = newCoordinates;
            }
        }
    }

    private boolean tryShiftBoxes(Coordinates newCoordinates, Direction direction) {
        Coordinates temp = new Coordinates(newCoordinates.getX(), newCoordinates.getY());
        while (getCharAtTileCoordinates(temp) != '.' && getCharAtTileCoordinates(temp) != '#') {
            temp.incrementInDirection(direction);
        }
        if (getCharAtTileCoordinates(temp) == '.') {
            while (!(temp.getX() == newCoordinates.getX() && temp.getY() == newCoordinates.getY())) {
                tiles.get(temp.getY()).set(temp.getX(), 'O');
                temp.incrementInDirection(Direction.Opposite(direction));
                tiles.get(temp.getY()).set(temp.getX(), '.');
            }
            return true;
        }
        return false;
    }

    private char getCharAtTileCoordinates(Coordinates coordinates) {
        return tiles.get(coordinates.getY()).get(coordinates.getX());
    }

    public int gpsCoordinate(int x, int y) {
        return 100 * y + x;
    }

    public void print() {
        for (List<Character> tile : tiles) {
            for (Character character : tile) {
                System.out.print(character + "");
            }
            System.out.println();
        }
    }

    public int getSumOfBoxesCoordinates() {
        var sumOfBoxesCoordinates = 0;
        for (int i = 0; i < tiles.size(); i++) {
            for (int j = 0; j < tiles.get(i).size(); j++) {
                if (tiles.get(i).get(j) == 'O') {
                    sumOfBoxesCoordinates += gpsCoordinate(j, i);
                }
            }
        }
        return sumOfBoxesCoordinates;
    }
}
