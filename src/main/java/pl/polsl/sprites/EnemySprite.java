package pl.polsl.sprites;

import pl.polsl.application.Global;
import pl.polsl.game.ActionCallbacks;
import pl.polsl.models.Color;
import pl.polsl.models.DVector;
import pl.polsl.models.IVector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EnemySprite extends MovableSprite {

    private int currentPointIndex = 0;
    private boolean isReversed = false;
    private final ArrayList<DVector> points;
    private final boolean isLooped;

    public EnemySprite(IVector position, IVector size, Color color, List<IVector> points, boolean isLooped) {
        super(position, size, color, color.getSum() / Global.SPEED_REDUCTION + 100);
        this.points = points.stream().map(IVector::toDVector).collect(Collectors.toCollection(ArrayList::new));
        this.isLooped = isLooped;
    }

    @Override
    public void restore() {
        super.restore();
        currentPointIndex = 0;
        if(isReversed)
            Collections.reverse(points);
        isReversed = false;
    }

    @Override
    public void onIntersect(UserSprite player, ActionCallbacks actionCallbacks) {
        actionCallbacks.onLose();
    }

    @Override
    public void handleAction(double elapsedTime) {
        DVector nextPoint = points.get(currentPointIndex);
        move(getDirection(nextPoint), elapsedTime);
        if(currentPosition.equals(nextPoint))
            currentPointIndex++;
        if(currentPointIndex >= points.size()){
            currentPointIndex = 0;
            if(!isLooped){
                Collections.reverse(points);
                isReversed = !isReversed;
            }

        }
    }
}
