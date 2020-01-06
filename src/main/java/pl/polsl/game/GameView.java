package pl.polsl.game;

import pl.polsl.models.IVector;

public interface GameView {

    void notifyEndOfMaps();

    void notifyError(Exception e);

    void notifyNewGame(IVector size, String name);

    void showLoadingView();

}
