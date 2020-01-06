package pl.polsl.sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import pl.polsl.game.GameView;

import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {

    private final GameView gameView;
    private final Map<Sound, MediaPlayer> mediaPlayers = new HashMap<>();
    private static final int isTrue = 0;

    public SoundPlayer(GameView gameView) {
        this.gameView = gameView;
    }

    public void play(Sound sound) {
        try {
            MediaPlayer oldPlayer = mediaPlayers.get(sound);
            if(oldPlayer != null){
                if(oldPlayer.getCurrentTime().compareTo(oldPlayer.getTotalDuration()) >= isTrue)
                    oldPlayer.seek(Duration.ZERO);
                return;
            }
            URL resource = SoundPlayer.class.getResource("/" + sound.getFileName());
            MediaPlayer mediaPlayer = new MediaPlayer(new Media(Paths.get(resource.toURI()).toFile().toURI().toString()));
            mediaPlayer.play();
            mediaPlayers.put(sound, mediaPlayer);
            mediaPlayer.setOnEndOfMedia(() -> {
                if (sound.isLooped())
                    mediaPlayer.seek(Duration.ZERO);
                else
                    mediaPlayer.pause();
            });
            mediaPlayer.setOnError(() -> gameView.notifyError(mediaPlayer.getError()));
        } catch (Exception e) {
            gameView.notifyError(e);
        }
    }

}