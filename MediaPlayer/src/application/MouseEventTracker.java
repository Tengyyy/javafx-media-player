package application;

import javafx.animation.PauseTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.util.Duration;

public class MouseEventTracker {

	int delay; // delay how long mouse has to stay still for controlbar to hide
	
	MainController mainController;
	ControlBarController controlBarController;
	SettingsController settingsController;

	
	BooleanProperty mouseMoving = new SimpleBooleanProperty(); // keeps track of the mouse state (have any mouse events taken place in the last x seconds or not
	PauseTransition pause;
	
	MouseEventTracker(int delay, MainController mainController, ControlBarController controlBarController, SettingsController settingsController){
		this.delay = delay;
		this.mainController = mainController;
		this.controlBarController = controlBarController;
		this.settingsController = settingsController;
		
		 mouseMoving.addListener((obs, wasMoving, isNowMoving) -> {
	           if (!isNowMoving) {
	        	   
	        	   if(mainController.playing && !mainController.captionsOpen && !settingsController.settingsOpen && !controlBarController.volumeSlider.isValueChanging() && !controlBarController.durationSlider.isValueChanging() && controlBarController.controlBarOpen) {
	        		   controlBarController.hideControls();
	        	   } 
	           }
	           else if(isNowMoving) {
	        	   
	        	   if(!controlBarController.controlBarOpen) {
	        		   controlBarController.displayControls();
	        	   }
	           }
	        });
		 
		 // countdown that changes mouseMoving property to false if it reaches the end
		 pause = new PauseTransition(Duration.millis(delay * 1000));
	     pause.setOnFinished(e -> mouseMoving.set(false));
	}
	
	public void move() { // resets the countdown when called
		mouseMoving.set(true);
		pause.playFromStart();
	}
	
}
