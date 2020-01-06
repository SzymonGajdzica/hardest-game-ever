package pl.polsl.sprites;

import pl.polsl.application.Global;
import pl.polsl.game.ActionCallbacks;
import pl.polsl.graphics.Direction;
import pl.polsl.graphics.KeyboardKey;
import pl.polsl.models.Color;
import pl.polsl.models.IVector;

import java.util.HashSet;
import java.util.Set;

public class UserSprite extends MovableSprite {

    private final Set<SubEndSprite> consumedSprites = new HashSet<>();

    public UserSprite(IVector position, IVector size, Color color) {
        super(position, size, color, color.getSum() / Global.SPEED_REDUCTION);
    }

    public void move(Set<KeyboardKey> pressedKeyboardKeys, double timeDelay){
        int sum = pressedKeyboardKeys.stream().mapToInt(KeyboardKey::getValue).sum();
        switch (sum) {
            case 1:
                move(Direction.UP, timeDelay);
                break;
            case 2:
                move(Direction.DOWN, timeDelay);
                break;
            case 4:
                move(Direction.RIGHT, timeDelay);
                break;
            case 8:
                move(Direction.LEFT, timeDelay);
                break;
            case 5:
                move(Direction.UP_RIGHT, timeDelay);
                break;
            case 9:
                move(Direction.UP_LEFT, timeDelay);
                break;
            case 6:
                move(Direction.DOWN_RIGHT, timeDelay);
                break;
            case 10:
                move(Direction.DOWN_LEFT, timeDelay);
                break;
        }
    }

    public void cancelMove(){
        currentPosition.copy(oldPosition);
    }

    @Override
    public void restore() {
        super.restore();
        consumedSprites.clear();
    }

    public void capture(SubEndSprite subEndSprite, ActionCallbacks actionCallbacks) {
        if(consumedSprites.add(subEndSprite))
            actionCallbacks.onConsume();
    }

    public boolean checkEnd(int numberOfSubEnds) {
        return consumedSprites.size() == numberOfSubEnds;
    }
}
