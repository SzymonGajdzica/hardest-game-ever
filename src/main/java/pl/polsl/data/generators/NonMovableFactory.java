package pl.polsl.data.generators;

import pl.polsl.data.models.NonMovableMapNode;
import pl.polsl.models.Color;
import pl.polsl.sprites.BaseSprite;
import pl.polsl.sprites.BorderSprite;
import pl.polsl.sprites.EndSprite;
import pl.polsl.sprites.SubEndSprite;

public class NonMovableFactory {

    public BaseSprite getSprite(NonMovableMapNode o, Color color){
        if(color.equals(Color.border))
            return new BorderSprite(o.getPosition(), o.getSize(), color);
        else if(color.equals(Color.subEnd))
            return new SubEndSprite(o.getPosition(), o.getSize(), color);
        else if(color.equals(Color.end))
            return new EndSprite(o.getPosition(), o.getSize(), color);
        return new BaseSprite(o.getPosition(), o.getSize(), color);
    }

}
