package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

public class Controller implements Initializable{

	@FXML
	public MediaView mediaView;
	
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
	
	@FXML
	Slider volumeSlider, durationSlider;
	
	@FXML
	FlowPane volumeSliderPane;
	
	@FXML
	Label durationLabel;
	
	private File file;
	private Media media;
	private MediaPlayer mediaPlayer;
	
	private boolean playing;
	private boolean wasPlaying;
	private boolean randomBool;
	
	
	private boolean atEnd = false;
	
	private DoubleProperty mediaViewWidth;
	private DoubleProperty mediaViewHeight;
	
	Image maximize, minimize, volumeUp, volumeDown, volumeMute;
	
	double volumeValue;


	private Image start;
	
	private File maximizeFile, minimizeFile, playFile, pauseFile, startFile, volumeUpFile, volumeDownFile, volumeMuteFile, replayFile, pauseImageFile;
	
	Timeline fullscreenTimeline;
	
	Timeline  volume;
	
	boolean muted = false;
	
	boolean isExited = false;
	
	boolean sliderFocus = false;
	
	boolean running = false; // media running status
	
	int videoLength;
	int currLength = 0;
	
	Timer durationTimer;
	TimerTask durationTimerTask;

	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		volumeSliderPane.setClip(new Rectangle(60, 36));
		
		volumeSlider.setTranslateX(-60);
		
		durationLabel.setTranslateX(-60);
		
		
		volumeSlider.setFocusTraversable(false);
		
		file = new File("hey.mp4");
		media = new Media(file.toURI().toString());
		
		
		mediaPlayer = new MediaPlayer(media);
		
		//mediaPlayer.get
		
		
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
		
		playButton.setOnAction((e) -> playOrPause());
		
		fullScreenIcon.setImage(maximize);
		fullScreenButton.setBackground(Background.EMPTY);
		
		volumeButton.setBackground(Background.EMPTY);
		volumeIcon.setImage(volumeUp);
		
		volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {	
				
				mediaPlayer.setVolume(volumeSlider.getValue() / 100);
				
				if(volumeSlider.getValue() == 0) {
					volumeIcon.setImage(volumeMute);
					muted = true;
				}
				else if(volumeSlider.getValue() < 50) {
					volumeIcon.setImage(volumeDown);
					muted = false;
				}
				else {
					volumeIcon.setImage(volumeUp);
				}
			}
			
		});
		
		durationSlider.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> durationSlider.setValueChanging(true));
		durationSlider.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> durationSlider.setValueChanging(false));
		
		
		durationSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				
				bindCurrentTimeLabel();
				
				if(atEnd) {
					atEnd = false;
					playLogo.setImage(new Image(pauseImageFile.toURI().toString()));
					//playing = true;
					
					playButton.setOnAction((e) -> {
						playOrPause();
					});
				}
				if(randomBool) {
					
				}
				
				if(Math.abs(mediaPlayer.getCurrentTime().toSeconds() - newValue.doubleValue()) > 0.5)	{
					mediaPlayer.seek(Duration.seconds(newValue.doubleValue()));
					
				
				}
				
				
			}
			
		});
		
		durationSlider.valueChangingProperty().addListener(new ChangeListener<Boolean>() { // vaja ära fixida see jama

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				
				bindCurrentTimeLabel();
				
				if(wasPlaying) {
					if(randomBool) {
						if(newValue) {
							//mediaPlayer.play();
							Platform.runLater(new Runnable() {

								@Override
								public void run() {
									// TODO Auto-generated method stub
									mediaPlayer.pause();
								}
								
							});
							
							playing = false;
							playLogo.setImage(new Image(pauseFile.toURI().toString()));
							//endTimer();
							mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));
							System.out.println(newValue);
							//randomBool = false;
						}
						else if(!newValue) {
							mediaPlayer.play();
							playing = true;
							playLogo.setImage(new Image(pauseImageFile.toURI().toString()));
						}
					}
					
					else {
						if(newValue) {
						
							mediaPlayer.pause();
							playing = false;
							playLogo.setImage(new Image(pauseFile.toURI().toString()));
							//endTimer();
							mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));
						}
						else if(!newValue) {
							mediaPlayer.play();
							playing = true;
							playLogo.setImage(new Image(playFile.toURI().toString()));
							//startTimer();
							}
						}
					}
					else {
						mediaPlayer.seek(Duration.seconds(durationSlider.getValue()));
						if(!newValue) {
							mediaPlayer.play();
						}
				
					}
				
			}
			
		});
		
		
		volumeSlider.focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
			
			sliderFocus = newValue;
			
			if(!newValue) {
				volumeSliderExit();
			}
			
		});

		
		mediaPlayer.setOnReady(new Runnable() {

			@Override
			public void run() {
				
				durationSlider.setMax(media.getDuration().toSeconds());
				
				bindCurrentTimeLabel();
				
				mediaPlayer.play();
				mediaPlayer.pause();
				
				//startTimer();
				
			}
			
		});
		
	    mediaPlayer.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observableValue, Duration oldTime, Duration newTime) {
                bindCurrentTimeLabel();
                if (!durationSlider.isValueChanging()) {
                    durationSlider.setValue(newTime.toSeconds());
                }
            }
        });
		
		mediaPlayer.setOnEndOfMedia(new Runnable() {

			@Override
			public void run() {
				atEnd = true;
				randomBool = true;
				
				//playing = false;
				
				//endTimer();
				
				playLogo.setImage(new Image(replayFile.toURI().toString()));
				
				playButton.setOnAction((e) -> replayMedia());
				
			}
			
		});
		
	}
	
	public void mediaClick() {
		playOrPause();
		mediaView.requestFocus();
	}
	
	public void playOrPause() {
		
		//displayControls();
		
		if(!playing) {
			mediaPlayer.play();
			playing = true;
			//startTimer();
			playLogo.setImage(new Image(playFile.toURI().toString()));
			
			wasPlaying = playing;
		}
		else {
			mediaPlayer.pause();
			playing = false;
			playLogo.setImage(new Image(pauseFile.toURI().toString()));
			//endTimer();
			
			wasPlaying = playing;
		}
	}
	
	
	// TODO: Fix bug when using duration slider to scroll back in the video after the video has already ended. 
	public void replayMedia() {
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.play();
		playing = true;
		atEnd = false;
		playLogo.setImage(new Image(pauseImageFile.toURI().toString()));
		
		//startTimer();
		
		playButton.setOnAction((e) -> playOrPause());
		
	}
	
	
	// timer to update video duration label and progress bar
	/*public void startTimer() {
		durationTimer = new Timer();
		
		durationTimerTask = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				running = true;
				
				// Timer thread can't directly access GUI elements created by the main fx thread so this method sends a request to update the duration label to the main thread
				Platform.runLater(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						
						Duration currentTime = mediaPlayer.getCurrentTime();
						durationSlider.setValue(currentTime.toSeconds());
						
					}
					});
			}
			
		};
		
		durationTimer.scheduleAtFixedRate(durationTimerTask, 0, 100);
	}*/
	
	/*public void endTimer() {
		running = false;
		durationTimer.cancel();
	}*/
	
	public void displayControls() {
		
		
		if(playing) {
			// control bar slide animation - display
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.2));
			slide.setNode(controlBar);
										
			slide.setToY(0);
			
			slide.play();
			controlBar.setTranslateY(-50);
		}
		else {
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
		
		if(Main.stage.isFullScreen()) {
			fullScreenIcon.setImage(minimize);
		}
		else {
			fullScreenIcon.setImage(maximize);
		}
	}
	
	public void fullScreenEnter() {
		
		fullscreenTimeline = new Timeline();
		
	     fullscreenTimeline.setCycleCount(2);
	     fullscreenTimeline.setAutoReverse(true);
	     fullscreenTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(200),
	    		 new KeyValue(fullScreenIcon.scaleYProperty(), 1.3)));
	     fullscreenTimeline.getKeyFrames().add(new KeyFrame(Duration.millis(200),
	    		 new KeyValue(fullScreenIcon.scaleXProperty(), 1.3)));
	     
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
	    
	    volumeSlider.setFocusTraversable(true);

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
		
		if(isExited && !sliderFocus) {
		
		//volume.stop();
		
		volume = new Timeline();
		
		//volume.getKeyFrames().clear();
		
		volume.getKeyFrames().add(new KeyFrame(Duration.millis(100),
				new KeyValue(volumeSlider.translateXProperty(), -60, Interpolator.EASE_OUT)));
		
		volume.getKeyFrames().add(new KeyFrame(Duration.millis(100),
				new KeyValue(durationLabel.translateXProperty(), -60, Interpolator.EASE_OUT)));
		
	    volume.play();
	    
	    volume.setOnFinished((e) -> {
	    	volume.stop();
			volume.getKeyFrames().clear();

	    });
	    
	    volumeSlider.setFocusTraversable(false);
	    
		}
		
	}
	
	public void mute() {
	
		if(!muted) {
			
			muted = true;
			volumeIcon.setImage(volumeMute);
			mediaPlayer.setVolume(0);
			
			volumeValue = volumeSlider.getValue();
			
			volumeSlider.setValue(0);
		}
		else {
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

        // Fix the issue with the timer going to 61 and above for seconds, minutes, and hours.
        if (seconds > 59) seconds = seconds % 60;
        if (minutes > 59) minutes = minutes % 60;
        if (hours > 59) hours = hours % 60;

        // Don't show the hours unless the video has been playing for an hour or longer.
        if (hours > 0) return String.format("%d:%02d:%02d",
                hours,
                minutes,
                seconds);
        else return String.format("%02d:%02d",
                minutes,
                seconds);
    }
	
	public void bindCurrentTimeLabel() {
	        
	        durationLabel.textProperty().bind(Bindings.createStringBinding(new Callable<String>() {
	            @Override
	        	public String call() throws Exception {
	           
	                return getTime(mediaPlayer.getCurrentTime()) + "/" + getTime(media.getDuration());
	            }
	        }, mediaPlayer.currentTimeProperty()));
	    }

	
	
}
