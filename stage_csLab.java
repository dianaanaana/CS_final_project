import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class stage_csLab extends Application{
    //basic info
    static int score = 0;
    static Text scoreT = new Text(Integer.toString(score));
    static String theAns;
    // obj of question
    static Label start = new Label("計算機實習上機考");
    static Text[] questions = new Text[10];
    // obj of answer
    static Rectangle red = new Rectangle(640, 180, Color.RED);
    static Rectangle blue = new Rectangle(640, 180, Color.BLUE);
    static Rectangle yellow = new Rectangle(640, 180, Color.YELLOW);
    static Rectangle green = new Rectangle(640, 180, Color.GREEN);
    static HBox hBox_rb = new HBox();
    static HBox hBox_yg = new HBox();
    static VBox vBox_rbyg = new VBox();
    static Group gAnsering = new Group();
    // Panes
    static BorderPane pane_main = new BorderPane();
    static BorderPane pane_start = new BorderPane();
    static BorderPane pane_ans = new BorderPane();
    // scene setting
    static Scene scene = new Scene(pane_main, 1080, 720);

    public static Scene scene() throws IOException{
        // question setting
        pane_start.setCenter(start);
        for(int i = 1; i <= 10; i++) {
            String tmp = readFile(Integer.toString(i));
            questions[i-1] = new Text(tmp);
        }
        // answer pane setting
        hBox_rb.getChildren().addAll(red, blue);
        hBox_yg.getChildren().addAll(yellow, green);
        vBox_rbyg.getChildren().addAll(hBox_rb, hBox_yg);
        pane_ans.setBottom(vBox_rbyg);
        pane_ans.setTop(scoreT);
        // 
        pane_main.setCenter(pane_start);
        pane_start.setOnMouseClicked(e -> switchPanes(pane_ans));
        // click answer
        EventHandler<MouseEvent> red_click = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if(theAns.equals("red")) {
                    score += 10;
                    scoreT.setText(Integer.toString(score));
                }
            }
        };
        EventHandler<MouseEvent> blue_click = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if(theAns.equals("blue")) {
                    score += 10;
                    scoreT.setText(Integer.toString(score));
                }
            }
        };
        EventHandler<MouseEvent> yellow_click = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if(theAns.equals("yellow")) {
                    score += 10;
                    scoreT.setText(Integer.toString(score));
                }
            }
        };
        EventHandler<MouseEvent> green_click = new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if(theAns.equals("green")) {
                    score += 10;
                    scoreT.setText(Integer.toString(score));
                }
            }
        };
        red.setOnMouseClicked(red_click);
        blue.setOnMouseClicked(blue_click);
        yellow.setOnMouseClicked(yellow_click);
        green.setOnMouseClicked(green_click);
        for(int i = 1; i <= 10; i++) {
            switchPanes_transition(i);

        }
        theAns = "red";
        return scene;
    }
    private static void switchPanes(Pane pane) {
        pane_main.setCenter(pane);
    }
    private static void switchPanes_transition(int count) {

    }
    private static String readFile(String p) throws IOException{
        String path = "src/csLab_questions/" + p + ".txt";
        File file = new File(path);
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String str;
        String res = "";
        try {
            // Read each line of the file
            while((str = bufferedReader.readLine()) != null) {
                res  += str;
            }
        }
        catch (Exception e) {
            e.getMessage();
        }
        bufferedReader.close();
        fileReader.close();
        return res;
    }
    public void start(Stage stage) throws IOException{
        // stage setting
        stage.setTitle("stage_csLab_test");
        stage.setScene(scene());
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
