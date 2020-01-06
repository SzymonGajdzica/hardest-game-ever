package pl.polsl.sprites;

import pl.polsl.game.ActionCallbacks;
import pl.polsl.graphics.Direction;
import pl.polsl.graphics.GraphicsAdapter;
import pl.polsl.models.Color;
import pl.polsl.models.DVector;
import pl.polsl.models.IVector;

public class BaseSprite implements Drawable {

    protected DVector currentPosition;
    protected final IVector size;
    protected final Color color;

    public BaseSprite(IVector currentPosition, IVector size, Color color){
        this.currentPosition = currentPosition.toDVector();
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(GraphicsAdapter graphicsAdapter) {
        graphicsAdapter.drawRect(getCalculatedPosition(currentPosition), size, color);
    }

    public void handleAction(double elapsedTime){

    }

    public void restore(){

    }

    protected IVector getCalculatedPosition(DVector position){
        return position.toIVector();
    }

    public void onIntersect(UserSprite player, ActionCallbacks actionCallbacks){

    }

    public boolean intersects(BaseSprite baseSprite) {
        IVector calculatedPosition1 = getCalculatedPosition(currentPosition);
        IVector calculatedPosition2 = baseSprite.getCalculatedPosition(baseSprite.currentPosition);
        IVector size1 = size;
        IVector size2 = baseSprite.size;
        int tw = size1.getX();
        int th = size1.getY();
        int rw = size2.getX();
        int rh = size2.getY();
        int tx = calculatedPosition1.getX();
        int ty = calculatedPosition1.getY();
        int rx = calculatedPosition2.getX();
        int ry = calculatedPosition2.getY();
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }

    protected Direction getDirection(DVector position) {
        DVector DVector = this.currentPosition.subtract(position);
        double tan = Math.abs(Math.atan2(DVector.getY(), DVector.getX()) * 180.0f / 3.14f - 180.0f);
        if (tan >= 22.5f && tan <= 67.5f)
            return Direction.UP_RIGHT;
        if (tan >= 67.5f && tan <= 112.5f)
            return Direction.UP;
        if (tan >= 112.5f && tan <= 157.5f)
            return Direction.UP_LEFT;
        if (tan >= 157.5f && tan <= 202.5f)
            return Direction.LEFT;
        if (tan >= 202.5f && tan <= 247.5f)
            return Direction.DOWN_LEFT;
        if (tan >= 247.5f && tan <= 292.5f)
            return Direction.DOWN;
        if (tan >= 292.5f && tan <= 337.5f)
            return Direction.DOWN_RIGHT;
        return Direction.RIGHT;
    }

}
