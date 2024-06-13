import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Music {
    String mainS = new File("src/resources/musics/main.mp3").toURI().toString();
    Media mainV = new Media(mainS);
    public MediaPlayer main = new MediaPlayer(mainV);
    String examS = new File("src/resources/musics/exam.mp3").toURI().toString();
    Media examV = new Media(examS);
    public MediaPlayer exam = new MediaPlayer(examV);
    String buttonClickS = new File("src/resources/musics/buttonClick.mp3").toURI().toString();
    Media buttonClickV = new Media(buttonClickS);
    public MediaPlayer buttonClick = new MediaPlayer(buttonClickV);
    String wrongTypingS = new File("src/resources/musics/wrongTyping.mp3").toURI().toString();
    Media wrongTypingV = new Media(wrongTypingS);
    public MediaPlayer wrongTyping = new MediaPlayer(wrongTypingV);
    String drawV = new File("src/resources/musics/draw.mp3").toURI().toString();
    Media drawM = new Media(drawV);
    public MediaPlayer draw = new MediaPlayer(drawM);
    Boolean initPlay = true;
    public void playMusic() {
        if(initPlay) {
            main.setCycleCount(MediaPlayer.INDEFINITE);
            exam.setCycleCount(MediaPlayer.INDEFINITE);
            main.setVolume(0.1);
            exam.setVolume(0.05);
            draw.setVolume(0.1);
            buttonClick.setVolume(0.1);
            initPlay = false;
        }
        main.play();
    }
    public void swtichMusic(MediaPlayer m1, MediaPlayer m2) {
        m1.stop();
        m2.play();
    }
}
