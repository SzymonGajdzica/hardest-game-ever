package pl.polsl.data.generators;

import pl.polsl.models.Color;
import pl.polsl.models.IVector;
import pl.polsl.sprites.BaseSprite;

import java.util.Map;
import java.util.Set;

public interface Generator {

    Set<BaseSprite> generate(Color[][] colors, Map<Color, Set<IVector>> coloredPoints);

    void displaySummary();

}
