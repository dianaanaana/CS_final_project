import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
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
import javafx.stage.Stage;
import javafx.util.Duration;

public class computerScience extends Application {
    ArrayList<String> numbers = new ArrayList<>();
    private int num = 5;
    private Label number;
    private VBox al;
    private Button[] buttons;
    private Label choose;
    private String numWeChoose = "";
    private VBox vanswer = new VBox();
    private BorderPane content = new BorderPane();
    private String score = "Score";
    private Timeline timeline;
    private Set<Integer> generatedNumbers;
    private List<Integer> availableNumbers;
    private Random random;
    private Label resultLabel;

    @Override
    public void start(Stage primaryStage) throws Exception {
        File imagefile = new File("./resources_112502564/blackBoard.jpg");
        Image image = new Image(imagefile.toURI().toString());

        ImageView imageView = new ImageView(image);
        Button enter = new Button("Enter");

        resultLabel = new Label();
        resultLabel.setStyle("-fx-font-size: 24pt;");

        VBox newbox = new VBox(10);
        newbox.setAlignment(Pos.CENTER);
        newbox.getChildren().addAll(resultLabel, enter);

        enter.setOnAction(e -> startSpin());

        // Ensure the image covers the entire background
        lossColor(imageView);
        imageView.setFitWidth(500);
        imageView.setFitHeight(500);
        imageView.setPreserveRatio(false);

        number = new Label("You can choose: " + num);
        choose = new Label(" You choose: " + "\n" + numWeChoose);
        al = createButtons();
        numbers.clear();

        VBox topBox = new VBox(10);
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(number);

        BorderPane.setAlignment(topBox, Pos.CENTER);
        BorderPane.setAlignment(al, Pos.CENTER);
//        BorderPane.setAlignment(choose, Pos.CENTER);
        
        content.setTop(topBox);
        content.setCenter(newbox);
        content.setLeft(choose); // 将 choose 放在左边
        content.setBottom(al);

        StackPane root = new StackPane();
        root.getChildren().addAll(imageView, content);

        Scene scene = new Scene(root, 1080, 720);

        primaryStage.setTitle("predict");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Initialize the random number generator and available numbers
        random = new Random();
        initializeNumbers();
    }

    private void initializeNumbers() {
        availableNumbers = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            availableNumbers.add(i);
        }
        generatedNumbers = new HashSet<>();
    }

    private void startSpin() {
        if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
            return; // 防止多次点击按钮
        }

        if (generatedNumbers.size() >= 3) {
            resultLabel.setText("Teacher choose: " + generatedNumbers.toString());
            return; // 已经生成了三个不重复的数字
        }

        timeline = new Timeline();
        timeline.setCycleCount(20); // 设置动画循环次数

        KeyFrame keyFrame = new KeyFrame(Duration.millis(100), event -> spin());

        timeline.getKeyFrames().add(keyFrame);
        timeline.setOnFinished(event -> displayFinalResult());

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
            }
        }
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

            int index = i + 1;
            button.setOnAction(event -> handleButtonClick(index));
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

    private void handleButtonClick(int index) {
        if (num > 0 && buttons[index - 1].getStyle().isEmpty()) {
            numbers.add(Integer.toString(index));
            numbers.sort(null);
            numWeChoose = "";
            for (String value : numbers) {
                numWeChoose += (value + " ");
                System.out.println(value);
            }
            buttons[index - 1].setStyle("-fx-background-color: red;");
            num--;
        } else if (!buttons[index - 1].getStyle().isEmpty()) {
            numbers.remove(Integer.toString(index));
            numbers.sort(null);
            numWeChoose = "";
            for (String value : numbers) {
                numWeChoose += (value + " ");
                System.out.println(value);
            }
            buttons[index - 1].setStyle("");
            num++;
        }
        number.setText("You can choose: " + num);
        choose.setText(" You choose: " + "\n" + numWeChoose);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
