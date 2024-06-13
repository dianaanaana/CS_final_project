import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Result_ncu extends Application {  

    Credit credit = new Credit();
    public Scene scene() {
        int passSub = 0;
        if(Main.scores_calculus >= 60) passSub++;
        if(Main.scores_discrete >= 60) passSub++;
        if(Main.scores_engineering >= 60) passSub++;
        if(Main.scores_cs >= 60) passSub++;

        Main.music.main.stop();
        StackPane root = new StackPane();
        Scene scene = new Scene(root, 1280, 720);
        try {
            Image allpass = new Image("resources/images/allpass.png");
            Image pass = new Image("resources/images/pass.png");
            Image fail = new Image("resources/images/fail.png");
            ImageView resImage = new ImageView();
            if(passSub <= 2) {
                resImage.setImage(fail);
                Main.music.swtichMusic(Main.music.main, Main.music.fail);
            } else {
                Main.music.swtichMusic(Main.music.main, Main.music.congratulation);
                if(passSub == 4) resImage.setImage(allpass);
                else resImage.setImage(pass);
            }
            root.getChildren().add(resImage);
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
