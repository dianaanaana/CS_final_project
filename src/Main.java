import java.io.IOException;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application{
    static Stage stage;
    public void start(Stage stage) throws IOException{
        Main.stage = stage;
        // scene setting
        VBox center = new VBox();
        ImageView startGame = new ImageView("/resources/images/startGame.png");
        ImageView settings = new ImageView("/resources/images/settings.png");
        ImageView exit = new ImageView("/resources/images/exit.png");
        center.setAlignment(Pos.CENTER);
        center.getChildren().addAll(startGame, settings, exit);
        BorderPane pane_main = new BorderPane();
        pane_main.setCenter(center);
        Scene scene = new Scene(pane_main, 1280, 720);
        // stage setting
        stage.setTitle("ncuRPG");
        stage.setScene(scene);
        stage.show();

        startGame.setOnMouseClicked(e -> {
            stage.setScene(Dorm.scene(640, 360));
        });
        exit.setOnMouseClicked(e -> {
            stage.close();
        });

    }
    public static void switchScene(Scene scene) {
        stage.setScene(scene);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}