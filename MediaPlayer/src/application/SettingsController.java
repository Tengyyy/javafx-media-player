package application;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXToggleButton;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class SettingsController implements Initializable{
	

	@FXML
	VBox settingsHome, playbackSpeedPage, customSpeedBox, playbackOptionsVBox;
	
	@FXML
	 StackPane bufferPane;

	@FXML
	StackPane customSpeedBuffer;

	@FXML
	StackPane customSpeedPane;

	@FXML
	StackPane playbackOptionsBuffer;

	@FXML
	StackPane playbackOptionsPane;

	@FXML
	Pane settingsBackgroundPane;

	@FXML
	Slider customSpeedSlider;

	@FXML
	ProgressBar customSpeedTrack;
	
	@FXML
	StackPane settingsPane;
	
	@FXML
	Label playbackValueLabel, videoNameLabel, playbackOptionsArrow, playbackSpeedArrow, playbackSpeedTitleLabel, playbackSpeedCustom, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, customSpeedArrow, customSpeedTitleLabel, customSpeedLabel, playbackOptionsTitleArrow, playbackOptionsTitleText, shuffleLabel, loopLabel, autoplayLabel, videoArrowLabel;
	

	@FXML
	ScrollPane playbackSpeedScroll;

	@FXML
	JFXToggleButton shuffleSwitch, loopSwitch, autoplaySwitch;
	
	@FXML
	HBox playbackSpeedBox, playbackOptionsBox, videoBox, playbackSpeedTitle, playbackSpeed1, playbackSpeed2, playbackSpeed3, playbackSpeed4, playbackSpeed5, playbackSpeed6, playbackSpeed7, playbackSpeed8, customSpeedTitle, shuffleBox, loopBox, autoplayBox, playbackOptionsTitle;

	private MainController mainController;
	private ControlBarController controlBarController;
	private AnimationsClass animationsClass;
	
	
	HBox playbackCustom;
	Label playbackCustomCheck;
	Label playbackCustomText;
	
	// variables to keep track of playback option toggles:
	 boolean shuffleOn = false;
	 boolean loopOn = false;
	 boolean autoplayOn = false;
	/////////////////////////////////////////////////////
	 
	 
	Image rightArrow;

	Image leftArrow;

	Image check;

	double formattedValue;
	double formattedValue2;
	
	DecimalFormat df;
	

	// counter to keep track of which playback speed field is selected in the settings menu
	int playbackSpeedTracker = 4;
	

	public  boolean settingsOpen = false;

	boolean playbackSpeedOpen = false;

	boolean playbackOptionsOpen = false;

	boolean customSpeedOpen = false;
	
	private File rightArrowFile, leftArrowFile, checkFile;
	
	
	
	
	HBox[] playbackSpeedBoxesArray; // array containing playback speed selection fields
	Label[] playbackSpeedCheckBoxesArray; // array containing checkmark fields inside playback speed tab
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		playbackSpeedBoxesArray = new HBox[] { playbackSpeed1, playbackSpeed2, playbackSpeed3, playbackSpeed4, playbackSpeed5, playbackSpeed6, playbackSpeed7, playbackSpeed8 };
		playbackSpeedCheckBoxesArray = new Label[] { checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8 };
		
		
		rightArrowFile = new File("Images/rightArrowFile.png");
		leftArrowFile = new File("Images/leftArrowFile.png");
		checkFile = new File("Images/checkFile.png");
		
		rightArrow = new Image(rightArrowFile.toURI().toString());
		leftArrow = new Image(leftArrowFile.toURI().toString());
		check = new Image(checkFile.toURI().toString());
		
		bufferPane.setBackground(Background.EMPTY);
		settingsPane.setBackground(Background.EMPTY);

		settingsPane.setStyle("-fx-background-color: rgba(35,35,35,0.8)");

		playbackSpeedScroll.setBackground(Background.EMPTY);

		playbackSpeedScroll.setStyle("-fx-background-color: rgba(35,35,35,0.8)");

		customSpeedPane.setStyle("-fx-background-color: rgba(35,35,35,0.8)");

		playbackOptionsPane.setStyle("-fx-background-color: rgba(35,35,35,0.8)");
		
		
		
		playbackValueLabel.setGraphic(new ImageView(rightArrow));

		videoArrowLabel.setGraphic(new ImageView(rightArrow));

		playbackOptionsArrow.setGraphic(new ImageView(rightArrow));

		playbackSpeedArrow.setGraphic(new ImageView(leftArrow));

		playbackOptionsTitleArrow.setGraphic(new ImageView(leftArrow));

		checkBox4.setGraphic(new ImageView(check));

		customSpeedArrow.setGraphic(new ImageView(leftArrow));
		
		
		
		playbackOptionsBox.setOnMouseClicked((e) -> {
			
			openPlaybackOptions();
		});

		playbackOptionsTitle.setOnMouseClicked((e) -> {
			closePlaybackOptions();
		});

		videoBox.setOnMouseClicked((e) -> {
			mainController.openVideoChooser();
		});

		shuffleBox.setOnMouseClicked((e) -> {
			shuffleSwitch.fire();

			if (loopSwitch.isSelected()) { // turns other switches off if this one is toggled on. makes it so only one
											// switch can be selected
				loopSwitch.fire();
			}

			if (autoplaySwitch.isSelected()) {
				autoplaySwitch.fire();
			}

		});
		loopBox.setOnMouseClicked((e) -> {
			loopSwitch.fire();

			if (shuffleSwitch.isSelected()) {
				shuffleSwitch.fire();
			}

			if (autoplaySwitch.isSelected()) {
				autoplaySwitch.fire();
			}
		});
		autoplayBox.setOnMouseClicked((e) -> {
			autoplaySwitch.fire();

			if (loopSwitch.isSelected()) {
				loopSwitch.fire();
			}

			if (shuffleSwitch.isSelected()) {
				shuffleSwitch.fire();
			}
		});

		shuffleSwitch.setOnMouseClicked((e) -> { // in addition to the hbox, also add same logic to the switch itself
													// (minus the .fire() part cause in that case the switch would
													// toggle twice in a row)

			if (loopSwitch.isSelected()) {
				loopSwitch.fire();
			}

			if (autoplaySwitch.isSelected()) {
				autoplaySwitch.fire();
			}

		});
		loopSwitch.setOnMouseClicked((e) -> {

			if (shuffleSwitch.isSelected()) {
				shuffleSwitch.fire();
			}

			if (autoplaySwitch.isSelected()) {
				autoplaySwitch.fire();
			}
		});
		autoplaySwitch.setOnMouseClicked((e) -> {

			if (loopSwitch.isSelected()) {
				loopSwitch.fire();
			}

			if (shuffleSwitch.isSelected()) {
				shuffleSwitch.fire();
			}
		});

		shuffleSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) { // ON
					shuffleOn = true;
				} else { // OFF
					shuffleOn = false;
				}
			}

		});

		loopSwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) { // ON
					loopOn = true;
				} else { // OFF
					loopOn = false;
				}
			}

		});

		autoplaySwitch.selectedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (newValue) { // ON
					autoplayOn = true;
				} else { // OFF
					autoplayOn = false;
				}
			}

		});
		
		
		customSpeedTrack.setProgress(0.75 / 1.75);

		customSpeedSlider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				formattedValue = Math.floor(newValue.doubleValue() * 20) / 20; // floors the new slider value to 2
																				// decimal points with the last decimal
																				// being only 5 or 0.

				mainController.mediaPlayer.setRate(formattedValue);

				double progress = (newValue.doubleValue() - 0.25) * 1 / 1.75; // adjust the slider scale ( 0.25 - 2 ) to
																				// match with the progress bar scale ( 0
																				// - 1 )

				customSpeedTrack.setProgress(progress);

				df = new DecimalFormat("#.##"); // makes it so that only the minimal amount of digits wil be displayed,
												// eg. 2 not 2.00

				customSpeedLabel.setText(df.format(formattedValue) + "x");

				if (playbackCustom == null) {

					switch (df.format(formattedValue)) {
					case "0.25": {
						playbackSpeedTracker = 1;
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox1.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(0);

					}
						break;
					case "0.5": {
						playbackSpeedTracker = 2;

						checkBox1.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox2.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(148 / playbackSpeedPage.getHeight());
					}
						break;
					case "0.75": {
						playbackSpeedTracker = 3;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox3.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(198 / playbackSpeedPage.getHeight());
					}
						break;
					case "1": {
						playbackSpeedTracker = 4;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox4.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(248 / playbackSpeedPage.getHeight());
					}
						break;
					case "1.25": {
						playbackSpeedTracker = 5;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox5.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(298 / playbackSpeedPage.getHeight());
					}
						break;
					case "1.5": {
						playbackSpeedTracker = 6;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox6.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(348 / playbackSpeedPage.getHeight());
					}
						break;
					case "1.75": {
						playbackSpeedTracker = 7;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox7.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(398 / playbackSpeedPage.getHeight());
					}
						break;
					case "2": {
						playbackSpeedTracker = 8;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(448 / playbackSpeedPage.getHeight());
					}
						break;
					default: {

						formattedValue2 = formattedValue;
						playbackCustom = new HBox();
						playbackCustomCheck = new Label();
						playbackCustomText = new Label();

						playbackCustom.setPrefWidth(235);
						playbackCustom.setPrefHeight(50);
						playbackCustom.setPadding(new Insets(0, 10, 0, 10));

						playbackCustomCheck.setPrefHeight(50);
						playbackCustomCheck.setPrefWidth(29);
						playbackCustomCheck.setPadding(new Insets(0, 5, 0, 0));
						playbackCustomCheck.setGraphic(new ImageView(check));

						switch (playbackSpeedTracker) {
						case 1:
							checkBox1.setGraphic(null);
							break;
						case 2:
							checkBox2.setGraphic(null);
							break;
						case 3:
							checkBox3.setGraphic(null);
							break;
						case 4:
							checkBox4.setGraphic(null);
							break;
						case 5:
							checkBox5.setGraphic(null);
							break;
						case 6:
							checkBox6.setGraphic(null);
							break;
						case 7:
							checkBox7.setGraphic(null);
							break;
						case 8:
							checkBox8.setGraphic(null);
							break;
						default:
							break;
						}

						playbackSpeedTracker = 0;

						playbackCustom.setOnMouseClicked((e) -> {

							switch (playbackSpeedTracker) {
							case 1:
								checkBox1.setGraphic(null);
								break;
							case 2:
								checkBox2.setGraphic(null);
								break;
							case 3:
								checkBox3.setGraphic(null);
								break;
							case 4:
								checkBox4.setGraphic(null);
								break;
							case 5:
								checkBox5.setGraphic(null);
								break;
							case 6:
								checkBox6.setGraphic(null);
								break;
							case 7:
								checkBox7.setGraphic(null);
								break;
							case 8:
								checkBox8.setGraphic(null);
								break;
							default:
								break;
							}

							playbackSpeedTracker = 0;
							playbackCustomCheck.setGraphic(new ImageView(check));
							mainController.mediaPlayer.setRate(formattedValue2);
							playbackValueLabel.setText(df.format(formattedValue2));
						});

						playbackCustom.setOnMouseEntered((e) -> {
							Utilities.hoverEffectOn(playbackCustom);
						});

						playbackCustom.setOnMouseExited((e) -> {
							Utilities.hoverEffectOff(playbackCustom);
						});

						playbackCustomText.setTextFill(Color.WHITE);
						playbackCustomText.setFont(new Font(15));
						playbackCustomText.setPrefHeight(50);
						playbackCustomText.setPrefWidth(186);
						playbackCustomText.setText("Custom " + "(" + df.format(formattedValue2) + ")");

						playbackValueLabel.setText(df.format(formattedValue2));

						playbackSpeedScroll.setVvalue(0);

						playbackCustom.getChildren().addAll(playbackCustomCheck, playbackCustomText);
						playbackSpeedPage.getChildren().add(2, playbackCustom);

					}
						break;
					}

				} else if (playbackCustom != null) {

					switch (df.format(formattedValue)) {
					case "0.25": {
						playbackCustomCheck.setGraphic(null);
						playbackSpeedTracker = 1;

						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox1.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(148 / playbackSpeedPage.getHeight());
						playbackValueLabel.setText(df.format(formattedValue));

					}
						break;
					case "0.5": {
						playbackCustomCheck.setGraphic(null);
						playbackSpeedTracker = 2;

						checkBox1.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox2.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(198 / playbackSpeedPage.getHeight());
						playbackValueLabel.setText(df.format(formattedValue));
					}
						break;
					case "0.75": {
						playbackCustomCheck.setGraphic(null);
						playbackSpeedTracker = 3;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox3.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(248 / playbackSpeedPage.getHeight());
						playbackValueLabel.setText(df.format(formattedValue));
					}
						break;
					case "1": {
						playbackCustomCheck.setGraphic(null);
						playbackSpeedTracker = 4;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox4.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(298 / playbackSpeedPage.getHeight());
						playbackValueLabel.setText("Normal");
					}
						break;
					case "1.25": {
						playbackCustomCheck.setGraphic(null);
						playbackSpeedTracker = 5;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox5.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(348 / playbackSpeedPage.getHeight());
						playbackValueLabel.setText(df.format(formattedValue));
					}
						break;
					case "1.5": {
						playbackCustomCheck.setGraphic(null);
						playbackSpeedTracker = 6;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox6.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(398 / playbackSpeedPage.getHeight());
						playbackValueLabel.setText(df.format(formattedValue));
					}
						break;
					case "1.75": {
						playbackCustomCheck.setGraphic(null);
						playbackSpeedTracker = 7;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox8.setGraphic(null);
						checkBox7.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(448 / playbackSpeedPage.getHeight());
						playbackValueLabel.setText(df.format(formattedValue));
					}
						break;
					case "2": {
						playbackCustomCheck.setGraphic(null);
						playbackSpeedTracker = 8;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(new ImageView(check));
						playbackSpeedScroll.setVvalue(498 / playbackSpeedPage.getHeight());
						playbackValueLabel.setText(df.format(formattedValue));
					}
						break;
					default: {

						formattedValue2 = formattedValue;

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);

						playbackCustomCheck.setGraphic(new ImageView(check));
						playbackSpeedTracker = 0;
						playbackCustomText.setText("Custom " + "(" + df.format(formattedValue2) + ")");
						playbackValueLabel.setText(df.format(formattedValue2));

						playbackSpeedScroll.setVvalue(0);
					}
						break;
					}

				}

			}

		});
		
		
		// On-hover effect for setting tab items
				//////////////////////////////////////////////////
				playbackSpeedBox.setOnMouseEntered((e) -> {
					Utilities.hoverEffectOn(playbackSpeedBox);
				});
				playbackSpeedBox.setOnMouseExited((e) -> {
					Utilities.hoverEffectOff(playbackSpeedBox);
				});

				playbackOptionsBox.setOnMouseEntered((e) -> {
					Utilities.hoverEffectOn(playbackOptionsBox);
				});
				playbackOptionsBox.setOnMouseExited((e) -> {
					Utilities.hoverEffectOff(playbackOptionsBox);
				});

				videoBox.setOnMouseEntered((e) -> {
					Utilities.hoverEffectOn(videoBox);
				});
				videoBox.setOnMouseExited((e) -> {
					Utilities.hoverEffectOff(videoBox);
				});
				

				// On-hover effect for playback speed items
				//////////////////////////////////////////////////

				for (int i = 0; i < playbackSpeedBoxesArray.length; i++) {

					final int j = i;

					playbackSpeedBoxesArray[i].setOnMouseEntered((e) -> {
						Utilities.hoverEffectOn(playbackSpeedBoxesArray[j]);
					});

					playbackSpeedBoxesArray[i].setOnMouseExited((e) -> {
						Utilities.hoverEffectOff(playbackSpeedBoxesArray[j]);
					});

				}
				
				

				/////////////////////////////////////////////////////////////
				////// Hover effect for playback options page ///////////////
				shuffleBox.setOnMouseEntered((e) -> {
					Utilities.hoverEffectOn(shuffleBox);
				});

				shuffleBox.setOnMouseExited((e) -> {
					Utilities.hoverEffectOff(shuffleBox);
				});

				loopBox.setOnMouseEntered((e) -> {
					Utilities.hoverEffectOn(loopBox);
				});

				loopBox.setOnMouseExited((e) -> {
					Utilities.hoverEffectOff(loopBox);
				});

				autoplayBox.setOnMouseEntered((e) -> {
					Utilities.hoverEffectOn(autoplayBox);
				});

				autoplayBox.setOnMouseExited((e) -> {
					Utilities.hoverEffectOff(autoplayBox);
				});
				
				
				settingsBackgroundPane.setPickOnBounds(false);

				bufferPane.prefWidthProperty().bind(settingsBackgroundPane.widthProperty());
				bufferPane.prefHeightProperty().bind(settingsBackgroundPane.heightProperty());


				customSpeedBuffer.prefHeightProperty().bind(settingsBackgroundPane.heightProperty());
				customSpeedBuffer.prefWidthProperty().bind(settingsBackgroundPane.widthProperty());


				playbackSpeedScroll.translateYProperty()
						.bind(Bindings.subtract(settingsBackgroundPane.heightProperty(), playbackSpeedScroll.heightProperty()));

				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						
						if (playbackCustom != null) {
							playbackSpeedScroll.prefHeightProperty().bind(Bindings.min(537, Bindings.subtract(mainController.mediaViewHeight, 100)));
						} else {
							playbackSpeedScroll.prefHeightProperty().bind(Bindings.min(487, Bindings.subtract(mainController.mediaViewHeight, 100)));
						}
						
						//this can surely be improved
						playbackOptionsBuffer.setTranslateX(settingsBackgroundPane.getWidth());
						playbackSpeedScroll.setTranslateX(settingsBackgroundPane.getWidth());
						customSpeedBuffer.setTranslateX(settingsBackgroundPane.getWidth());

					}

				});
				playbackOptionsBuffer.translateYProperty().bind(
						Bindings.subtract(settingsBackgroundPane.heightProperty(), playbackOptionsBuffer.heightProperty()));


				bufferPane.setTranslateY(170);

				// Clipping for the settings pane
				Rectangle rectangle = new Rectangle(settingsBackgroundPane.getWidth(), settingsBackgroundPane.getHeight());
				rectangle.widthProperty().bind(settingsBackgroundPane.widthProperty());
				rectangle.heightProperty().bind(settingsBackgroundPane.heightProperty());
				settingsBackgroundPane.setClip(rectangle);

				/////////////////////////////////
				// mouse listeners for playback speed

				for (int i = 0; i < playbackSpeedBoxesArray.length; i++) {

					final int I = i;


					playbackSpeedBoxesArray[i].setOnMouseClicked((e) -> {

						if (playbackCustomCheck != null) {
							playbackCustomCheck.setGraphic(null);
						}

						checkBox1.setGraphic(null);
						checkBox2.setGraphic(null);
						checkBox3.setGraphic(null);
						checkBox4.setGraphic(null);
						checkBox5.setGraphic(null);
						checkBox6.setGraphic(null);
						checkBox7.setGraphic(null);
						checkBox8.setGraphic(null);

						playbackSpeedCheckBoxesArray[I].setGraphic(new ImageView(check));

						playbackSpeedTracker = I + 1;

						double temp = playbackSpeedTracker;

						mainController.mediaPlayer.setRate(temp / 4);

						if (I == 3) {
							playbackValueLabel.setText("Normal");
						} else {
							playbackValueLabel.setText(String.valueOf(temp / 4));
						}

					});
				}
		
	}
	
	public void init(MainController mainController, ControlBarController controlBarController, AnimationsClass animationsClass) {
		this.mainController = mainController;
		this.controlBarController = controlBarController;
		this.animationsClass = animationsClass;
	}
	
	public   void openCloseSettings() {
		if (settingsOpen) {
			if (!playbackSpeedOpen && !playbackOptionsOpen) {
				controlBarController.settingsExit = new Image(controlBarController.settingsExitFile.toURI().toString());
				controlBarController.settingsIcon.setImage(controlBarController.settingsExit);
				settingsOpen = false;

				FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), bufferPane);
				fadeTransition1.setFromValue(1);
				fadeTransition1.setToValue(0.0f);
				fadeTransition1.setCycleCount(1);

				TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), bufferPane);
				translateTransition1.setFromY(0);
				translateTransition1.setToY(bufferPane.getHeight());
				translateTransition1.setCycleCount(1);

				ParallelTransition parallelTransition = new ParallelTransition();
				parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
				parallelTransition.setCycleCount(1);
				parallelTransition.play();
			} else if (playbackOptionsOpen) {
				// CLOSING ANIMATION WHEN PLAYBACK OPTIONS PAGE IS OPEN

				controlBarController.settingsExit = new Image(controlBarController.settingsExitFile.toURI().toString());
				controlBarController.settingsIcon.setImage(controlBarController.settingsExit);
				settingsOpen = false;
				playbackSpeedOpen = false;
				customSpeedOpen = false;
				playbackOptionsOpen = false;

				settingsBackgroundPane.prefHeightProperty().unbind();
				playbackOptionsBuffer.translateYProperty().unbind();
				FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), playbackOptionsBuffer);
				fadeTransition1.setFromValue(1);
				fadeTransition1.setToValue(0.0f);
				fadeTransition1.setCycleCount(1);
				TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100),
						playbackOptionsBuffer);
				translateTransition1.setFromY(playbackOptionsBuffer.getTranslateY());
				translateTransition1.setToY(bufferPane.getHeight());
				translateTransition1.setCycleCount(1);

				ParallelTransition parallelTransition = new ParallelTransition();
				parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
				parallelTransition.setCycleCount(1);
				parallelTransition.play();

				parallelTransition.setOnFinished((e) -> {
					playbackOptionsBuffer.setTranslateX(settingsBackgroundPane.getWidth());
					bufferPane.setTranslateX(0);
					settingsBackgroundPane.setPrefHeight(170);
					playbackOptionsBuffer
							.setTranslateY(settingsBackgroundPane.getHeight() - playbackOptionsBuffer.getHeight());
					playbackOptionsBuffer.setOpacity(1);
					playbackOptionsBuffer.translateYProperty().bind(Bindings
							.subtract(settingsBackgroundPane.heightProperty(), playbackOptionsBuffer.heightProperty()));
					bufferPane.setTranslateY(bufferPane.getHeight());

				});

			} else if (!customSpeedOpen) {
				controlBarController.settingsExit = new Image(controlBarController.settingsExitFile.toURI().toString());
				controlBarController.settingsIcon.setImage(controlBarController.settingsExit);
				settingsOpen = false;
				playbackSpeedOpen = false;

				settingsBackgroundPane.prefHeightProperty().unbind();

				playbackSpeedScroll.translateYProperty().unbind();

				FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), playbackSpeedScroll);
				fadeTransition1.setFromValue(1);
				fadeTransition1.setToValue(0.0f);
				fadeTransition1.setCycleCount(1);
				TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100),
						playbackSpeedScroll);
				translateTransition1.setFromY(playbackSpeedScroll.getTranslateY());
				translateTransition1.setToY(bufferPane.getHeight());
				translateTransition1.setCycleCount(1);

				/*
				 * FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(100),
				 * bufferPane); fadeTransition2.setFromValue(0.8f);
				 * fadeTransition2.setToValue(0.0f); fadeTransition2.setCycleCount(1);
				 * TranslateTransition translateTransition2 = new
				 * TranslateTransition(Duration.millis(100), bufferPane);
				 * translateTransition2.setFromY(0);
				 * translateTransition2.setToY(bufferPane.getHeight());
				 * translateTransition2.setCycleCount(1);
				 */

				ParallelTransition parallelTransition = new ParallelTransition();
				parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
				parallelTransition.setCycleCount(1);
				parallelTransition.play();

				parallelTransition.setOnFinished((e) -> {
					playbackSpeedScroll.setTranslateX(settingsBackgroundPane.getWidth());
					bufferPane.setTranslateX(0);
					settingsBackgroundPane.setPrefHeight(170);
					playbackSpeedScroll
							.setTranslateY(settingsBackgroundPane.getHeight() - playbackSpeedScroll.getHeight());
					playbackSpeedScroll.setOpacity(1);
					playbackSpeedScroll.translateYProperty().bind(Bindings
							.subtract(settingsBackgroundPane.heightProperty(), playbackSpeedScroll.heightProperty()));
					bufferPane.setTranslateY(bufferPane.getHeight());

					playbackSpeedScroll.setVvalue(0);
				});
			} else if (customSpeedOpen) {
				// TODO: Settings closing animation when custom playback speed selector is open.
				controlBarController.settingsExit = new Image(controlBarController.settingsExitFile.toURI().toString());
				controlBarController.settingsIcon.setImage(controlBarController.settingsExit);
				settingsOpen = false;
				playbackSpeedOpen = false;
				customSpeedOpen = false;

				settingsBackgroundPane.prefHeightProperty().unbind();

				playbackSpeedScroll.translateYProperty().unbind();

				FadeTransition fadeTransition1 = new FadeTransition(Duration.millis(100), customSpeedBuffer);
				fadeTransition1.setFromValue(1);
				fadeTransition1.setToValue(0.0f);
				fadeTransition1.setCycleCount(1);

				TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100),
						customSpeedBuffer);
				translateTransition1.setFromY(0);
				translateTransition1.setToY(customSpeedBuffer.getHeight());
				translateTransition1.setCycleCount(1);

				ParallelTransition parallelTransition = new ParallelTransition();
				parallelTransition.getChildren().addAll(fadeTransition1, translateTransition1);
				parallelTransition.setCycleCount(1);
				parallelTransition.play();

				parallelTransition.setOnFinished((e) -> {
					playbackSpeedScroll.setTranslateX(settingsBackgroundPane.getWidth());
					bufferPane.setTranslateX(0);
					settingsBackgroundPane.setPrefHeight(170);
					playbackSpeedScroll
							.setTranslateY(settingsBackgroundPane.getHeight() - playbackSpeedScroll.getHeight());
					playbackSpeedScroll.setOpacity(1);
					playbackSpeedScroll.translateYProperty().bind(Bindings
							.subtract(settingsBackgroundPane.heightProperty(), playbackSpeedScroll.heightProperty()));
					bufferPane.setTranslateY(bufferPane.getHeight());

					customSpeedBuffer.setOpacity(1);
					customSpeedBuffer.setTranslateX(settingsBackgroundPane.getWidth());
					customSpeedBuffer.setTranslateY(0);

					playbackSpeedScroll.setVvalue(0);
				});
			}

		}

		else {
			controlBarController.settingsEnter = new Image(controlBarController.settingsEnterFile.toURI().toString());
			controlBarController.settingsIcon.setImage(controlBarController.settingsEnter);
			settingsOpen = true;
			
			animationsClass.openSettings(bufferPane);
			
			
		}
	}

	public void openPlaybackSpeedPage() {

		playbackSpeedOpen = true;

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition1.setFromX(0);
		translateTransition1.setToX(-settingsBackgroundPane.getWidth());
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(settingsBackgroundPane.getWidth());
		translateTransition2.setToX(0);
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);

		double toHeight;
		if (playbackCustom != null) {
			toHeight = mainController.mediaView.sceneProperty().get().getHeight() < 637
					? mainController.mediaView.sceneProperty().get().getHeight() - 100
					: 537;
		} else {
			toHeight = mainController.mediaView.sceneProperty().get().getHeight() < 587
					? mainController.mediaView.sceneProperty().get().getHeight() - 100
					: 487;
		}

		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100),
				new KeyValue(settingsBackgroundPane.prefHeightProperty(), toHeight, Interpolator.LINEAR)));

		/*
		 * settingsTimeline.setOnFinished((e) -> { settingsTimeline.stop();
		 * settingsTimeline.getKeyFrames().clear(); });
		 */

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		/*
		 * SequentialTransition seqTrans = new SequentialTransition();
		 * seqTrans.getChildren().addAll(parallelTransition, translateTransition2);
		 * seqTrans.play();
		 */

		// settingsTimeline.play();

		parallelTransition.setOnFinished((e) -> {
			settingsBackgroundPane.prefHeightProperty()
					.bind(Bindings.max(Bindings.add(playbackSpeedScroll.heightProperty(), 40), 170));
		});
	}
	
	public void closePlaybackSpeedPage() {

		playbackSpeedOpen = false;

		settingsBackgroundPane.prefHeightProperty().unbind();

		settingsBackgroundPane.setPrefHeight(playbackSpeedScroll.getHeight());

		// bufferPane.prefHeightProperty().unbind();

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition1.setFromX(-settingsBackgroundPane.getWidth());
		translateTransition1.setToX(0);
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(settingsBackgroundPane.getWidth());
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100),
				new KeyValue(settingsBackgroundPane.prefHeightProperty(), 170, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			playbackSpeedScroll.setVvalue(0);
		});

	}
	
	
	public void openCustomSpeed() {
		customSpeedOpen = true;

		settingsBackgroundPane.prefHeightProperty().unbind();

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), customSpeedBuffer);
		translateTransition1.setFromX(settingsBackgroundPane.getWidth());
		translateTransition1.setToX(0);
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-playbackSpeedScroll.getWidth() - 1.5);
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100),
				new KeyValue(settingsBackgroundPane.prefHeightProperty(), 130, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			playbackSpeedScroll.setVvalue(0);
		});

	}

	public void closeCustomSpeed() {
		customSpeedOpen = false;

		// settingsBackgroundPane.prefHeightProperty().unbind();

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), customSpeedBuffer);
		translateTransition1.setFromX(0);
		translateTransition1.setToX(settingsBackgroundPane.getWidth());
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), playbackSpeedScroll);
		translateTransition2.setFromX(-settingsBackgroundPane.getWidth());
		translateTransition2.setToX(0);
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);

		double toHeight;
		if (playbackCustom != null) {
			toHeight = mainController.mediaView.sceneProperty().get().getHeight() < 637
					? mainController.mediaView.sceneProperty().get().getHeight() - 100
					: 537;
		} else {
			toHeight = mainController.mediaView.sceneProperty().get().getHeight() < 587
					? mainController.mediaView.sceneProperty().get().getHeight() - 100
					: 487;
		}

		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100),
				new KeyValue(settingsBackgroundPane.prefHeightProperty(), toHeight, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

		parallelTransition.setOnFinished((e) -> {
			settingsBackgroundPane.prefHeightProperty().bind(Bindings.add(playbackSpeedScroll.heightProperty(), 40));
		});

	}

	public void openPlaybackOptions() {

		playbackOptionsOpen = true;

		// OPENING ANIMATION GOES HERE//

		settingsBackgroundPane.prefHeightProperty().unbind();

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), playbackOptionsBuffer);
		translateTransition1.setFromX(settingsBackgroundPane.getWidth());
		translateTransition1.setToX(0);
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition2.setFromX(0);
		translateTransition2.setToX(-settingsBackgroundPane.getWidth());
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100),
				new KeyValue(settingsBackgroundPane.prefHeightProperty(), 230, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();

	}

	public void closePlaybackOptions() {

		playbackOptionsOpen = false;

		// CLOSING ANIMATION GOES HERE//

		TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(100), playbackOptionsBuffer);
		translateTransition1.setFromX(0);
		translateTransition1.setToX(settingsBackgroundPane.getWidth());
		translateTransition1.setCycleCount(1);
		translateTransition1.setInterpolator(Interpolator.LINEAR);

		TranslateTransition translateTransition2 = new TranslateTransition(Duration.millis(100), bufferPane);
		translateTransition2.setFromX(-settingsBackgroundPane.getWidth());
		translateTransition2.setToX(0);
		translateTransition2.setCycleCount(1);
		translateTransition2.setInterpolator(Interpolator.LINEAR);

		Timeline settingsTimeline1 = new Timeline();

		settingsTimeline1.setCycleCount(1);
		settingsTimeline1.setAutoReverse(false);
		settingsTimeline1.getKeyFrames().add(new KeyFrame(Duration.millis(100),
				new KeyValue(settingsBackgroundPane.prefHeightProperty(), 170, Interpolator.LINEAR)));

		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(translateTransition1, translateTransition2, settingsTimeline1);
		parallelTransition.setCycleCount(1);
		parallelTransition.play();
	}
	
}
