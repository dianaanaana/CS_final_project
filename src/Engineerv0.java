import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

class Question{
	private ArrayList<String> topic_list = new ArrayList<String>();
	String topic_combine="";
	
	public void ReadTopic() throws IOException{
		String str;
		FileReader fileReader = new FileReader("src/resources/docs/em.txt");
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		while((str = bufferedReader.readLine()) != null) {
			topic_list.add(str);
		}
		
		bufferedReader.close();
		fileReader.close();
	}
	
	
	public String topic_com() {
		for(int i=0; i<topic_list.size(); i++) {
			topic_combine+=topic_list.get(i)+"|";
		}
		return topic_combine;
	}
	
	public ArrayList<String> gettopicList(){
		return topic_list;
	}
}


public class Engineerv0 extends Application{
	private int score=0;
	private int timeSeconds = 30;
	private Text timerLabel = new Text();

	Boolean start = false;
	Boolean b2b = false;
	Boolean b2s = false;
	
	public BorderPane conclude() {
		BorderPane pane = new BorderPane();
		Text scoreText = new Text();
		scoreText.setText("Your score: "+String.valueOf(score));
		scoreText.setFont(new Font(150));
		pane.setCenter(scoreText);
		BorderPane.setAlignment(scoreText, Pos.CENTER);
		
		// Scene scene = new Scene(pane, 1280, 720);
		// return scene;
		return pane;
	}
	
	public Scene scene() throws IOException{
		Main.engNotDone = false;
		Main.music.swtichMusic(Main.music.main, Main.music.exam);
		Question question = new Question();
		question.ReadTopic();
		
		ImageView startGame = new ImageView("resources/images/ss.png");
		ImageView bg = new ImageView("/resources/images/classroom.png");
		ImageView ib = new ImageView("/resources/images/exam_instruction.png");
		ImageView instruction = new ImageView("/resources/images/instruction_engineering.png");
        BorderPane pane_start = new BorderPane();
		BorderPane pane_instruction = new BorderPane();
		BorderPane pane = new BorderPane();
		BorderPane pane_ctrler = new BorderPane();
		StackPane stackPane = new StackPane();
		VBox buttons = new VBox(5);
		stackPane.getChildren().addAll(bg, pane_ctrler);
		pane_start.setCenter(buttons);
        pane_ctrler.setCenter(pane_start);
		FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), bg);
        fadeOut.setFromValue(0.5);
        fadeOut.setToValue(0.5);
		fadeOut.play();
		pane_instruction.setCenter(instruction);
		buttons.getChildren().addAll(startGame, ib);
		buttons.setAlignment(Pos.CENTER);
		
		HBox topicBox = new HBox();
		topicBox.setAlignment(Pos.CENTER);
		pane.setCenter(topicBox);
		
		Label scoreLabel = new Label();
		scoreLabel.setText(String.valueOf(score));
		Text score_infoText = new Text();
		score_infoText.setText("score: ");
		HBox infoBox = new HBox(5);
		infoBox.getChildren().addAll(score_infoText, scoreLabel);
		BorderPane.setAlignment(scoreLabel, Pos.TOP_CENTER);
		BorderPane.setAlignment(score_infoText, Pos.TOP_CENTER);
		infoBox.setAlignment(Pos.TOP_CENTER);
		pane.setTop(infoBox);
		scoreLabel.setFont(new Font(75));
		score_infoText.setFont(new Font(75));
		
		Scene scene = new Scene(stackPane, 1280, 720);
		ArrayList<String> answerArrayList = new ArrayList<String>();
		String answerStr = question.topic_com();;
		for(int i=0; i<answerStr.length(); i++) {
			answerArrayList.add(String.valueOf(answerStr.charAt(i)));
		}
		
		final int[] locate= {0};
		ArrayList<Text> topicTextList = new ArrayList<Text>();
		for(int i=0; i<question.gettopicList().get(locate[0]).length(); i++) {
			Text topicWord = new Text();
			topicWord.setText(String.valueOf(question.gettopicList().get(locate[0]).charAt(i)));
			BorderPane.setAlignment(topicWord, Pos.CENTER);
			topicWord.setFont(new Font(100));
			topicBox.getChildren().add(topicWord);
			topicTextList.add(topicWord);
		}
		
		// Timer
		Timeline timeline = new Timeline();
		timerLabel.setText("time: "+String.valueOf(timeSeconds));
		timerLabel.setFont(new Font(100));
		timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), e -> {
			timeSeconds--;
			if(timeSeconds > 0) {
				timerLabel.setText("time: "+String.valueOf(timeSeconds));
				// timerLabel.setFont(new Font(100));
				if(timeSeconds <= 5) {
					timerLabel.setFill(javafx.scene.paint.Color.RED);
				}
			}
			else if(timeSeconds == 0) {
				timerLabel.setText("時間到！");
				scene.setOnKeyPressed(null);
			}
			else if(timeSeconds <= -3) {
				pane_ctrler.setCenter(conclude());
				Main.scores_engineering = score;
				b2b = true;
			}
		}));
		timeline.setCycleCount(timeSeconds+3);
		
		pane.setBottom(timerLabel);
		BorderPane.setAlignment(timerLabel, Pos.BOTTOM_CENTER);
		
		final int[] check = {0};
		
		scene.setOnKeyPressed(e -> {
			if(answerArrayList.isEmpty()) {  //delete
				System.out.println("End");
			}
			else {
				if(String.valueOf(e.getCode()).toLowerCase().equals(answerArrayList.get(0).toLowerCase())) {

					answerArrayList.remove(0);
					check[0]+=1;
					if(answerArrayList.get(0).equals("|")) {
						
						System.out.println("Pass!");
						answerArrayList.remove(0);
						score+=10;
						scoreLabel.setText(String.valueOf(score));
						
						if(answerArrayList.isEmpty()) {
							scene.setOnKeyPressed(null);
							System.out.println("End");
						}
						
						else {
							check[0]=0;
							locate[0]+=1;
							topicBox.getChildren().clear();
							topicTextList.clear();
							
							for(int i=0; i<question.gettopicList().get(locate[0]).length(); i++) {
								Text topicWord = new Text();
								topicWord.setText(String.valueOf(question.gettopicList().get(locate[0]).charAt(i)));
								BorderPane.setAlignment(topicWord, Pos.CENTER);
								topicWord.setFont(new Font(100));
								topicBox.getChildren().add(topicWord);
								topicTextList.add(topicWord);
							}
						}
						
					}
					
					if(check[0]>0) {
						for(int i=0; i<check[0]; i++) {
							topicTextList.get(i).setFill(javafx.scene.paint.Color.LIGHTGRAY);
						}
					}
					
				}
				else if(e.getCode() == KeyCode.SPACE && answerArrayList.get(0).equals(" ")){
					check[0]+=1;
					answerArrayList.remove(0);
				}
				else {
					Main.music.wrongTyping.stop();
					Main.music.wrongTyping.play();
					System.out.println("Wrong: {"+answerArrayList.get(0)+"}|{"+e.getCode()+"}");
				}
			}
		});
		startGame.setOnMouseClicked(e -> {
            pane_ctrler.setCenter(pane);
            start = true;
            if(start) {
                timeline.play();
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
			if (b2b) {
				Main.switchScene(Loading.scene(Building_2.scene(0, 360), 2));
				Main.music.swtichMusic(Main.music.exam, Main.music.main);
			}
		});
		return scene;
	}

	public void start(Stage primaryStage) throws IOException {
		
		primaryStage.setTitle("Engineer");
		primaryStage.setScene(scene());
		primaryStage.show();
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
