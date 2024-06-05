import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

class Question{
	private ArrayList<String> topic_list = new ArrayList<String>();
	String topic_combine="";
	
	public void ReadTopic() throws IOException{
		String str;
		FileReader fileReader = new FileReader("src/em.txt");
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


public class Engineer extends Application{
	private int score=0;
	private int timeSeconds = 10;
	private Text timerLabel = new Text();
	
	public Scene conclude() {
		BorderPane pane = new BorderPane();
		Text scoreText = new Text();
		scoreText.setText("Your score: "+String.valueOf(score));
		scoreText.setFont(new Font(150));
		pane.setCenter(scoreText);
		BorderPane.setAlignment(scoreText, Pos.CENTER);
		
		Scene scene = new Scene(pane, 1080, 720);
		return scene;
	}
	
	public void start(Stage primaryStage) throws IOException {
		Question question = new Question();
		question.ReadTopic();
		
		BorderPane pane = new BorderPane();
		
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
		
		Scene scene = new Scene(pane, 1080, 720);
		primaryStage.setTitle("Engineer");
		primaryStage.setScene(scene);
		primaryStage.show();
		
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
				// timeline.stop();
				primaryStage.setScene(conclude());
			}
		}));
		// timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.setCycleCount(13);
		timeline.play();
		
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
					System.out.println("Wrong: {"+answerArrayList.get(0)+"}|{"+e.getCode()+"}");
				}
			}
		});
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
