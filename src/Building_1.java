import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Building_1 extends Application{
    static Boolean initInvoke = true;
    static Image mainC = new Image("/resources/images/IMG_5414.png");
    static ImageView imageView = new ImageView();
    static Pane move = new Pane();
    static BorderPane pane = new BorderPane();
    static Scene scene = new Scene(pane, 1280, 720);
    public static Scene scene(int iniX, int iniY) {
        if (initInvoke) {
            imageView.setImage(mainC);
            move.getChildren().add(imageView);
            pane.setCenter(move);
            initInvoke = false;
        }
        // movement
        imageView.setTranslateX(iniX - mainC.getWidth()/2);
        imageView.setTranslateY(iniY - mainC.getHeight()/2);
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
            timeline.setOnFinished(e2 -> {
                // left boundary
            if (targetX < 0) {
                System.err.println("Error: ImageView has hit the left boundary!");
                Main.switchScene(Calculus.scene());
            }
            // right boundary
            if (targetX + mainC.getWidth() > scene.getWidth()) {
                System.err.println("Error: ImageView has hit the right boundary!");
                Main.switchScene(Discrete.scene());
            }
            // top boundary
            if (targetY < 0) {
                System.err.println("Error: ImageView has hit the top boundary!");
                Main.switchScene(Building_2.scene(640, 720));
            }
            // bottom boundary
            if (targetY + mainC.getHeight() > scene.getHeight()) {
                System.err.println("Error: ImageView has hit the bottom boundary!");
            }
            });
        }
        });
        return scene;
    }
    public void start(Stage stage) {
            // stage setting
            stage.setTitle("building_1");
            // csLab
            stage.setScene(scene(640, 720));
            stage.show();
        }
    public static void main(String[] args) {
        launch(args);
    }
}
