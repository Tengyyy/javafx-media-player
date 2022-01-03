package application;
	

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;



public class Main extends Application {
	
	public static Stage stage;
	
	public EventHandler<KeyEvent> eventHandler;
	
	public static boolean fullScreen;
	
	 DirectoryChooser directoryChooser;
	 
	 ControlBarController controlBarController;
	 SettingsController settingsController;
	 
	 MouseEventTracker mouseEventTracker;

	
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Resources/Views/Main.fxml"));
			
			Parent root = loader.load();
			
			MainController mainController = loader.getController();

			
			controlBarController = mainController.getControlBarController();
			
			settingsController = mainController.getSettingsController();
			
			
			Scene scene = new Scene(root, 600, 400);
			
			scene.getStylesheets().add(getClass().getResource("Resources/Styles/application.css").toExternalForm());
			
			
			
			//TODO: Continue this bit to make the controlbar hiding logic
			mouseEventTracker = new MouseEventTracker(4, mainController, controlBarController, settingsController);
			
			scene.addEventFilter(MouseEvent.ANY, event -> {
				if(mouseEventTracker != null)
					mouseEventTracker.move();
			});

			
			
			primaryStage.setMinHeight(325);
			primaryStage.setMinWidth(400);
			
			Main.stage = primaryStage;
			
			primaryStage.setFullScreenExitHint("Press Esc to exit fullscreen mode");
			
			directoryChooser = new DirectoryChooser();


			
			//press F11 to set full screen
			primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				
				switch(event.getCode()) {
					case TAB: {
						
						mouseEventTracker.move();

						if(event.isShiftDown()) {
							// user pressed SHIFT + TAB which means focus should traverse backwards
							if(mainController.focusNodeTracker == 0) {
								mainController.focusNodeTracker = 8;
							}
							else {
								mainController.focusNodeTracker--;
							}
							mainController.traverseFocusBackwards();
						}
						else {
							if(mainController.focusNodeTracker == 8) {
								mainController.focusNodeTracker = 0;
							}
							else {
								mainController.focusNodeTracker++;
							}
							// user pressed TAB which means focus should traverse forwards
							
							mainController.traverseFocusForwards();
						}
					}
					break;	
					
					case RIGHT: {
						
						mouseEventTracker.move();
						
						if(!mainController.getControlBarController().volumeSlider.isFocused()) {
							
							if(mainController.mediaPlayer.getCurrentTime().toSeconds() + 5 >= controlBarController.durationSlider.getMax()) {
								mainController.seekedToEnd = true;
							}
							
							controlBarController.durationSlider.setValue(controlBarController.durationSlider.getValue() + 5);
							event.consume();
							
							
						}
					}
					break;
					case LEFT: {
						
						mouseEventTracker.move();
						
						if(!controlBarController.volumeSlider.isFocused()) {
							mainController.seekedToEnd = false;
							
							controlBarController.durationSlider.setValue(controlBarController.durationSlider.getValue() - 5);
							event.consume();
							
						}
					}
					break;
					
					case ESCAPE: {
						
						mouseEventTracker.move();
						
						if(settingsController.settingsOpen && !fullScreen) {
							settingsController.closeSettings();
						}
						fullScreen = false;
						
						controlBarController.fullScreenIcon.setImage(controlBarController.maximize);
						primaryStage.setFullScreen(false);
						controlBarController.fullScreenButton.setTooltip(controlBarController.enterFullScreen);
					}
					break;
					
					case L: {
						
						mouseEventTracker.move();

						if(!controlBarController.volumeSlider.isFocused()) {
							
							
							if(mainController.mediaPlayer.getCurrentTime().toSeconds() + 10 >= controlBarController.durationSlider.getMax()) {
								mainController.seekedToEnd = true;
							}
								controlBarController.durationSlider.setValue(controlBarController.durationSlider.getValue() + 10);
							event.consume();
							
							
						}	
					}
					break;
					
					case J: {
						
						mouseEventTracker.move();

						if(!controlBarController.volumeSlider.isFocused()) {
							mainController.seekedToEnd = false;
					
								controlBarController.durationSlider.setValue(controlBarController.durationSlider.getValue() - 10.0);

							
						}
					}
					break;
					case DIGIT1: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 1/10);
					}
					break;
					case DIGIT2: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 2/10);
					}
					break;
					case DIGIT3: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 3/10);
					}
					break;
					case DIGIT4: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 4/10);
					}
					break;
					case DIGIT5: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 5/10);
					}
					break;
					case DIGIT6: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 6/10);
					}
					break;
					case DIGIT7: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 7/10);
					}
					break;
					case DIGIT8: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 8/10);
					}
					break;
					case DIGIT9: {
						
						mouseEventTracker.move();

						controlBarController.durationSlider.setValue(mainController.media.getDuration().toSeconds() * 9/10);
					}
					break;
					case DIGIT0: {
						
						mouseEventTracker.move();

						mainController.seekedToEnd = true;
						controlBarController.durationSlider.setValue(controlBarController.durationSlider.getMax());
					}
					break;
					
					case K: {
						
						mouseEventTracker.move();
						
						if(!controlBarController.durationSlider.isValueChanging()) {  // wont let user play/pause video while media slider is seeking
							if(mainController.atEnd) {
								controlBarController.replayMedia();
							}
							else {
								controlBarController.playOrPause();
							}
						}
						
					}
					break;
					
					case M: {
						
						mouseEventTracker.move();

						if (!controlBarController.muted) {

							controlBarController.muted = true;
							controlBarController.volumeIcon.setImage(controlBarController.volumeMute);
							mainController.mediaPlayer.setVolume(0);

							controlBarController.volumeValue = controlBarController.volumeSlider.getValue();
							
							controlBarController.volumeButton.setTooltip(controlBarController.unmute);

							controlBarController.volumeSlider.setValue(0);
						} else {
							controlBarController.muted = false;
							controlBarController.volumeIcon.setImage(controlBarController.volumeUp);
							mainController.mediaPlayer.setVolume(controlBarController.volumeValue);
							
							controlBarController.volumeButton.setTooltip(controlBarController.mute);

							controlBarController.volumeSlider.setValue(controlBarController.volumeValue);
						}
					}
					break;
					
					case F11: {
						mouseEventTracker.move();
						controlBarController.fullScreen();
					}
					break;
					
					case F: {
						mouseEventTracker.move();
						controlBarController.fullScreen();
					}
					break;
					
					case SPACE:	{
					
						mouseEventTracker.move();

						if(!controlBarController.durationSlider.isValueChanging()) { // wont let user play/pause video while media slider is seeking
							
							if(!controlBarController.playButton.isFocused()) {
								if(mainController.atEnd) {
									controlBarController.replayMedia();
								}
								else {
									controlBarController.playOrPause();
								}
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
	
	
	public MouseEventTracker getMouseEventTracker() {
		return mouseEventTracker;
	}
}