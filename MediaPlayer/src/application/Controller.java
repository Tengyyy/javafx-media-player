package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable{

	@FXML
	MediaView mediaView;
	
	@FXML
	VBox controlBar;
	
	@FXML
	Button fullScreenButton, playButton;
	
	@FXML
	ImageView playLogo;
	
	@FXML
	StackPane pane;
	
	@FXML
	Pane playPane;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	private boolean playing;
	
	private DoubleProperty mediaViewWidth;
	private DoubleProperty mediaViewHeight;
	
	private SVGPath path;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		file = new File("hey.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
		// Make mediaView adjust to frame size
		mediaViewWidth = mediaView.fitWidthProperty();
		mediaViewHeight = mediaView.fitHeightProperty();
		mediaViewWidth.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		mediaViewHeight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		mediaView.setPreserveRatio(true);
		
		
		mediaView.setMediaPlayer(mediaPlayer);
		playing = false;
		
		pane.setStyle("-fx-background-color: rgb(24,24,24)");
		
		playLogo.setImage(new Image(new File("src/application/play.png").toURI().toString()));
		playButton.setBackground(Background.EMPTY);
		
		
	}
	
	public void playOrPause() {
		
		//displayControls();
		
		if(!playing) {
			mediaPlayer.play();
			playing = true;
			playLogo.setImage(new Image(new File("src/application/play.gif").toURI().toString()));
		}
		else {
			mediaPlayer.pause();
			playing = false;
			playLogo.setImage(new Image(new File("src/application/pause.gif").toURI().toString()));
		}
	}
	
	public void displayControls() {
		
		
		if(playing) {
			// control bar slide animation - display
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.2));
			slide.setNode(controlBar);
										
			slide.setToY(0);
			System.out.println(mediaView.getFitHeight());
			slide.play();
			controlBar.setTranslateY(-50);
		}
		else {
			// control bar slide animation - hide
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.4));
			slide.setNode(controlBar);
					
			slide.setToY(50);
			System.out.println(mediaView.getFitHeight());
			slide.play();
			controlBar.setTranslateY(0);
		}
		
	}
	
	public void fullScreen() {
		Main.stage.setFullScreen(!Main.stage.isFullScreen());
	}
	
}
