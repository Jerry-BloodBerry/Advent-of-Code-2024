import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AntennaMap {
    private final HashMap<Character, List<Coordinates>> antennaGroups = new HashMap<>();
    private final char[][] mapFields;
    private final HashSet<Coordinates> antinodeCoordinates = new HashSet<>();

    public AntennaMap(char[][] mapFields) {
        this.mapFields = mapFields;
    }

    public void place(int x, int y, char field)
    {
        if (field == '.') {
            mapFields[y][x] = field;
        } else {
            mapFields[y][x] = field;
            antennaGroups.putIfAbsent(field, new ArrayList<>());
            antennaGroups.get(field).add(new Coordinates(x, y));
        }
    }

    public void placeAntinodes()
    {
        for (var group : antennaGroups.entrySet())
        {
            for (int i = 0; i < group.getValue().size(); i++) {
                var topAntennaCoordinates = group.getValue().get(i);
                for (int j = i + 1; j < group.getValue().size(); j++) {
                    var bottomAntennaCoordinates = group.getValue().get(j);
                    var translationVector = TranslationVector.FromCoordinates(topAntennaCoordinates, bottomAntennaCoordinates);
                    var oppositeVector = TranslationVector.Opposite(translationVector);
                    List<Coordinates> possibleCoordinates = new ArrayList<>();
                    possibleCoordinates.add(topAntennaCoordinates.add(translationVector));
                    possibleCoordinates.add(bottomAntennaCoordinates.add(oppositeVector));
                    for (var c : possibleCoordinates)
                    {
                        placeAntinode(c);
                    }
                }
            }
        }
    }

    public void placeAntinodesWithResonantHarmonics()
    {
        for (var group : antennaGroups.entrySet())
        {
            if (group.getValue().size() < 2) continue;
            antinodeCoordinates.addAll(group.getValue());
            for (int i = 0; i < group.getValue().size(); i++) {
                var topAntennaCoordinates = group.getValue().get(i);
                for (int j = i + 1; j < group.getValue().size(); j++) {
                    var bottomAntennaCoordinates = group.getValue().get(j);
                    var translationVector = TranslationVector.FromCoordinates(topAntennaCoordinates, bottomAntennaCoordinates);
                    var oppositeVector = TranslationVector.Opposite(translationVector);
                    List<Coordinates> possibleCoordinates = new ArrayList<>();
                    var tempTopCoordinates = topAntennaCoordinates.add(translationVector);
                    while (isOnTheMap(tempTopCoordinates))
                    {
                        possibleCoordinates.add(tempTopCoordinates.copy());
                        tempTopCoordinates = tempTopCoordinates.add(translationVector);
                    }
                    var tempBottomCoordinates = bottomAntennaCoordinates.add(oppositeVector);
                    while (isOnTheMap(tempBottomCoordinates))
                    {
                        possibleCoordinates.add(tempBottomCoordinates.copy());
                        tempBottomCoordinates = tempBottomCoordinates.add(oppositeVector);
                    }
                    for (var c : possibleCoordinates)
                    {
                        placeAntinode(c);
                    }
                }
            }
        }
    }

    private boolean isOnTheMap(Coordinates coordinates) {
        return isOnTheMap(coordinates.x(), coordinates.y());
    }

    private void placeAntinode(Coordinates c) {
        placeAntinode(c.x(), c.y());
    }

    private void placeAntinode(int x, int y) {
        if (!isOnTheMap(x, y)) {
            return;
        }
        if (isAntenna(x, y)) {
            antinodeCoordinates.add(new Coordinates(x, y));
            return;
        }
        mapFields[y][x] = '#';
        antinodeCoordinates.add(new Coordinates(x, y));
    }

    private boolean isAntenna(int x, int y) {
        return isOccupied(x, y) && mapFields[y][x] != '#';
    }

    public boolean isOccupied(int x, int y) {
        return mapFields[y][x] != '.';
    }

    public boolean isOnTheMap(int x, int y) {
        return x >= 0 && y >= 0 && x < mapFields[0].length && y < mapFields.length;
    }

    public int getAntinodeCount() {
        return antinodeCoordinates.size();
    }
}
