import javafx.application.Application;
import javafx.beans.binding.StringBinding;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;

class Slider_for_music extends Application {
    private MediaPlayer mediaPlayer;
    private Slider volumeSlider;

    @Override
    public void start(Stage primaryStage) {
        ToggleGroup togglegroup = new ToggleGroup();
        RadioButton b1 = new RadioButton("1");
        RadioButton b2 = new RadioButton("2");
        RadioButton b3 = new RadioButton("3");
        b1.setToggleGroup(togglegroup);
        b2.setToggleGroup(togglegroup);
        b3.setToggleGroup(togglegroup);

        b1.setOnAction(event -> {
            System.out.println("1");
            playMusic(new File("src/a.mp3").toURI().toString());
        });

        b2.setOnAction(event -> {
            System.out.println("2");
            playMusic(new File("src/b.mp3").toURI().toString());
        });

        b3.setOnAction(event -> {
            System.out.println("3");
            playMusic(new File("src/c.mp3").toURI().toString());
        });

        VBox musicVbox = new VBox();
        musicVbox.getChildren().addAll(b1, b2, b3);

        // 创建播放按钮
        Button playButton = new Button("播放音樂");
        playButton.setOnAction(e -> {
            if (mediaPlayer != null) {
                mediaPlayer.play();
            }
        });

        // 创建音量滑块和音量显示标签
        Label titleLabel = new Label("音樂設定");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        HBox volumeBox = new HBox(10);
        Label volumeLabel = new Label("音量調整:");
        volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setShowTickLabels(true);
        volumeSlider.setShowTickMarks(true);
        volumeSlider.setMajorTickUnit(10);
        volumeSlider.setBlockIncrement(1);

        // 音量百分比标签
        Label volumeValueLabel = new Label();
        volumeValueLabel.textProperty().bind(new StringBinding() {
            {
                bind(volumeSlider.valueProperty());
            }

            @Override
            protected String computeValue() {
                return String.format("%.0f%%", volumeSlider.getValue());
            }
        });

        volumeBox.getChildren().addAll(volumeLabel, volumeSlider, volumeValueLabel);

        // 创建主要布局
        VBox mainVbox = new VBox(20);
        mainVbox.setAlignment(Pos.CENTER);
        mainVbox.getChildren().addAll(titleLabel, volumeBox, musicVbox);

        // 创建StackPane布局
        StackPane root = new StackPane();
        root.getChildren().add(mainVbox);
        StackPane.setAlignment(playButton, Pos.BOTTOM_CENTER); // 将播放按钮对齐到StackPane的底部
        root.getChildren().add(playButton);

        // 设置场景和舞台
        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("音樂播放器");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void playMusic(String audioFilePath) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
        Media media = new Media(audioFilePath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(volumeSlider.getValue() / 100); // 设置初始音量

        // 绑定音量滑块与新的MediaPlayer
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue() / 100);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
