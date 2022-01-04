package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventDispatcher;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class ControlBarController implements Initializable{

	@FXML
	
	VBox controlBar;
	
	@FXML
	Button fullScreenButton, playButton, volumeButton, settingsButton, nextVideoButton, captionsButton;

	
	@FXML
	ImageView settingsIcon;

	@FXML
	ImageView nextVideoIcon;

	@FXML
	ImageView captionsIcon;

	@FXML
	public  Slider volumeSlider;
	
	@FXML
	public ProgressBar volumeTrack;

	@FXML
	public  Slider durationSlider;
	
	@FXML
	public ProgressBar durationTrack;
	
	@FXML
	StackPane volumeSliderPane, durationPane;
	
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
	
	MainController mainController;
	SettingsController settingsController;
	
	

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

	
	public  boolean muted = false;
	 boolean isExited = true;
	 boolean sliderFocus = false;
	 boolean showingTimeLeft = false;
	 boolean durationSliderHover = false;
	 boolean controlBarOpen = false;

	 
	
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
	
	MouseEventTracker mouseEventTracker;
	
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

		volumeSliderPane.setClip(new Rectangle(60, 38.666666664));

		volumeSlider.setTranslateX(-60);
		volumeTrack.setTranslateX(-60);

		durationLabel.setTranslateX(-60);
		
		controlBar.setTranslateY(50);

		durationLabel.setOnMouseClicked((e) -> toggleDurationLabel());
		
		
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
					settingsController.closeSettings();
				}

			}

		});
		
		
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				// TODO Auto-generated method stub
				
				mainController.mediaPlayer.setVolume(volumeSlider.getValue() / 100);
				
				volumeTrack.setProgress(volumeSlider.getValue() / 100);

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
				settingsController.closeSettings();
			}
		});
		
		
		Platform.runLater(new Runnable() { // this part has to be run later because the slider thumb loads in later than the slider itself

			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				durationSlider.lookup(".thumb").setScaleX(0);
				durationSlider.lookup(".thumb").setScaleY(0);
				
				durationSlider.lookup(".track").setCursor(Cursor.HAND);
				durationSlider.lookup(".thumb").setCursor(Cursor.HAND);
				
				
				durationSlider.setOnMouseEntered((e) -> {
					durationSliderHover = true;
					durationSliderHoverOn();
				});
				
				durationSlider.setOnMouseExited((e) -> {
					durationSliderHover = false;
					if(!e.isPrimaryButtonDown() && !e.isSecondaryButtonDown() && !e.isMiddleButtonDown()) {
						durationSliderHoverOff();
					}
				});
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

				
				if(newValue) { // pause video when user starts seeking
					playLogo.setImage(playImage);
					mainController.mediaPlayer.pause();
					mainController.playing = false;
					playButton.setTooltip(play);
				}
				else {
					
					if(!durationSliderHover) {
						durationSliderHoverOff();
					}
					
					mainController.mediaPlayer.seek(Duration.seconds(durationSlider.getValue())); // seeks to exact position when user finishes dragging
					
					if (settingsController.settingsOpen) { // close settings pane after user finishes seeking media (if its open)
						settingsController.closeSettings();
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

volumeButton.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			if (!newValue) {
				volumeButton.setStyle("-fx-border-color: transparent;");
			} else {
				mainController.focusNodeTracker = 4;
			}
		});

volumeSlider.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {

			sliderFocus = newValue;

			if (!newValue) {
				if(isExited) {
					volumeSliderExit();
				}
				volumeSlider.setStyle("-fx-border-color: transparent;");

			} else {
				if(isExited) {
					volumeSliderEnter();
				}
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

	public void init(MainController mainController, SettingsController settingsController) {
		this.mainController = mainController;
		this.settingsController = settingsController;
		
		mouseEventTracker = new MouseEventTracker(4, mainController, this, settingsController); // creates instance of the MouseEventTracker class which keeps track of when to hide and show the control-bar
		
	}

	public void toggleDurationLabel() {
		if(showingTimeLeft) {
			Utilities.setCurrentTimeLabel(durationLabel, mainController.mediaPlayer, mainController.media);
			showingTimeLeft = false;
		}
		else {
			Utilities.setTimeLeftLabel(durationLabel, mainController.mediaPlayer, mainController.media);
			showingTimeLeft= true;
		}
	}
	
	

	public  void playButtonClick1() {
		if (settingsController.settingsOpen) {
			settingsController.closeSettings();
		} else {
			playOrPause();
		}
	}
	
	
	public  void playButtonClick2() {
		if (settingsController.settingsOpen) {
			settingsController.closeSettings();
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

	public void enterArea() {
		if(isExited && !sliderFocus) {
			volumeSliderEnter();
		}
		isExited = false;
	}

	public void exitArea() {
		if(!sliderFocus && !isExited) {
			volumeSliderExit();
		}
		isExited = true;
	}
	
	public  void volumeSliderEnter() {
		AnimationsClass.volumeSliderHoverOn(volumeSlider, durationLabel, volumeTrack);
	}

	public  void volumeSliderExit() {
		AnimationsClass.volumeSliderHoverOff(volumeSlider, durationLabel, volumeTrack);
	}
	
	public void fullScreen() {
		// gotta move some of this logic to the main class
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

	public void fullScreenButtonHoverOn() {
		AnimationsClass.fullScreenHoverOn(fullScreenIcon);
	}

	public void fullSreenButtonHoverOff() {
		AnimationsClass.fullScreenHoverOff(fullScreenIcon);
	}
	
	
	public void volumeButtonClick() {
		if (settingsController.settingsOpen) {
			settingsController.closeSettings();
		}
		else {
			if(!muted)
				mute();
			else
				unmute();
		}
	}

	public void mute() {
		muted = true;
		volumeIcon.setImage(volumeMute);
		mainController.mediaPlayer.setVolume(0);
		volumeValue = volumeSlider.getValue(); //stores the value of the volumeslider before setting it to 0
		volumeButton.setTooltip(unmute);
		volumeSlider.setValue(0);
	}
	
	public void unmute() {
		muted = false;
		volumeIcon.setImage(volumeUp);
		mainController.mediaPlayer.setVolume(volumeValue);
		volumeButton.setTooltip(mute);
		volumeSlider.setValue(volumeValue); // sets volume back to the value it was at before muting
	}
	
	

	public void playNextMedia() {
		if (settingsController.settingsOpen) {
			settingsController.closeSettings();
		} else {

		}
	}

	
	public void openCaptions() {
		mainController.captionsOpen = true;
		AnimationsClass.openCaptions(captionLine);
	}
	
	public void closeCaptions() {
		mainController.captionsOpen = false;
		AnimationsClass.closeCaptions(captionLine);
	}
	
	public void settingsButtonClick() {
		if(settingsController.settingsOpen)
			settingsController.closeSettings();
		else
			settingsController.openSettings();
	}
	
	
	public void fullScreenButtonClick() {
		if (settingsController.settingsOpen)
			settingsController.closeSettings();
		else
			fullScreen();
	}
	

	public void controlBarClick() {
		if (settingsController.settingsOpen) {
			settingsController.closeSettings();
		}
	}
	
	public void captionsButtonClick() {
		if(!mainController.captionsOpen)
			openCaptions();
		else
			closeCaptions();
	}
	
	public void durationSliderHoverOn(){
		AnimationsClass.durationSliderHoverOn(durationTrack, durationSlider);
	}
	
	
	public void durationSliderHoverOff(){
		AnimationsClass.durationSliderHoverOff(durationTrack, durationSlider);
	}
	
	public void displayControls() {
		AnimationsClass.displayControls(this);
	}
	
	public void hideControls() {
		AnimationsClass.hideControls(this);
	}
	
	public void setControlBarOpen(boolean value) {
		controlBarOpen = value;
	}
	
}
