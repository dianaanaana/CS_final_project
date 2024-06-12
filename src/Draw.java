import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Draw extends Application{
	private static boolean isMousePressedEnabled = true;
	private static boolean isFinishLine = false;
	private static BorderPane pane_control = new BorderPane();
	Lines line = new Lines();
	
	public void Draw_talk(BorderPane pane, String LineLocate) throws IOException {
		line.LoadLine(LineLocate);
		
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
		
		pane.setBottom(dialog);
		pane.setStyle("-fx-background-color: black;");
		
		talk(pane, dialog, nameText, lineText);
	}
	
	public void talk(BorderPane pane, StackPane dialog, Text nameText, Text lineText) {
		
		final int[] line_locate = {-1};
		dialog.setOnMousePressed(e -> {
			if(line_locate[0]<line.get_lineArray().size()-1 && isMousePressedEnabled) {
//				System.out.println(line_locate[0]);
				isMousePressedEnabled = false;
				line_locate[0]+=1;
				if(line.get_lineArray().get(line_locate[0]).substring(0, 8).equals("--------")) {
					Main.music.swtichMusic(Main.music.main, Main.music.draw);
					isMousePressedEnabled = true;
					line_locate[0]+=1;
				}
				
				else if(line.get_lineArray().get(line_locate[0]).substring(0, 8).equals("    end1")) {
					isMousePressedEnabled = true;
					pane_control.setCenter(drawPane());
				}
				
				else if(line.get_lineArray().get(line_locate[0]).substring(0, 8).equals("    end ")) {
					// new Road();
					Main.switchScene(Loading.scene(Road.scene(400, 930), 2));
					dialog.setOnMousePressed(null);
				}
				
				else if(line_locate[0]>=0) {
					if(!line.get_lineArray().get(line_locate[0]).substring(0, 6).equals("option")) {
						Timeline timeline = new Timeline();
						String nameSpeaking = line.get_lineArray().get(line_locate[0]).substring(0, 4);
						nameText.setText(nameSpeaking);
						for(int i=4; i < line.get_lineArray().get(line_locate[0]).length(); i++) {
							final int[] index = {i};
							KeyFrame keyFrame = new KeyFrame(Duration.millis(40*i), event -> {
								lineText.setText(line.get_lineArray().get(line_locate[0]).substring(4, index[0]+1));
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
	}
	
	public BorderPane drawPane() {
		
		BorderPane pane = new BorderPane();
		HBox hBox = new HBox(15);
		
		Text chose_infoText = new Text();
		chose_infoText.setFont(new Font(100));
		chose_infoText.setText("選擇你的直屬");
		pane.setTop(chose_infoText);
		BorderPane.setAlignment(chose_infoText, Pos.TOP_CENTER);
		
		File imageFile_tim = new File("src/resources/images/senpei_tim.jpg");
		Image image_tim = new Image(imageFile_tim.toURI().toString());
		File imageFile_tim_1 = new File("src/resources/images/senpei_tim_1.jpg");
		Image image_tim_1 = new Image(imageFile_tim_1.toURI().toString());
		ImageView view_tim = new ImageView(image_tim);
		view_tim.setFitWidth(375);
		view_tim.setFitHeight(500);
		
		view_tim.setOnMousePressed(e -> {
			Main.music.swtichMusic(Main.music.draw, Main.music.main);
			hBox.getChildren().clear();
		try {
			Draw_talk(pane, "src/resources/scripts/tim.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		});
		
		view_tim.setOnMouseEntered(e -> {
			view_tim.setImage(image_tim_1);
			view_tim.setFitWidth(412.5);
			view_tim.setFitHeight(550);
		});
		view_tim.setOnMouseExited(e -> {
			view_tim.setImage(image_tim);
			view_tim.setFitWidth(375);
			view_tim.setFitHeight(500);
		});
		
		
		
		File imageFile_noah = new File("src/resources/images/senpei_noah.jpg");
		Image image_noah = new Image(imageFile_noah.toURI().toString());
		File imageFile_noah_1 = new File("src/resources/images/senpei_noah_1.jpg");
		Image image_noah_1 = new Image(imageFile_noah_1.toURI().toString());
		ImageView view_noah = new ImageView(image_noah);
		view_noah.setFitWidth(375);
		view_noah.setFitHeight(500);
		
		view_noah.setOnMousePressed(e -> {
			Main.music.swtichMusic(Main.music.draw, Main.music.main);
		hBox.getChildren().clear();
		try {
			Draw_talk(pane, "src/resources/scripts/noah.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		});
		
		view_noah.setOnMouseEntered(e -> {
			view_noah.setImage(image_noah_1);
			view_noah.setFitWidth(412.5);
			view_noah.setFitHeight(550);
		});
		view_noah.setOnMouseExited(e -> {
			view_noah.setImage(image_noah);
			view_noah.setFitWidth(375);
			view_noah.setFitHeight(500);
		});
		
		
		
		File imageFile_steven = new File("src/resources/images/senpei_steven.jpg");
		Image image_steven = new Image(imageFile_steven.toURI().toString());
		File imageFile_steven_1 = new File("src/resources/images/senpei_steven_1.jpg");
		Image image_steven_1 = new Image(imageFile_steven_1.toURI().toString());
		ImageView view_steven = new ImageView(image_steven);
		view_steven.setFitWidth(375);
		view_steven.setFitHeight(500);
		
		view_steven.setOnMousePressed(e -> {
			Main.music.swtichMusic(Main.music.draw, Main.music.main);
		hBox.getChildren().clear();
		try {
			Draw_talk(pane, "src/resources/scripts/steven.txt");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		});
		
		view_steven.setOnMouseEntered(e -> {
			view_steven.setImage(image_steven_1);
			view_steven.setFitWidth(412.5);
			view_steven.setFitHeight(550);
		});
		view_steven.setOnMouseExited(e -> {
			view_steven.setImage(image_steven);
			view_steven.setFitWidth(375);
			view_steven.setFitHeight(500);
		});
		
		
		hBox.getChildren().addAll(view_tim, view_noah, view_steven);
		pane.setCenter(hBox);
		hBox.setAlignment(Pos.CENTER);
		
		
		
		return pane;
	}
	public Scene scene() throws IOException{
		BorderPane pane_1 = new BorderPane();
		pane_control.setCenter(pane_1);
		// pane_control.setCenter(drawPane());
		line.switchBackground(pane_1, "black.jpg");
		Draw_talk(pane_1, "src/resources/scripts/introduce.txt");
		
		
		Scene scene = new Scene(pane_control, 1280, 720);
		return scene;
	}
	
	public void start(Stage primaryStage) throws IOException {
		
		
		
		primaryStage.setScene(scene());
		primaryStage.setTitle("DrawSystem");
		primaryStage.show();
		
	}
	
	public static void main(String args[]) {
		launch(args);
	}
}
