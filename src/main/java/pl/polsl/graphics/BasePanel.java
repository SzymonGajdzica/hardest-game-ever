package pl.polsl.graphics;

import javax.swing.*;
import java.awt.*;

public class BasePanel extends JPanel {

    private final OnViewUpdateListener onViewUpdateListener;
    private final KeyboardManager keyboardManager;
    private long startTime;

    public BasePanel(OnViewUpdateListener onViewUpdateListener){
        this.onViewUpdateListener = onViewUpdateListener;
        keyboardManager = new KeyboardManager();
        startTime = System.nanoTime();
        setFocusable(true);
        addKeyListener(keyboardManager);
        setBackground(Color.WHITE);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        long oldTime = startTime;
        startTime = System.nanoTime();
        onViewUpdateListener.onViewUpdate(new GraphicsAdapter(g), System.nanoTime() - oldTime, keyboardManager.getKeyboardKeys());
        repaint();
    }

}
