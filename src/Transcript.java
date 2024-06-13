import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Transcript extends Application{
    static BorderPane pane_ctrler = new BorderPane();
    static Scene scene = new Scene(pane_ctrler, 1280, 720);

    static Text calculusT = new Text("Calculus : " + Main.scores_calculus);
    static Text discreteT = new Text("Discrete : " + Main.scores_discrete);
    static Text engineeringT = new Text("Engineering : " + Main.scores_engineering);
    static Text csT = new Text("CS : " + Main.scores_cs);
    static String mentorSRT = "要使用SR直屬的效果嗎？選擇一個科目加20分";
    static String mentorNT = "要使用SR直屬的效果嗎？選擇一個科目加10分";
    static String mentorNull = "沒直屬，只好靠自己";
    static Text mentor = new Text();
    static Image pp = new Image("resources/images/pp.jpg");
    static Image pp2 = new Image("resources/images/pp2.jpg");
    static Image eg = new Image("resources/images/endGame.jpg");
    static Image eg2 = new Image("resources/images/endGame2.jpg");
    static ImageView pp_cal = new ImageView(pp);
    static ImageView pp_dis = new ImageView(pp);
    static ImageView pp_eng = new ImageView(pp);
    static ImageView pp_cs = new ImageView(pp);
    static ImageView EG = new ImageView(eg);
    static VBox vbox = new VBox();
    static HBox hBox_cal = new HBox(50);
    static HBox hBox_dis = new HBox(50);
    static HBox hBox_eng = new HBox(50);
    static HBox hBox_cs = new HBox(50);
    

    public static Scene scene() {
        calculusT.setFont(new Font("Arial", 80));
        discreteT.setFont(new Font("Arial", 80));
        engineeringT.setFont(new Font("Arial", 80));
        csT.setFont(new Font("Arial", 80));
        mentor.setFont(new Font("Arial", 50));
        hBox_cal.getChildren().addAll(calculusT, pp_cal);
        hBox_dis.getChildren().addAll(discreteT, pp_dis);
        hBox_eng.getChildren().addAll(engineeringT, pp_eng);
        hBox_cs.getChildren().addAll(csT, pp_cs);
        hBox_cal.setAlignment(Pos.CENTER);
        hBox_dis.setAlignment(Pos.CENTER);
        hBox_eng.setAlignment(Pos.CENTER);
        hBox_cs.setAlignment(Pos.CENTER);
        if(Main.mentorPP == 20) mentor.setText(mentorSRT);
        else if(Main.mentorPP == 10) mentor.setText(mentorNT);
        else mentor.setText(mentorNull);
        vbox.getChildren().addAll(mentor, hBox_cal, hBox_dis, hBox_eng, hBox_cs, EG);
        vbox.setAlignment(Pos.CENTER);
        pane_ctrler.setCenter(vbox);
        pp_cal.setOnMouseEntered(on -> {
            pp_cal.setImage(pp2);
        });
        pp_cal.setOnMouseExited(on -> {
            pp_cal.setImage(pp);
        });
        pp_dis.setOnMouseEntered(on -> {
            pp_dis.setImage(pp2);
        });
        pp_dis.setOnMouseExited(on -> {
            pp_dis.setImage(pp);
        });
        pp_eng.setOnMouseEntered(on -> {
            pp_eng.setImage(pp2);
        });
        pp_eng.setOnMouseExited(on -> {
            pp_eng.setImage(pp);
        });
        pp_cs.setOnMouseEntered(on -> {
            pp_cs.setImage(pp2);
        });
        pp_cs.setOnMouseExited(on -> {
            pp_cs.setImage(pp);
        });
        pp_cal.setOnMouseClicked(pp -> {
            Main.scores_calculus += Main.mentorPP;
            calculusT.setText("Calculus : " + Main.scores_calculus);
            vbox.getChildren().remove(mentor);
            hBox_cal.getChildren().remove(pp_cal);
            hBox_dis.getChildren().remove(pp_dis);
            hBox_eng.getChildren().remove(pp_eng);
            hBox_cs.getChildren().remove(pp_cs);
        });
        pp_dis.setOnMouseClicked(pp -> {
            Main.scores_discrete += Main.mentorPP;
            discreteT.setText("Discrete : " + Main.scores_discrete);
            vbox.getChildren().remove(mentor);
            hBox_cal.getChildren().remove(pp_cal);
            hBox_dis.getChildren().remove(pp_dis);
            hBox_eng.getChildren().remove(pp_eng);
            hBox_cs.getChildren().remove(pp_cs);
        });
        pp_eng.setOnMouseClicked(pp -> {
            Main.scores_engineering += Main.mentorPP;
            engineeringT.setText("Engineering : " + Main.scores_engineering);
            vbox.getChildren().remove(mentor);
            hBox_cal.getChildren().remove(pp_cal);
            hBox_dis.getChildren().remove(pp_dis);
            hBox_eng.getChildren().remove(pp_eng);
            hBox_cs.getChildren().remove(pp_cs);
        });
        pp_cs.setOnMouseClicked(pp -> {
            Main.scores_cs += Main.mentorPP;
            csT.setText("CS : " + Main.scores_cs);
            vbox.getChildren().remove(mentor);
            hBox_cal.getChildren().remove(pp_cal);
            hBox_dis.getChildren().remove(pp_dis);
            hBox_eng.getChildren().remove(pp_eng);
            hBox_cs.getChildren().remove(pp_cs);
        });
        EG.setOnMouseEntered(on -> {
            EG.setImage(eg2);
        });
        EG.setOnMouseExited(on -> {
            EG.setImage(eg);
        });
        EG.setOnMouseClicked(on -> {
            Result_ncu result_ncu = new Result_ncu();
            Main.switchScene(result_ncu.scene());
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
