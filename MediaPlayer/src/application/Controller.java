package application;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.EnumSet;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
import javafx.scene.media.SubtitleTrack;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;



public class Controller implements Initializable {

	@FXML
	public MediaView mediaView;

	@FXML
	VBox controlBar, settingsHome, playbackSpeedPage, customSpeedBox, playbackOptionsVBox, menuVBox;

	@FXML
	HBox playbackSpeedBox, playbackOptionsBox, videoBox, durationSliderBox, playbackSpeedTitle, playbackSpeed1, playbackSpeed2, playbackSpeed3, playbackSpeed4, playbackSpeed5, playbackSpeed6, playbackSpeed7, playbackSpeed8, customSpeedTitle, shuffleBox, loopBox, autoplayBox, playbackOptionsTitle;

	@FXML
	Button fullScreenButton, playButton, volumeButton, settingsButton, nextVideoButton, captionsButton, menuButton, menuCloseButton;
	
	
	@FXML
	JFXButton directoryChooserButton;
	
	@FXML
	ImageView playLogo, fullScreenIcon, volumeIcon, settingsIcon, nextVideoIcon, captionsIcon, menuIcon, menuCloseIcon;

	@FXML
	StackPane pane, settingsPane, bufferPane, customSpeedBuffer, customSpeedPane, playbackOptionsBuffer, playbackOptionsPane, menuPane;


	@FXML
	Pane playPane, settingsBackgroundPane;

	@FXML
	Slider volumeSlider, durationSlider, customSpeedSlider;
	
	@FXML
	ProgressBar customSpeedTrack;


	@FXML
	FlowPane volumeSliderPane;

	@FXML
	Label durationLabel, playbackValueLabel, videoNameLabel, playbackOptionsArrow, playbackSpeedArrow, playbackSpeedTitleLabel, playbackSpeedCustom, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, customSpeedArrow, customSpeedTitleLabel, customSpeedLabel, playbackOptionsTitleArrow, playbackOptionsTitleText, shuffleLabel, loopLabel, autoplayLabel;

	
	@FXML
	ScrollPane playbackSpeedScroll, menuScroll;
	
	@FXML
	JFXToggleButton shuffleSwitch, loopSwitch, autoplaySwitch;
	
	@FXML
	Line captionLine;
	
	//TODO: Create custom playback speed selector inside the scrollpane using these objects.
	HBox playbackCustom;
	Label playbackCustomCheck;
	Label playbackCustomText;

	private File file;
	Media media;
	MediaPlayer mediaPlayer;
	
	
	//TODO: Continue work on playback options menu
	boolean shuffleOn = false;
	boolean loopOn = false;
	boolean autoplayOn = false;
	
	
	
	boolean playing = false;
	boolean wasPlaying = false;

	boolean tempBool = false;

	boolean atEnd = false;
	
	boolean seekedToEnd = false;

	private DoubleProperty mediaViewWidth;
	private DoubleProperty mediaViewHeight;

	Image maximize, minimize, volumeUp, volumeDown, volumeMute, settingsEnter, settingsExit, settingsImage, rightArrow, nextVideo, leftArrow, check, captionsImage, menuImage, menuCloseImage;

	double volumeValue;
	
	double formattedValue;
	double formattedValue2;
	DecimalFormat df;

	private Image start;

	private File maximizeFile, minimizeFile, playFile, pauseFile, startFile, volumeUpFile, volumeDownFile,
			volumeMuteFile, pauseImageFile, settingsEnterFile, settingsExitFile, settingsImageFile,
			rightArrowFile, nextVideoFile, leftArrowFile, checkFile, captionsFile, menuFile, menuCloseFile;
	
	File replayFile;

	Timeline fullscreenTimeline;

	Timeline volume;

	boolean muted = false;

	boolean isExited = false;

	boolean settingsOpen = false;
	
	boolean playbackSpeedOpen = false;
	
	boolean playbackOptionsOpen = false;
	
	boolean customSpeedOpen = false;
	
	boolean customSpeedChanged = false;
	
	boolean captionsOpen = false;


	boolean sliderFocus = false;

	boolean running = false; // media running status

	int videoLength;
	int currLength = 0;
	
	int focusNodeTracker = 0;
	
	int playbackSpeedTracker = 4;

	Timer durationTimer;
	TimerTask durationTimerTask;

	Tooltip play;
	Tooltip pause;
	Tooltip replay;
	
	Tooltip mute;
	Tooltip unmute;

	Tooltip settings;
	
	Tooltip enterFullScreen;
	Tooltip exitFullScreen;
	
	Tooltip next;
	
	//TODO: Finish creating these tooltips.

	
	Tooltip directoryTooltip;
	
	Tooltip captionsTooltip;
	
	
	FileChooser fileChooser;
	File selectedFile;
	
	DirectoryChooser directoryChooser;
	File selectedDirectory;
	
	
	SubtitleTrack subtitles;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		fileChooser = new FileChooser();
		
		
		directoryChooser = new DirectoryChooser();
		
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
		
		leftArrowFile = new File("src/application/leftArrow.png");
		
		checkFile = new File("src/application/check.png");
		
		nextVideoFile = new File("src/application/nextMedia.png");
		
		captionsFile = new File("src/application/captions.png");

		menuFile = new File("src/application/menu.png");

		menuCloseFile = new File("src/application/menuClose.png");

		
		
		nextVideo = new Image(nextVideoFile.toURI().toString());

		maximize = new Image(maximizeFile.toURI().toString());
		minimize = new Image(minimizeFile.toURI().toString());
		start = new Image(startFile.toURI().toString());

		volumeUp = new Image(volumeUpFile.toURI().toString());
		volumeDown = new Image(volumeDownFile.toURI().toString());
		volumeMute = new Image(volumeMuteFile.toURI().toString());

		rightArrow = new Image(rightArrowFile.toURI().toString());
		leftArrow = new Image(leftArrowFile.toURI().toString());
		
		check = new Image(checkFile.toURI().toString());

		settingsImage = new Image(settingsImageFile.toURI().toString());

		captionsImage = new Image(captionsFile.toURI().toString());

		
		menuImage = new Image(menuFile.toURI().toString());

		menuCloseImage = new Image(menuCloseFile.toURI().toString());


		
		// Make mediaView adjust to frame size
		mediaViewWidth = mediaView.fitWidthProperty();
		mediaViewHeight = mediaView.fitHeightProperty();
		mediaViewWidth.bind(Bindings.selectDouble(mediaView.sceneProperty(), "width"));
		mediaViewHeight.bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		mediaView.setPreserveRatio(true);
		
		
		menuPane.prefHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));
		
		menuScroll.prefHeightProperty().bind(Bindings.selectDouble(mediaView.sceneProperty(), "height"));



		mediaView.setMediaPlayer(mediaPlayer);
		playing = false;

		pane.setStyle("-fx-background-color: rgb(0,0,0)");

		bufferPane.setBackground(Background.EMPTY);
		settingsPane.setBackground(Background.EMPTY);
		
		settingsPane.setStyle("-fx-background-color: rgba(35,35,35,0.8)");

		
		//playbackSpeedPage.setStyle("-fx-background-color: rgba(35,35,35,0.8)");
		
		
		playbackSpeedScroll.setBackground(Background.EMPTY);
		
		
		//menuSearchBox.setBackground(Background.EMPTY);

		
		playbackSpeedScroll.setStyle("-fx-background-color: rgba(35,35,35,0.8)");
		
		customSpeedPane.setStyle("-fx-background-color: rgba(35,35,35,0.8)");
		
		playbackOptionsPane.setStyle("-fx-background-color: rgba(35,35,35,0.8)");
		
		//settingsBackgroundPane.setStyle("-fx-background-color: rgba(35,35,35,0.8)");



		playLogo.setImage(start);
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
		
		menuButton.setBackground(Background.EMPTY);
		menuIcon.setImage(menuImage);
		
		//menuCloseButton.setBackground(Background.EMPTY);
		menuCloseIcon.setImage(menuCloseImage);
		

		playbackValueLabel.setGraphic(new ImageView(rightArrow));

		videoNameLabel.setGraphic(new ImageView(rightArrow));

		playbackOptionsArrow.setGraphic(new ImageView(rightArrow));
		
		playbackSpeedArrow.setGraphic(new ImageView(leftArrow));
		
		playbackOptionsTitleArrow.setGraphic(new ImageView(leftArrow));
		
		checkBox4.setGraphic(new ImageView(check));
		
		customSpeedArrow.setGraphic(new ImageView(leftArrow));

		
		
		volumeSlider.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> volumeSlider.setValueChanging(true));
		volumeSlider.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> volumeSlider.setValueChanging(false));
		
		
		playbackOptionsBox.setOnMouseClicked((e) -> {
			openPlaybackOptions();
		});
		
		
		playbackOptionsTitle.setOnMouseClicked((e) -> {
			closePlaybackOptions();
		});
		
		
		videoBox.setOnMouseClicked((e) -> {
			openVideoChooser();
		});
		
		
		
		//shuffleBox.addEventFilter(null, null)
		
		
		shuffleBox.setOnMouseClicked((e) -> {
			shuffleSwitch.fire();
			
			//shuffleSwitch.requestFocus();
			
			if(loopSwitch.isSelected()) { // turns other switches off if this one is toggled on. makes it so only one switch can be selected
				loopSwitch.fire();
			}
			
			if(autoplaySwitch.isSelected()) {
				autoplaySwitch.fire();
			}
			
		});
		loopBox.setOnMouseClicked((e) -> {
			loopSwitch.fire();
			
			//loopSwitch.requestFocus();
			
			if(shuffleSwitch.isSelected()) {
				shuffleSwitch.fire();
			}
			
			if(autoplaySwitch.isSelected()) {
				autoplaySwitch.fire();
			}
		});
		autoplayBox.setOnMouseClicked((e) -> {
			autoplaySwitch.fire();
			
			//autoplaySwitch.requestFocus();
			
			if(loopSwitch.isSelected()) {
				loopSwitch.fire();
			}
			
			if(shuffleSwitch.isSelected()) {
				shuffleSwitch.fire();
			}
		});
		
		
		shuffleSwitch.setOnMouseClicked((e) -> { // in addition to the hbox, also add same logic to the switch itself (minus the .fire() part cause in that case the switch would toggle twice in a row) 
			//shuffleSwitch.fire();
			
			if(loopSwitch.isSelected()) { 
				loopSwitch.fire();
			}
			
			if(autoplaySwitch.isSelected()) {
				autoplaySwitch.fire();
			}
			
		});
		loopSwitch.setOnMouseClicked((e) -> {
			//loopSwitch.fire();
			
			if(shuffleSwitch.isSelected()) {
				shuffleSwitch.fire();
			}
			
			if(autoplaySwitch.isSelected()) {
				autoplaySwitch.fire();
			}
		});
		autoplaySwitch.setOnMouseClicked((e) -> {
			//autoplaySwitch.fire();
			
			if(loopSwitch.isSelected()) {
				loopSwitch.fire();
			}
			
			if(shuffleSwitch.isSelected()) {
				shuffleSwitch.fire();
			}
		});
		
		shuffleSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) { // ON
					shuffleOn = true;
				}
				else { // OFF
					shuffleOn = false;
				}
			}
			
		});
		
		
		
		loopSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) { // ON
					loopOn = true;
				}
				else { // OFF
					loopOn = false;
				}
			}
			
		});
		
		
		
		autoplaySwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(newValue) { // ON
					autoplayOn = true;
				}
				else { // OFF
					autoplayOn = false;
				}
			}
			
		});

		
		


		
		volumeSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if(!newValue && settingsOpen) {
					openCloseSettings();
				}
				
			}
			
		});


		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				mediaPlayer.setVolume(volumeSlider.getValue() / 100);


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
		
		customSpeedTrack.setProgress(0.75 /1.75);

		
		customSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				customSpeedChanged = true;

				formattedValue = Math.floor(newValue.doubleValue()* 20) / 20; // floors the new slider value to 2 decimal points with the last decimal being only 5 or 0.
				
				
				
				mediaPlayer.setRate(formattedValue);
				
				double progress = (newValue.doubleValue() - 0.25) * 1/1.75; // adjust the slider scale ( 0.25 - 2 ) to match with the progress bar scale ( 0 - 1 )

				customSpeedTrack.setProgress(progress);
				
				df = new DecimalFormat("#.##"); // makes it so that only the minimum amount of digits wil be displayed, eg. 2 not 2.00
				
				customSpeedLabel.setText(df.format(formattedValue) + "x");
				
				if(playbackCustom == null && customSpeedChanged) {
					
					switch(df.format(formattedValue)) {
					case "0.25": {
						playbackSpeedTracker = 1;
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox1.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(0);

					}
					break;
					case "0.5": {
						playbackSpeedTracker = 2;

						checkBox1.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox2.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(148/playbackSpeedPage.getHeight());
					}
					break;
					case "0.75": {
						playbackSpeedTracker = 3;
						
						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox3.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(198/playbackSpeedPage.getHeight());
					}
					break;
					case "1": {
						playbackSpeedTracker = 4;
						
						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox4.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(248/playbackSpeedPage.getHeight());
					}
					break;
					case "1.25": {
						playbackSpeedTracker = 5;
						
						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox5.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(298/playbackSpeedPage.getHeight());
					}
					break;
					case "1.5": {
						playbackSpeedTracker = 6;
						
						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox6.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(348/playbackSpeedPage.getHeight());
					}
					break;
					case "1.75": {
						playbackSpeedTracker = 7;
						
						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox7.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(398/playbackSpeedPage.getHeight());
					}
					break;
					case "2": {
						playbackSpeedTracker = 8;
						
						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(448/playbackSpeedPage.getHeight());
					}
					break;
					default: {

						formattedValue2 = formattedValue;
						playbackCustom = new HBox();
						playbackCustomCheck = new Label();
						playbackCustomText = new Label();
						
						playbackCustom.setPrefWidth(235);
						playbackCustom.setPrefHeight(50);
						playbackCustom.setPadding(new Insets(0, 10, 0, 10));
						
						playbackCustomCheck.setPrefHeight(50);
						playbackCustomCheck.setPrefWidth(29);
						playbackCustomCheck.setPadding(new Insets(0, 5, 0, 0));
						playbackCustomCheck.setGraphic(new ImageView(check));
						
						switch(playbackSpeedTracker) {
						case 1: checkBox1.setGraphic(null);
						break;
						case 2: checkBox2.setGraphic(null);
						break;
						case 3: checkBox3.setGraphic(null);
						break;
						case 4: checkBox4.setGraphic(null);
						break;
						case 5: checkBox5.setGraphic(null);
						break;
						case 6: checkBox6.setGraphic(null);
						break;
						case 7: checkBox7.setGraphic(null);
						break;
						case 8: checkBox8.setGraphic(null);
						break;
						default: break;
					}
						
						playbackSpeedTracker = 0;
						
						playbackCustom.setOnMouseClicked((e) -> {
							
							switch(playbackSpeedTracker) {
								case 1: checkBox1.setGraphic(null);
								break;
								case 2: checkBox2.setGraphic(null);
								break;
								case 3: checkBox3.setGraphic(null);
								break;
								case 4: checkBox4.setGraphic(null);
								break;
								case 5: checkBox5.setGraphic(null);
								break;
								case 6: checkBox6.setGraphic(null);
								break;
								case 7: checkBox7.setGraphic(null);
								break;
								case 8: checkBox8.setGraphic(null);
								break;
								default: break;
							}
							
							playbackSpeedTracker = 0;
							playbackCustomCheck.setGraphic(new ImageView(check));
							mediaPlayer.setRate(formattedValue2);
							playbackValueLabel.setText(df.format(formattedValue2));
						});
						
						playbackCustom.setOnMouseEntered((e) -> {
							hoverEffectOn(playbackCustom);
						});
						
						playbackCustom.setOnMouseExited((e) -> {
							hoverEffectOff(playbackCustom);
						});
						
						playbackCustomText.setTextFill(Color.WHITE);
						playbackCustomText.setFont(new Font(15));
						playbackCustomText.setPrefHeight(50);
						playbackCustomText.setPrefWidth(186);
						playbackCustomText.setText("Custom " + "("+df.format(formattedValue2)+")");
						
						playbackValueLabel.setText(df.format(formattedValue2));
						
						playbackSpeedScroll.setVvalue(0);
						
						
						
						playbackCustom.getChildren().addAll(playbackCustomCheck, playbackCustomText);
						playbackSpeedPage.getChildren().add(2, playbackCustom);
						
					}
					break;
				}

				}
				else if(playbackCustom != null){
					
					switch(df.format(formattedValue)) {
						case "0.25": {
							playbackCustomCheck.setGraphic(null);
							playbackSpeedTracker = 1;
							
							checkBox2.setGraphic(null);
							checkBox3.setGraphic(null);
							checkBox4.setGraphic(null);
							checkBox5.setGraphic(null);
							checkBox6.setGraphic(null);
							checkBox7.setGraphic(null);
							checkBox8.setGraphic(null);
							checkBox1.setGraphic(new ImageView(check));
							playbackSpeedScroll.setVvalue(148/playbackSpeedPage.getHeight());
							playbackValueLabel.setText(df.format(formattedValue));

						}
						break;
						case "0.5": {
							playbackCustomCheck.setGraphic(null);
							playbackSpeedTracker = 2;
							
							checkBox1.setGraphic(null);
							checkBox3.setGraphic(null);
							checkBox4.setGraphic(null);
							checkBox5.setGraphic(null);
							checkBox6.setGraphic(null);
							checkBox7.setGraphic(null);
							checkBox8.setGraphic(null);
							checkBox2.setGraphic(new ImageView(check));
							playbackSpeedScroll.setVvalue(198/playbackSpeedPage.getHeight());
							playbackValueLabel.setText(df.format(formattedValue));
						}
						break;
						case "0.75": {
							playbackCustomCheck.setGraphic(null);
							playbackSpeedTracker = 3;
							
							checkBox1.setGraphic(null);
							checkBox2.setGraphic(null);
							checkBox4.setGraphic(null);
							checkBox5.setGraphic(null);
							checkBox6.setGraphic(null);
							checkBox7.setGraphic(null);
							checkBox8.setGraphic(null);
							checkBox3.setGraphic(new ImageView(check));
							playbackSpeedScroll.setVvalue(248/playbackSpeedPage.getHeight());
							playbackValueLabel.setText(df.format(formattedValue));
						}
						break;
						case "1": {
							playbackCustomCheck.setGraphic(null);
							playbackSpeedTracker = 4;
							
							checkBox1.setGraphic(null);
							checkBox2.setGraphic(null);
							checkBox3.setGraphic(null);
							checkBox5.setGraphic(null);
							checkBox6.setGraphic(null);
							checkBox7.setGraphic(null);
							checkBox8.setGraphic(null);
							checkBox4.setGraphic(new ImageView(check));
							playbackSpeedScroll.setVvalue(298/playbackSpeedPage.getHeight());
							playbackValueLabel.setText(df.format(formattedValue));
						}
						break;
						case "1.25": {
							playbackCustomCheck.setGraphic(null);
							playbackSpeedTracker = 5;
							
							checkBox1.setGraphic(null);
							checkBox2.setGraphic(null);
							checkBox3.setGraphic(null);
							checkBox4.setGraphic(null);
							checkBox6.setGraphic(null);
							checkBox7.setGraphic(null);
							checkBox8.setGraphic(null);
							checkBox5.setGraphic(new ImageView(check));
							playbackSpeedScroll.setVvalue(348/playbackSpeedPage.getHeight());
							playbackValueLabel.setText(df.format(formattedValue));
						}
						break;
						case "1.5": {
							playbackCustomCheck.setGraphic(null);
							playbackSpeedTracker = 6;
							
							checkBox1.setGraphic(null);
							checkBox2.setGraphic(null);
							checkBox3.setGraphic(null);
							checkBox4.setGraphic(null);
							checkBox5.setGraphic(null);
							checkBox7.setGraphic(null);
							checkBox8.setGraphic(null);
							checkBox6.setGraphic(new ImageView(check));
							playbackSpeedScroll.setVvalue(398/playbackSpeedPage.getHeight());
							playbackValueLabel.setText(df.format(formattedValue));
						}
						break;
						case "1.75": {
							playbackCustomCheck.setGraphic(null);
							playbackSpeedTracker = 7;
							
							checkBox1.setGraphic(null);
							checkBox2.setGraphic(null);
							checkBox3.setGraphic(null);
							checkBox4.setGraphic(null);
							checkBox5.setGraphic(null);
							checkBox6.setGraphic(null);
							checkBox8.setGraphic(null);
							checkBox7.setGraphic(new ImageView(check));
							playbackSpeedScroll.setVvalue(448/playbackSpeedPage.getHeight());
							playbackValueLabel.setText(df.format(formattedValue));
						}
						break;
						case "2": {
							playbackCustomCheck.setGraphic(null);
							playbackSpeedTracker = 8;
							
							checkBox1.setGraphic(null);
							checkBox2.setGraphic(null);
							checkBox3.setGraphic(null);
							checkBox4.setGraphic(null);
							checkBox5.setGraphic(null);
							checkBox6.setGraphic(null);
							checkBox7.setGraphic(null);
							checkBox8.setGraphic(new ImageView(check));
							playbackSpeedScroll.setVvalue(498/playbackSpeedPage.getHeight());
							playbackValueLabel.setText(df.format(formattedValue));
						}
						break;
						default: {
							
							formattedValue2 = formattedValue;

							
							checkBox1.setGraphic(null);
							checkBox2.setGraphic(null);
							checkBox3.setGraphic(null);
							checkBox4.setGraphic(null);
							checkBox5.setGraphic(null);
							checkBox6.setGraphic(null);
							checkBox7.setGraphic(null);
							checkBox8.setGraphic(null);
							
							playbackCustomCheck.setGraphic(new ImageView(check));
							playbackSpeedTracker = 0;
							playbackCustomText.setText("Custom " + "("+df.format(formattedValue2)+")");
							playbackValueLabel.setText(df.format(formattedValue2));
							
							playbackSpeedScroll.setVvalue(0);
						}
						break;
					}
					
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
		
		durationSlider.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
			if(settingsOpen) {
				openCloseSettings();
			}
		});

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
				
				if(!newValue) {
						if(settingsOpen) {
							openCloseSettings();
						}

				}
				
				if (wasPlaying) {

					if (!newValue && tempBool && !atEnd) {
						mediaPlayer.play();
						playing = true;
						playLogo.setImage(new Image(pauseImageFile.toURI().toString()));
						tempBool = false;
						playButton.setTooltip(pause);
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

						playButton.setTooltip(play);						
						// mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));

					} else if (!newValue && !atEnd) {
						mediaPlayer.play();
						playing = true;
						playLogo.setImage(new Image(playFile.toURI().toString()));
						playButton.setTooltip(pause);
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

		
		
		//TODO: Add focuslisteners for all 9 traversable nodes

		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {

				durationSlider.setMax(media.getDuration().toSeconds());

				bindCurrentTimeLabel();

				//dont know why this works
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
					System.out.println("2");
					
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

		videoBox.setOnMouseEntered((e) -> {
			hoverEffectOn(videoBox);
		});
		videoBox.setOnMouseExited((e) -> {
			hoverEffectOff(videoBox);
		});
		
		// On-hover effect for playback speed items
		//////////////////////////////////////////////////

		
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
		
		
		
		
		
		shuffleBox.setOnMouseEntered((e) -> {
			hoverEffectOn(shuffleBox);
		});
		
		shuffleBox.setOnMouseExited((e) -> {
			hoverEffectOff(shuffleBox);
		});
		
		loopBox.setOnMouseEntered((e) -> {
			hoverEffectOn(loopBox);
		});
		
		loopBox.setOnMouseExited((e) -> {
			hoverEffectOff(loopBox);
		});
		
		autoplayBox.setOnMouseEntered((e) -> {
			hoverEffectOn(autoplayBox);
		});
		
		autoplayBox.setOnMouseExited((e) -> {
			hoverEffectOff(autoplayBox);
		});
		
		
		

		settingsBackgroundPane.setPickOnBounds(false);
		//////////////////////////////////////////////////
		
		bufferPane.prefWidthProperty().bind(settingsBackgroundPane.widthProperty());
		bufferPane.prefHeightProperty().bind(settingsBackgroundPane.heightProperty());
		
		//bufferPane.prefHeightProperty().bind(Bindings.max(settingsBackgroundPane.heightProperty().subtract(50), 170));
		
		customSpeedBuffer.prefHeightProperty().bind(settingsBackgroundPane.heightProperty());
		customSpeedBuffer.prefWidthProperty().bind(settingsBackgroundPane.widthProperty());

		
		if(playbackCustom != null) {
			playbackSpeedScroll.prefHeightProperty().bind(Bindings.min(537, Bindings.subtract(mediaViewHeight, 100)));
		}
		else {
			playbackSpeedScroll.prefHeightProperty().bind(Bindings.min(487, Bindings.subtract(mediaViewHeight, 100)));
		}
		
		playbackSpeedScroll.translateYProperty().bind(Bindings.subtract(settingsBackgroundPane.heightProperty(), playbackSpeedScroll.heightProperty()));
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				
				playbackOptionsBuffer.setTranslateX(settingsBackgroundPane.getWidth() + 1);
				playbackSpeedScroll.setTranslateX(settingsBackgroundPane.getWidth() + 1);
				customSpeedBuffer.setTranslateX(settingsBackgroundPane.getWidth() + 1);
				
			}
			
		});
		playbackOptionsBuffer.translateYProperty().bind(Bindings.subtract(settingsBackgroundPane.heightProperty(), playbackOptionsBuffer.heightProperty()));
		
		//customSpeedBuffer.translateYProperty().bind(Bindings.subtract(customSpeedBuffer.heightProperty(), playbackOptionsBuffer.heightProperty()));
		
		//bufferPane.translateYProperty().bind(Bindings.subtract(bufferPane.heightProperty(), playbackOptionsBuffer.heightProperty()));

		bufferPane.setTranslateY(170);
		
		// Clipping for the settings pane
		Rectangle rectangle = new Rectangle(settingsBackgroundPane.getWidth(), settingsBackgroundPane.getHeight());
		rectangle.widthProperty().bind(settingsBackgroundPane.widthProperty());
		rectangle.heightProperty().bind(settingsBackgroundPane.heightProperty());
		settingsBackgroundPane.setClip(rectangle);


		
		/////////////////////////////////
		// mouse listeners for playback speed
		
		playbackSpeed1.setOnMouseClicked((e) -> {
			
			switch(playbackSpeedTracker) {
			
				case 0: {
					if(playbackCustomCheck != null) {
						playbackCustomCheck.setGraphic(null);
					}
				}
				break;
				case 2: checkBox2.setGraphic(null);
				break;
				case 3: checkBox3.setGraphic(null);
				break;
				case 4: checkBox4.setGraphic(null);
				break;
				case 5: checkBox5.setGraphic(null);
				break;
				case 6: checkBox6.setGraphic(null);
				break;
				case 7: checkBox7.setGraphic(null);
				break;
				case 8: checkBox8.setGraphic(null);
				break;
				default: break;
			}
			
			playbackSpeedTracker = 1;
			checkBox1.setGraphic(new ImageView(check));
			
			mediaPlayer.setRate(0.25);
			
			playbackValueLabel.setText("0.25");
		});
		
		
		playbackSpeed2.setOnMouseClicked((e) -> {
			
			switch(playbackSpeedTracker) {
				case 0: {
					if(playbackCustomCheck != null) {
						playbackCustomCheck.setGraphic(null);
					}
				}
				break;
				case 1: checkBox1.setGraphic(null);
				break;
				case 3: checkBox3.setGraphic(null);
				break;
				case 4: checkBox4.setGraphic(null);
				break;
				case 5: checkBox5.setGraphic(null);
				break;
				case 6: checkBox6.setGraphic(null);
				break;
				case 7: checkBox7.setGraphic(null);
				break;
				case 8: checkBox8.setGraphic(null);
				break;
				default: break;
			}
			
			playbackSpeedTracker = 2;
			checkBox2.setGraphic(new ImageView(check));
			
			mediaPlayer.setRate(0.5);
			
			playbackValueLabel.setText("0.5");
		});
		
		
		playbackSpeed3.setOnMouseClicked((e) -> {
			
			switch(playbackSpeedTracker) {
				case 0: {
					if(playbackCustomCheck != null) {
						playbackCustomCheck.setGraphic(null);
					}
				}
				break;
				case 1: checkBox1.setGraphic(null);
				break;
				case 2: checkBox2.setGraphic(null);
				break;
				case 4: checkBox4.setGraphic(null);
				break;
				case 5: checkBox5.setGraphic(null);
				break;
				case 6: checkBox6.setGraphic(null);
				break;
				case 7: checkBox7.setGraphic(null);
				break;
				case 8: checkBox8.setGraphic(null);
				break;
				default: break;
			}
			
			playbackSpeedTracker = 3;
			checkBox3.setGraphic(new ImageView(check));
			
			mediaPlayer.setRate(0.75);
			
			playbackValueLabel.setText("0.75");
		});
		
		
		playbackSpeed4.setOnMouseClicked((e) -> {
			
			switch(playbackSpeedTracker) {
				case 0: {
					if(playbackCustomCheck != null) {
						playbackCustomCheck.setGraphic(null);
					}
				}
				break;
				case 1: checkBox1.setGraphic(null);
				break;
				case 2: checkBox2.setGraphic(null);
				break;
				case 3: checkBox3.setGraphic(null);
				break;
				case 5: checkBox5.setGraphic(null);
				break;
				case 6: checkBox6.setGraphic(null);
				break;
				case 7: checkBox7.setGraphic(null);
				break;
				case 8: checkBox8.setGraphic(null);
				break;
				default: break;
			}
			
			playbackSpeedTracker = 4;
			checkBox4.setGraphic(new ImageView(check));
			
			mediaPlayer.setRate(1);
			
			playbackValueLabel.setText("Normal");
		});
		
		
		playbackSpeed5.setOnMouseClicked((e) -> {
			
			switch(playbackSpeedTracker) {
			
				case 0: {
					if(playbackCustomCheck != null) {
						playbackCustomCheck.setGraphic(null);
					}
				}
				break;
				case 1: checkBox1.setGraphic(null);
				break;
				case 2: checkBox2.setGraphic(null);
				break;
				case 3: checkBox3.setGraphic(null);
				break;
				case 4: checkBox4.setGraphic(null);
				break;
				case 6: checkBox6.setGraphic(null);
				break;
				case 7: checkBox7.setGraphic(null);
				break;
				case 8: checkBox8.setGraphic(null);
				break;
				default: break;
				
			}
			
			playbackSpeedTracker = 5;
			checkBox5.setGraphic(new ImageView(check));
			
			mediaPlayer.setRate(1.25);
			
			playbackValueLabel.setText("1.25");
			});
		
		
		playbackSpeed6.setOnMouseClicked((e) -> {
			
			switch(playbackSpeedTracker) {
			
				case 0: {
					if(playbackCustomCheck != null) {
						playbackCustomCheck.setGraphic(null);
					}
				}
				break;
				case 1: checkBox1.setGraphic(null);
				break;
				case 2: checkBox2.setGraphic(null);
				break;
				case 3: checkBox3.setGraphic(null);
				break;
				case 4: checkBox4.setGraphic(null);
				break;
				case 5: checkBox5.setGraphic(null);
				break;
				case 7: checkBox7.setGraphic(null);
				break;
				case 8: checkBox8.setGraphic(null);
				break;
				default: break;
			}
			
			playbackSpeedTracker = 6;
			checkBox6.setGraphic(new ImageView(check));
			
			mediaPlayer.setRate(1.5);
			
			playbackValueLabel.setText("1.5");
		});
		
		
		playbackSpeed7.setOnMouseClicked((e) -> {
			
			switch(playbackSpeedTracker) {
			
				case 0: {
					if(playbackCustomCheck != null) {
						playbackCustomCheck.setGraphic(null);
					}
				}
				break;
				case 1: checkBox7.setGraphic(null);
				break;
				case 2: checkBox2.setGraphic(null);
				break;
				case 3: checkBox3.setGraphic(null);
				break;
				case 4: checkBox4.setGraphic(null);
				break;
				case 5: checkBox5.setGraphic(null);
				break;
				case 6: checkBox6.setGraphic(null);
				break;
				case 8: checkBox8.setGraphic(null);
				break;
				default: break;
			}
			
			playbackSpeedTracker = 7;
			checkBox7.setGraphic(new ImageView(check));
			
			mediaPlayer.setRate(1.75);
			
			playbackValueLabel.setText("1.75");
		});
		
		
		playbackSpeed8.setOnMouseClicked((e) -> {
			
			switch(playbackSpeedTracker) {
			
				case 0: {
					if(playbackCustomCheck != null) {
						playbackCustomCheck.setGraphic(null);
					}
				}
				break;
				case 1: checkBox1.setGraphic(null);
				break;
				case 2: checkBox2.setGraphic(null);
				break;
				case 3: checkBox3.setGraphic(null);
				break;
				case 4: checkBox4.setGraphic(null);
				break;
				case 5: checkBox5.setGraphic(null);
				break;
				case 6: checkBox6.setGraphic(null);
				break;
				case 7: checkBox7.setGraphic(null);
				break;
				default: break;
			}
			
			playbackSpeedTracker = 8;
			checkBox8.setGraphic(new ImageView(check));
			
			mediaPlayer.setRate(2);
			
			playbackValueLabel.setText("2");
		});
	
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
			
			playButton.setTooltip(pause);

		} else {
			mediaPlayer.pause();
			playing = false;
			playLogo.setImage(new Image(pauseFile.toURI().toString()));
			
			playButton.setTooltip(play);

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
		seekedToEnd = false;
		playButton.setTooltip(pause);

		// startTimer();

		playButton.setOnAction((e) -> playButtonClick1());

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

	public void openCloseSettings() {
		if (settingsOpen) {
			if(!playbackSpeedOpen && !playbackOptionsOpen) {
				settingsExit = new Image(settingsExitFile.toURI().toString());
				settingsIcon.setImage(settingsExit);
				settingsOpen = false;

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
			else if(playbackOptionsOpen) {
				//CLOSING ANIMATION WHEN PLAYBACK OPTIONS PAGE IS OPEN
				
				settingsExit = new Image(settingsExitFile.toURI().toString());
				settingsIcon.setImage(settingsExit);
				settingsOpen = false;
				playbackSpeedOpen = false;
				customSpeedOpen = false;
				playbackOptionsOpen = false;
				
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
			else if(!customSpeedOpen){
				settingsExit = new Image(settingsExitFile.toURI().toString());
				settingsIcon.setImage(settingsExit);
				settingsOpen = false;
				playbackSpeedOpen = false;
				
				settingsBackgroundPane.prefHeightProperty().unbind();
				
				playbackSpeedScroll.translateYProperty().unbind();
				
				FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), playbackSpeedScroll);
				fadeTransition1.setFromValue(1);
				fadeTransition1.setToValue(0.0f);
				fadeTransition1.setCycleCount(1);
				TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
				translateTransition1.setFromY(playbackSpeedScroll.getTranslateY());
				translateTransition1.setToY(bufferPane.getHeight());
				translateTransition1.setCycleCount(1);
				
				/*FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(100), bufferPane);
				fadeTransition2.setFromValue(0.8f);
				fadeTransition2.setToValue(0.0f);
				fadeTransition2.setCycleCount(1);
				TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), bufferPane);
				translateTransition2.setFromY(0);
				translateTransition2.setToY(bufferPane.getHeight());
				translateTransition2.setCycleCount(1);*/

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
			else if(customSpeedOpen){
				// TODO: Settings closing animation when custom playback speed selector is open.
				settingsExit = new Image(settingsExitFile.toURI().toString());
				settingsIcon.setImage(settingsExit);
				settingsOpen = false;
				playbackSpeedOpen = false;
				customSpeedOpen = false;
				
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
			
			

		}
		
		else {
			settingsEnter = new Image(settingsEnterFile.toURI().toString());
			settingsIcon.setImage(settingsEnter);
			settingsOpen = true;

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
		
		if(settingsOpen) {
			openCloseSettings();
		}
		else {

			if (!muted) {

				muted = true;
				volumeIcon.setImage(volumeMute);
				mediaPlayer.setVolume(0);

				volumeValue = volumeSlider.getValue();
				
				volumeButton.setTooltip(unmute);

				volumeSlider.setValue(0);
			} else {
				muted = false;
				volumeIcon.setImage(volumeUp);
				mediaPlayer.setVolume(volumeValue);
				
				volumeButton.setTooltip(mute);

				volumeSlider.setValue(volumeValue);
			}
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
				fullScreenButton.setStyle("-fx-border-color: blue;");
			}
			break;
			
			// menuButton
			case 8: {
				mediaView.setStyle("-fx-border-color: transparent;");
			}
			break;
			
			default: break;
		
		}
		
	}
	
	public void updateMedia(double newValue) {

		bindCurrentTimeLabel();

		if (atEnd) {
			atEnd = false;
			seekedToEnd = false;

			if (wasPlaying) {
				playLogo.setImage(new Image(pauseImageFile.toURI().toString()));

				if (!durationSlider.isValueChanging()) {
					playing = true;
					mediaPlayer.play();
					playButton.setTooltip(pause);
				}
			} else {
				playLogo.setImage(new Image(startFile.toURI().toString()));
				playing = false;
				playButton.setTooltip(play);
			}

			playButton.setOnAction((e) -> {
				playButtonClick1();
			});
		} else if (newValue >= durationSlider.getMax()) {
			//durationSlider.setValue(durationSlider.getMax());
			
			if(durationSlider.isValueChanging()) {
				seekedToEnd = true;
				
				System.out.println("1");
			}
			
			
				atEnd = true;
				//endMedia();
				tempBool = true;
				playing = false;
				mediaPlayer.pause();
				playLogo.setImage(new Image(replayFile.toURI().toString()));
				playButton.setTooltip(replay);
				playButton.setOnAction((e) -> playButtonClick2());
		}

		if (Math.abs(mediaPlayer.getCurrentTime().toSeconds() - newValue) > 0.5) {
			mediaPlayer.seek(Duration.seconds(newValue));


		}
	}
	
	public void endMedia() {
		
		
		if((!shuffleOn && !loopOn && !autoplayOn) || (loopOn && seekedToEnd)) {
			System.out.println("lol");
			durationSlider.setValue(durationSlider.getMax());
			if (!durationLabel.textProperty().getValue().equals(getTime(mediaPlayer.getCurrentTime()) + "/" + getTime(media.getDuration()))) {
				durationLabel.textProperty().unbind();
				durationLabel.setText(getTime(mediaPlayer.getCurrentTime()) + "/" + getTime(media.getDuration()));
			}
			playLogo.setImage(new Image(replayFile.toURI().toString()));
			playButton.setTooltip(replay);
			playButton.setOnAction((e) -> playButtonClick2());
		}
		else if(loopOn && !seekedToEnd) {
			// restart current video

			System.out.println(seekedToEnd);
			mediaPlayer.seek(Duration.ZERO);
			
		}
		else if(shuffleOn) {
			// randomly select next video to play from current directory
		}
		else if(autoplayOn) {
			//play next song in queue/directory
		}
	
	}
	
	public void openPlaybackSpeedPage() {
		
		playbackSpeedOpen = true;

		
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
		
		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		
		double toHeight;
		if(playbackCustom != null) {
			toHeight = mediaView.sceneProperty().get().getHeight() < 637 ? mediaView.sceneProperty().get().getHeight() - 100 : 537;
		}
		else {
			toHeight = mediaView.sceneProperty().get().getHeight() < 587 ? mediaView.sceneProperty().get().getHeight() - 100 : 487;
		}
		
		settingsTimeline1.getKeyFrames()
				.add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), toHeight, Interpolator.LINEAR )));

		/*settingsTimeline.setOnFinished((e) -> {
			settingsTimeline.stop();
			settingsTimeline.getKeyFrames().clear();
		});*/

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		/*SequentialTransition seqTrans = new SequentialTransition();
		seqTrans.getChildren().addAll(parallelTransition, translateTransition2);
		seqTrans.play();*/
		
		//settingsTimeline.play();
		
		parallelTransition.setOnFinished((e) -> {
			settingsBackgroundPane.prefHeightProperty().bind(Bindings.max(Bindings.add( playbackSpeedScroll.heightProperty(), 40), 170) );
		});
	}
	
	
	
	public void closePlaybackSpeedPage() {
		
		playbackSpeedOpen = false;
		
		settingsBackgroundPane.prefHeightProperty().unbind();
		
		settingsBackgroundPane.setPrefHeight(playbackSpeedScroll.getHeight());
		
		//bufferPane.prefHeightProperty().unbind();
		
		
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
		settingsTimeline1.getKeyFrames()
				.add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), 170, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
		
		parallelTransition.setOnFinished((e) -> {
			playbackSpeedScroll.setVvalue(0);
		});

	}

	
	public void playNextMedia() {
		if(settingsOpen) {
			openCloseSettings();
		}
		else {
			
		}
	}
	
	public void pressFullScreen() {
		if(settingsOpen) {
			openCloseSettings();
		}
		else {
			fullScreen();
		}
	}
	
	public void controlBarClick() {
		if(settingsOpen) {
			openCloseSettings();
		}
	}
	
	public void playButtonClick1() {
		if(settingsOpen) {
			openCloseSettings();
		}
		else {
			playOrPause();
		}
	}
	
	public void playButtonClick2() {
		if(settingsOpen) {
			openCloseSettings();
		}
		else {
			replayMedia();
			seekedToEnd = false;
		}
	}
	
	public void openCustomSpeed() {
		customSpeedOpen = true;
		
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
		settingsTimeline1.getKeyFrames()
				.add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), 130, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
		
		parallelTransition.setOnFinished((e) -> {
			playbackSpeedScroll.setVvalue(0);
		});

	}
	
	public void closeCustomSpeed() {
		customSpeedOpen = false;
		

		//settingsBackgroundPane.prefHeightProperty().unbind();
		
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
		
		double toHeight;
		if(playbackCustom != null) {
			toHeight = mediaView.sceneProperty().get().getHeight() < 637 ? mediaView.sceneProperty().get().getHeight() - 100 : 537;
		}
		else {
			toHeight = mediaView.sceneProperty().get().getHeight() < 587 ? mediaView.sceneProperty().get().getHeight() - 100 : 487;
		}
		
		settingsTimeline1.getKeyFrames()
				.add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), toHeight, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
		
		parallelTransition.setOnFinished((e) -> {
			settingsBackgroundPane.prefHeightProperty().bind(Bindings.add(playbackSpeedScroll.heightProperty(), 40));
		});
		
	}
	
	public void openPlaybackOptions() {
		
			playbackOptionsOpen = true;
			
			//OPENING ANIMATION GOES HERE//
			
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
			settingsTimeline1.getKeyFrames()
					.add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), 230, Interpolator.LINEAR)));

			ParallelTransition parallelTransition = new ParallelTransition();
			parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
			parallelTransition.setCycleCount(1);
			parallelTransition.play();
			
	}

	public void closePlaybackOptions() {
		
		playbackOptionsOpen = false;
		
		//CLOSING ANIMATION GOES HERE//
		
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
		settingsTimeline1.getKeyFrames()
				.add(new KeyFrame(Duration.millis(100), new KeyValue(settingsBackgroundPane.prefHeightProperty(), 170, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
	}
	
	
	public void openCloseCaptions(){
		if(captionsOpen) {
			//CLOSE CAPTIONS
			captionsOpen = false;
			
			ScaleTransition scale = new ScaleTransition(Duration.millis(100), captionLine);
			scale.setFromX(1);
			scale.setToX(0);
			scale.setCycleCount(1);
			scale.setInterpolator(Interpolator.LINEAR);
			scale.play();

			
		}
		else {
			// OPEN CAPTIONS
			captionsOpen = true;
			
			ScaleTransition scale = new ScaleTransition(Duration.millis(100), captionLine);
			scale.setFromX(0);
			scale.setToX(1);
			scale.setCycleCount(1);
			scale.setInterpolator(Interpolator.LINEAR);
			scale.play();

		}
	}
	
	public void openVideoChooser() {
		selectedFile = fileChooser.showOpenDialog(Main.stage);
		
		if(selectedFile != null) {
			videoNameLabel.setText(selectedFile.getName());
		}
		
	}
	
	
	public void openMenu() {
		//opening animation
		
		menuPane.setOpacity(1);

		
		TranslateTransition translate = new TranslateTransition(Duration.millis(200), menuPane);
		translate.setFromX(-216);
		translate.setToX(0);
		translate.setCycleCount(1);
		translate.setInterpolator(Interpolator.LINEAR);
		translate.play();
		
		/*FadeTransition fade = new FadeTransition(Duration.millis(200), menuPane);
		fade.setFromValue(0);
		fade.setToValue(1);
		fade.setCycleCount(1);
		fade.setInterpolator(Interpolator.LINEAR);
		
		ParallelTransition parallel = new ParallelTransition(translate,fade);
		parallel.play();*/
	}
	
	public void closeMenu() {
		//closing animation
		TranslateTransition translate = new TranslateTransition(Duration.millis(200), menuPane);
		translate.setFromX(0);
		translate.setToX(-215);
		translate.setCycleCount(1);
		translate.setInterpolator(Interpolator.LINEAR);
		translate.play();
		
		translate.setOnFinished((e) -> {
			menuPane.setOpacity(0);
		});
		
		/*FadeTransition fade = new FadeTransition(Duration.millis(200), menuPane);
		fade.setFromValue(1);
		fade.setToValue(0);
		fade.setCycleCount(1);
		fade.setInterpolator(Interpolator.LINEAR);
		fade.play();
		
		ParallelTransition parallel = new ParallelTransition(translate,fade);
		parallel.play();*/
	}
	
	
	
	public void openDirectoryChooser() {
		selectedDirectory = directoryChooser.showDialog(Main.stage);
	}
	
}
