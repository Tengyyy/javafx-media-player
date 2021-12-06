package application;

import java.io.File;
import java.net.URL;

import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;


import javafx.animation.TranslateTransition;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Background;

import javafx.scene.layout.StackPane;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.media.SubtitleTrack;

import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import javafx.util.Duration;

public class MainController implements Initializable {

	@FXML
	public MediaView mediaView;

	@FXML
	Button menuButton;


	@FXML
	ImageView menuIcon;

	@FXML
	StackPane pane;
	
	@FXML 
	private ControlBarController controlBarController;
	
	@FXML
	private SettingsController settingsController;
	

	
	//private MainController mainController;

	// custom playback speed selection box that will be created if the user selects a custom speed using the slider

	private File file;
	public  Media media;
	public  MediaPlayer mediaPlayer;
	
	
	// Variables to keep track of mediaplayer status:
	 boolean playing = false; // is mediaplayer currently playing
	 boolean wasPlaying = false; // was mediaplayer playing before a seeking action occurred
	public  boolean atEnd = false; // is mediaplayer at the end of the video
	public  boolean seekedToEnd = false; // true = video was seeked to the end; false = video naturally reached the end or the video is still playing
	////////////////////////////////////////////////

	private DoubleProperty mediaViewWidth;
	DoubleProperty mediaViewHeight;

	Image menuImage;

	Image menuCloseImage;

	private File menuFile, menuCloseFile;

	boolean running = false; // media running status
	

	boolean captionsOpen = false;


	// counter to keep track of the current node that has focus (used for focus traversing with tab and shift+tab)
	public  int focusNodeTracker = 0;

	FileChooser fileChooser;
	File selectedFile;

	SubtitleTrack subtitles;


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		
		controlBarController.init(this, settingsController);
		
		settingsController.init(this, controlBarController);

		fileChooser = new FileChooser();
		fileChooser.setTitle("Open video");
		
		
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Videos","*.mp4"));

		file = new File("hey.mp4");

		// declaring media control images
		menuFile = new File("Images/menuFile.png");
		menuCloseFile = new File("Images/menuCloseFile.png");
		
		menuImage = new Image(menuFile.toURI().toString());
		menuCloseImage = new Image(menuCloseFile.toURI().toString());

		// Make mediaView adjust to frame size
		mediaViewWidth = mediaView.fitWidthProperty();
		mediaViewHeight = mediaView.fitHeightProperty();
		mediaViewWidth.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		mediaViewHeight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		mediaView.setPreserveRatio(true);



		pane.setStyle("-fx-background-color: rgb(0,0,0)");


		menuButton.setBackground(Background.EMPTY);
		menuIcon.setImage(menuImage);


		mediaView.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					if (!newValue) {
						mediaView.setStyle("-fx-border-color: transparent;");
					} else {
						focusNodeTracker = 0;
					}
				});

		menuButton.focusedProperty()
				.addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
					if (!newValue) {
						menuButton.setStyle("-fx-border-color: transparent;");
					} else {
						focusNodeTracker = 8;
					}
				});

		createMediaPlayer(file);

	}

	public void mediaClick() {

		// Clicking on the mediaview node will close the settings tab if its open or
		// otherwise play/pause/replay the video

		if (settingsController.settingsOpen) {
			settingsController.openCloseSettings();
		} else {
			if (atEnd) {
				controlBarController.replayMedia();
			} else {
				controlBarController.playOrPause();
			}
		}

		mediaView.requestFocus();
	}


	public void displayControls() {

		if (playing) {
			// control bar slide animation - display
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.2));
			slide.setNode(controlBarController.controlBar);

			slide.setToY(0);

			slide.play();
			controlBarController.controlBar.setTranslateY(-50);
		} else {
			// control bar slide animation - hide
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.4));
			slide.setNode(controlBarController.controlBar);

			slide.setToY(50);
			slide.play();
			controlBarController.controlBar.setTranslateY(0);
		}

	}
	

	public void traverseFocusForwards() {

		switch (focusNodeTracker) {

		// mediaView
		case 0: {

			mediaView.setStyle("-fx-border-color: blue;");
		}
			break;

		// durationSlider
		case 1: {
			mediaView.setStyle("-fx-border-color: transparent;");
			controlBarController.durationSlider.setStyle("-fx-border-color: blue;");
		}
			break;

		// playButton
		case 2: {
			controlBarController.durationSlider.setStyle("-fx-border-color: transparent;");
			controlBarController.playButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// nextVideoButton
		case 3: {
			controlBarController.playButton.setStyle("-fx-border-color: transparent;");
			controlBarController.nextVideoButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// muteButton
		case 4: {
			controlBarController.nextVideoButton.setStyle("-fx-border-color: transparent;");
			controlBarController.volumeButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// volumeSlider
		case 5: {
			controlBarController.volumeButton.setStyle("-fx-border-color: transparent;");
			controlBarController.volumeSlider.setStyle("-fx-border-color: blue;");
		}
			break;

		// settingsButton
		case 6: {
			controlBarController.volumeSlider.setStyle("-fx-border-color: transparent;");
			controlBarController.settingsButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// fullscreenButton
		case 7: {
			controlBarController.settingsButton.setStyle("-fx-border-color: transparent;");
			controlBarController.fullScreenButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// menuButton
		case 8: {
			controlBarController.fullScreenButton.setStyle("-fx-border-color: transparent;");
		}
			break;

		default:
			break;

		}

	}

	public void traverseFocusBackwards() {

		switch (focusNodeTracker) {

		// mediaView
		case 0: {
			controlBarController.durationSlider.setStyle("-fx-border-color: transparent;");
			mediaView.setStyle("-fx-border-color: blue;");
		}
			break;

		// durationSlider
		case 1: {
			controlBarController.playButton.setStyle("-fx-border-color: transparent;");
			controlBarController.durationSlider.setStyle("-fx-border-color: blue;");
		}
			break;

		// playButton
		case 2: {
			controlBarController.nextVideoButton.setStyle("-fx-border-color: transparent;");
			controlBarController.playButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// nextVideoButton
		case 3: {
			controlBarController.volumeButton.setStyle("-fx-border-color: transparent;");
			controlBarController.nextVideoButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// muteButton
		case 4: {
			controlBarController.volumeSlider.setStyle("-fx-border-color: transparent;");
			controlBarController.volumeButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// volumeSlider
		case 5: {
			controlBarController.settingsButton.setStyle("-fx-border-color: transparent;");
			controlBarController.volumeSlider.setStyle("-fx-border-color: blue;");
		}
			break;

		// settingsButton
		case 6: {
			controlBarController.fullScreenButton.setStyle("-fx-border-color: transparent;");
			controlBarController.settingsButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// fullscreenButton
		case 7: {
			controlBarController.fullScreenButton.setStyle("-fx-border-color: blue;");
		}
			break;

		// menuButton
		case 8: {
			mediaView.setStyle("-fx-border-color: transparent;");
		}
			break;

		default:
			break;

		}

	}

	public void updateMedia(double newValue) {

		Utilities.bindCurrentTimeLabel(controlBarController.durationLabel, mediaPlayer, media);

		if (atEnd) {
			atEnd = false;
			seekedToEnd = false;
						
			if (wasPlaying) {
				if (!controlBarController.durationSlider.isValueChanging()) {
					controlBarController.playLogo.setImage(controlBarController.pauseImage);

					playing = true;
					mediaPlayer.play();
					controlBarController.playButton.setTooltip(controlBarController.pause);

				}
			}
			else {
				controlBarController.playLogo.setImage(controlBarController.playImage);
				playing = false;
				controlBarController.playButton.setTooltip(controlBarController.play);
			}
			controlBarController.playButton.setOnAction((e) -> {
				controlBarController.playButtonClick1();
			});
		} else if (newValue >= controlBarController.durationSlider.getMax()) {

			if (controlBarController.durationSlider.isValueChanging()) {
				seekedToEnd = true;
			}

			atEnd = true;
			playing = false;
			mediaPlayer.pause();
			if(!controlBarController.durationSlider.isValueChanging()) {
				
				endMedia();
				
			}
		}
		
			if (Math.abs(mediaPlayer.getCurrentTime().toSeconds() - newValue) > 0.5) {
				mediaPlayer.seek(Duration.seconds(newValue));
		}
	}

	public void endMedia() {

		if ((!settingsController.shuffleOn && !settingsController.loopOn && !settingsController.autoplayOn) || (settingsController.loopOn && seekedToEnd)) {
			controlBarController.durationSlider.setValue(controlBarController.durationSlider.getMax());

			controlBarController.durationLabel.textProperty().unbind();
			controlBarController.durationLabel.setText(Utilities.getTime(new Duration(controlBarController.durationSlider.getMax() * 1000)) + "/" + Utilities.getTime(media.getDuration()));
				

			controlBarController.playLogo.setImage(new Image(controlBarController.replayFile.toURI().toString()));
			controlBarController.playButton.setTooltip(controlBarController.replay);
			controlBarController.playButton.setOnAction((e) -> controlBarController.playButtonClick2());
			
			
			
		} else if (settingsController.loopOn && !seekedToEnd) {
			// restart current video

			
			mediaPlayer.stop();
			
		} else if (settingsController.shuffleOn) {
		

		} else if (settingsController.autoplayOn) {
			// play next song in queue/directory
		}

	}


	public void openVideoChooser() {
		selectedFile = fileChooser.showOpenDialog(Main.stage);

		if (selectedFile != null) {
			settingsController.videoNameLabel.setText(selectedFile.getName());
			
			mediaPlayer.dispose();
			
			atEnd = false;
			seekedToEnd = false;
			playing = false;
			wasPlaying = false;
			
			createMediaPlayer(selectedFile);

		}

	}
	
	public void openMenu() {
		System.out.println("OPENING MENU");
	}
	
	public SettingsController getSettingsController() {
		return settingsController;
	}
	
	public ControlBarController getControlBarController() {
		return controlBarController;
	}
	




	public void createMediaPlayer(File file) {

		controlBarController.durationSlider.setValue(0);

		media = new Media(file.toURI().toString());
		mediaPlayer = new MediaPlayer(media);
		mediaView.setMediaPlayer(mediaPlayer);

		mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
			@Override
			public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {
				Utilities.bindCurrentTimeLabel(controlBarController.durationLabel, mediaPlayer, media);
				
				if (!controlBarController.durationSlider.isValueChanging()) {
					controlBarController.durationSlider.setValue(newTime.toSeconds());
				}

			}
		});


		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				mediaPlayer.setVolume(controlBarController.volumeSlider.getValue() / 100);

				controlBarController.playOrPause();

				controlBarController.durationSlider.setMax(Math.floor(media.getDuration().toSeconds()));

				Utilities.bindCurrentTimeLabel(controlBarController.durationLabel, mediaPlayer, media);

				TimerTask setRate = new TimerTask() {

					@Override
					public void run() {

						switch (settingsController.playbackSpeedTracker) {
						case 0:
							mediaPlayer.setRate(settingsController.formattedValue);
							break;
						case 1:
							mediaPlayer.setRate(0.25);
							break;
						case 2:
							mediaPlayer.setRate(0.5);
							break;
						case 3:
							mediaPlayer.setRate(0.75);
							break;
						case 4:
							mediaPlayer.setRate(1);
							break;
						case 5:
							mediaPlayer.setRate(1.25);
							break;
						case 6:
							mediaPlayer.setRate(1.5);
							break;
						case 7:
							mediaPlayer.setRate(1.75);
							break;
						case 8:
							mediaPlayer.setRate(2);
							break;
						default:
							break;
						}
					}

				};

				Timer timer = new Timer();

				// this is mega stupid but it works
				timer.schedule(setRate, 100);
			}

		});

	}




}