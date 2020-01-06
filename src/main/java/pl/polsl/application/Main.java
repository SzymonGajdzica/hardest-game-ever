package pl.polsl.application;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.polsl.game.GameViewImpl;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        if(!getParameters().getRaw().contains("run"))
            throw new Exception("Execute file with launcher or launcher_debug file");
        else
            GameViewImpl.init();
    }

}

