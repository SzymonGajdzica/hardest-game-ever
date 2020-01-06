package pl.polsl.graphics;

import java.util.Set;

public interface OnViewUpdateListener {

    void onViewUpdate(GraphicsAdapter graphicsAdapter, long elapsedTimeInNanoSeconds, Set<KeyboardKey> pressedKeyboardKeys);

}
