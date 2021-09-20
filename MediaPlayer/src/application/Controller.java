package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
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
	Button fullScreenButton, playButton, volumeButton;
	
	@FXML
	ImageView playLogo, fullScreenIcon, volumeIcon;
	
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
	
	Image maximize, minimize, volumeUp, volumeDown, volumeMute;


	private Image start;
	
	private File maximizeFile, minimizeFile, playFile, pauseFile, startFile, volumeUpFile, volumeDownFile, volumeMuteFile;
	
	final Timeline timeline = new Timeline();
	
	boolean muted = false;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		file = new File("hey.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
		
		// declaring media control images
		maximizeFile = new File("src/application/maximize.png");
		minimizeFile = new File("src/application/minimize.png");
		playFile = new File("src/application/play.gif");
		pauseFile = new File("src/application/pause.gif");
		startFile = new File("src/application/play.png");
		
		volumeUpFile = new File("src/application/volumeUp.png");
		volumeDownFile = new File("src/application/volumeDown.png");
		volumeMuteFile = new File("src/application/volumeMute.png");
		
		
		
		maximize = new Image(maximizeFile.toURI().toString());
		minimize = new Image(minimizeFile.toURI().toString());
		start = new Image(startFile.toURI().toString());
		
		volumeUp = new Image(volumeUpFile.toURI().toString());
		volumeDown = new Image(volumeDownFile.toURI().toString());
		volumeMute = new Image(volumeMuteFile.toURI().toString());
		
		// Make mediaView adjust to frame size
		mediaViewWidth = mediaView.fitWidthProperty();
		mediaViewHeight = mediaView.fitHeightProperty();
		mediaViewWidth.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		mediaViewHeight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		mediaView.setPreserveRatio(true);
		
		
		mediaView.setMediaPlayer(mediaPlayer);
		playing = false;
		
		pane.setStyle("-fx-background-color: rgb(24,24,24)");
		
		playLogo.setImage(start);
		playButton.setBackground(Background.EMPTY);
		
		fullScreenIcon.setImage(maximize);
		fullScreenButton.setBackground(Background.EMPTY);
		
		volumeButton.setBackground(Background.EMPTY);
		volumeIcon.setImage(volumeUp);
		
		
	}
	
	public void playOrPause() {
		
		//displayControls();
		
		if(!playing) {
			mediaPlayer.play();
			playing = true;
			playLogo.setImage(new Image(playFile.toURI().toString()));
		}
		else {
			mediaPlayer.pause();
			playing = false;
			playLogo.setImage(new Image(pauseFile.toURI().toString()));
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
		
		if(Main.stage.isFullScreen()) {
			fullScreenIcon.setImage(minimize);
		}
		else {
			fullScreenIcon.setImage(maximize);
		}
	}
	
	public void fullScreenEnter() {
	     
	     timeline.setCycleCount(2);
	     timeline.setAutoReverse(true);
	     timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200),
	    		 new KeyValue(fullScreenIcon.scaleYProperty(), 1.3)));
	     timeline.getKeyFrames().add(new KeyFrame(Duration.millis(200),
	    		 new KeyValue(fullScreenIcon.scaleXProperty(), 1.3)));
	     
	     timeline.play();
	}
	
	public void fullSreenExit() {
		
		timeline.stop();
		
		fullScreenIcon.scaleXProperty().set(1);
		fullScreenIcon.scaleYProperty().set(1);
	}
	
	public void mute() {
	
		if(!muted) {
			
			muted = true;
			volumeIcon.setImage(volumeMute);
			mediaPlayer.setMute(muted);
		}
		else {
			muted = false;
			volumeIcon.setImage(volumeUp);
			mediaPlayer.setMute(muted);
		}
		
	}
	
	
}
