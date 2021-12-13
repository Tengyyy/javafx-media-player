package application;

import java.io.File;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXToggleButton;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
	Label playbackValueLabel, playbackOptionsArrow, playbackSpeedArrow, playbackSpeedTitleLabel, playbackSpeedCustom, checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, customSpeedArrow, customSpeedTitleLabel, customSpeedLabel, playbackOptionsTitleArrow, playbackOptionsTitleText, shuffleLabel, loopLabel, autoplayLabel, videoArrowLabel;
	

	@FXML
	ScrollPane playbackSpeedScroll;

	@FXML
	JFXToggleButton shuffleSwitch, loopSwitch, autoplaySwitch;
	
	@FXML
	HBox playbackSpeedBox, playbackOptionsBox, videoBox, videoNameBox, playbackSpeedTitle, playbackSpeed1, playbackSpeed2, playbackSpeed3, playbackSpeed4, playbackSpeed5, playbackSpeed6, playbackSpeed7, playbackSpeed8, customSpeedTitle, shuffleBox, loopBox, autoplayBox, playbackOptionsTitle;

	@FXML
	Text videoNameText;
	
	
	
	private MainController mainController;
	private ControlBarController controlBarController;
	
	
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
	
	FileChooser fileChooser;
	

	// counter to keep track of which playback speed field is selected in the settings menu
	int playbackSpeedTracker = 4;
	

	public  boolean settingsOpen = false; // true if settings home is open
	boolean playbackSpeedOpen = false;
	boolean playbackOptionsOpen = false;
	boolean customSpeedOpen = false;
	
	private File rightArrowFile, leftArrowFile, checkFile;	
	
	
	final double OFFSET = 0;
	
	
	HBox[] playbackSpeedBoxesArray; // array containing playback speed selection fields
	Label[] playbackSpeedCheckBoxesArray; // array containing checkmark fields inside playback speed tab
	
	
	
	Timeline videoNameTimeline = new Timeline();
	Timeline resetTimeline = new Timeline();
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		

		fileChooser = new FileChooser();
		fileChooser.setTitle("Open video");
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Videos","*.mp4"));
		
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
			openVideoChooser();
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

				/*videoBox.setOnMouseEntered((e) -> {
					Utilities.hoverEffectOn(videoBox);
				});
				videoBox.setOnMouseExited((e) -> {
					Utilities.hoverEffectOff(videoBox);
				});*/
				

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
				
			    videoNameText.setManaged(false);
			    videoNameText.setLayoutY(30);
			    videoNameText.setLayoutX(OFFSET);
			    
			    Rectangle videoNameClip = new Rectangle(195, 50);
			    videoNameBox.setClip(videoNameClip);

			    KeyFrame updateFrame = new KeyFrame(Duration.seconds(1 / 60d), new EventHandler<ActionEvent>() {

			    	private boolean rightMovement;
			    	
			        @Override
			        public void handle(ActionEvent event) {
			            double tW = videoNameText.getLayoutBounds().getWidth();
			            double pW = videoNameBox.getWidth();
			            double layoutX = videoNameText.getLayoutX();

			                if ((rightMovement && layoutX >= OFFSET) || (!rightMovement && layoutX + tW + OFFSET <= pW)) {
			                    // invert movement, if bounds are reached
			                    rightMovement = !rightMovement;
			                }

			                // update position
			                if (rightMovement) {
			                    layoutX += 0.5;
			                } else {
			                    layoutX -= 0.5;
			                }
			                videoNameText.setLayoutX(layoutX);
			        }
			    });

			    videoNameTimeline.getKeyFrames().add(updateFrame);
			    videoNameTimeline.setCycleCount(Animation.INDEFINITE);
			    
			    KeyFrame resetFrame = new KeyFrame(Duration.seconds(1/60d), new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						
			            double layoutX = videoNameText.getLayoutX();

			            if(Math.round(layoutX) == 0) {
			            	resetTimeline.stop();
			            }
			            else if(layoutX < 0)
			            	layoutX += 1;
			            
			            
			            videoNameText.setLayoutX(layoutX);
					}
			    });

			    resetTimeline.getKeyFrames().add(resetFrame);
			    resetTimeline.setCycleCount(Animation.INDEFINITE);
			    
			    videoBox.setOnMouseEntered((e) -> {
					Utilities.hoverEffectOn(videoBox);
			    	if(videoNameTimeline.getStatus() != Animation.Status.RUNNING && resetTimeline.getStatus() != Animation.Status.RUNNING && videoNameText.getLayoutBounds().getWidth() > videoNameBox.getWidth()) {
					    videoNameText.setLayoutX(OFFSET);
			        	videoNameTimeline.play();
			    	}
			    });
			    
			    videoBox.setOnMouseExited((e) -> {
					Utilities.hoverEffectOff(videoBox);
			    	if(videoNameTimeline.getStatus() == Animation.Status.RUNNING) {
			    		videoNameTimeline.stop();
					    resetTimeline.play();
			    	}
			    });

		
	}
	
	public void init(MainController mainController, ControlBarController controlBarController) {
		this.mainController = mainController;
		this.controlBarController = controlBarController;
	}
	
	public void settingsClick() { // this needs to be moved to controlBarController
		
		if(settingsOpen)
			closeSettings();
		else
			openSettings();
		
	}
	
	public void openSettings() {
		controlBarController.settingsEnter = new Image(controlBarController.settingsEnterFile.toURI().toString());
		controlBarController.settingsIcon.setImage(controlBarController.settingsEnter);
		settingsOpen = true;
		
		AnimationsClass.openSettings(bufferPane);

	}
	
	public void closeSettings() {
		
		controlBarController.settingsExit = new Image(controlBarController.settingsExitFile.toURI().toString());
		controlBarController.settingsIcon.setImage(controlBarController.settingsExit);
		
		if(settingsOpen) {
			settingsOpen = false;
			AnimationsClass.closeSettings(bufferPane);
		}
		else if(playbackOptionsOpen) {
			playbackOptionsOpen = false;
			AnimationsClass.closeSettingsFromPlaybackOptions(settingsBackgroundPane, playbackOptionsBuffer, bufferPane);
		}
		else if(playbackSpeedOpen) {
			playbackSpeedOpen = false;
			AnimationsClass.closeSettingsFromPlaybackSpeed(settingsBackgroundPane, playbackSpeedScroll, bufferPane);
		}
		else if(customSpeedOpen) {
			customSpeedOpen = false;
			AnimationsClass.closeSettingsFromCustomSpeed(settingsBackgroundPane, playbackSpeedScroll, customSpeedBuffer, bufferPane);
		}
	}


	public void openPlaybackSpeedPage() {

		playbackSpeedOpen = true;
		settingsOpen = false;

		double toHeight;
		if (playbackCustom != null)
			toHeight = mainController.mediaView.sceneProperty().get().getHeight() < 637 ? mainController.mediaView.sceneProperty().get().getHeight() - 100 : 537;
		else
			toHeight = mainController.mediaView.sceneProperty().get().getHeight() < 587 ? mainController.mediaView.sceneProperty().get().getHeight() - 100 : 487;
		
		AnimationsClass.openPlaybackSpeed(bufferPane, settingsBackgroundPane, playbackSpeedScroll, toHeight);
		
	}
	
	public void closePlaybackSpeedPage() {

		playbackSpeedOpen = false;
		settingsOpen = true;

		AnimationsClass.closePlaybackSpeed(settingsBackgroundPane, playbackSpeedScroll, bufferPane);

	}
	
	
	public void openCustomSpeed() {
		customSpeedOpen = true;
		playbackSpeedOpen = false;

		AnimationsClass.openCustomSpeed(settingsBackgroundPane, customSpeedBuffer, playbackSpeedScroll);

	}

	public void closeCustomSpeed() {
		customSpeedOpen = false;
		playbackSpeedOpen = true;

		double toHeight;
		if (playbackCustom != null)
			toHeight = mainController.mediaView.sceneProperty().get().getHeight() < 637 ? mainController.mediaView.sceneProperty().get().getHeight() - 100 : 537;
		else 
			toHeight = mainController.mediaView.sceneProperty().get().getHeight() < 587 ? mainController.mediaView.sceneProperty().get().getHeight() - 100 : 487;
		
		AnimationsClass.closeCustomSpeed(customSpeedBuffer, settingsBackgroundPane, playbackSpeedScroll, toHeight);
		
	}

	public void openPlaybackOptions() {

		playbackOptionsOpen = true;
		settingsOpen = false;
		
		AnimationsClass.openPlaybackOptions(settingsBackgroundPane, playbackOptionsBuffer, bufferPane);

	}

	public void closePlaybackOptions() {

		playbackOptionsOpen = false;
		settingsOpen = true;

		AnimationsClass.closePlaybackOptions(playbackOptionsBuffer, settingsBackgroundPane, bufferPane);
		
	}
	
	public void openVideoChooser() {
		File selectedFile = fileChooser.showOpenDialog(Main.stage);

		if (selectedFile != null) {
			videoNameText.setText(selectedFile.getName());
			Main.stage.setTitle(selectedFile.getName());
			
			if(videoNameTimeline.getStatus() == Animation.Status.RUNNING) {
				videoNameTimeline.stop();
				videoNameText.setLayoutX(0);
			}
			else if(resetTimeline.getStatus() == Animation.Status.RUNNING) {
				resetTimeline.stop();
				videoNameText.setLayoutX(0);

			}
			
			////////////// this can be turned into one mediaplayer cleaning method
			mainController.mediaPlayer.dispose();
			mainController.atEnd = false;
			mainController.seekedToEnd = false;
			mainController.playing = false;
			mainController.wasPlaying = false;
			//////////////
			
			
			mainController.createMediaPlayer(selectedFile);

		}

	}
	
}
