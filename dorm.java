import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;





public class dorm extends Application{

    static Image mainC = new Image("/src/images/IMG_5414.png");
    static ImageView imageView = new ImageView();

    static Pane move = new Pane();
    static BorderPane pane = new BorderPane();
    static Scene scene = new Scene(pane, 1080, 720);

    public static Scene scene() {
        // movement.start();
        imageView.setImage(mainC);
        move.getChildren().add(imageView);
        
        pane.setCenter(move);

        pane.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, new EventHandler<ContextMenuEvent>() {
            public void handle(ContextMenuEvent e) {
                double targetX = e.getSceneX() - mainC.getWidth()/2;
                double targetY = e.getSceneY() - mainC.getHeight()/2;
                double distanceX = targetX - imageView.getTranslateX();
                double distanceY = targetY - imageView.getTranslateY();
                double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                double duration = distance / 400;
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(imageView.translateXProperty(), imageView.getTranslateX()), new KeyValue(imageView.translateYProperty(), imageView.getTranslateY())),
                    new KeyFrame(Duration.seconds(duration), new KeyValue(imageView.translateXProperty(), targetX), new KeyValue(imageView.translateYProperty(), targetY))
                );
                timeline.play();
            }
        });
        scene.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.SPACE) System.err.printf("x : %f\ny : %f\n", imageView.getX(), imageView.getY());
            if(e.getCode() == KeyCode.W) imageView.setX(imageView.getX() + 10);
        });

        return scene;
    }
    public void start(Stage stage) {
            // stage setting
            stage.setTitle("dorm");
            // csLab
            stage.setScene(scene());
            stage.show();
        }
    public static void main(String[] args) {
        launch(args);
    }
}