package pl.polsl.data.generators;

import pl.polsl.application.Global;
import pl.polsl.data.models.EnemyMapNode;
import pl.polsl.models.Color;
import pl.polsl.models.IVector;
import pl.polsl.sprites.BaseSprite;
import pl.polsl.sprites.EnemySprite;

import java.util.*;

public class EnemyGenerator implements Generator {

    private int totalPoints = 0;
    private int totalUsedPoints = 0;
    private double executionTime;

    public Set<BaseSprite> generate(Color[][] colors, Map<Color, Set<IVector>> coloredPoints) {
        long startTime = System.nanoTime();
        Set<BaseSprite> enemies = new HashSet<>();
        Map<IVector, EnemyMapNode> mapNodeHashMap = new HashMap<>();
        HashMap<Color, Set<EnemyMapNode>> mapNodes = generateMapNodes(mapNodeHashMap, colors, coloredPoints);
        mapNodes.forEach((color, enemyMapNodes) -> enemies.add(generateEnemy(color, enemyMapNodes, mapNodeHashMap)));
        mapNodes.keySet().forEach(coloredPoints::remove);
        executionTime = (System.nanoTime() - startTime) / 1000000000.0;
        return enemies;
    }

    @Override
    public void displaySummary() {
        double usage = 100.0 - ((totalPoints - totalUsedPoints) / (totalPoints / 100.0));
        System.out.printf("Created all enemies in %.2f seconds and used %d from given %d path points (%.2f%s)\n",
                executionTime, totalUsedPoints, totalPoints, usage, "%");
    }

    private HashMap<Color, Set<EnemyMapNode>> generateMapNodes(Map<IVector, EnemyMapNode> mapNodeHashMap, Color[][] colors, Map<Color, Set<IVector>> coloredPoints){
        HashMap<Color, Set<EnemyMapNode>> colorsWithMapNodes = new HashMap<>();
        coloredPoints
                .entrySet()
                .stream()
                .filter(colorSetEntry -> !Color.specialColors.contains(colorSetEntry.getKey()))
                .forEach(colorSetEntry -> {
                    Color color = colorSetEntry.getKey();
                    colorsWithMapNodes.put(color, new HashSet<>());
                    colorSetEntry.getValue().forEach(iVector -> {
                        IVector position = iVector.copied();
                        EnemyMapNode newMapNode = new EnemyMapNode(findNeighbors(position, color, colors), position, mapNodeHashMap);
                        mapNodeHashMap.put(position, newMapNode);
                        colorsWithMapNodes.get(color).add(newMapNode);
                    });
                });
        return colorsWithMapNodes;
    }

    private Set<IVector> findNeighbors(IVector position, Color color, Color[][] colors) {
        HashSet<IVector> neighbors = new HashSet<>();
        addNeighbor(position,0, 1, color, neighbors, colors);
        addNeighbor(position,0, -1, color, neighbors, colors);
        addNeighbor(position,1, 1, color, neighbors, colors);
        addNeighbor(position,1, -1, color, neighbors, colors);
        addNeighbor(position,1, 0, color, neighbors, colors);
        addNeighbor(position,-1, 1, color, neighbors, colors);
        addNeighbor(position,-1, -1, color, neighbors, colors);
        addNeighbor(position,-1, 0, color, neighbors, colors);
        return neighbors;
    }

    private void addNeighbor(IVector position, int moveX, int moveY, Color color, HashSet<IVector> neighbors, Color[][] colors){
        try {
            if (colors[position.getX() + moveX][position.getY() + moveY].equals(color))
                neighbors.add(position.moved(moveX, moveY));
            else if(!colors[position.getX() + moveX][position.getY() + moveY].equals(Color.background))
                addNeighbor(position.moved(moveX, moveY), moveX, moveY, color, neighbors, colors);
        }catch (ArrayIndexOutOfBoundsException ignore){

        }
    }

    private EnemySprite generateEnemy(Color color, Set<EnemyMapNode> enemyMapNodes, Map<IVector, EnemyMapNode> mapNodeHashMap) {
        EnemyMapNode startNode = findStartNode(enemyMapNodes);
        enemyMapNodes.forEach(EnemyMapNode::clearCache);
        LinkedList<IVector> enemyPath = generateEnemyPath(startNode);
        boolean isLooped = mapNodeHashMap.get(enemyPath.getFirst()).isNeighborOf(mapNodeHashMap.get(enemyPath.getLast()));
        double usage = 100.0 - ((enemyMapNodes.size() - enemyPath.size()) / (enemyMapNodes.size() / 100.0));
        totalPoints += enemyMapNodes.size();
        totalUsedPoints += enemyPath.size();
        if(usage < 80.0)
            System.out.printf("Warning! Only (%.2f%s) points used for generating enemy with color %s\n", usage, "%", color);
        return new EnemySprite(startNode.getPosition(), new IVector(Global.SPRITE_SIZE, Global.SPRITE_SIZE), color, enemyPath, isLooped);
    }

    private LinkedList<IVector> generateEnemyPath(EnemyMapNode startNode){
        LinkedList<IVector> points = new LinkedList<>();
        Set<EnemyMapNode> usedNodes = new HashSet<>();
        EnemyMapNode currentNode = startNode;
        while (currentNode != null) {
            points.add(currentNode.getPosition());
            usedNodes.add(currentNode);
            currentNode = currentNode.getBestNeighbor(usedNodes);
        }
        return points;
    }

    private EnemyMapNode findStartNode(Set<EnemyMapNode> enemyMapNodes){
        EnemyMapNode currentNode = Objects.requireNonNull(enemyMapNodes.stream().findAny().orElse(null));
        Set<EnemyMapNode> usedNodes = new HashSet<>();
        usedNodes.add(currentNode);
        EnemyMapNode nextNode;
        while ((nextNode = currentNode.getBestNeighbor(usedNodes)) != null) {
            currentNode = nextNode;
            usedNodes.add(currentNode);
        }
        return currentNode;
    }

}
