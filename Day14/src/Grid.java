import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    GridDimensions dimensions;
    private final int[][] tiles;
    List<Robot> robots = new ArrayList<>();
    HashMap<Integer, Integer> quadrantCounts = new HashMap<>();

    public Grid(GridDimensions dimensions) {
        this.dimensions = dimensions;
        this.tiles = new int[dimensions.height()][dimensions.width()];
        quadrantCounts.put(0, 0);
        quadrantCounts.put(1, 0);
        quadrantCounts.put(2, 0);
        quadrantCounts.put(3, 0);
        quadrantCounts.put(-1, 0);
    }

    public void addRobot(int initialX, int initialY, VelocityVector velocityVector) {
        Robot robot = new Robot(initialX, initialY, velocityVector, this);
        robots.add(robot);
        updateTilesAndQuadrantCount(robot.getX(), robot.getY(), Operation.Enter);
    }

    enum Operation
    {
        Enter, Leave
    }

    private void updateTilesAndQuadrantCount(int x, int y, Operation operation) {
        tiles[y][x] += (operation == Operation.Enter ? 1 : - 1);
        int quadrantNumber = quadrantNumber(x, y);
        quadrantCounts.put(quadrantNumber, quadrantCounts.get(quadrantNumber) + (operation == Operation.Enter ? 1 : -1));
    }

    private int quadrantNumber(int x, int y) {
        if (y < dimensions.height() / 2 && x < dimensions.width() / 2) return 0;
        if (y < dimensions.height() / 2 && x > dimensions.width() / 2) return 1;
        if (y > dimensions.height() / 2 && x < dimensions.width() / 2) return 2;
        if (y > dimensions.height() / 2 && x > dimensions.width() / 2) return 3;
        return -1;
    }

    public GridDimensions getDimensions() {
        return dimensions;
    }

    public void moveAllRobots() {
        for (Robot robot : robots) {
            updateTilesAndQuadrantCount(robot.getX(), robot.getY(), Operation.Leave);
            robot.move();
            updateTilesAndQuadrantCount(robot.getX(), robot.getY(), Operation.Enter);
        }
    }

    public int getQuadrantScoresMultiplied()
    {
        return this.quadrantCounts.entrySet().stream().filter(x -> x.getKey() != -1).map(Map.Entry::getValue).reduce(1, (a, b) -> a * b);
    }

    public int getCountInQuadrants() {
        return this.quadrantCounts.entrySet().stream().filter(x -> x.getKey() != -1).map(Map.Entry::getValue).reduce(0, Integer::sum);
    }

    public int getCountInCenter() {
        return this.quadrantCounts.get(-1);
    }

    public void printGrid(int imageNumber) throws IOException {
        var width = dimensions.width();
        var height = dimensions.height();
        int[] pixels = new int[width*height];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                if (tiles[j][i] > 0) pixels[j*width+i] = Color.WHITE.getRGB();
                else pixels[j*width+i] = Color.BLACK.getRGB();
            }
        }
        BufferedImage pixelImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixelImage.setRGB(0, 0, width, height, pixels, 0, width);
        // Get the project root directory
        Path projectDir = Paths.get(System.getProperty("user.dir"));

        // Define a relative directory within the project directory
        Path saveDirectory = projectDir.resolve("outputImages");
        Files.createDirectories(saveDirectory); // Create directory if it doesn't exist
        File imageFile = new File(saveDirectory.resolve("capturedImage" + imageNumber + ".png").toString());
        ImageIO.write(pixelImage, "png", imageFile);
    }
}
