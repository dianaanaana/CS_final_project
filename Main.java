import java.io.IOException;
import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    static Stage stage;
    public void start(Stage stage) throws IOException{
        Main.stage = stage;
        // scene setting
        VBox center = new VBox();
        Button startGame = new Button();
        Button settings = new Button();
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(startGame, settings);
        BorderPane pane_main = new BorderPane();
        pane_main.setCenter(center);
        Scene scene = new Scene(pane_main, 1080, 720);

        startGame.setOnAction(e -> {
            stage.setScene(dorm.scene(540, 360));
        });
        // stage setting
        stage.setTitle("ncuRPG");
        stage.setScene(scene);
        stage.show();
    }
    public static void switchScene(Scene scene) {
        stage.setScene(scene);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}