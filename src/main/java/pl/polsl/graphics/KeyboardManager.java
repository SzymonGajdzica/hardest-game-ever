package pl.polsl.graphics;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashSet;

class KeyboardManager extends KeyAdapter {

    private final HashSet<KeyboardKey> keyboardKeys = new HashSet<>();

    @Override
    public void keyReleased(KeyEvent e) {
        keyboardKeys.remove(KeyboardKey.getKey(e));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        KeyboardKey keyboardKey = KeyboardKey.getKey(e);
        if(keyboardKey != null)
            keyboardKeys.add(keyboardKey);
    }

    public HashSet<KeyboardKey> getKeyboardKeys() {
        return keyboardKeys;
    }
}
