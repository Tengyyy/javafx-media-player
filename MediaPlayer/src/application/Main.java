package application;
	
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;


public class Main extends Application {
	
	public static Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
			
			Parent root = loader.load();
			
			Controller controller = loader.getController();
			
			Scene scene = new Scene(root, 600, 400);
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());		
			
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent event) {
					
					switch(event.getCode()) {
						case SPACE:	controller.playOrPause();
						break;
						
						default: System.out.println(event.getCode());
						
					}
					
				}
				
			});
			
			Main.stage = primaryStage;
			
			primaryStage.setFullScreenExitHint("Press Esc to exit fullscreen mode");
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.valueOf("Esc"));
			
			//press F11 to set full screen
			primaryStage.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
	            if (KeyCode.F11.equals(event.getCode()) || KeyCode.F.equals(event.getCode())) {
	            	controller.fullScreen();
	            }
	            else if(KeyCode.ESCAPE.equals(event.getCode())) {
	            	controller.fullScreenIcon.setImage(controller.maximize);
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
