import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Settings extends Application {
    static BorderPane pane = new BorderPane();
    static Scene scene = new Scene(pane, 1280, 720);
    static VBox center = new VBox();
    static ImageView musicSetting = new ImageView("/resources/images/musicSetting.png");
    static ImageView exit = new ImageView("/resources/images/back.png");
    public static Scene scene() {
        pane.setCenter(center);
        center.getChildren().addAll(musicSetting, exit);
        center.setAlignment(Pos.CENTER);
        musicSetting.setOnMouseClicked(e -> {
            System.err.println("Music setting");
            Slider_for_music app = new Slider_for_music();
            app.startApplication(new String[0]);
        });
        exit.setOnMouseClicked(e -> {
            System.err.println("Exit settings");
            Main.switchScene(Main.scene());
        });
        return scene;
    }
    public void start(Stage stage) {
        // stage setting
        stage.setTitle("");
        stage.setScene(scene());
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}