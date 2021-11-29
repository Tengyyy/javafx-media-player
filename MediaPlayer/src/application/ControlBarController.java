package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ControlBarController implements Initializable{

	@FXML
	
	VBox controlBar;
	
	@FXML
	public  Button fullScreenButton;

	@FXML
	public  Button playButton;

	@FXML
	public  Button volumeButton;

	@FXML
	Button settingsButton;

	@FXML
	
	Button nextVideoButton;

	@FXML
	Button captionsButton;
	
	@FXML
	
	ImageView settingsIcon;

	@FXML
	ImageView nextVideoIcon;

	@FXML
	ImageView captionsIcon;

	@FXML
	public  Slider volumeSlider;

	@FXML
	public  Slider durationSlider;
	
	@FXML
	FlowPane volumeSliderPane;
	
	@FXML
	
	Label durationLabel;

	@FXML
	
	Line captionLine;
	
	@FXML
	ImageView playLogo;

	@FXML
	public  ImageView fullScreenIcon;

	@FXML
	public  ImageView volumeIcon;
	
	private MainController mainController;
	private SettingsController settingsController;
	
	private AnimationsClass animationsClass;
	

	public  Image maximize;

	 Image minimize;

	public  Image volumeUp;

	Image volumeDown;

	public  Image volumeMute;

	 Image settingsEnter;

	 Image settingsExit;

	Image settingsImage;

	Image nextVideo;
	
	Image captionsImage;
	
	public  double volumeValue;

	
	private File maximizeFile, minimizeFile;

	File playFile;

	File pauseFile;


	private File volumeUpFile;

	private File volumeDownFile;

	private File volumeMuteFile;
	
	Image playImage;
	Image pauseImage;
	
	Image replayImage;

	 File settingsEnterFile;

	 File settingsExitFile;

	private File settingsImageFile;

	private File captionsFile;

	 File replayFile;

	private File nextVideoFile;

	Timeline fullscreenTimeline;

	 Timeline volume;
	
	public  boolean muted = false;

	 boolean isExited = false;
	
	 boolean sliderFocus = false;
	 
	
	 Tooltip play;
	 Tooltip pause;
	 Tooltip replay;
	public  Tooltip mute;
	public  Tooltip unmute;
	Tooltip settings;
	public  Tooltip enterFullScreen;
	 Tooltip exitFullScreen;
	Tooltip next;
	Tooltip directoryTooltip;
	Tooltip captionsTooltip;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		// TOOLTIPS//
		play = new Tooltip("Play (k)");
		pause = new Tooltip("Pause (k)");
		replay = new Tooltip("Replay (k)");
		mute = new Tooltip("Mute (m)");
		unmute = new Tooltip("Unmute (m)");
		settings = new Tooltip("Settings");
		enterFullScreen = new Tooltip("Full screen (f)");
		exitFullScreen = new Tooltip("Exit full screen (f)");
		next = new Tooltip("Next video (SHIFT + N)");
		captionsTooltip = new Tooltip("Subtitles/closed captions (c)");

		captionsButton.setTooltip(captionsTooltip);

		volumeSliderPane.setClip(new Rectangle(60, 17));

		volumeSlider.setTranslateX(-60);

		durationLabel.setTranslateX(-60);
		
		maximizeFile = new File("Images/maximizeFile.png");
		minimizeFile = new File("Images/minimizeFile.png");
		playFile = new File("Images/play.png");
		volumeUpFile = new File("Images/volumeUpFile.png");
		volumeDownFile = new File("Images/volumeDownFile.png");
		volumeMuteFile = new File("Images/volumeMuteFile.png");
		replayFile = new File("Images/replay.png");
		pauseFile = new File("Images/pause.png");
		settingsImageFile = new File("Images/settingsImageFile.png");
		captionsFile = new File("Images/captionsFile.png");
		nextVideoFile = new File("Images/nextVideoFile.png");

		settingsEnterFile = new File("Images/settingsEnterFile.gif");
		settingsExitFile = new File("Images/settingsExitFile.gif");
		
		
		nextVideo = new Image(nextVideoFile.toURI().toString());
		maximize = new Image(maximizeFile.toURI().toString());
		minimize = new Image(minimizeFile.toURI().toString());
		playImage = new Image(playFile.toURI().toString());
		pauseImage = new Image(pauseFile.toURI().toString());
		replayImage = new Image(replayFile.toURI().toString());
		volumeUp = new Image(volumeUpFile.toURI().toString());
		volumeDown = new Image(volumeDownFile.toURI().toString());
		volumeMute = new Image(volumeMuteFile.toURI().toString());
		settingsImage = new Image(settingsImageFile.toURI().toString());
		captionsImage = new Image(captionsFile.toURI().toString());
		
		playLogo.setImage(playImage);
		playButton.setBackground(Background.EMPTY);

		playButton.setTooltip(play);

		nextVideoButton.setBackground(Background.EMPTY);
		nextVideoButton.setTooltip(next);
		nextVideoIcon.setImage(nextVideo);

		playButton.setOnAction((e) -> playButtonClick1());

		fullScreenIcon.setImage(maximize);
		fullScreenButton.setBackground(Background.EMPTY);
		fullScreenButton.setTooltip(enterFullScreen);

		settingsButton.setBackground(Background.EMPTY);
		settingsButton.setTooltip(settings);
		settingsIcon.setImage(settingsImage);

		volumeButton.setBackground(Background.EMPTY);
		volumeButton.setTooltip(mute);
		volumeIcon.setImage(volumeUp);

		captionsButton.setBackground(Background.EMPTY);
		captionsIcon.setImage(captionsImage);
		
		
		volumeSlider.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> volumeSlider.setValueChanging(true));
		volumeSlider.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> volumeSlider.setValueChanging(false));
		
		
		volumeSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue && settingsController.settingsOpen) {
					settingsController.openCloseSettings();
				}

			}

		});
		
		
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				
				mainController.mediaPlayer.setVolume(volumeSlider.getValue() / 100);

				if (volumeSlider.getValue() == 0) {
					volumeIcon.setImage(volumeMute);
					muted = true;
					volumeButton.setTooltip(unmute);
				} else if (volumeSlider.getValue() < 50) {
					volumeIcon.setImage(volumeDown);
					muted = false;
					volumeButton.setTooltip(mute);
				} else {
					volumeIcon.setImage(volumeUp);
					muted = false;
					volumeButton.setTooltip(mute);
				}
				
				
			}



		});
		
		
		durationSlider.addEventFilter(MouseEvent.DRAG_DETECTED, e -> {
			durationSlider.setValueChanging(true);
			
		});
		durationSlider.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> durationSlider.setValueChanging(false));
		
		durationSlider.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if (settingsController.settingsOpen) {
				settingsController.openCloseSettings();
			}
		});
		
		
		
		durationSlider.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				mainController.updateMedia(newValue.doubleValue());
			}

		});
		
		durationSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() { // vaja ära fixida see jama
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				//Utilities.bindCurrentTimeLabel(durationLabel, mainController.mediaPlayer, mainController.media);

				
				if(newValue) { // pause video when user starts seeking
					playLogo.setImage(playImage);
					mainController.mediaPlayer.pause();
					mainController.playing = false;
					playButton.setTooltip(play);
				}
				else {
					
					mainController.mediaPlayer.seek(Duration.seconds(durationSlider.getValue())); // seeks to exact position when user finishes dragging
					
					if (settingsController.settingsOpen) { // close settings pane after user finishes seeking media (if its open)
						settingsController.openCloseSettings();
					}
					
					if(mainController.atEnd) { // if user drags the duration slider to the end turn play button to replay button
						playLogo.setImage(replayImage);
						playButton.setTooltip(replay);
						playButton.setOnAction((e) -> playButtonClick2());
					}
					
					else if (mainController.wasPlaying) { // starts playing the video in the new position when user finishes seeking with the slider
						mainController.mediaPlayer.play();
						mainController.playing = true;
						playLogo.setImage(pauseImage);
						playButton.setTooltip(pause);
					}
				}
			}
		});
		
		// this will all go to FocusTraversalEngine.java
		durationSlider.focusedProperty()
		.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				durationSlider.setStyle("-fx-border-color: transparent;");
			} else {
				mainController.focusNodeTracker = 1;
			}
		});

		playButton.focusedProperty()
		.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				playButton.setStyle("-fx-border-color: transparent;");
			} else {
				mainController.focusNodeTracker = 2;
			}
		});

nextVideoButton.focusedProperty()
		.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				nextVideoButton.setStyle("-fx-border-color: transparent;");
			} else {
				mainController.focusNodeTracker = 3;
			}
		});

volumeButton.focusedProperty()
		.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				volumeButton.setStyle("-fx-border-color: transparent;");
			} else {
				mainController.focusNodeTracker = 4;
			}
		});

volumeSlider.focusedProperty()
		.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

			sliderFocus = newValue;

			if (!newValue) {
				volumeSliderExit();

				volumeSlider.setStyle("-fx-border-color: transparent;");

			} else {
				volumeSliderEnter();
				isExited = true;
				mainController.focusNodeTracker = 5;
			}

		});

settingsButton.focusedProperty()
		.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				settingsButton.setStyle("-fx-border-color: transparent;");
			} else {
				mainController.focusNodeTracker = 6;
			}
		});

fullScreenButton.focusedProperty()
		.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				fullScreenButton.setStyle("-fx-border-color: transparent;");
			} else {
				mainController.focusNodeTracker = 7;
			}
		});
		//////////////////////////////////////////////////////////////////////////
		
	}
	
	public void init(MainController mainController, SettingsController settingsController, AnimationsClass animationsClass) {
		
		this.mainController = mainController;
		
		this.settingsController = settingsController;
		
		this.animationsClass = animationsClass;
	}

	

	public  void playButtonClick1() {
		if (settingsController.settingsOpen) {
			settingsController.openCloseSettings();
		} else {
			playOrPause();
		}
	}
	
	
	public  void playButtonClick2() {
		if (settingsController.settingsOpen) {
			settingsController.openCloseSettings();
		} else {
			replayMedia();
			mainController.seekedToEnd = false;
		}
	}
	
	
	public  void playOrPause() {

		// displayControls();

		if (!mainController.playing) { // plays media
			mainController.mediaPlayer.play();
			mainController.playing = true;

			playLogo.setImage(pauseImage);

			playButton.setTooltip(pause);

		} else { // pauses media
			mainController.mediaPlayer.pause();
			mainController.playing = false;
			playLogo.setImage(playImage);

			playButton.setTooltip(play);

		}

		mainController.wasPlaying = mainController.playing; // updates the value of wasPlaying variable - when this method is called the
								// user really wants to play or pause the video and therefore the previous
								// wasPlaying state no longer needs to be tracked

	}
	
	
	public void replayMedia() {
		mainController.mediaPlayer.seek(Duration.ZERO);
		mainController.mediaPlayer.play();
		mainController.playing = true;
		mainController.atEnd = false;
		playLogo.setImage(pauseImage);
		mainController.seekedToEnd = false;
		playButton.setTooltip(pause);
		playButton.setOnAction((e) -> playButtonClick1());

	}
	
	
	public  void volumeSliderEnter() {
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

	public  void volumeSliderExit() {
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
	
	public void fullScreen() {
		Main.stage.setFullScreen(!Main.stage.isFullScreen());

		if (Main.stage.isFullScreen()) {
			fullScreenIcon.setImage(minimize);
			Main.fullScreen = true;
			fullScreenButton.setTooltip(exitFullScreen);

		} else {
			fullScreenIcon.setImage(maximize);
			Main.fullScreen = false;
			fullScreenButton.setTooltip(enterFullScreen);
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
	
	


	public void mute() {

		if (settingsController.settingsOpen) {
			settingsController.openCloseSettings();
		} else {

			if (!muted) {

				muted = true;
				volumeIcon.setImage(volumeMute);
				mainController.mediaPlayer.setVolume(0);

				volumeValue = volumeSlider.getValue(); //stores the value of the volumeslider before setting it to 0

				volumeButton.setTooltip(unmute);

				volumeSlider.setValue(0);
			} else {
				muted = false;
				volumeIcon.setImage(volumeUp);

				mainController.mediaPlayer.setVolume(volumeValue);

				volumeButton.setTooltip(mute);

				volumeSlider.setValue(volumeValue); // sets volume back to the value it was at before muting
			}
		}

	}
	
	

	public void playNextMedia() {
		if (settingsController.settingsOpen) {
			settingsController.openCloseSettings();
		} else {

		}
	}

	
	public void openCloseCaptions() {
		if (mainController.captionsOpen) {
			// CLOSE CAPTIONS
			mainController.captionsOpen = false;

			ScaleTransition scale = new ScaleTransition(Duration.millis(100), captionLine);
			scale.setFromX(1);
			scale.setToX(0);
			scale.setCycleCount(1);
			scale.setInterpolator(Interpolator.LINEAR);
			scale.play();

		} else {
			// OPEN CAPTIONS
			mainController.captionsOpen = true;

			ScaleTransition scale = new ScaleTransition(Duration.millis(100), captionLine);
			scale.setFromX(0);
			scale.setToX(1);
			scale.setCycleCount(1);
			scale.setInterpolator(Interpolator.LINEAR);
			scale.play();

		}
	}
	
	public void settingsClick() {
		settingsController.openCloseSettings();
	}
	
	
	public void pressFullScreen() {
		if (settingsController.settingsOpen) {
			settingsController.openCloseSettings();
		} else {
			fullScreen();
		}
	}
	

	public void controlBarClick() {
		if (settingsController.settingsOpen) {
			settingsController.openCloseSettings();
		}
	}
	
}
