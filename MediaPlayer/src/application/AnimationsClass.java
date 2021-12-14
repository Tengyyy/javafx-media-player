package application;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.util.Duration;

public class AnimationsClass {

	static ScaleTransition fullScreenButtonScaleTransition;
	
	static TranslateTransition volumeSliderTranslateTransition1;
	static TranslateTransition volumeSliderTranslateTransition2;
	static TranslateTransition volumeSliderTranslateTransition3;
	
	public static void openSettings(StackPane bufferPane) {

		FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), bufferPane);
		fadeTransition1.setFromValue(0.0f);
		fadeTransition1.setToValue(1);
		fadeTransition1.setCycleCount(1);

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition1.setFromY(170);
		translateTransition1.setToY(0);
		translateTransition1.setCycleCount(1);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
	}
	
	public static void closeSettings(StackPane bufferPane) {
		
		FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), bufferPane);
		fadeTransition1.setFromValue(1);
		fadeTransition1.setToValue(0.0f);
		fadeTransition1.setCycleCount(1);

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition1.setFromY(0);
		translateTransition1.setToY(bufferPane.getHeight());
		translateTransition1.setCycleCount(1);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
	}
	
	
	public static void closeSettingsFromPlaybackOptions(Pane settingsBackgroundPane, StackPane playbackOptionsBuffer, StackPane bufferPane) {
		
		settingsBackgroundPane.prefHeightProperty().unbind();
		playbackOptionsBuffer.translateYProperty().unbind();
		
		FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), playbackOptionsBuffer);
		fadeTransition1.setFromValue(1);
		fadeTransition1.setToValue(0.0f);
		fadeTransition1.setCycleCount(1);
		
		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), playbackOptionsBuffer);
		translateTransition1.setFromY(playbackOptionsBuffer.getTranslateY());
		translateTransition1.setToY(bufferPane.getHeight());
		translateTransition1.setCycleCount(1);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			playbackOptionsBuffer.setTranslateX(settingsBackgroundPane.getWidth());
			bufferPane.setTranslateX(0);
			settingsBackgroundPane.setPrefHeight(170);
			playbackOptionsBuffer.setTranslateY(settingsBackgroundPane.getHeight() - playbackOptionsBuffer.getHeight());
			playbackOptionsBuffer.setOpacity(1);
			playbackOptionsBuffer.translateYProperty().bind(Bindings.subtract(settingsBackgroundPane.heightProperty(), playbackOptionsBuffer.heightProperty()));
			bufferPane.setTranslateY(bufferPane.getHeight());
		});
	}
	
	
	public static void closeSettingsFromPlaybackSpeed(Pane settingsBackgroundPane, ScrollPane playbackSpeedScroll, StackPane bufferPane) {
		settingsBackgroundPane.prefHeightProperty().unbind();
		playbackSpeedScroll.translateYProperty().unbind();

		FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), playbackSpeedScroll);
		fadeTransition1.setFromValue(1);
		fadeTransition1.setToValue(0.0f);
		fadeTransition1.setCycleCount(1);
		
		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100),playbackSpeedScroll);
		translateTransition1.setFromY(playbackSpeedScroll.getTranslateY());
		translateTransition1.setToY(bufferPane.getHeight());
		translateTransition1.setCycleCount(1);


		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			playbackSpeedScroll.setTranslateX(settingsBackgroundPane.getWidth());
			bufferPane.setTranslateX(0);
			settingsBackgroundPane.setPrefHeight(170);
			playbackSpeedScroll.setTranslateY(settingsBackgroundPane.getHeight() - playbackSpeedScroll.getHeight());
			playbackSpeedScroll.setOpacity(1);
			playbackSpeedScroll.translateYProperty().bind(Bindings.subtract(settingsBackgroundPane.heightProperty(), playbackSpeedScroll.heightProperty()));
			bufferPane.setTranslateY(bufferPane.getHeight());
			playbackSpeedScroll.setVvalue(0);
		});
	}
	
	public static void closeSettingsFromCustomSpeed(Pane settingsBackgroundPane, ScrollPane playbackSpeedScroll, StackPane customSpeedBuffer, StackPane bufferPane) {
		settingsBackgroundPane.prefHeightProperty().unbind();
		playbackSpeedScroll.translateYProperty().unbind();

		FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), customSpeedBuffer);
		fadeTransition1.setFromValue(1);
		fadeTransition1.setToValue(0.0f);
		fadeTransition1.setCycleCount(1);

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), customSpeedBuffer);
		translateTransition1.setFromY(0);
		translateTransition1.setToY(customSpeedBuffer.getHeight());
		translateTransition1.setCycleCount(1);

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			playbackSpeedScroll.setTranslateX(settingsBackgroundPane.getWidth());
			bufferPane.setTranslateX(0);
			settingsBackgroundPane.setPrefHeight(170);
			playbackSpeedScroll.setTranslateY(settingsBackgroundPane.getHeight() - playbackSpeedScroll.getHeight());
			playbackSpeedScroll.setOpacity(1);
			playbackSpeedScroll.translateYProperty().bind(Bindings.subtract(settingsBackgroundPane.heightProperty(), playbackSpeedScroll.heightProperty()));
			bufferPane.setTranslateY(bufferPane.getHeight());
			customSpeedBuffer.setOpacity(1);
			customSpeedBuffer.setTranslateX(settingsBackgroundPane.getWidth());
			customSpeedBuffer.setTranslateY(0);
			playbackSpeedScroll.setVvalue(0);
			
		});
	}
	
	public static void openPlaybackSpeed(StackPane bufferPane, Pane settingsBackgroundPane, ScrollPane playbackSpeedScroll, double toHeight) {
		
		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition1.setFromX(0);
		translateTransition1.setToX(-settingsBackgroundPane.getWidth());
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(settingsBackgroundPane.getWidth());
		translateTransition2.setToX(0);
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline = new Timeline();

		settingsTimeline.setCycleCount(1);
		settingsTimeline.setAutoReverse(false);

		settingsTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), toHeight, Interpolator.LINEAR)));


		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();


		parallelTransition.setOnFinished((e) -> {
			settingsBackgroundPane.prefHeightProperty().bind(Bindings.max(Bindings.add(playbackSpeedScroll.heightProperty(), 40), 170));
		});
	}
	
	public static void closePlaybackSpeed(Pane settingsBackgroundPane, ScrollPane playbackSpeedScroll, StackPane bufferPane) {
		
		settingsBackgroundPane.prefHeightProperty().unbind();
		settingsBackgroundPane.setPrefHeight(playbackSpeedScroll.getHeight());

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition1.setFromX(-settingsBackgroundPane.getWidth());
		translateTransition1.setToX(0);
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(settingsBackgroundPane.getWidth());
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), 170, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			playbackSpeedScroll.setVvalue(0);
		});
	}
	
	public static void openCustomSpeed(Pane settingsBackgroundPane, StackPane customSpeedBuffer, ScrollPane playbackSpeedScroll) {
		
		settingsBackgroundPane.prefHeightProperty().unbind();
		
		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), customSpeedBuffer);
		translateTransition1.setFromX(settingsBackgroundPane.getWidth());
		translateTransition1.setToX(0);
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-playbackSpeedScroll.getWidth() - 1.5);
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), 130, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			playbackSpeedScroll.setVvalue(0);
		});
	}
	
	
	public static void closeCustomSpeed(StackPane customSpeedBuffer, Pane settingsBackgroundPane, ScrollPane playbackSpeedScroll, double toHeight) {
		
		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), customSpeedBuffer);
		translateTransition1.setFromX(0);
		translateTransition1.setToX(settingsBackgroundPane.getWidth());
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(-settingsBackgroundPane.getWidth());
		translateTransition2.setToX(0);
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();
		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), toHeight, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			settingsBackgroundPane.prefHeightProperty().bind(Bindings.add(playbackSpeedScroll.heightProperty(), 40));
		});
	}
	
	public static void openPlaybackOptions(Pane settingsBackgroundPane, StackPane playbackOptionsBuffer, StackPane bufferPane) {
		
		settingsBackgroundPane.prefHeightProperty().unbind();

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), playbackOptionsBuffer);
		translateTransition1.setFromX(settingsBackgroundPane.getWidth());
		translateTransition1.setToX(0);
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-settingsBackgroundPane.getWidth());
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), 230, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
	}
	
	
	public static void closePlaybackOptions(StackPane playbackOptionsBuffer, Pane settingsBackgroundPane, StackPane bufferPane) {
		
		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), playbackOptionsBuffer);
		translateTransition1.setFromX(0);
		translateTransition1.setToX(settingsBackgroundPane.getWidth());
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition2.setFromX(-settingsBackgroundPane.getWidth());
		translateTransition2.setToX(0);
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), 170, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
	}
	
	public static void openCaptions(Line captionLine) {
		ScaleTransition scale = new ScaleTransition(Duration.millis(100), captionLine);
		scale.setFromX(0);
		scale.setToX(1);
		scale.setCycleCount(1);
		scale.setInterpolator(Interpolator.LINEAR);
		scale.play();
	}
	
	public static void closeCaptions(Line captionLine) {
		ScaleTransition scale = new ScaleTransition(Duration.millis(100), captionLine);
		scale.setFromX(1);
		scale.setToX(0);
		scale.setCycleCount(1);
		scale.setInterpolator(Interpolator.LINEAR);
		scale.play();
	}
	
	public static void fullScreenHoverOn(ImageView fullScreenIcon) {
		fullScreenButtonScaleTransition = new ScaleTransition(Duration.millis(200), fullScreenIcon);
		fullScreenButtonScaleTransition.setCycleCount(2);
		fullScreenButtonScaleTransition.setAutoReverse(true);
		fullScreenButtonScaleTransition.setFromX(1);
		fullScreenButtonScaleTransition.setToX(1.3);
		fullScreenButtonScaleTransition.setFromY(1);
		fullScreenButtonScaleTransition.setToY(1.3);
		fullScreenButtonScaleTransition.play();
	}
	
	public static void fullScreenHoverOff(ImageView fullScreenIcon) {
		fullScreenButtonScaleTransition.stop();
		fullScreenIcon.setScaleX(1);
		fullScreenIcon.setScaleY(1);
	}
	
	public static void volumeSliderHoverOn(Slider volumeSlider, Label durationLabel, ProgressBar volumeTrack) {
		volumeSliderTranslateTransition1 = new TranslateTransition(Duration.millis(100), volumeSlider);
		volumeSliderTranslateTransition1.setFromX(-60);
		volumeSliderTranslateTransition1.setToX(0);
		volumeSliderTranslateTransition1.setInterpolator(Interpolator.EASE_OUT);
		volumeSliderTranslateTransition1.play();
		
		volumeSliderTranslateTransition2 = new TranslateTransition(Duration.millis(100), durationLabel);
		volumeSliderTranslateTransition2.setFromX(-60);
		volumeSliderTranslateTransition2.setToX(0);
		volumeSliderTranslateTransition2.setInterpolator(Interpolator.EASE_OUT);
		volumeSliderTranslateTransition2.play();
		
		volumeSliderTranslateTransition3 = new TranslateTransition(Duration.millis(100), volumeTrack);
		volumeSliderTranslateTransition3.setFromX(-60);
		volumeSliderTranslateTransition3.setToX(0);
		volumeSliderTranslateTransition3.setInterpolator(Interpolator.EASE_OUT);
		volumeSliderTranslateTransition3.play();
	}
	
	public static void volumeSliderHoverOff(Slider volumeSlider, Label durationLabel, ProgressBar volumeTrack) {
		volumeSliderTranslateTransition1.stop();
		volumeSliderTranslateTransition1 = new TranslateTransition(Duration.millis(100), volumeSlider);
		volumeSliderTranslateTransition1.setFromX(0);
		volumeSliderTranslateTransition1.setToX(-60);
		volumeSliderTranslateTransition1.setInterpolator(Interpolator.EASE_OUT);
		volumeSliderTranslateTransition1.play();
		
		volumeSliderTranslateTransition2.stop();
		volumeSliderTranslateTransition2 = new TranslateTransition(Duration.millis(100), durationLabel);
		volumeSliderTranslateTransition2.setFromX(0);
		volumeSliderTranslateTransition2.setToX(-60);
		volumeSliderTranslateTransition2.setInterpolator(Interpolator.EASE_OUT);
		volumeSliderTranslateTransition2.play();
		
		volumeSliderTranslateTransition3.stop();
		volumeSliderTranslateTransition3 = new TranslateTransition(Duration.millis(100), volumeTrack);
		volumeSliderTranslateTransition3.setFromX(0);
		volumeSliderTranslateTransition3.setToX(-60);
		volumeSliderTranslateTransition3.setInterpolator(Interpolator.EASE_OUT);
		volumeSliderTranslateTransition3.play();
	}
	
}
