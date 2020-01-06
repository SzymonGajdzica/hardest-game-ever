package pl.polsl.data.models;

import pl.polsl.models.IVector;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class NonMovableMapNode extends MapNode<NonMovableMapNode> {

    private boolean isRoot = true;
    private IVector size = new IVector(1, 1);

    public NonMovableMapNode(Set<IVector> neighbors, IVector position, Map<IVector, NonMovableMapNode> baseObjectMapNodeHashMap){
        super(neighbors, position, baseObjectMapNodeHashMap);
    }

    public IVector getSize() {
        return size;
    }

    public boolean optimize(int optimizeCounter) {
        if(!isRoot)
            return false;
        Optional<NonMovableMapNode> unusedNeighborOpt = neighbors
                .stream()
                .map(mapNodeHashMap::get)
                .filter(NonMovableMapNode::isRoot)
                .filter(nonMovableMapNode -> (nonMovableMapNode.getSize().getX() == size.getX() && nonMovableMapNode.getPosition().getX() == position.getX()) ||
                        (nonMovableMapNode.getSize().getY() == size.getY() && nonMovableMapNode.getPosition().getY() == position.getY()))
                .findAny();
        if(!unusedNeighborOpt.isPresent())
            return optimizeCounter != 0;
        NonMovableMapNode unusedNeighbor = unusedNeighborOpt.get();
        unusedNeighbor.isRoot = false;
        if(unusedNeighbor.getPosition().getX() == position.getX())
            size = size.moved(0, unusedNeighbor.getSize().getY());
        else
            size = size.moved(unusedNeighbor.getSize().getX(), 0);
        neighbors.addAll(unusedNeighbor.neighbors);
        return optimize(optimizeCounter + 1);
    }

    public boolean isRoot() {
        return isRoot;
    }
}
