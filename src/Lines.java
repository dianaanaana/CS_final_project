import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Lines extends Application{
	private static ArrayList<String> lineArray = new ArrayList<String>();
	private boolean isMousePressedEnabled = true;
    Boolean ncuYes = false;
	
	public ArrayList<String> get_lineArray(){
		return lineArray;
	}

	public void LoadLine(String FileLocate) throws IOException{
		StoryLine storyLine = new StoryLine();
		storyLine.get_lineText(FileLocate);
		lineArray = storyLine.getLine();
	}
	
	public void switchBackground(BorderPane pane, String SceneName) {
		File imageFile = new File("src/resources/images/" + SceneName);
		Image image = new Image(imageFile.toURI().toString());
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,  // 不重复背景图片
                BackgroundRepeat.NO_REPEAT,  // 不重复背景图片
                BackgroundPosition.CENTER,   // 背景图片居中
                new BackgroundSize(
                    BackgroundSize.AUTO, BackgroundSize.AUTO, // 使用图片的原始尺寸
                    false, false,
                    true, true)  // 保持宽高比，且调整大小以覆盖整个 BorderPane
            );
        
        Background background = new Background(backgroundImage);
        pane.setBackground(background);
	}
	
	public void option(BorderPane pane) {
		Text option_Yes = new Text();
		option_Yes.setText("媽祖叫我讀種秧，這是天意");
		option_Yes.setStroke(Color.WHITE);
		option_Yes.setFont(new Font(20));
		
		Text option_No = new Text();
		option_No.setText("破大學，狗都不讀");
		option_No.setStroke(Color.WHITE);
		option_No.setFont(new Font(20));
		
		VBox vBox = new VBox();
		vBox.getChildren().addAll(option_Yes, option_No);
	
		pane.setCenter(vBox);
		vBox.setAlignment(Pos.CENTER_LEFT);
		
		
		option_Yes.setOnMouseEntered(e -> {
			option_Yes.setStroke(Color.YELLOW);
		});
		option_Yes.setOnMouseExited(e -> {
			option_Yes.setStroke(Color.WHITE);
		});
		option_Yes.setOnMousePressed(e -> {
            ncuYes = true;
			option_Yes.setVisible(false);
			option_No.setVisible(false);
			lineArray.clear();
			try {
				LoadLine("src/resources/scripts/NCUstory.txt");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		
		
		option_No.setOnMouseEntered(e -> {
			option_No.setStroke(Color.YELLOW);
		});
		option_No.setOnMouseExited(e -> {
			option_No.setStroke(Color.WHITE);
		});
		option_No.setOnMousePressed(e -> {
			option_Yes.setVisible(false);
			option_No.setVisible(false);
			switchBackground(pane, "NTU.jpg");
			lineArray.clear();
			try {
				LoadLine("src/resources/scripts/NTUend.txt");
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		
		
	}
	     
    

    public Scene scene() throws IOException{
        File imageFile = new File("src/resources/images/box_blue_name.png");
		Image image = new Image(imageFile.toURI().toString());
		ImageView view = new ImageView(image);
		view.setFitWidth(1280);
		view.setFitHeight(250);
		
		Text nameText = new Text();
		nameText.setFont(new Font(20));
		nameText.setStroke(Color.WHITE);
		
		Text lineText = new Text();
		lineText.setFont(new Font(20));
		lineText.setStroke(Color.WHITE);
		
		StackPane dialog = new StackPane();
		dialog.getChildren().addAll(view, nameText, lineText);
		StackPane.setAlignment(nameText, Pos.TOP_LEFT);
		StackPane.setAlignment(lineText, Pos.CENTER_LEFT);
		StackPane.setMargin(lineText, new Insets(20));
		StackPane.setMargin(nameText, new Insets(20));
		
		BorderPane pane = new BorderPane();
		pane.setBottom(dialog);
		pane.setStyle("-fx-background-color: black;");
		
		

		Scene scene = new Scene(pane, 1280, 720);
        LoadLine("src/resources/scripts/LineOpen.txt");
		final int[] line_locate = {-1};
		dialog.setOnMousePressed(e -> {
			if(line_locate[0]<lineArray.size()-1 && isMousePressedEnabled) {
//				System.out.println(lineArray.get(line_locate[0]).length());
				isMousePressedEnabled = false;
				line_locate[0]+=1;
				if(lineArray.get(line_locate[0]).substring(0, 5).equals("-----")) {
					switchBackground(pane, lineArray.get(line_locate[0]).substring(5, lineArray.get(line_locate[0]).length()));
					isMousePressedEnabled = true;
					line_locate[0]+=1;
					if(lineArray.get(line_locate[0]).substring(0, 6).equals("option")) {
						switchBackground(pane, "black.jpg");
						option(pane);
						line_locate[0] = -1;
					}
				}
				
				else if(lineArray.get(line_locate[0]).substring(0, 7).equals("    end")) {
					dialog.setOnMousePressed(null);
                    if(ncuYes) Main.switchScene(Loading.scene(Dorm.scene(640, 360), 2));
				}
				
				else if(line_locate[0]>=0) {
					if(!lineArray.get(line_locate[0]).substring(0, 6).equals("option")) {
						Timeline timeline = new Timeline();
						String nameSpeaking = lineArray.get(line_locate[0]).substring(0, 4);
						nameText.setText(nameSpeaking);
						for(int i=4; i < lineArray.get(line_locate[0]).length(); i++) {
							final int[] index = {i};
							KeyFrame keyFrame = new KeyFrame(Duration.millis(40*i), event -> {
								lineText.setText(lineArray.get(line_locate[0]).substring(4, index[0]+1));
							});
							timeline.getKeyFrames().add(keyFrame);
						}
						timeline.play();
						
						timeline.setOnFinished(event2 -> {
							isMousePressedEnabled = true;
						});
					}
				}
			}
		});
        return scene;
    }
	
	public void start(Stage primaryStage) throws IOException{
		
		primaryStage.setScene(scene());
		primaryStage.setTitle("ncuRPG");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

