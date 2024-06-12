import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

// import game.game_scene.GameSettings;
// import game.game_scene.Music;
// import game.game_scene.SoundPlayer;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ComputerScience extends Application {

    Boolean b2b = false;

    ArrayList<String> numbers = new ArrayList<>();
    private int num = 5;
    private Label number;
    private VBox al;
    private Button[] buttons;
    private Label choose;
    private String numWeChoose = "";
    private VBox vanswer = new VBox();
    private BorderPane content = new BorderPane();
    private String score = "";
    private Timeline timeline;
    private Set<Integer> generatedNumbers;
    private List<Integer> availableNumbers;
    private Random random;
    private Label resultLabel;
    private Label scoreLabel;
    private int scores=100;
    // private SoundPlayer soundPlayer;
    // private Music gamebegin;    
    // private Music pass;
    // private Music fail;   
    ImageView bg = new ImageView("/resources/images/classroom.png");
    ImageView startGame = new ImageView("/resources/images/ss.png");
    ImageView ib = new ImageView("/resources/images/exam_instruction.png");
    ImageView instruction = new ImageView("/resources/images/instruction_cs.png");
    VBox buttons_s = new VBox();
    BorderPane pane_ctrler = new BorderPane();
    BorderPane pane_start = new BorderPane();
    BorderPane pane_instruction = new BorderPane();
    StackPane pane_bg = new StackPane();
    Boolean b2s = false;

    
    private MediaPlayer pedro;
    private MediaPlayer pedro2;

    private MediaPlayer pedroPass;
    private MediaPlayer pedroFa;
    private Button enter = new Button();

    public Scene scene() {
        Main.music.swtichMusic(Main.music.main, Main.music.exam);
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), bg);
        fadeOut.setFromValue(0.5);
        fadeOut.setToValue(0.5);
		fadeOut.play();

        pane_instruction.setCenter(instruction);
        pane_bg.getChildren().addAll(bg, pane_ctrler);
        pane_ctrler.setCenter(pane_start);
        pane_start.setCenter(buttons_s);
        buttons_s.getChildren().addAll(startGame, ib);
        buttons_s.setAlignment(Pos.CENTER);
        StackPane root = new StackPane();
        Scene scene = new Scene(pane_bg, 1280, 720);
        try {
    		//background
    		File imagefile = new File("./game/blackBoard.jpg");
            Image image = new Image(imagefile.toURI().toString());
            ImageView imageView = new ImageView(image);
            
            //start
            File beginfile = new File("src/resources/images/csStart.jpg");
            Image begin_pic = new Image(beginfile.toURI().toString());
            ImageView beginView = new ImageView(begin_pic);
            beginView.setFitWidth(100);
            beginView.setFitHeight(40);
            enter.setGraphic(beginView);  // 開始按鈕圖片
            enter.setStyle(
                    "-fx-background-color: transparent; " +
                    "-fx-border-color: transparent; " +
                    "-fx-padding: 0;");

            enter.setPrefSize(100, 40);

//            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
//            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
//            Background background = new Background(backgroundImage);   
            
            
            
            resultLabel = new Label();
            resultLabel.setStyle("-fx-font-size: 24pt;");
            scoreLabel = new Label("Score: " + score);
            scoreLabel.setStyle("-fx-font-size: 18pt;");

            VBox newbox = new VBox(10);
            
            newbox.getChildren().addAll(resultLabel, enter);
            newbox.getChildren().addAll(scoreLabel);
            newbox.setAlignment(Pos.CENTER);

            //enter.setOnAction(e -> startSpin());
           

            String music = new File("src/resources/musics/spinNumber.mp3").toURI().toString();
            String passStr = new File("src/resources/musics/pass.mp3").toURI().toString();
            String failStr = new File("src/resources/musics/fail.mp3").toURI().toString();
            String beginmusic = new File("src/resources/musics/begin.mp3").toURI().toString();
            
            Media musicMedia = new Media(music);
            Media passMe = new Media(passStr);
            Media failMe = new Media(failStr);
            Media beginMe = new Media(beginmusic);
            pedro2 = new MediaPlayer(musicMedia);
            pedro = new MediaPlayer(beginMe);
            pedroPass = new MediaPlayer(passMe);
            pedroFa = new MediaPlayer(failMe);

            MediaView mediaview2 = new MediaView(pedro2);
            MediaView mediaview = new MediaView(pedro);
            MediaView mediaviewpa = new MediaView(pedroPass);
            MediaView mediaviewfa = new MediaView (pedroFa);
            
            enter.setOnAction(e->{
                startSpin();
                pedro2.seek(Duration.ZERO);
                pedro2.play();
            });
            
            // Ensure the image covers the entire background
            lossColor(imageView);
            imageView.setFitWidth(500);
            imageView.setFitHeight(500);
            imageView.setPreserveRatio(false);

            number = new Label("You can choose: " + num);
            choose = new Label(" You choose: " + "\n" + numWeChoose);
            al = createButtons();
            numbers.clear();
            number.setFont(new Font(25));  //tim

            VBox topBox = new VBox(10);
            topBox.setAlignment(Pos.CENTER);
            topBox.getChildren().addAll(number);

            BorderPane.setAlignment(topBox, Pos.CENTER);
            BorderPane.setAlignment(al, Pos.CENTER);
//            BorderPane.setAlignment(choose, Pos.CENTER);
            
            choose.setFont(new Font(25));  //tim
            content.setTop(topBox);
            content.setCenter(newbox);
            content.setLeft(choose); // 将 choose 放在左边
            content.setBottom(al);

            
            root.getChildren().addAll(imageView, content);

//            root.setBackground(background);
            
            // Initialize the random number generator and available numbers
            random = new Random();
            initializeNumbers();
            startGame.setOnMouseClicked(e -> {
                pane_ctrler.setCenter(root);
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
                    // primaryStage.close();
                    Main.switchScene(Loading.scene(Building_2.scene(1280, 360), 2));
                    Main.music.swtichMusic(Main.music.exam, Main.music.main);
                }
            });
    	}
    	catch(Exception e) {
    		System.out.println(e.getMessage());
    	}

        


        return scene;
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
    	

            primaryStage.setTitle("predict");
            primaryStage.setScene(scene());
            primaryStage.show();

            
        
    }

    private void initializeNumbers() {
        availableNumbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            availableNumbers.add(i);
        }
        generatedNumbers = new HashSet<>();
    }

    private void startSpin() {
    	al.setVisible(false);
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
            return; // 防止多次点击按钮
        }

        if (generatedNumbers.size() >= 3) {
            resultLabel.setText("Teacher choose: " + generatedNumbers.toString());
            return; // 已经生成了三个不重复的数字
        }

        timeline = new Timeline();
        timeline.setCycleCount(60); // spin times

        KeyFrame keyFrame = new KeyFrame(Duration.millis(130), event -> spin());

        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event -> {
            displayFinalResult();
            b2b = true;
        });
        timeline.play();
    }

    private void spin() {
        Collections.shuffle(availableNumbers);
        int randomNumber = availableNumbers.get(random.nextInt(availableNumbers.size()));
        resultLabel.setText(String.valueOf(randomNumber));
    }

    private void displayFinalResult() {
        if (availableNumbers.size() > 0) {
            int randomNumber = availableNumbers.remove(random.nextInt(availableNumbers.size()));
            generatedNumbers.add(randomNumber);

            StringBuilder result = new StringBuilder();
            for (int number : generatedNumbers) {
                result.append(number).append(" ");
            }
            resultLabel.setText(result.toString().trim());

            if (generatedNumbers.size() >= 3) {
                resultLabel.setText("Teacher choose: " + result.toString().trim());
                
                checkResult();
                enter.setVisible(false);

            }
        }
    }

    private void checkResult() {
        int matches = 0;
        
//        System.out.println("\n");
        
        for (String chosen : numbers) {
            System.out.print(chosen);

            int chosenNumber = Integer.parseInt(chosen);
           
            if (generatedNumbers.contains(chosenNumber)) {
                matches++;
            }
        }
        
//        for (int num:generatedNumbers) {
//        	System.out.print(num);
//        }

        scores -= matches * 30;
        scoreLabel.setText("Score: " + Integer.toString(scores));
        if(scores>=60) {
        	pedroPass.play();
        }else {
        	pedroFa.play();
        }
        System.out.println(scores);
        generatedNumbers.clear(); // 清空生成的數字
        initializeNumbers(); // 重新初始化可用數字
    }

    
    private void lossColor(ImageView image) {
        FadeTransition fadeInImage = new FadeTransition(Duration.seconds(1), image);
        fadeInImage.setFromValue(0.0);
        fadeInImage.setToValue(0.4);
        fadeInImage.play();
    }

    private VBox createButtons() {
        VBox vbox = new VBox();
        vbox.setSpacing(10);

        buttons = new Button[10];
        for (int i = 0; i < buttons.length; i++) {
            Button button = new Button(Integer.toString(i + 1));
            button.setPrefSize(50, 50);  //tim

            int index = i + 1;
            button.setOnAction(event -> handleButtonClick(index));
            button.setOnMouseEntered(e -> {
                button.setPrefSize(75, 75);
            });
            button.setOnMouseExited(e -> {
                button.setPrefSize(50, 50);
            });
            buttons[i] = button;
        }

        HBox h1 = new HBox();
        HBox h2 = new HBox();
        h1.setSpacing(5);
        h2.setSpacing(5);
        for (int i = 0; i < 5; i++) {
            h1.getChildren().add(buttons[i]);
            h2.getChildren().add(buttons[i + 5]);
        }
        h1.setAlignment(Pos.BOTTOM_CENTER);
        h2.setAlignment(Pos.BOTTOM_CENTER);
        vbox.getChildren().addAll(h1, h2);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }

//    

    private void handleButtonClick(int index) {
        if (num > 0 && buttons[index - 1].getStyle().isEmpty()) {
            numbers.add(Integer.toString(index));
            sortNumbers();
            updateChosenNumbers();
            buttons[index - 1].setStyle("-fx-background-color: red;");
            num--;
        } else if (!buttons[index - 1].getStyle().isEmpty()) {
            numbers.remove(Integer.toString(index));
            sortNumbers();
            updateChosenNumbers();
            buttons[index - 1].setStyle("");
            num++;
        }
        number.setText("You can choose: " + num);
        choose.setText("You choose: " + "\n" + numWeChoose);
    }

    private void sortNumbers() {
        // sort
        numbers.sort(Comparator.comparingInt(Integer::parseInt));
    }

    private void updateChosenNumbers() {
        numWeChoose = "";
        for (String value : numbers) {
            numWeChoose += (value + " ");
            System.out.println(value);
        }
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
