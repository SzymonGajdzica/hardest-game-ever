package pl.polsl.graphics;

import java.awt.event.KeyEvent;

public enum KeyboardKey {

    UP(1),
    DOWN(2),
    RIGHT(4),
    LEFT(8);

    private final int value;

    KeyboardKey(int value){
        this.value = value;
    }

    public static KeyboardKey getKey(KeyEvent keyEvent) {
        switch (keyEvent.getKeyCode()) {
            case KeyEvent.VK_DOWN:
                return KeyboardKey.DOWN;
            case KeyEvent.VK_UP:
                return KeyboardKey.UP;
            case KeyEvent.VK_RIGHT:
                return KeyboardKey.RIGHT;
            case KeyEvent.VK_LEFT:
                return KeyboardKey.LEFT;
            default:
                return null;
        }
    }

    public int getValue() {
        return value;
    }
}
