package pl.polsl.data;

import pl.polsl.data.generators.EnemyGenerator;
import pl.polsl.data.generators.Generator;
import pl.polsl.data.generators.NonMovableGenerator;
import pl.polsl.data.generators.PlayerGenerator;
import pl.polsl.data.models.MapData;
import pl.polsl.models.Color;
import pl.polsl.models.IVector;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;

public class DataController {

    public MapData generateMapData(File file) throws Exception {
        long startTime = System.nanoTime();

        Color[][] colors = getPixels(ImageIO.read(file));
        MapData mapData = new MapData(new IVector(colors.length, colors[0].length));

        List<Generator> generators = getGenerators();
        Map<Color, Set<IVector>> coloredPoints = getColoredPoints(colors);
        generators.forEach(generator -> mapData.addSprites(generator.generate(colors, coloredPoints)));

        double executionTime = (System.nanoTime() - startTime) / 1000000000.0;
        System.out.printf("Generating map %s took %.2f seconds\n", file.getName(), executionTime);
        generators.forEach(Generator::displaySummary);
        System.out.println();

        return mapData;
    }

    private List<Generator> getGenerators() {
        List<Generator> generators = new LinkedList<>();
        generators.add(new NonMovableGenerator());
        generators.add(new EnemyGenerator());
        generators.add(new PlayerGenerator());
        return generators;
    }

    private Map<Color, Set<IVector>> getColoredPoints(Color[][] colors) {
        Map<Color, Set<IVector>> coloredPoints = new HashMap<>();
        for (int i = 0; i < colors.length; i++) {
            Color[] colorArray = colors[i];
            for (int j = 0; j < colorArray.length; j++) {
                Color color = colorArray[j];
                if (!color.equals(Color.background)) {
                    Set<IVector> enemyMapNodes = coloredPoints.get(color);
                    if (enemyMapNodes == null)
                        coloredPoints.put(color, new HashSet<>(Collections.singletonList(new IVector(i, j))));
                    else
                        enemyMapNodes.add(new IVector(i, j));
                }
            }
        }
        return coloredPoints;
    }

    private Color[][] getPixels(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        Color[][] result = new Color[width][height];
        for (int row = 0; row < width; row++)
            for (int col = 0; col < height; col++) {
                int p = image.getRGB(row, col);
                result[row][col] = new Color((p >> 16) & 0xff, (p >> 8) & 0xff, p & 0xff);
            }
        return result;
    }

}
