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
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Road extends Application{
    static Boolean initInvoke = true;

    static Image mainC = new Image("/resources/images/stand_right.png");
    static Image mainC_left = new Image("/resources/images/stand_left.png");
    static Image walk_right = new Image("/resources/images/walk_rightv150.gif");
    static Image walk_left = new Image("/resources/images/walk_leftv150.gif");
    static ImageView imageView = new ImageView();
    static ImageView bg = new ImageView("/resources/images/road.png");
    static Pane move = new Pane();
    static BorderPane pane = new BorderPane();
    static StackPane pane_bg = new StackPane();
    static Scene scene = new Scene(pane_bg, 1280, 720);
    public static Scene scene(double iniX, double iniY) {
        if (initInvoke) {
            imageView.setImage(mainC);
            move.getChildren().add(imageView);
            pane.setCenter(move);
            pane_bg.getChildren().addAll(bg, pane);
            initInvoke = false;
        }

        imageView.setTranslateX(iniX - mainC.getWidth()/2);
        imageView.setTranslateY(iniY - mainC.getHeight()/2);
        
        pane.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, new EventHandler<ContextMenuEvent>() {
            public void handle(ContextMenuEvent e) {
                double oldX = imageView.getTranslateX();
                double oldY = imageView.getTranslateY();
                double targetX = e.getSceneX() - mainC.getWidth()/2;
                double targetY = e.getSceneY() - mainC.getHeight()/2;
                double distanceX = targetX - imageView.getTranslateX();
                double distanceY = targetY - imageView.getTranslateY();
                double distance = Math.sqrt(distanceX * distanceX + distanceY * distanceY);
                double duration = distance / 400;
                if(targetX > oldX){
                    imageView.setImage(walk_right);
                }else{
                    imageView.setImage(walk_left);
                }
                Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(imageView.translateXProperty(), oldX), new KeyValue(imageView.translateYProperty(), oldY)),
                    new KeyFrame(Duration.seconds(duration), new KeyValue(imageView.translateXProperty(), targetX), new KeyValue(imageView.translateYProperty(), targetY))
                );
                timeline.play();
                timeline.setOnFinished(e2 -> {
                    if(targetX > oldX){
                        imageView.setImage(mainC);
                    }else{
                        imageView.setImage(mainC_left);
                    }
                    // left boundary
                if (targetX < 0) {
                    System.err.println("Error: ImageView has hit the left boundary!");
                    Main.switchScene(Loading.scene(Dorm.scene(1280, 360), 2));
                }
                // right boundary
                if (targetX + mainC.getWidth() > scene.getWidth()) {
                    System.err.println("Error: ImageView has hit the right boundary!");
                    Main.switchScene(Loading.scene(Building_1.scene(600, 640), 2));
                }
                // top boundary
                if (targetY < 0) {
                    System.err.println("Error: ImageView has hit the top boundary!");
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
            stage.setTitle("road_1");
            // csLab
            stage.setScene(scene(0, 360));
            stage.show();
        }
    public static void main(String[] args) {
        launch(args);
    }
}