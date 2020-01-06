package pl.polsl.data.generators;

import pl.polsl.data.models.NonMovableMapNode;
import pl.polsl.models.Color;
import pl.polsl.models.IVector;
import pl.polsl.sprites.BaseSprite;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class NonMovableGenerator implements Generator {

    private final NonMovableFactory nonMovableFactory = new NonMovableFactory();
    private int totalPoints = 0;
    private int totalUsedPoints = 0;
    private double executionTime;

    @Override
    public Set<BaseSprite> generate(Color[][] colors, Map<Color, Set<IVector>> coloredPoints) {
        long startTime = System.nanoTime();
        Map<IVector, NonMovableMapNode> mapNodeHashMap = new HashMap<>();
        Map<Color, Set<NonMovableMapNode>> mapNodes = generateMapNodes(mapNodeHashMap, colors, coloredPoints);
        Set<BaseSprite> optimizedMapNodes = new HashSet<>();
        mapNodes.forEach((color, nonMovableMapNodes) -> optimizedMapNodes.addAll(generateSprites(color, nonMovableMapNodes)));
        mapNodes.keySet().forEach(coloredPoints::remove);
        executionTime = (System.nanoTime() - startTime) / 1000000000.0;
        return optimizedMapNodes;
    }

    @Override
    public void displaySummary() {
        double usage = (totalPoints - totalUsedPoints) / (totalPoints / 100.0);
        System.out.printf("Created all non movable objects in %.2f seconds and optimized from %d points to %d rectangles (%.2f%s)\n",
                executionTime, totalPoints, totalUsedPoints, usage, "%");
    }

    private Set<BaseSprite> generateSprites(Color color, Set<NonMovableMapNode> mapNodes) {
        boolean didOptimize = true;
        while (didOptimize){
            didOptimize = false;
            for (NonMovableMapNode mapNode : mapNodes) {
                if(mapNode.optimize(0))
                    didOptimize = true;
            }
        }
        Set<BaseSprite> optimizedMapNodes = mapNodes
                .stream()
                .filter(NonMovableMapNode::isRoot)
                .map(nonMovableMapNode -> nonMovableFactory.getSprite(nonMovableMapNode, color))
                .collect(Collectors.toSet());
        totalPoints += mapNodes.size();
        totalUsedPoints += optimizedMapNodes.size();
        return optimizedMapNodes;
    }

    private HashMap<Color, Set<NonMovableMapNode>> generateMapNodes(Map<IVector, NonMovableMapNode> mapNodeHashMap, Color[][] colors, Map<Color, Set<IVector>> coloredPoints) {
        HashMap<Color, Set<NonMovableMapNode>> colorsWithMapNodes = new HashMap<>();
        coloredPoints
                .entrySet()
                .stream()
                .filter(colorSetEntry -> Color.nonMovableColors.contains(colorSetEntry.getKey()))
                .forEach(colorSetEntry -> {
                    Color color = colorSetEntry.getKey();
                    colorsWithMapNodes.put(color, new HashSet<>());
                    colorSetEntry.getValue().forEach(iVector -> {
                        IVector position = iVector.copied();
                        NonMovableMapNode newMapNode = new NonMovableMapNode(findNeighbors(position, color, colors), position, mapNodeHashMap);
                        mapNodeHashMap.put(position, newMapNode);
                        colorsWithMapNodes.get(color).add(newMapNode);
                    });
                });
        return colorsWithMapNodes;
    }

    private Set<IVector> findNeighbors(IVector position, Color color, Color[][] colors) {
        HashSet<IVector> neighbors = new HashSet<>();
        addNeighbor(position,0, 1, neighbors, color, colors);
        addNeighbor(position,1, 0, neighbors, color, colors);
        return neighbors;
    }

    private void addNeighbor(IVector position, int moveX, int moveY, HashSet<IVector> neighbors, Color color, Color[][] colors){
        try {
            if (colors[position.getX() + moveX][position.getY() + moveY].equals(color))
                neighbors.add(position.moved(moveX, moveY));
        }catch (ArrayIndexOutOfBoundsException ignore){

        }
    }

}
