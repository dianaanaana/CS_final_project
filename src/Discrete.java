import java.util.*;

import javafx.scene.text.Font;
import javafx.util.Duration;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

class objs extends ImageView {
    public int start, end;
    public Boolean isScored = false;
    private Image img;
    private Image gif;
    public objs(Image image_1, Image image_2) {
        super();
        this.setFitHeight(100);
        this.setFitWidth(100);
        this.img = image_1;
        this.gif = image_2;
        this.setImage(image_1);
    }
    public void changeImage() {
        this.setImage(gif);
    }
}
public class Discrete extends Application{
    static Boolean start = false;
    static Boolean end = false;
    static Boolean b2b = false;
    static Boolean b2s = false;
    static int score = 0;
    static Text scoreT = new Text(Integer.toString(score));
    static ImageView startGame = new ImageView("resources/images/ss.png");
    static Image info1 = new Image("/resources/images/info1.png");
    static Image info2 = new Image("/resources/images/info2.gif");
    static Image bomb1 = new Image("/resources/images/bomb1.jpg");
    static Image bomb2 = new Image("/resources/images/bomb2.gif");
    static ImageView bg = new ImageView("/resources/images/classroom.png");
    static ImageView ib = new ImageView("/resources/images/exam_instruction.png");
    static ImageView instruction = new ImageView("/resources/images/instruction_discrete.png");
    static BorderPane pane_start = new BorderPane();
    static BorderPane pane = new BorderPane();
    static BorderPane pane_instruction = new BorderPane();
    static StackPane stackPane = new StackPane();
    static BorderPane pane_ctrler = new BorderPane();
    static VBox buttons = new VBox(5);
    static Scene scene = new Scene(stackPane, 1280, 720);
    static int gameTime = 60;
    static Text timerText = new Text();
    static int[][][] positions = {
        {{0, 0}, {160, 0}, {320, 0}, {480, 0}, {640, 0}, {800, 0}, {960, 0}, {1120, 0}, {1280, 0},
         {0, 120}, {0, 240}, {0, 360}, {0, 480}, {0, 600}},
        {{0, 720}, {160, 720}, {320, 720}, {480, 720}, {640, 720}, {800, 720}, {960, 720}, {1120, 720}, {1280, 720},
         {1080, 120}, {1080, 240}, {1080, 360}, {1080, 480}, {1080, 600}}
    };
    static ArrayList<objs> infos_1 = new ArrayList<>();
    static ArrayList<objs> infos_2 = new ArrayList<>();
    static ArrayList<objs> waste_1 = new ArrayList<>();
    static ArrayList<objs> waste_2 = new ArrayList<>();

    public static Scene scene() {
        // Main.examDone++;
        Main.disNotDone = false;
        Main.music.swtichMusic(Main.music.main, Main.music.exam);
        pane_ctrler.setCenter(pane_start);
        pane_instruction.setCenter(instruction);
        buttons.getChildren().addAll(startGame, ib);
        buttons.setAlignment(Pos.CENTER);
        pane_start.setCenter(buttons);
        stackPane.getChildren().addAll(bg, pane_ctrler); 
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), bg);
        fadeOut.setFromValue(0.5);
        fadeOut.setToValue(0.5);
		fadeOut.play();
        
        // infos and wastes
        for(int i = 0; i < gameTime; i++) {
            objs tmp = new objs(info1, info2);
            objs tmp2 = new objs(info1, info2);
            objsSetting(tmp, 1);
            objsSetting(tmp2, 1);
            infos_1.add(tmp);
            infos_2.add(tmp2);
            if(i % 2 == 0) {
                objs tmp3 = new objs(bomb1, bomb2);
                objs tmp4 = new objs(bomb1, bomb2);
                objsSetting(tmp3, -10);
                objsSetting(tmp4, -10);
                waste_1.add(tmp3);
                waste_2.add(tmp4);
            }
        }
        System.err.println("infos_1: " + infos_1.size());
        System.err.println("infos_2: " + infos_2.size());
        System.err.println("waste_1: " + waste_1.size());
        System.err.println("waste_2: " + waste_2.size());
        Timeline generateInfo = new Timeline();
        Timeline generateWaste = new Timeline();
        final int[] idx_1 = {gameTime - 1};
        final int[] idx_2 = {gameTime - 1};
        final int[] idx_3 = {gameTime / 2 - 1};
        final int[] idx_4 = {gameTime / 2 - 1};
        generateInfo.getKeyFrames().add(new KeyFrame(Duration.seconds(1), gnr -> {
            if(end) generateInfo.stop();
            pane.getChildren().addAll(infos_1.get(idx_1[0]), infos_2.get(idx_2[0]));
            infos_1.get(idx_1[0]).setTranslateX(positions[0][infos_1.get(idx_1[0]).start][0] - infos_1.get(idx_1[0]).getFitWidth() / 2);
            infos_1.get(idx_1[0]).setTranslateY(positions[0][infos_1.get(idx_1[0]).start][1] - infos_1.get(idx_1[0]).getFitHeight() / 2);
            infos_2.get(idx_2[0]).setTranslateX(positions[1][infos_2.get(idx_2[0]).start][0] - infos_2.get(idx_2[0]).getFitWidth() / 2);
            infos_2.get(idx_2[0]).setTranslateY(positions[1][infos_2.get(idx_2[0]).start][1] - infos_2.get(idx_2[0]).getFitHeight() / 2);
            movement(infos_1.get(idx_1[0]), 1, rdn_2to4());
            movement(infos_2.get(idx_2[0]), 0, rdn_2to4());
            infos_1.remove(idx_1[0]);
            infos_2.remove(idx_2[0]);
            idx_1[0]--;
            idx_2[0]--;
        }));
        generateWaste.getKeyFrames().add(new KeyFrame(Duration.seconds(2), gnr -> {
            if(end) generateWaste.stop();
            pane.getChildren().addAll(waste_1.get(idx_3[0]), waste_2.get(idx_4[0]));
            waste_1.get(idx_3[0]).setTranslateX(positions[0][waste_1.get(idx_3[0]).start][0] - waste_1.get(idx_3[0]).getFitWidth() / 2);
            waste_1.get(idx_3[0]).setTranslateY(positions[0][waste_1.get(idx_3[0]).start][1] - waste_1.get(idx_3[0]).getFitHeight() / 2);
            waste_2.get(idx_4[0]).setTranslateX(positions[1][waste_2.get(idx_4[0]).start][0] - waste_2.get(idx_4[0]).getFitWidth() / 2);
            waste_2.get(idx_4[0]).setTranslateY(positions[1][waste_2.get(idx_4[0]).start][1] - waste_2.get(idx_4[0]).getFitHeight() / 2);
            movement(waste_1.get(idx_3[0]), 1, rdn_5to7());
            movement(waste_2.get(idx_4[0]), 0, rdn_5to7());
            waste_1.remove(idx_3[0]);
            waste_2.remove(idx_4[0]);
            idx_3[0]--;
            idx_4[0]--;
        
        }));
        generateInfo.setCycleCount(gameTime);
        // generateInfo.play();
        generateWaste.setCycleCount(gameTime / 2);
        // generateWaste.play();
        // score
        scoreT.setFont(new Font(75));
        pane.setTop(scoreT);
        BorderPane.setAlignment(scoreT, Pos.TOP_CENTER);
        // Timer
        Timeline timer = new Timeline();
        timerText.setText("Time: " + gameTime);
        timerText.setFont(new Font(100));
        timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
            // printHeapStats();
            gameTime--;
            if (gameTime > 0) {
                timerText.setText("Time: " + gameTime);
                if(gameTime <= 5) {
                    timerText.setFill(javafx.scene.paint.Color.RED);
                }
            }else if(gameTime == 0) {
                end = true;
                timerText.setText("時間到！");
                
            }else if(gameTime <= -3) {
                System.err.println("times up!");
                pane_ctrler.setCenter(conclude());
                Main.scores_discrete = score;
                b2b = true;
            }
        }));
        timer.setCycleCount(gameTime+3);
        // timer.play();
        pane.setBottom(timerText);
        BorderPane.setAlignment(timerText, Pos.TOP_CENTER);
        startGame.setOnMouseClicked(e -> {
            pane_ctrler.setCenter(pane);
            start = true;
            if(start) {
                generateInfo.play();
                generateWaste.play();
                timer.play();
            }
        });
        ib.setOnMouseClicked(e -> {
			System.err.println("Instruction");
			pane_ctrler.setCenter(pane_instruction);
			Timeline b2sT = new Timeline();
			b2sT.getKeyFrames().add(new KeyFrame(Duration.seconds(5), e2 -> {
			}));
			b2sT.play();
			b2sT.setOnFinished(e3 -> {
				b2s = true;
			});
		});
        
        scene.setOnMouseClicked(e -> {
            if(b2s) {
                System.err.println("Back to start");
				pane_ctrler.setCenter(pane_start);
				b2s = false;
			}
            if(b2b) {
                Main.switchScene(Loading.scene(Building_1.scene(1280, 360), 2));
                Main.music.swtichMusic(Main.music.exam, Main.music.main);
            }
        });
        return scene;
    }
    private static void objsSetting(objs obj, int s) {
        obj.start = rdn_14();
        obj.end = rdn_14();
        obj.setOnMouseClicked(e -> {
            obj.changeImage();
            if(!obj.isScored && !end) {
                score += s;
                scoreT.setText(Integer.toString(score));
                if(s == -10) {
                    Main.music.wrongTyping.stop();
                    Main.music.wrongTyping.play();
                // else Main.music.correctTyping.play();
                }
            }
            obj.isScored = true;
            Timeline vanish = new Timeline();
            vanish.getKeyFrames().add(new KeyFrame(Duration.seconds(1), v -> {
                pane.getChildren().remove(obj);

            }));
            vanish.setCycleCount(1);
            vanish.play();
        });
    }
    private static void movement(objs obj, int e, int s) {
        Timeline move = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(obj.translateXProperty(), obj.getTranslateX()), new KeyValue(obj.translateYProperty(), obj.getTranslateY())),
            new KeyFrame(Duration.seconds(s), new KeyValue(obj.translateXProperty(), positions[e][obj.end][0] - obj.getFitWidth() / 2), new KeyValue(obj.translateYProperty(), positions[e][obj.end][1]- obj.getFitHeight() / 2))
        );
        move.play();
        move.setOnFinished(eMove -> {
            pane.getChildren().remove(obj);
        });
    }
    private static int rdn_14() {
        return (int)(Math.random() * 14);
    }
    private static int rdn_2to4() {
        return (int)(Math.random() * 3) + 2;
    }
    private static int rdn_5to7() {
        return (int)(Math.random() * 3) + 5;
    }
    public static void printHeapStats() {
        Runtime runtime = Runtime.getRuntime();

        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        System.err.println("Max memory: " + maxMemory / (1024 * 1024) + "MB");
        System.err.println("Allocated memory: " + allocatedMemory / (1024 * 1024) + "MB");
        System.err.println("Free memory: " + freeMemory / (1024 * 1024) + "MB");
        System.err.println("Used memory: " + (allocatedMemory - freeMemory) / (1024 * 1024) + "MB");
    }
    public static BorderPane conclude() {
        BorderPane pane = new BorderPane();
		Text result = new Text();
		if(score >= 60) {
			result.setText("Pass!");
            if(score > 100) score = 100;
		} else {
			result.setText("Fail!");
            if(score < 0) score = 0;
		}
        Text scoreText = new Text("Your Score: "+String.valueOf(score));
		result.setFont(new Font(150));
		scoreText.setFont(new Font(150));
		BorderPane.setAlignment(result, Pos.CENTER);
		BorderPane.setAlignment(scoreText, Pos.CENTER);
		VBox show_result = new VBox(5);
		show_result.getChildren().addAll(result, scoreText);
		show_result.setAlignment(Pos.CENTER);
		pane.setCenter(show_result);
		// Scene scene = new Scene(pane, 1280, 720);
		return pane;
    }
    public void start(Stage stage) {
            // stage setting
            stage.setTitle("");
            // csLab
            stage.setScene(scene());
            stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}