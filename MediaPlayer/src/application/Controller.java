package application;

import java.io.File;
import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Controller implements Initializable {

	@FXML
	public MediaView mediaView;

	@FXML
	VBox controlBar, settingsHome, playbackSpeedPage;

	@FXML
	HBox playbackSpeedBox, playbackOptionsBox, directoryBox, durationSliderBox, playbackSpeedTitle, playbackSpeed1, playbackSpeed2, playbackSpeed3, playbackSpeed4, playbackSpeed5, playbackSpeed6, playbackSpeed7, playbackSpeed8;

	@FXML
	Button fullScreenButton, playButton, volumeButton, settingsButton, menuButton, nextVideoButton;

	@FXML
	ImageView playLogo, fullScreenIcon, volumeIcon, settingsIcon, nextVideoIcon;

	@FXML
	StackPane pane, settingsPane;

	@FXML
	Pane settingsBackgroundPane;

	@FXML
	Pane playPane;

	@FXML
	Slider volumeSlider, durationSlider;

	@FXML
	FlowPane volumeSliderPane;

	@FXML
	Label durationLabel, playbackValueLabel, changeDirectoryLabel, playbackOptionsArrow;
	
	@FXML
	ScrollPane playbackSpeedScroll;

	private File file;
	Media media;
	MediaPlayer mediaPlayer;

	boolean playing = false;
	boolean wasPlaying = false;

	boolean tempBool = false;

	boolean atEnd = false;
	
	boolean seekedToEnd = false;

	private DoubleProperty mediaViewWidth;
	private DoubleProperty mediaViewHeight;

	Image maximize, minimize, volumeUp, volumeDown, volumeMute, settingsEnter, settingsExit, settingsImage, rightArrow, nextVideo;

	double volumeValue;

	private Image start;

	private File maximizeFile, minimizeFile, playFile, pauseFile, startFile, volumeUpFile, volumeDownFile,
			volumeMuteFile, pauseImageFile, settingsEnterFile, settingsExitFile, settingsImageFile,
			rightArrowFile, nextVideoFile;
	
	File replayFile;

	Timeline fullscreenTimeline;

	Timeline volume;

	boolean muted = false;

	boolean isExited = false;

	boolean settingsOpen = false;

	boolean sliderFocus = false;

	boolean running = false; // media running status

	int videoLength;
	int currLength = 0;
	
	int focusNodeTracker = 0;

	Timer durationTimer;
	TimerTask durationTimerTask;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		volumeSliderPane.setClip(new Rectangle(60, 17));

		volumeSlider.setTranslateX(-60);

		durationLabel.setTranslateX(-60);

		// volumeSlider.setFocusTraversable(false);

		file = new File("hey.mp4");
		media = new Media(file.toURI().toString());

		mediaPlayer = new MediaPlayer(media);

		// mediaPlayer.get

		// declaring media control images
		maximizeFile = new File("src/application/maximize.png");
		minimizeFile = new File("src/application/minimize.png");
		playFile = new File("src/application/play.gif");
		pauseFile = new File("src/application/pause.gif");
		startFile = new File("src/application/play.png");

		volumeUpFile = new File("src/application/volumeUp.png");
		volumeDownFile = new File("src/application/volumeDown.png");
		volumeMuteFile = new File("src/application/volumeMute.png");

		replayFile = new File("src/application/replay.png");
		pauseImageFile = new File("src/application/pause.png");

		settingsEnterFile = new File("src/application/settingsMenuOpen.gif");
		settingsExitFile = new File("src/application/settingsMenuClose.gif");
		settingsImageFile = new File("src/application/settingsMenuImage.png");

		rightArrowFile = new File("src/application/rightArrow.png");
		
		nextVideoFile = new File("src/application/nextMedia.png");
		
		nextVideo = new Image(nextVideoFile.toURI().toString());

		maximize = new Image(maximizeFile.toURI().toString());
		minimize = new Image(minimizeFile.toURI().toString());
		start = new Image(startFile.toURI().toString());

		volumeUp = new Image(volumeUpFile.toURI().toString());
		volumeDown = new Image(volumeDownFile.toURI().toString());
		volumeMute = new Image(volumeMuteFile.toURI().toString());

		rightArrow = new Image(rightArrowFile.toURI().toString());

		settingsImage = new Image(settingsImageFile.toURI().toString());

		// Make mediaView adjust to frame size
		mediaViewWidth = mediaView.fitWidthProperty();
		mediaViewHeight = mediaView.fitHeightProperty();
		mediaViewWidth.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		mediaViewHeight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		mediaView.setPreserveRatio(true);

		mediaView.setMediaPlayer(mediaPlayer);
		playing = false;

		pane.setStyle("-fx-background-color: rgb(24,24,24)");

		settingsPane.setStyle("-fx-background-color: rgba(35,35,35,0.8)");
		
		playbackSpeedPage.setStyle("-fx-background-color: rgba(35,35,35,0.8)");
		playbackSpeedScroll.setStyle("-fx-background-color: rgba(35,35,35,0.8)");
		
		menuButton.setBackground(Background.EMPTY);

		playLogo.setImage(start);
		playButton.setBackground(Background.EMPTY);
		
		nextVideoButton.setBackground(Background.EMPTY);
		nextVideoIcon.setImage(nextVideo);

		playButton.setOnAction((e) -> playOrPause());

		fullScreenIcon.setImage(maximize);
		fullScreenButton.setBackground(Background.EMPTY);

		settingsButton.setBackground(Background.EMPTY);
		settingsIcon.setImage(settingsImage);

		volumeButton.setBackground(Background.EMPTY);
		volumeIcon.setImage(volumeUp);

		playbackValueLabel.setGraphic(new ImageView(rightArrow));

		changeDirectoryLabel.setGraphic(new ImageView(rightArrow));

		playbackOptionsArrow.setGraphic(new ImageView(rightArrow));

		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				mediaPlayer.setVolume(volumeSlider.getValue() / 100);

				if (volumeSlider.getValue() == 0) {
					volumeIcon.setImage(volumeMute);
					muted = true;
				} else if (volumeSlider.getValue() < 50) {
					volumeIcon.setImage(volumeDown);
					muted = false;
				} else {
					volumeIcon.setImage(volumeUp);
				}
			}

		});

		/**
		 * totalDurationProperty() - the total amount of play time if allowed to play
		 * until finished. This checks how long the the video attached to the media
		 * player is. If the media attached to the media player changes then the max of
		 * the slider will change as well.
		 */
		mediaPlayer.totalDurationProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration oldDuration,
					Duration newDuration) {

				// Note that duration is originally in milliseconds.
				// newDuration is the time of the current video, oldDuration is the duration of
				// the previous video.

				durationSlider.setMax(newDuration.toSeconds());
				// labelTotalTime.setText(getTime(newDuration));

			}
		});

		durationSlider.addEventFilter(MouseEvent.DRAG_DETECTED, e -> durationSlider.setValueChanging(true));
		durationSlider.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> durationSlider.setValueChanging(false));

		durationSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				updateMedia(newValue.doubleValue());
			}

		});

		durationSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() { // vaja ära fixida see jama
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				bindCurrentTimeLabel();
				if (wasPlaying) {

					if (!newValue && tempBool && !atEnd) {
						mediaPlayer.play();
						playing = true;
						playLogo.setImage(new Image(pauseImageFile.toURI().toString()));
						tempBool = false;
					}

					else if (newValue && tempBool) {

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								mediaPlayer.pause();
							}

						});
					}

					else if (newValue && !atEnd) {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								mediaPlayer.pause();
							}

						});

						playing = false;
						playLogo.setImage(new Image(pauseFile.toURI().toString()));

						// mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));

					} else if (!newValue && !atEnd) {
						mediaPlayer.play();
						playing = true;
						playLogo.setImage(new Image(playFile.toURI().toString()));
					}

					else if (newValue && tempBool) {

						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								mediaPlayer.pause();
							}

						});
					}
				}

				else {
					if (newValue) {

						// playLogo.setImage(new Image(pauseFile.toURI().toString()));

						// mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));
					}

				}

				mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));

			}

		});
		
		mediaView.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if(!newValue) {
				mediaView.setStyle("-fx-border-color: transparent;");
			}
			else {
				focusNodeTracker = 0;
			}
		});
		
		durationSlider.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if(!newValue) {
				durationSlider.setStyle("-fx-border-color: transparent;");
			}
			else {
				focusNodeTracker = 1;
			}
		});
		
		playButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if(!newValue) {
				playButton.setStyle("-fx-border-color: transparent;");
			}
			else {
				focusNodeTracker = 2;
			}
		});
		
		nextVideoButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if(!newValue) {
				nextVideoButton.setStyle("-fx-border-color: transparent;");
			}
			else {
				focusNodeTracker = 3;
			}
		});
		
		volumeButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if(!newValue) {
				volumeButton.setStyle("-fx-border-color: transparent;");
			}
			else {
				focusNodeTracker = 4;
			}
		});

		volumeSlider.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

					sliderFocus = newValue;

					if (!newValue) {
						volumeSliderExit();
						
						volumeSlider.setStyle("-fx-border-color: transparent;");
						
						
					} else {
						volumeSliderEnter();
						isExited = true;
						focusNodeTracker = 5;
					}

				});
		
		settingsButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if(!newValue) {
				settingsButton.setStyle("-fx-border-color: transparent;");
			}
			else {
				focusNodeTracker = 6;
			}
		});
		
		fullScreenButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if(!newValue) {
				fullScreenButton.setStyle("-fx-border-color: transparent;");
			}
			else {
				focusNodeTracker = 7;
			}
		});
		
		menuButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if(!newValue) {
				menuButton.setStyle("-fx-border-color: transparent;");
			}
			else {
				focusNodeTracker = 8;
			}
		});
		
		
		//TODO: Add focuslisteners for all 9 traversable nodes

		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {

				durationSlider.setMax(media.getDuration().toSeconds());

				bindCurrentTimeLabel();

				mediaPlayer.play();
				mediaPlayer.pause();

				// startTimer();

			}

		});

		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime,
					Duration newTime) {
				bindCurrentTimeLabel();
				if (!durationSlider.isValueChanging()) {
					durationSlider.setValue(newTime.toSeconds());
				}
			}
		});

		mediaPlayer.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

					endMedia();
			}
			
		});

		// On-hover effect for setting tab items
		//////////////////////////////////////////////////
		playbackSpeedBox.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeedBox);
		});
		playbackSpeedBox.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeedBox);
		});

		playbackOptionsBox.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackOptionsBox);
		});
		playbackOptionsBox.setOnMouseExited((e) -> {
			hoverEffectOff(playbackOptionsBox);
		});

		directoryBox.setOnMouseEntered((e) -> {
			hoverEffectOn(directoryBox);
		});
		directoryBox.setOnMouseExited((e) -> {
			hoverEffectOff(directoryBox);
		});
		
		// On-hover effect for playback speed items
		//////////////////////////////////////////////////
		
		playbackSpeedTitle.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeedTitle);
		});
		
		playbackSpeedTitle.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeedTitle);
		});
		
		
		playbackSpeed1.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeed1);
		});
		
		playbackSpeed1.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeed1);
		});

		playbackSpeed2.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeed2);
		});
		
		playbackSpeed2.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeed2);
		});
		
		playbackSpeed3.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeed3);
		});
		
		playbackSpeed3.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeed3);
		});
		
		playbackSpeed4.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeed4);
		});
		
		playbackSpeed4.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeed4);
		});
		
		playbackSpeed5.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeed5);
		});
		
		playbackSpeed5.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeed5);
		});
		
		playbackSpeed6.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeed6);
		});
		
		playbackSpeed6.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeed6);
		});
		
		playbackSpeed7.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeed7);
		});
		
		playbackSpeed7.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeed7);
		});
		
		playbackSpeed8.setOnMouseEntered((e) -> {
			hoverEffectOn(playbackSpeed8);
		});
		
		playbackSpeed8.setOnMouseExited((e) -> {
			hoverEffectOff(playbackSpeed8);
		});
		
		
		

		settingsBackgroundPane.setPickOnBounds(false);
		//////////////////////////////////////////////////
		
		settingsBackgroundPane.prefWidthProperty().bind(settingsPane.widthProperty());
		settingsBackgroundPane.prefHeightProperty().bind(settingsPane.heightProperty());
		
		

		// Clipping for the settings pane
		Rectangle rectangle = new Rectangle(settingsBackgroundPane.getWidth(), settingsBackgroundPane.getHeight());
		rectangle.widthProperty().bind(settingsBackgroundPane.widthProperty());
		rectangle.heightProperty().bind(settingsBackgroundPane.heightProperty());
		settingsBackgroundPane.setClip(rectangle);

		settingsPane.setTranslateY(170);
		

	}

	public void mediaClick() {

		if(settingsOpen) {
			openCloseSettings();
		}
		else {
			if (atEnd) {
				replayMedia();
			} else {
				playOrPause();
			}
		}

		mediaView.requestFocus();
	}

	public void playOrPause() {

		// displayControls();

		if (!playing) {
			mediaPlayer.play();
			playing = true;

			playLogo.setImage(new Image(playFile.toURI().toString()));

		} else {
			mediaPlayer.pause();
			playing = false;
			playLogo.setImage(new Image(pauseFile.toURI().toString()));

		}

		wasPlaying = playing;
	}

	// TODO: Fix bug when using duration slider to scroll back in the video after
	// the video has already ended.
	public void replayMedia() {
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.play();
		playing = true;
		atEnd = false;
		playLogo.setImage(new Image(pauseImageFile.toURI().toString()));

		// startTimer();

		playButton.setOnAction((e) -> playOrPause());

	}

	public void displayControls() {

		if (playing) {
			// control bar slide animation - display
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.2));
			slide.setNode(controlBar);

			slide.setToY(0);

			slide.play();
			controlBar.setTranslateY(-50);
		} else {
			// control bar slide animation - hide
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.4));
			slide.setNode(controlBar);

			slide.setToY(50);
			slide.play();
			controlBar.setTranslateY(0);
		}

	}

	public void fullScreen() {
		Main.stage.setFullScreen(!Main.stage.isFullScreen());

		if (Main.stage.isFullScreen()) {
			fullScreenIcon.setImage(minimize);
		} else {
			fullScreenIcon.setImage(maximize);
		}
	}

	public void fullScreenEnter() {

		fullscreenTimeline = new Timeline();

		fullscreenTimeline.setCycleCount(2);
		fullscreenTimeline.setAutoReverse(true);
		fullscreenTimeline.getKeyFrames()
				.add(new KeyFrame(Duration.millis(200), new KeyValue(fullScreenIcon.scaleYProperty(), 1.3)));
		fullscreenTimeline.getKeyFrames()
				.add(new KeyFrame(Duration.millis(200), new KeyValue(fullScreenIcon.scaleXProperty(), 1.3)));

		fullscreenTimeline.play();

		fullscreenTimeline.setOnFinished((e) -> {
			fullscreenTimeline.stop();
			fullscreenTimeline.getKeyFrames().clear();
		});
	}

	public void fullSreenExit() {

		fullscreenTimeline = new Timeline();

		fullscreenTimeline.stop();

		fullscreenTimeline.getKeyFrames().clear();

		fullScreenIcon.scaleXProperty().set(1);
		fullScreenIcon.scaleYProperty().set(1);
	}

	public void openCloseSettings() {
		if (settingsOpen) {
			settingsExit = new Image(settingsExitFile.toURI().toString());
			settingsIcon.setImage(settingsExit);
			settingsOpen = false;

			FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), settingsPane);
			fadeTransition.setFromValue(0.8f);
			fadeTransition.setToValue(0.0f);
			fadeTransition.setCycleCount(1);

			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), settingsPane);
			translateTransition.setFromY(0);
			translateTransition.setToY(170);
			translateTransition.setCycleCount(1);

			ParallelTransition parallelTransition = new ParallelTransition();
			parallelTransition.getChildren().addAll(fadeTransition, translateTransition);
			parallelTransition.setCycleCount(1);
			parallelTransition.play();

		} else {
			settingsEnter = new Image(settingsEnterFile.toURI().toString());
			settingsIcon.setImage(settingsEnter);
			settingsOpen = true;

			FadeTransition fadeTransition = new FadeTransition(Duration.millis(100), settingsPane);
			fadeTransition.setFromValue(0.0f);
			fadeTransition.setToValue(0.8f);
			fadeTransition.setCycleCount(1);

			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), settingsPane);
			translateTransition.setFromY(170);
			translateTransition.setToY(0);
			translateTransition.setCycleCount(1);

			ParallelTransition parallelTransition = new ParallelTransition();
			parallelTransition.getChildren().addAll(fadeTransition, translateTransition);
			parallelTransition.setCycleCount(1);
			parallelTransition.play();
		}
	}

	public void volumeSliderEnter() {
		// display volume slider

		volume = new Timeline();

		volume.setCycleCount(1);
		volume.setAutoReverse(false);

		volume.getKeyFrames().add(new KeyFrame(Duration.millis(50),
				new KeyValue(volumeSlider.translateXProperty(), 0, Interpolator.EASE_OUT)));
		volume.getKeyFrames().add(new KeyFrame(Duration.millis(50),
				new KeyValue(durationLabel.translateXProperty(), 0, Interpolator.EASE_OUT)));

		volume.play();

		volume.setOnFinished((e) -> {
			volume.stop();
			volume.getKeyFrames().clear();
		});

	}

	public void enterArea() {

		isExited = false;

		volumeSliderEnter();
	}

	public void exitArea() {

		isExited = true;

		volumeSliderExit();
	}

	public void volumeSliderExit() {
		// hide volume slider

		if (isExited && !sliderFocus) {

			// volume.stop();

			volume = new Timeline();

			// volume.getKeyFrames().clear();

			volume.getKeyFrames().add(new KeyFrame(Duration.millis(100),
					new KeyValue(volumeSlider.translateXProperty(), -60, Interpolator.EASE_OUT)));

			volume.getKeyFrames().add(new KeyFrame(Duration.millis(100),
					new KeyValue(durationLabel.translateXProperty(), -60, Interpolator.EASE_OUT)));

			volume.play();

			volume.setOnFinished((e) -> {
				volume.stop();
				volume.getKeyFrames().clear();

			});

		}

	}

	public void mute() {

		if (!muted) {

			muted = true;
			volumeIcon.setImage(volumeMute);
			mediaPlayer.setVolume(0);

			volumeValue = volumeSlider.getValue();

			volumeSlider.setValue(0);
		} else {
			muted = false;
			volumeIcon.setImage(volumeUp);
			mediaPlayer.setVolume(volumeValue);

			volumeSlider.setValue(volumeValue);
		}

	}

	public String getTime(Duration time) { // this has to be finished before moving on to binding the duration label

		int hours = (int) time.toHours();
		int minutes = (int) time.toMinutes();
		int seconds = (int) time.toSeconds();

		// Fix the issue with the timer going to 61 and above for seconds, minutes, and
		// hours.
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

	public void bindCurrentTimeLabel() {

		durationLabel.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
			@Override
			public String call() throws Exception {

				return getTime(mediaPlayer.getCurrentTime()) + "/" + getTime(media.getDuration());
			}
		}, mediaPlayer.currentTimeProperty()));
	}

	public void hoverEffectOn(HBox setting) {
		setting.setStyle("-fx-background-color: rgba(73,73,73,0.8)");
	}

	public void hoverEffectOff(HBox setting) {
		setting.setStyle("-fx-background-color: rgba(83,83,83,0)");
	}

	public void traverseFocusForwards() {
		
		switch(focusNodeTracker) {
		
			// mediaView
			case 0: {
				menuButton.setStyle("-fx-border-color: transparent;");
				mediaView.setStyle("-fx-border-color: blue;");
			}
				break;
		
			// durationSlider
			case 1: {
				mediaView.setStyle("-fx-border-color: transparent;");
				durationSlider.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// playButton
			case 2: {
				durationSlider.setStyle("-fx-border-color: transparent;");
				playButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// nextVideoButton
			case 3: {
				playButton.setStyle("-fx-border-color: transparent;");
				nextVideoButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// muteButton
			case 4: {
				nextVideoButton.setStyle("-fx-border-color: transparent;");
				volumeButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// volumeSlider
			case 5: {
				volumeButton.setStyle("-fx-border-color: transparent;");
				volumeSlider.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// settingsButton
			case 6: {
				volumeSlider.setStyle("-fx-border-color: transparent;");
				settingsButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// fullscreenButton
			case 7: {
				settingsButton.setStyle("-fx-border-color: transparent;");
				fullScreenButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// menuButton
			case 8: {
				fullScreenButton.setStyle("-fx-border-color: transparent;");
				menuButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			default: break;
		
		}
		
	}
	
	public void traverseFocusBackwards() {
		
		switch(focusNodeTracker) {
		
			// mediaView
			case 0: {
				durationSlider.setStyle("-fx-border-color: transparent;");
				mediaView.setStyle("-fx-border-color: blue;");
			}
				break;
		
			// durationSlider
			case 1: {
				playButton.setStyle("-fx-border-color: transparent;");
				durationSlider.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// playButton
			case 2: {
				nextVideoButton.setStyle("-fx-border-color: transparent;");
				playButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// nextVideoButton
			case 3: {
				volumeButton.setStyle("-fx-border-color: transparent;");
				nextVideoButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// muteButton
			case 4: {
				volumeSlider.setStyle("-fx-border-color: transparent;");
				volumeButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// volumeSlider
			case 5: {
				settingsButton.setStyle("-fx-border-color: transparent;");
				volumeSlider.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// settingsButton
			case 6: {
				fullScreenButton.setStyle("-fx-border-color: transparent;");
				settingsButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// fullscreenButton
			case 7: {
				menuButton.setStyle("-fx-border-color: transparent;");
				fullScreenButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// menuButton
			case 8: {
				mediaView.setStyle("-fx-border-color: transparent;");
				menuButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			default: break;
		
		}
		
	}
	
	public void updateMedia(double newValue) {

		bindCurrentTimeLabel();

		if (atEnd) {
			atEnd = false;

			if (wasPlaying) {
				playLogo.setImage(new Image(pauseImageFile.toURI().toString()));

				if (!durationSlider.isValueChanging()) {
					playing = true;
					mediaPlayer.play();
				}
			} else {
				playLogo.setImage(new Image(startFile.toURI().toString()));
				playing = false;
			}

			playButton.setOnAction((e) -> {
				playOrPause();
			});
		} else if (newValue >= durationSlider.getMax()) {
			//durationSlider.setValue(durationSlider.getMax());
			atEnd = true;
			
			
				//endMedia();
				seekedToEnd = false;
			tempBool = true;


			playing = false;

			mediaPlayer.pause();

			playLogo.setImage(new Image(replayFile.toURI().toString()));

			playButton.setOnAction((e) -> replayMedia());
		}

		if (Math.abs(mediaPlayer.getCurrentTime().toSeconds() - newValue) > 0.5) {
			mediaPlayer.seek(Duration.seconds(newValue));


		}

	}
	
	public void endMedia() {
		
		//System.out.println("TEST");

		durationSlider.setValue(durationSlider.getMax());

		if (!durationLabel.textProperty().getValue().equals(getTime(mediaPlayer.getCurrentTime()) + "/" + getTime(media.getDuration()))) {

			durationLabel.textProperty().unbind();
			durationLabel.setText(getTime(mediaPlayer.getCurrentTime()) + "/" + getTime(media.getDuration()));
			
		}

		playLogo.setImage(new Image(replayFile.toURI().toString()));

		playButton.setOnAction((e) -> replayMedia());


	}
	
	public void openPlaybackSpeedPage() {
		

		
		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), settingsHome);
		translateTransition1.setFromX(0);
		translateTransition1.setToX(-235);
		translateTransition1.setCycleCount(1);
		
		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(235);
		translateTransition2.setToX(0);
		translateTransition2.setCycleCount(1);
		
		Timeline settingsTimeline = new Timeline();

		settingsTimeline.setCycleCount(1);
		settingsTimeline.setAutoReverse(false);
		settingsTimeline.getKeyFrames()
				.add(new KeyFrame(Duration.millis(100), new KeyValue(settingsPane.prefHeightProperty(), mediaView.sceneProperty().get().getHeight() < 575 ? mediaView.sceneProperty().get().getHeight() - 100 : 475 )));
		


		/*settingsTimeline.setOnFinished((e) -> {
			settingsTimeline.stop();
			settingsTimeline.getKeyFrames().clear();
		});*/

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		/*SequentialTransition seqTrans = new SequentialTransition();
		seqTrans.getChildren().addAll(parallelTransition, translateTransition2);
		seqTrans.play();*/
		
		//settingsTimeline.play();
	}


}
