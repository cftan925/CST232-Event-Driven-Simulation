package application;

import java.io.IOException;
import application.Main;
import javafx.fxml.FXML;

public class DynamicController {

	private Main main;
	
	@FXML
	private void goDynamicFirst() throws IOException{
		main.algoOption = 1;
		main.showDynamicMemory();
	}
	
	@FXML
	private void goDynamicBest() throws IOException{
		main.algoOption = 2;
		main.showDynamicMemory();
	}
	
	@FXML
	private void goDynamicWorst() throws IOException{
		main.algoOption = 3;
		main.showDynamicMemory();
	}
}
