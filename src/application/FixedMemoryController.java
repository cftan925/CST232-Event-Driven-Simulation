package application;

import java.io.IOException;
import application.Main;
import application.Event;
import javafx.fxml.FXML;

public class FixedMemoryController {
	
	private Main main;
	private Event event;
	
	@FXML
	private void goFixedMemory1() throws IOException{
		main.memoryBlock = 3;
		main.runEvent();
	}

	@FXML
	private void goFixedMemory2() throws IOException{
		main.memoryBlock = 5;
		main.runEvent();
	}
	
	@FXML
	private void goFixedMemory3() throws IOException{
		main.memoryBlock = 7;
		main.runEvent();
	}
	
	@FXML
	private void goFixedMemory4() throws IOException{
		main.memoryBlock = 10;
		main.runEvent();
	}
}
