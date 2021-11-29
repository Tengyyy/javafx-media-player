package application;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public class AnimationsClass {

	ControlBarController controlBarController = new ControlBarController();
	SettingsController settingsController = new SettingsController();
	
	public void openSettings(StackPane bufferPane) {

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
	
	public void closeSettings() {
		
	}
	
}
