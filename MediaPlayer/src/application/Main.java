package application;
	
import java.io.File;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	public static Stage stage;
	
	public EventHandler<KeyEvent> eventHandler;
	
	static boolean fullScreen;
	
	static DirectoryChooser directoryChooser;

	
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
			
			Parent root = loader.load();
			
			Controller controller = loader.getController();
			
			Scene scene = new Scene(root, 600, 400);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
			
			Screen screen = Screen.getPrimary();
			
			double scaleY = screen.getOutputScaleY();
			
			
			primaryStage.setMinHeight(325);
			primaryStage.setMinWidth(400);
			
			Main.stage = primaryStage;
			
			primaryStage.setFullScreenExitHint("Press Esc to exit fullscreen mode");

			
			
			directoryChooser = new DirectoryChooser();


			
			//press F11 to set full screen
			primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				
				switch(event.getCode()) {
					case TAB: {
						if(event.isShiftDown()) {
							// user pressed SHIFT + TAB which means focus should traverse backwards
							if(controller.focusNodeTracker == 0) {
								controller.focusNodeTracker = 8;
							}
							else {
								controller.focusNodeTracker--;
							}
							controller.traverseFocusBackwards();
						}
						else {
							if(controller.focusNodeTracker == 8) {
								controller.focusNodeTracker = 0;
							}
							else {
								controller.focusNodeTracker++;
							}
							// user pressed TAB which means focus should traverse forwards
							
							controller.traverseFocusForwards();
						}
					}
					break;	
					
					case RIGHT: {
						if(!controller.volumeSlider.isFocused()) {
							
							
							
							if(controller.mediaPlayer.getCurrentTime().toSeconds() + 5 >= controller.durationSlider.getMax()) {
								controller.seekedToEnd = true;
								//controller.mediaPlayer.seek(controller.media.getDuration());
								
								controller.durationSlider.setValue(controller.durationSlider.getMax());

							}
							controller.durationSlider.setValue(controller.durationSlider.getValue() + 5);
							event.consume();
							
							
						}
					}
					
					break;
					
					case LEFT: {
						if(!controller.volumeSlider.isFocused()) {
							controller.seekedToEnd = false;
							if(controller.atEnd) {
								System.out.println('h');
								controller.mediaPlayer.seek(new Duration(controller.mediaPlayer.getCurrentTime().toMillis() - 5000));

							}
							
							else {
								controller.durationSlider.setValue(controller.durationSlider.getValue() - 5);
								//controller.mediaPlayer.seek(new Duration(controller.mediaPlayer.getCurrentTime().toMillis() - 5000));
							}
							event.consume();
							
						}
					}
					break;
					
					case ESCAPE: {
						if(controller.settingsOpen && !fullScreen) {
							controller.openCloseSettings();
						}
						fullScreen = false;
						
						controller.fullScreenIcon.setImage(controller.maximize);
						primaryStage.setFullScreen(false);
						controller.fullScreenButton.setTooltip(controller.enterFullScreen);
					}
					break;
					
					case L: {
						if(!controller.volumeSlider.isFocused()) {
							
							
							if(controller.mediaPlayer.getCurrentTime().toSeconds() + 10 >= controller.durationSlider.getMax()) {
								controller.seekedToEnd = true;
								controller.durationSlider.setValue(controller.durationSlider.getMax());
							}
							else {
								controller.durationSlider.setValue(controller.durationSlider.getValue() + 10);
							}
							event.consume();
							
							
						}	
					}
					break;
					
					case J: {
						if(!controller.volumeSlider.isFocused()) {
							controller.seekedToEnd = false;
							if(controller.atEnd) {
								controller.mediaPlayer.seek(new Duration(controller.mediaPlayer.getCurrentTime().toMillis() - 10000));
							}
							
							else {
								controller.durationSlider.setValue(controller.durationSlider.getValue() - 10.0);
								//controller.mediaPlayer.seek(new Duration(controller.mediaPlayer.getCurrentTime().toMillis() - 5000));
							}

							
						}
					}
					break;
					case DIGIT1: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 1/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT2: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 2/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT3: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 3/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT4: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 4/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT5: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 5/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT6: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 6/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT7: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 7/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT8: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 8/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT9: {
						controller.durationSlider.setValue(controller.media.getDuration().toSeconds() * 9/10);
						System.out.println("su ema");
					}
					break;
					case DIGIT0: {
						controller.seekedToEnd = true;
						controller.durationSlider.setValue(controller.durationSlider.getMax());
					}
					break;
					
					case K: {
						if(controller.atEnd) {
							controller.replayMedia();
						}
						else {
							controller.playOrPause();
						}
					}
					break;
					
					case M: {
						if (!controller.muted) {

							controller.muted = true;
							controller.volumeIcon.setImage(controller.volumeMute);
							controller.mediaPlayer.setVolume(0);

							controller.volumeValue = controller.volumeSlider.getValue();
							
							controller.volumeButton.setTooltip(controller.unmute);

							controller.volumeSlider.setValue(0);
						} else {
							controller.muted = false;
							controller.volumeIcon.setImage(controller.volumeUp);
							controller.mediaPlayer.setVolume(controller.volumeValue);
							
							controller.volumeButton.setTooltip(controller.mute);

							controller.volumeSlider.setValue(controller.volumeValue);
						}
					}
					break;
					
					case F11: controller.fullScreen();
					break;
					case F: {
						controller.fullScreen();

					}
					break;
					
					case SPACE:	{
					
						if(!controller.playButton.isFocused()) {
							if(controller.atEnd) {
								controller.replayMedia();
							}
							else {
								controller.playOrPause();
							}
						}
					
					}
					break;
					
					default: break;
				}
			});
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("MP4 Player");
			primaryStage.show();
			
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>(){

				@Override
				public void handle(WindowEvent event) {
					// TODO Auto-generated method stub
					
					Platform.exit();
					System.exit(0);
					
				}
				
			});

			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
