module mediaPlayer {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.base;
	requires com.jfoenix;
	requires java.logging;

	
	opens application to javafx.graphics, javafx.fxml;
}