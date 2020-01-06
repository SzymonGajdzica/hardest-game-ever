package pl.polsl.data.models;

import javafx.util.Pair;
import pl.polsl.models.IVector;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EnemyMapNode extends MapNode<EnemyMapNode> {

    private EnemyMapNode bestNeighbor = null;
    private int bestDepth = 0;

    public EnemyMapNode(Set<IVector> neighbors, IVector position, Map<IVector, EnemyMapNode> baseObjectMapNodeHashMap){
        super(neighbors, position, baseObjectMapNodeHashMap);
    }

    private int getMaxNeighborDepth(Set<EnemyMapNode> usedNodes, int currentCounter) {
        if(bestNeighbor != null && !usedNodes.contains(bestNeighbor))
            return bestDepth;
        Set<EnemyMapNode> mUsedNodes = new HashSet<>(usedNodes);
        mUsedNodes.add(this);
        Pair<Integer, EnemyMapNode> pair = neighbors
                .stream()
                .map(mapNodeHashMap::get)
                .filter(enemyMapNode -> !mUsedNodes.contains(enemyMapNode))
                .map(enemyMapNode -> new Pair<>(enemyMapNode.getMaxNeighborDepth(mUsedNodes, currentCounter + 1), enemyMapNode))
                .max((o1, o2) ->  o1.getKey() - o2.getKey())
                .orElse(new Pair<>(currentCounter + 1, null));
        EnemyMapNode bestNeighbor = pair.getValue();
        if(bestNeighbor != null){
            this.bestNeighbor = bestNeighbor;
            this.bestDepth = pair.getKey();
        }
        return pair.getKey();
    }

    public EnemyMapNode getBestNeighbor(Set<EnemyMapNode> usedNodes) {
        Set<EnemyMapNode> mUsedNodes = new HashSet<>(usedNodes);
        return neighbors
                .stream()
                .map(mapNodeHashMap::get)
                .filter(enemyMapNode -> !mUsedNodes.contains(enemyMapNode))
                .max(Comparator.comparingInt(o -> o.getMaxNeighborDepth(mUsedNodes, 0)))
                .orElse(null);
    }

    public IVector getPosition() {
        return position;
    }

    public void clearCache(){
        bestDepth = 0;
        bestNeighbor = null;
    }

    public boolean isNeighborOf(EnemyMapNode enemyMapNode) {
        return neighbors.contains(enemyMapNode.getPosition());
    }
}
