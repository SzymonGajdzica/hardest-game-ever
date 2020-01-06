package pl.polsl.game;

import pl.polsl.graphics.KeyboardKey;
import pl.polsl.sprites.Drawable;

import java.util.List;
import java.util.Set;

public interface GameController {

    void handleAction(long elapsedTimeInNanoSeconds, Set<KeyboardKey> pressedKeyboardKeys);

    List<Drawable> getDrawables();

    void onCreate();

}
