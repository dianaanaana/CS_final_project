import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Credit extends Application {


    public Scene scene() {
        // 創建一個 Pane 作為容器
        StackPane pane = new StackPane();
        pane.setStyle("-fx-background-color: black;");

        // 創建影片人員名單文本
        String credits = "A long time ago in a galaxy far,\nfar away...\n\n"
                + "Episode IV\n\n"
                + "A NEW HOPE\n\n"
                + "It is a period of civil war.\n"
                + "Rebel spaceships, striking\n"
                + "from a hidden base, have won\n"
                + "their first victory against\n"
                + "the evil Galactic Empire...\n\n"
                + "Produced by\n"
                + "Tim, Noah, Steven\n\n"
                + "Directed by\n"
                + "Tim\n\n"
                + "Programing by\n"
                + "Tim, Noah, Steven\n\n"
                + "Game Design by\n" 
        		+ "Tim, Noah, Steven\n\n"
    			+ "Art Design by\n" 
    			+ "Noah\n\n"
    			+ "Music Design by\n" 
    			+ "Steven\n\n"
    			+ "Transition Effect by\n" 
    			+ "Noah\n\n"
    			+ "Special Thanks to\n" 
    			+ "All the TAs\n\n";
    			
    			
    			
    			
        Text text = new Text(credits);
        text.setFill(Color.YELLOW); // 設置文本顏色
        text.setFont(Font.font("Verdana", 20)); // 設置字體和大小
        text.setLayoutX(50); // 設置文本的 X 坐標
        
        // 計算文本初始的 Y 坐標，使其位於屏幕下方
        double startY = 400;
        text.setLayoutY(startY);
        
        // 添加文本到 Pane
        pane.getChildren().add(text);
        
        // 創建一個平移動畫
        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(16000)); // 動畫持續時間
        translateTransition.setNode(text); // 動畫作用的對象
        translateTransition.setFromY(startY); // 起始 Y 坐標
        translateTransition.setToY(-1 * (text.getLayoutBounds().getHeight() + 80)); // 終止 Y 坐標，離開屏幕頂部
        translateTransition.setCycleCount(1); // 不循環
        
        // 開始動畫
        translateTransition.play();
        Scene scene = new Scene(pane, 1280, 720, Color.BLACK); // 設置場景背景為黑色

        translateTransition.setOnFinished(EVENT -> {
            // // 創建返回按鈕
            // scene.setFill(Color.BLACK);
        
            // Button backButton = new Button("返回");
            // backButton.setLayoutX(250); // 設置按鈕的 X 坐標
            // backButton.setLayoutY(500); // 設置按鈕的 Y 坐標，將按鈕上移以避免被覆蓋
            // backButton.setVisible(true); // 顯示按鈕
            
            // // 添加按鈕到 Pane
            // pane.getChildren().add(backButton);
            
            // // 重新設置場景背景色為黑色
            Main.stage.close();
        });
        return scene;
    }

    @Override
    public void start(Stage primaryStage) {
        
        
        // 創建場景並顯示
        primaryStage.setScene(scene());
        primaryStage.setTitle("Star Wars Credits Animation");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
