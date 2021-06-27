package application;

import java.io.IOException;
import application.Main;
import javafx.fxml.FXML;

public class FixedController {

	private Main main;
	
	@FXML
	private void goFixedFirst() throws IOException{
		main.algoOption = 1;
		main.showFixedMemory();
	}
	
	@FXML
	private void goFixedBest() throws IOException{
		main.algoOption = 2;
		main.showFixedMemory();
	}
	
}