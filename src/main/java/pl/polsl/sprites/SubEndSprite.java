package pl.polsl.sprites;

import pl.polsl.game.ActionCallbacks;
import pl.polsl.graphics.GraphicsAdapter;
import pl.polsl.models.Color;
import pl.polsl.models.IVector;

public class SubEndSprite extends BaseSprite {

    private boolean captured = false;

    public SubEndSprite(IVector currentPosition, IVector size, Color color) {
        super(currentPosition, size, color);
    }

    @Override
    public void draw(GraphicsAdapter graphicsAdapter) {
        if(!captured)
            super.draw(graphicsAdapter);
    }

    @Override
    public void restore() {
        super.restore();
        captured = false;
    }

    @Override
    public void onIntersect(UserSprite player, ActionCallbacks actionCallbacks) {
        player.capture(this, actionCallbacks);
        captured = true;
    }

}
