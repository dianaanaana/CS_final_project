import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

class LoadingIcon extends ImageView {
    public LoadingIcon(int duration) {
        super();
        rotate(duration);
    }
    public void rotate(int duration) {
        RotateTransition rotation = new RotateTransition(Duration.seconds(duration), this);
        rotation.setFromAngle(0);
        rotation.setByAngle(1800);
        rotation.play();
    }
}



public class Loading extends Application {
    static int duration;
    static Boolean initInvoke = true;
    static Image[] images = new Image[2];
    static LoadingIcon loadingIcon = new LoadingIcon(duration);
    static Image[] loadingTexts = new Image[4];
    static ImageView loadingText = new ImageView();
    static VBox vBox = new VBox();

    static BorderPane pane = new BorderPane();
    static Scene scene = new Scene(pane, 1280, 720);
    public static Scene scene(Scene nextScene, int duration) {
        if(initInvoke) {
            images[0] = new Image("/resources/images/shih.png");
            images[1] = new Image("/resources/images/pepsi.png");
            loadingTexts[0] = new Image("/resources/images/Loading0.png");
            loadingTexts[1] = new Image("/resources/images/Loading1.png");
            loadingTexts[2] = new Image("/resources/images/Loading2.png");
            loadingTexts[3] = new Image("/resources/images/Loading3.png");
            vBox.getChildren().addAll(loadingIcon, loadingText);
            pane.setCenter(vBox);
            vBox.setAlignment(Pos.CENTER);
            // BorderPane.setAlignment(vBox, Pos.CENTER);
            initInvoke = false;
        }
        loadingIcon.setImage(images[(int)(Math.random()*2)]);
        loadingIcon.rotate(duration);
        Timeline textAnimation = new Timeline();
        int i[] = {0};
        textAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(200), e -> {
            if(i[0] == 4) i[0] = 0;
            loadingText.setImage(loadingTexts[i[0]]);
            i[0]++;
        }));
        textAnimation.setCycleCount(duration*5);
        textAnimation.play();
        Timeline switchScene = new Timeline();
        switchScene.getKeyFrames().add(new KeyFrame(Duration.seconds(duration), e -> {
            Main.switchScene(nextScene);
        }));
        switchScene.play();
        switchScene.setOnFinished(ss -> {
            Main.switchScene(nextScene);
        });
        return scene;
    }
    public void start(Stage stage) {
            // stage setting
            stage.setTitle("");
            stage.setScene(scene(Dorm.scene(640, 360), 10));
            stage.show();
        }
    public static void main(String[] args) {
        launch(args);
    }
}
