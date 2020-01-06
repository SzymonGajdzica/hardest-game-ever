package pl.polsl.sprites;

import pl.polsl.graphics.Direction;
import pl.polsl.models.Color;
import pl.polsl.models.DVector;
import pl.polsl.models.IVector;

public class MovableSprite extends BaseSprite {

    private final double speed;
    private final double sqrt2 = Math.sqrt(2);
    protected final DVector oldPosition = new DVector(0.0, 0.0);
    protected final IVector startPosition;

    public MovableSprite(IVector position, IVector size, Color color, double speed){
        super(position, size, color);
        this.speed = speed;
        this.startPosition = position.copied();
    }

    protected void move(Direction direction, double timeDelay) {
        oldPosition.copy(currentPosition);
        double additionalFactor = 1.0;
        if(direction.getY() != 0.0 && direction.getX() != 0.0)
            additionalFactor = sqrt2;
        currentPosition.move(direction.getX() * speed / additionalFactor * timeDelay,
                direction.getY() * speed / additionalFactor * timeDelay);
    }

    @Override
    protected IVector getCalculatedPosition(DVector position) {
        return new DVector(position.getX() - (size.getX() / 2.0), position.getY() - (size.getY() / 2.0)).toIVector();
    }

    @Override
    public void restore() {
        super.restore();
        currentPosition = startPosition.toDVector();
    }
}
