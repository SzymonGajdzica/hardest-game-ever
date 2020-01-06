package pl.polsl.data.models;

import pl.polsl.models.IVector;
import pl.polsl.sprites.BaseSprite;
import pl.polsl.sprites.SubEndSprite;
import pl.polsl.sprites.UserSprite;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class MapData {

    private UserSprite player;
    private final IVector mapSize;
    private final List<BaseSprite> sprites = new LinkedList<>();
    private int numberOfSubEnds = 0;

    public MapData(IVector mapSize) {
        this.mapSize = mapSize;
    }

    public void addSprites(Set<BaseSprite> sprites) {
        this.sprites.addAll(sprites);
        player = (UserSprite) sprites
                .stream()
                .filter(baseSprite -> baseSprite instanceof UserSprite)
                .findAny()
                .orElse(player);
        numberOfSubEnds += (int) sprites
                .stream()
                .filter(baseSprite -> baseSprite instanceof SubEndSprite)
                .count();
    }

    public List<BaseSprite> getSprites() {
        return sprites;
    }

    public IVector getMapSize() {
        return mapSize;
    }

    public UserSprite getPlayer() {
        return player;
    }

    public int getNumberOfSubEnds() {
        return numberOfSubEnds;
    }
}
