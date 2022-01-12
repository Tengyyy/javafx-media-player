package application;


import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.util.Duration;

public class ControlTooltip extends Tooltip{

	String tooltipText;
	Button tooltipParent;

	
	double tooltipMiddle;
	double tooltipHeight;
	
	double nodeMiddle;
	
	ControlTooltip(String tooltipText, Button tooltipParent ){
		
		this.tooltipText = tooltipText;
		this.tooltipParent = tooltipParent;
		this.setText(tooltipText);
		this.setShowDelay(Duration.ZERO);
		
		//tooltipParent.setTooltip(this);
		
		this.show(tooltipParent, 0, 0);
		tooltipMiddle = (this.getWidth() - 18) / 2;
		tooltipHeight = this.getHeight();
		this.hide();
		
		tooltipParent.setOnMouseEntered((e) -> {
			Bounds bounds = tooltipParent.localToScreen(tooltipParent.getBoundsInLocal());
			nodeMiddle = tooltipParent.getWidth() / 2;
			
			this.show(tooltipParent, bounds.getMinX() + nodeMiddle - tooltipMiddle, bounds.getMinY() - tooltipHeight);
		});
		
		tooltipParent.setOnMouseExited((e) -> {
			this.hide();
		});
		
	}
	
	public void showTooltip() {
		Bounds bounds = tooltipParent.localToScreen(tooltipParent.getBoundsInLocal());
		nodeMiddle = tooltipParent.getWidth() / 2;
		
		this.show(tooltipParent, bounds.getMinX() + nodeMiddle - tooltipMiddle, bounds.getMinY() - tooltipHeight);
		
	}
	
	

}
