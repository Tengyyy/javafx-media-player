package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

public class Controller implements Initializable{

	@FXML
	MediaView mediaView;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	private boolean playing;
	
	private DoubleProperty mediaViewWidth;
	private DoubleProperty mediaViewHeight;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		file = new File("hey.mp4");
		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		
		mediaViewWidth = mediaView.fitWidthProperty();
		mediaViewHeight = mediaView.fitHeightProperty();
		
		mediaViewWidth.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		mediaViewHeight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		mediaView.setPreserveRatio(true);
		
		mediaView.setMediaPlayer(mediaPlayer);
		playing = false;
		
	}
	
	public void playOrPause() {
		
		if(!playing) {
			mediaPlayer.play();
			playing = true;
		}
		else {
			mediaPlayer.pause();
			playing = false;
		}
	}
	
}
