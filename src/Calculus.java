import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
	static ImageView bg = new ImageView(new Image("resources/images/classroom.png"));
	static ImageView startGame = new ImageView(new Image("resources/images/ss.png"));
	static ImageView ib = new ImageView(new Image("resources/images/exam_instruction.png"));
	static ImageView instruction = new ImageView(new Image("resources/images/instruction_calculus.png"));
	static BorderPane pane = new BorderPane();
	static BorderPane pane_start = new BorderPane();
	static BorderPane pane_instruction = new BorderPane();
	static VBox buttons = new VBox(5);
	static StackPane stackPane = new StackPane();
	static BorderPane pane_ctrler = new BorderPane();
	static Scene scene = new Scene(stackPane, 1280, 720);
	static Boolean b2b = false;
	static Boolean b2s = false;

	public static Scene scene() {
		// Main.examDone++;
		Main.calNotDone = false;
		Main.music.swtichMusic(Main.music.main, Main.music.exam);
		buttons.getChildren().addAll(startGame, ib);
		pane_start.setCenter(buttons);
		buttons.setAlignment(Pos.CENTER);
		pane_instruction.setCenter(instruction);
		// stackPane.getChildren().addAll(bg, pane);
		stackPane.getChildren().addAll(bg, pane_ctrler);
		// pane_ctrler.setCenter(stackPane);
		pane_ctrler.setCenter(pane_start);
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), bg);
        fadeOut.setFromValue(0.5);
        fadeOut.setToValue(0.5);
		fadeOut.play();
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
		roundLabel.setTextFill(Color.BLACK);
		BorderPane.setAlignment(roundLabel, Pos.BOTTOM_CENTER);
		roundLabel.setFont(new Font(150));
		Label win_not = new Label();
		win_not.setTextFill(Color.BLACK);
		Text score_infoText = new Text("Score :");
		score_infoText.setFill(Color.BLACK);
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
					Main.music.wrongTyping.stop();
					Main.music.wrongTyping.play();
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
					Main.scores_calculus = score;
					b2b = true;
				} else if(round == 10 && score < 60) {
					scene.setOnKeyPressed(null);
					timeline.stop();
					// Scene conclude = Conclude(score, false);
					// primaryStage.setScene(conclude);
					// Main.switchScene(conclude);
					pane_ctrler.setCenter(Conclude(score, false));
					Main.scores_calculus = score;
					b2b = true;
				} else if(score == 0) {
					scene.setOnKeyPressed(null);
					timeline.stop();
					// Scene conclude = Conclude(score, false);
					// primaryStage.setScene(conclude);
					// Main.switchScene(conclude);
					pane_ctrler.setCenter(Conclude(score, false));
					Main.scores_calculus = score;
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
		startGame.setOnMouseClicked(e -> {
			System.err.println("Start Game");
            pane_ctrler.setCenter(pane);
        });
		ib.setOnMouseClicked(e -> {
			System.err.println("Instruction");
			pane_ctrler.setCenter(pane_instruction);
			Timeline timer = new Timeline();
			timer.getKeyFrames().add(new KeyFrame(Duration.seconds(5), e2 -> {
			}));
			timer.play();
			timer.setOnFinished(e3 -> {
				b2s = true;
			});
		});

		scene.setOnMouseClicked(e -> {
			if(b2s) {
				pane_ctrler.setCenter(pane_start);
				b2s = false;
			}
			if(b2b) {
				Main.switchScene(Loading.scene(Building_1.scene(0, 360), 2));
				Main.music.swtichMusic(Main.music.exam, Main.music.main);
			}
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
