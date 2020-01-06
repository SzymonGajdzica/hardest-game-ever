package pl.polsl.sprites;

import pl.polsl.game.ActionCallbacks;
import pl.polsl.models.Color;
import pl.polsl.models.IVector;

public class EndSprite extends BaseSprite {

    public EndSprite(IVector currentPosition, IVector size, Color color) {
        super(currentPosition, size, color);
    }

    @Override
    public void onIntersect(UserSprite player, ActionCallbacks actionCallbacks) {
        actionCallbacks.checkWin();
    }

}
