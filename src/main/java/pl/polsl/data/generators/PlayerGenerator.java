package pl.polsl.data.generators;

import pl.polsl.application.Global;
import pl.polsl.models.Color;
import pl.polsl.models.IVector;
import pl.polsl.sprites.BaseSprite;
import pl.polsl.sprites.UserSprite;

import java.util.*;

public class PlayerGenerator implements Generator {

    private boolean foundPlayer = false;
    private double executionTime;

    @Override
    public Set<BaseSprite> generate(Color[][] colors, Map<Color, Set<IVector>> coloredPoints) {
        long startTime = System.nanoTime();
        Set<IVector> matchingPoints = coloredPoints.get(Color.player);
        Optional<IVector> pointOpt = matchingPoints.stream().findAny();
        coloredPoints.remove(Color.player);
        executionTime = (System.nanoTime() - startTime) / 1000000000.0;
        if(!pointOpt.isPresent())
            return new HashSet<>();
        foundPlayer = true;
        return new HashSet<>(Collections.singletonList(new UserSprite(pointOpt.get().copied(), new IVector(Global.SPRITE_SIZE, Global.SPRITE_SIZE), Color.player)));
    }

    @Override
    public void displaySummary() {
        if(foundPlayer)
            System.out.printf("Created Player in %.2f seconds\n", executionTime);
        else
            System.out.printf("Could not find player in %.2f seconds\n", executionTime);
    }

}
