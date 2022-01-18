package application;

import java.io.File;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class MenuItem extends HBox{
	
	ScrollPane menuScroll;
	
	File videoFile; // the mp4 file that this menu-item is representing
	
	
	MenuItem(ScrollPane menuScroll, File videoFile){
		
		this.menuScroll = menuScroll;
		this.videoFile = videoFile;
		
		this.prefWidthProperty().bind(menuScroll.widthProperty());
		this.setPrefHeight(100);

		
		 // create a background fill
        BackgroundFill background_fill = new BackgroundFill(Color.rgb(255, 255, 255, 0.7), 
                                      CornerRadii.EMPTY, Insets.EMPTY);

        // create Background
        Background background = new Background(background_fill);

        // set background
        this.setBackground(background);
		
		Label label = new Label("SU EMA!!!!!!!");
		label.setFont(new Font(30));
		
		this.getChildren().add(label);
		
	}
	
}
