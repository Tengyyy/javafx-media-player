package application;

import java.util.concurrent.Callable;

import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Utilities {

	
	// Create neatly formatted video duration string
	public static String getTime(Duration time) { 

		int hours = (int) time.toHours();
		int minutes = (int) time.toMinutes();
		int seconds = (int) time.toSeconds();

		
		if (seconds > 59)
			seconds = seconds % 60;
		if (minutes > 59)
			minutes = minutes % 60;
		if (hours > 59)
			hours = hours % 60;

		// Don't show the hours unless the video has been playing for an hour or longer.
		if (hours > 0)
			return String.format("%d:%02d:%02d", hours, minutes, seconds);
		else
			return String.format("%02d:%02d", minutes, seconds);
	}
	
	// binds the video duration label string to mediaplayers current time value
	public static void bindCurrentTimeLabel(Label durationLabel, MediaPlayer mediaPlayer, Media media) {

		durationLabel.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
			@Override
			public String call() throws Exception {

				return getTime(mediaPlayer.getCurrentTime()) + "/" + getTime(media.getDuration());
			}
		}, mediaPlayer.currentTimeProperty()));
	}
	
	public static void bindTimeLeftLabel(Label durationLabel, MediaPlayer mediaPlayer, Media media) { // gotta make formatting of this label prettier

		durationLabel.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
			@Override
			public String call() throws Exception {

				return "-" + getTime(media.getDuration().subtract(mediaPlayer.getCurrentTime())) + "/" + getTime(media.getDuration());
			}
		}, mediaPlayer.currentTimeProperty()));
	}
	
	
	// makes HBox's background lighter and less transparent on hovering
	public static void hoverEffectOn(HBox setting) {
		setting.setStyle("-fx-background-color: rgba(73,73,73,0.8)");
	}

	// turns HBox background back to normal
	public static void hoverEffectOff(HBox setting) {
		setting.setStyle("-fx-background-color: rgba(83,83,83,0)");
	}
	
}
