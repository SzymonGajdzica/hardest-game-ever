package pl.polsl.data.models;

import pl.polsl.models.IVector;

import java.util.Map;
import java.util.Set;

public class MapNode<T> {

    protected final Set<IVector> neighbors;
    protected final Map<IVector, T> mapNodeHashMap;
    protected final IVector position;

    public MapNode(Set<IVector> neighbors, IVector position, Map<IVector, T> mapNodeHashMap){
        this.neighbors = neighbors;
        this.position = position;
        this.mapNodeHashMap = mapNodeHashMap;
    }

    public IVector getPosition() {
        return position;
    }

}
