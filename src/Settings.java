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
    static Boolean initinvoke = true;
    public static Scene scene() {
        if(initinvoke) {
            pane.setCenter(center);
            center.getChildren().addAll(musicSetting, exit);
            center.setAlignment(Pos.CENTER);
            initinvoke = false;
        }
        musicSetting.setOnMouseClicked(e -> {
            System.err.println("Music setting");
            Main.music.buttonClick.stop();
            Main.music.buttonClick.play();
            Slider_for_music slider_for_music = new Slider_for_music();
            Main.switchScene(Loading.scene(slider_for_music.scene(), 2));
        });
        exit.setOnMouseClicked(e -> {
            System.err.println("Exit settings");
            Main.music.buttonClick.stop();
            Main.music.buttonClick.play();
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