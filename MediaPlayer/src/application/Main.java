package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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

	
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
			
			Parent root = loader.load();
			
			Controller controller = loader.getController();
			
			Scene scene = new Scene(root, 600, 400);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());	
			
			
			Main.stage = primaryStage;
			
			primaryStage.setFullScreenExitHint("Press Esc to exit fullscreen mode");
			//primaryStage.setFullScreenExitKeyCombination(KeyCombination.);
			
			primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
				
				switch(event.getCode()) {
					case F11: controller.fullScreen();
					break;
					case F: {
						controller.fullScreen();

					}
					break;
					case ESCAPE: {
						controller.fullScreenIcon.setImage(controller.maximize);
						primaryStage.setFullScreen(false);
						controller.fullScreenButton.setTooltip(controller.enterFullScreen);
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
				
					case RIGHT: {
						/*if(!controller.atEnd) {
						
							if(controller.durationSlider.getValue() + 5 >= controller.durationSlider.getMax()) {
							controller.durationSlider.setValue(controller.durationSlider.getMax());
							}
							else {
								controller.durationSlider.setValue(controller.durationSlider.getValue() + 5);
							}
						
						}*/
					
					}
					break;
				
					case LEFT: {

						
						/*if(controller.mediaPlayer.getCurrentTime().toSeconds() > 5.0) {
							controller.durationSlider.setValue(controller.durationSlider.getValue() - 5);

						}
						else {
							controller.durationSlider.setValue(0);
						}*/

					}
					break;
						default:
							break;
				
				}
				
			});

			

			
			
			//press F11 to set full screen
			primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				
				if(event.getCode() == KeyCode.TAB){
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
				else if(event.getCode() == KeyCode.RIGHT) {
					
					if(!controller.volumeSlider.isFocused()) {
						
						
						if(controller.mediaPlayer.getCurrentTime().toSeconds() + 5 >= controller.media.getDuration().toSeconds()) {
							controller.seekedToEnd = true;
							controller.mediaPlayer.seek(controller.media.getDuration());
						}
						else {
						controller.durationSlider.setValue(controller.durationSlider.getValue() + 5);
						}
						event.consume();
						
						
					}

					

					
				}
				else if(event.getCode() == KeyCode.LEFT) {
					
					if(!controller.volumeSlider.isFocused()) {
						if(controller.atEnd) {
							controller.seekedToEnd = false;
							controller.mediaPlayer.seek(new Duration(controller.mediaPlayer.getCurrentTime().toMillis() - 5000));

						}
						
						else {
							controller.durationSlider.setValue(controller.durationSlider.getValue() - 5.0);
							//controller.mediaPlayer.seek(new Duration(controller.mediaPlayer.getCurrentTime().toMillis() - 5000));
						}

						
					}
				}
				else if(event.getCode() == KeyCode.ESCAPE) {
					if(controller.settingsOpen && !fullScreen) {
						controller.openCloseSettings();
					}
					fullScreen = false;
				}
				else if(event.getCode() == KeyCode.L) {
					
					if(!controller.volumeSlider.isFocused()) {
						
						
						if(controller.mediaPlayer.getCurrentTime().toSeconds() + 10 >= controller.media.getDuration().toSeconds()) {
							controller.seekedToEnd = true;
							controller.mediaPlayer.seek(controller.media.getDuration());
						}
						else {
						controller.durationSlider.setValue(controller.durationSlider.getValue() + 10);
						}
						event.consume();
						
						
					}

					

					
				}
				
				else if(event.getCode() == KeyCode.J) {
					
					if(!controller.volumeSlider.isFocused()) {
						if(controller.atEnd) {
							controller.seekedToEnd = false;
							controller.mediaPlayer.seek(new Duration(controller.mediaPlayer.getCurrentTime().toMillis() - 10000));

						}
						
						else {
							controller.durationSlider.setValue(controller.durationSlider.getValue() - 10.0);
							//controller.mediaPlayer.seek(new Duration(controller.mediaPlayer.getCurrentTime().toMillis() - 5000));
						}

						
					}
				}
					
				
			});
			
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Media Player");
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
