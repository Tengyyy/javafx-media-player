package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class MenuController implements Initializable{
	
	@FXML
	Pane menuBackgroundPane;
	
	@FXML
	ScrollPane menuScroll;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		// fit scrollpane to whole screen
		menuScroll.prefWidthProperty().bind(Bindings.selectDouble(menuScroll.sceneProperty(), "width"));
		menuScroll.prefHeightProperty().bind(Bindings.selectDouble(menuScroll.sceneProperty(), "height"));
	}

}
