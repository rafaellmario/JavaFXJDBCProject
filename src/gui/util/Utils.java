package gui.util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {
	
	// methods
	// Access the stage where my event occurs
	public static Stage currentStage(ActionEvent event) {
		return (Stage)(((Node)event.getSource()).getScene().getWindow());
	}


}
