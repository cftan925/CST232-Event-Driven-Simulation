package application;

import java.io.IOException;
import application.Main;
import application.Event;
import javafx.fxml.FXML;

public class DynamicMemoryController {
	
	private Main main;
	private Event event;
	
	@FXML
	private void goDynamicMemory1() throws IOException{
		main.memoryBlock = 20000;
		main.runEvent();
	}

	@FXML
	private void goDynamicMemory2() throws IOException{
		main.memoryBlock = 30000;
		main.runEvent();
	}
	
	@FXML
	private void goDynamicMemory3() throws IOException{
		main.memoryBlock = 40000;
		main.runEvent();
	}
	
	@FXML
	private void goDynamicMemory4() throws IOException{
		main.memoryBlock = 50000;
		main.runEvent();
	}
}
