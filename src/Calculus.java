import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

class Obj extends Pane{
	private Group group = new Group();
	private Rectangle target = new Rectangle(300, 10);
	
	public Obj() {
		getChildren().addAll(group, target);
		switch_target();
		printLine();
	}
	
	
	public void printLine() {
		Line line = new Line(0, 50, getWidth(), 50);
		line.setStroke(Color.RED);
		group.getChildren().add(line);
		line.toBack();
	}
	
	public void switch_target() {
		double pos_Y = 45;
		double tmp = (Math.random()*9)*50+50;
		target.setWidth((int) tmp);
		double pos_X = (double)(Math.random()*1280 - tmp);  //getWidth又變得怪怪的
		target.setX(pos_X);
		target.setY(pos_Y);
		target.setFill(Color.GREEN);
		target.setFill(Color.GREEN);
		target.toFront();
	}
	
	public Rectangle get_target() {
		return target;
	}

	@Override
	public void setHeight(double height) {
		super.setHeight(height);
		printLine();
	}
	
	public void setWidth(double width) {
		super.setWidth(width);
		printLine();
	}
}

public class Calculus extends Application{
	// static Stage primaryStage;
	static int score = 100;
	static int round = 0;
	static BorderPane pane = new BorderPane();
	static BorderPane pane_ctrler = new BorderPane();
	static Scene scene = new Scene(pane_ctrler, 1280, 720);
	static Boolean b2b = false;

	public static Scene scene() {
		pane_ctrler.setCenter(pane);
		Obj obj = new Obj();
		pane.setCenter(obj);
		Rectangle rectangle = new Rectangle(5,70);
		pane.getChildren().add(rectangle);
		final int[] speed= {1};
		Timeline timeline = new Timeline();
		KeyFrame keyFrame = new KeyFrame(Duration.millis(1), e ->{
			rectangle.setX(rectangle.getX()+speed[0]);
			if(rectangle.getX() > pane.getWidth() || rectangle.getX() < 0) {
				speed[0]*=-1;
			}
		});
		timeline.getKeyFrames().add(keyFrame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
		Label roundLabel = new Label();
		roundLabel.setText("Round: " + String.valueOf(round));
		BorderPane.setAlignment(roundLabel, Pos.BOTTOM_CENTER);
		roundLabel.setFont(new Font(150));
		Label win_not = new Label();
		Text score_infoText = new Text("Score :");
		score_infoText.setFont(new Font(150));
		win_not.setFont(new Font(150));
		HBox bottomBox = new HBox(5) ;
		win_not.setText(String.valueOf(score));
		bottomBox.getChildren().addAll(score_infoText, win_not);
		win_not.setVisible(true);
//		pane.setBottom(bottomBox);
		BorderPane.setAlignment(score_infoText, Pos.BOTTOM_CENTER);
		BorderPane.setAlignment(win_not, Pos.CENTER);
		bottomBox.setAlignment(Pos.BOTTOM_CENTER);
		VBox vBox = new VBox();
		vBox.getChildren().addAll(bottomBox, roundLabel);
		pane.setBottom(vBox);
		vBox.setAlignment(Pos.BOTTOM_CENTER);
		scene.setOnKeyPressed(e ->{
			if(e.getCode() == KeyCode.SPACE) {
				timeline.play();
				boolean achieve = rectangle.getBoundsInLocal().intersects(obj.get_target().getBoundsInLocal());
				if(achieve) {
					obj.switch_target();
					round+=1;
					roundLabel.setText("Round: " + String.valueOf(round));

				} else {
					score-=10;
					win_not.setText(String.valueOf(score));
				}
				if(round == 10 && score >= 60) {
					scene.setOnKeyPressed(null);
					timeline.stop();
					// Scene conclude = Conclude(score, true);
					// primaryStage.setScene(conclude);
					// Main.switchScene(conclude);
					pane_ctrler.setCenter(Conclude(score, true));
					b2b = true;
				} else if(round == 10 && score < 60) {
					scene.setOnKeyPressed(null);
					timeline.stop();
					// Scene conclude = Conclude(score, false);
					// primaryStage.setScene(conclude);
					// Main.switchScene(conclude);
					pane_ctrler.setCenter(Conclude(score, false));
					b2b = true;
				} else if(score == 0) {
					scene.setOnKeyPressed(null);
					timeline.stop();
					// Scene conclude = Conclude(score, false);
					// primaryStage.setScene(conclude);
					// Main.switchScene(conclude);
					pane_ctrler.setCenter(Conclude(score, false));
					b2b = true;
				}
			}
			// switch(e.getCode()) {
			// case SPACE: 
			// 	timeline.play();
			// 	boolean achieve = rectangle.getBoundsInLocal().intersects(obj.get_target().getBoundsInLocal());
			// 	if(achieve) {
			// 		obj.switch_target();
			// 		round+=1;
			// 		roundLabel.setText("Round: " + String.valueOf(round));

			// 	} else {
			// 		score-=10;
			// 		win_not.setText(String.valueOf(score));
			// 	}
			// 	if(round == 10 && score >= 60) {
			// 		scene.setOnKeyPressed(null);
			// 		timeline.stop();
			// 		// Scene conclude = Conclude(score, true);
			// 		// primaryStage.setScene(conclude);
			// 		// Main.switchScene(conclude);
			// 		pane_ctrler.setCenter(Conclude(score, true));
			// 		b2b = true;
			// 	} else if(round == 10 && score < 60) {
			// 		scene.setOnKeyPressed(null);
			// 		timeline.stop();
			// 		// Scene conclude = Conclude(score, false);
			// 		// primaryStage.setScene(conclude);
			// 		// Main.switchScene(conclude);
			// 		pane_ctrler.setCenter(Conclude(score, false));
			// 		b2b = true;
			// 	} else if(score == 0) {
			// 		scene.setOnKeyPressed(null);
			// 		timeline.stop();
			// 		// Scene conclude = Conclude(score, false);
			// 		// primaryStage.setScene(conclude);
			// 		// Main.switchScene(conclude);
			// 		pane_ctrler.setCenter(Conclude(score, false));
			// 		b2b = true;
			// 	}
			// 	break;
			// }
		});
		scene.setOnMouseClicked(e -> {
			if(b2b) Main.switchScene(Building_1.scene(0, 360));
		});
		return scene;
	}
	
	public void start(Stage primaryStage) {
		// this.primaryStage = primaryStage;
		primaryStage.setTitle("Calculus");
		primaryStage.setScene(scene());
		primaryStage.show();
	}
	
	
	static public BorderPane Conclude(int score, boolean pass) {
		BorderPane pane = new BorderPane();
		Text result = new Text();
		Text scoreText = new Text("Your Score: "+String.valueOf(score));
		if(pass) {
			result.setText("Pass!");
		} else {
			result.setText("Fail!");
		}
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
	
	public static void main(String[] args) {
		launch(args);
	}
}
