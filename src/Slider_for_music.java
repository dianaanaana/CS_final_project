import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Slider_for_music extends Application {
    private Slider volumeSlider;


    public Scene scene() {
        // 创建音量滑块和音量显示标签
        Label titleLabel = new Label("音量：");
        titleLabel.setFont(new Font(50));
        ImageView back = new ImageView(new Image("resources/images/back.png"));
        HBox volumeBox = new HBox(10);
        volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setPrefWidth(400); // Sets the preferred width to 400
        volumeSlider.setPrefHeight(50); // Sets the preferred height to 50
        volumeSlider.setMinWidth(300); // Sets the minimum width to 300
        volumeSlider.setMinHeight(40); // Sets the minimum height to 40
        volumeSlider.setMaxWidth(500); // Sets the maximum width to 500
        volumeSlider.setMaxHeight(60); // Sets the maximum height to 60
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(10);
        volumeSlider.setBlockIncrement(1);
        volumeBox.setAlignment(Pos.CENTER);
        // 音量百分比标签
        Label volumeValueLabel = new Label();
        volumeValueLabel.setFont(new Font(20));
        volumeValueLabel.textProperty().bind(new StringBinding() {
            {
                bind(volumeSlider.valueProperty());
            }

            @Override
            protected String computeValue() {
                return String.format("%.0f%%", volumeSlider.getValue());
            }
        });
        volumeBox.getChildren().addAll(volumeSlider, volumeValueLabel);
        // 创建主要布局
        VBox mainVbox = new VBox(20);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.getChildren().addAll(titleLabel, volumeBox, back);
        BorderPane root = new BorderPane();
        root.setCenter(mainVbox);
        // 设置场景和舞台
        Scene scene = new Scene(root, 1280, 720);
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
                Main.music.main.setVolume(newValue.doubleValue() / 100);
                Main.music.exam.setVolume(newValue.doubleValue() / 200);
                Main.music.draw.setVolume(newValue.doubleValue() / 100);
                Main.music.congratulation.setVolume(newValue.doubleValue() / 100);
                Main.music.fail.setVolume(newValue.doubleValue() / 100);
                Main.music.buttonClick.setVolume(newValue.doubleValue() / 100);
        });
        back.setOnMouseClicked(e -> {
            Main.music.buttonClick.stop();
            Main.music.buttonClick.play();
            Main.switchScene(Loading.scene(Settings.scene(), 2));
        });
        return scene;
    }
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("音樂播放器");
        primaryStage.setScene(scene());
        primaryStage.show();
    }
    public void startApplication(String[] args) {
        Platform.runLater(() -> {
            try {
                start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}
