package pl.polsl.game;

import pl.polsl.graphics.BasePanel;
import pl.polsl.graphics.GraphicsAdapter;
import pl.polsl.graphics.KeyboardKey;
import pl.polsl.graphics.OnViewUpdateListener;
import pl.polsl.models.IVector;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.Set;

public class GameViewImpl extends JFrame implements GameView, OnViewUpdateListener {

    private final GameController gameController = new GameControllerImpl(this);
    private static GameViewImpl gameView;

    private GameViewImpl() {
        add(new BasePanel(this));
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        setVisible(true);
        pack();
        setBounds(0, 0,500, 500);
    }

    public static void init(){
        if(gameView == null){
            gameView = new GameViewImpl();
            gameView.gameController.onCreate();
        }
    }

    @Override
    public void onViewUpdate(GraphicsAdapter graphicsAdapter, long elapsedTimeInNanoSeconds, Set<KeyboardKey> pressedKeyboardKeys) {
        gameController.handleAction(elapsedTimeInNanoSeconds, pressedKeyboardKeys);
        gameController.getDrawables().forEach(baseSprite -> baseSprite.draw(graphicsAdapter));
    }

    @Override
    public void notifyEndOfMaps() {
        JOptionPane.showMessageDialog(this,
                "You completed all maps",
                "CONGRATULATIONS",
                JOptionPane.INFORMATION_MESSAGE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void notifyError(Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
                e.getMessage(),
                e.getClass().getSimpleName(),
                JOptionPane.ERROR_MESSAGE);
        dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }

    @Override
    public void notifyNewGame(IVector size, String name) {
        pack();
        setTitle(name);
        setBounds(0, 0,size.getX() + getInsets().right + getInsets().left,
                size.getY() + getInsets().top + getInsets().bottom);
    }

    @Override
    public void showLoadingView() {
        setTitle("Loading");
    }
}
