package pl.polsl.game;

import pl.polsl.data.DataController;
import pl.polsl.data.models.MapData;
import pl.polsl.graphics.KeyboardKey;
import pl.polsl.sound.Sound;
import pl.polsl.sound.SoundPlayer;
import pl.polsl.sprites.BaseSprite;
import pl.polsl.sprites.Drawable;
import pl.polsl.sprites.UserSprite;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameControllerImpl implements GameController, ActionCallbacks {

    private final GameView gameView;
    private final DataController dataController = new DataController();
    private final SoundPlayer soundPlayer;
    private final List<File> mapFiles = new ArrayList<>();

    private MapData mapData;
    private int mapCounter = 0;
    private boolean isReady = false;

    public GameControllerImpl(GameView gameView) {
        this.gameView = gameView;
        this.soundPlayer = new SoundPlayer(gameView);
    }

    @Override
    public void onCreate() {
        try (Stream<Path> paths = Files.walk(Paths.get("maps"))) {
            mapFiles.addAll(paths
                    .filter(Files::isRegularFile)
                    .map(Path::toFile)
                    .collect(Collectors.toList()));
            generateGame();
            soundPlayer.play(Sound.BACKGROUND);
        } catch (IOException e) {
            gameView.notifyError(e);
        }
    }

    private void generateGame() {
        isReady = false;
        gameView.showLoadingView();
        new Thread(() -> {
            try {
                if (mapFiles.size() < mapCounter) {
                    gameView.notifyEndOfMaps();
                } else {
                    File file = mapFiles.get(mapCounter);
                    mapData = dataController.generateMapData(file);
                    gameView.notifyNewGame(mapData.getMapSize(), file.getName());
                    isReady = true;
                }
            } catch (Exception ignore) {
                mapCounter++;
                generateGame();
            }
        }).start();
    }

    @Override
    public void handleAction(long elapsedTimeInNanoSeconds, Set<KeyboardKey> pressedKeyboardKeys) {
        if(!checkReady())
            return;
        double elapsedTime = elapsedTimeInNanoSeconds / 1000000000.0;
        UserSprite player = mapData.getPlayer();
        player.move(pressedKeyboardKeys, elapsedTime);
        mapData.getSprites().forEach(baseSprite -> {
            baseSprite.handleAction(elapsedTime);
            if(baseSprite.intersects(player))
                baseSprite.onIntersect(player, this);
        });
    }

    private boolean checkReady(){
        return isReady && mapData.getPlayer() != null;
    }

    @Override
    public List<Drawable> getDrawables() {
        if(!checkReady())
            return new LinkedList<>();
        return new LinkedList<>(mapData.getSprites());
    }

    @Override
    public void checkWin() {
        if(mapData.getPlayer().checkEnd(mapData.getNumberOfSubEnds()) && checkReady()){
            mapCounter++;
            soundPlayer.play(Sound.SUCCESS);
            generateGame();
        }
    }

    @Override
    public void onLose() {
        if(checkReady()){
            soundPlayer.play(Sound.FAIL);
            mapData.getSprites().forEach(BaseSprite::restore);
        }
    }

    @Override
    public void onConsume() {
        soundPlayer.play(Sound.CONSUME);
    }
}

