import java.io.File;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Result_ntu extends Application {  
    private MediaPlayer mediaForMusic;

    Credit credit = new Credit();
    public Scene scene() {
        Main.music.main.stop();
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1280, 720);
        try {
            File imageFile = new File("src/resources/images/resNTU.png");  
            Image image = new Image(imageFile.toURI().toString());
            ImageView imageView = new ImageView(image);
            File musicFile = new File("src/resources/musics/congratulation.mp3");
            Media music = new Media(musicFile.toURI().toString());
            mediaForMusic = new MediaPlayer(music);
            mediaForMusic.setVolume(0.1);
            mediaForMusic.play();
            root.getChildren().add(imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Timeline timer = new Timeline();
        timer.getKeyFrames().add(new javafx.animation.KeyFrame(Duration.seconds(5), e -> {
            // mediaForMusic.stop();
            Main.stage.setScene(credit.scene());
        }));
        timer.play();
        return scene;
    }


    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.setTitle("game");
        primaryStage.setScene(scene());
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
