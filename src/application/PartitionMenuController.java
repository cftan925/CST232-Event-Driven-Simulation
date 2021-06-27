package application;

import java.io.IOException;
import application.Main;
import javafx.fxml.FXML;

public class PartitionMenuController {

	private Main main;
	
	@FXML
	private void goDynamic() throws IOException{
		main.partitionChoice = 2;
		main.showDynamicMenu();
	}
	
	@FXML
	private void goFixed() throws IOException {
		main.partitionChoice = 1;
		main.showFixedMenu();
	}
}
