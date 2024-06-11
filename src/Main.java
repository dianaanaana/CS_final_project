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
    static Boolean initInvoke = true;
    static Stage stage;
    static VBox center = new VBox();
    static ImageView startGame = new ImageView("/resources/images/startGame.png");
    static ImageView settingsImage = new ImageView("/resources/images/settings.png");
    static ImageView exit = new ImageView("/resources/images/exit.png");
    static BorderPane pane_main = new BorderPane();
    static StackPane pane_bg = new StackPane();
    static Scene scene = new Scene(pane_bg, 1280, 720);
    static Settings settings = new Settings();
    
    public static Scene scene() {
        
        // scene setting
        if(initInvoke) {
            center.getChildren().addAll(startGame, settingsImage, exit);
            pane_bg.getChildren().addAll(new ImageView("/resources/images/start_bg.png"), pane_main);
            initInvoke = false;
        }
        center.setAlignment(Pos.CENTER);
        pane_main.setCenter(center);
        StackPane.setAlignment(center, Pos.CENTER);
        return scene;
    }
    public void start(Stage stage) throws IOException{
        
        
        Main.stage = stage;
        // stage setting
        stage.setTitle("ncuRPG");
        stage.setScene(Loading.scene(scene(), 2));
        stage.show();

        startGame.setOnMouseClicked(e -> {
            stage.setScene(Loading.scene(Dorm.scene(640, 360), 2));
        });
        settingsImage.setOnMouseClicked(e -> {
            // stage.setScene(Loading.scene(Settings.scene(), 2));
            stage.setScene(Loading.scene(settings.scene(), 2));
            System.err.println("Settings");

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