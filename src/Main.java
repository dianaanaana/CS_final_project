import java.io.IOException;

import javafx.application.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
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
        center.getChildren().addAll(startGame, settings, exit);
        center.setAlignment(Pos.CENTER);
        BorderPane pane_main = new BorderPane();
        StackPane pane_bg = new StackPane();
        pane_main.setCenter(center);
        pane_bg.getChildren().addAll(new ImageView("/resources/images/start_bg.png"), pane_main);
        StackPane.setAlignment(center, Pos.CENTER);
        Scene scene = new Scene(pane_bg, 1280, 720);
        // stage setting
        stage.setTitle("ncuRPG");
        stage.setScene(Loading.scene(scene, 10));
        stage.show();

        startGame.setOnMouseClicked(e -> {
            stage.setScene(Loading.scene(Dorm.scene(640, 360), 2));
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