package application;

import java.io.IOException;
import javafx.fxml.FXML;

public class MainViewController {
	
	@FXML
	private void goHome() throws IOException{
		Main.showPartitionMenu();
		Event.totalProcessTime=0;
		Event.totalRunJob=0;
		Event.previousTime=0;
		Allocation.totalFrag=0;
		Waiting.minWaitingTime=0;
		Waiting.maxWaitingTime=0;
		Waiting.totalWaitingTime=0;
		Waiting.waitingList.clear();
		Queue.queueLength=0;
		Queue.maxQueLength=0;
		Queue.queue.clear();
		Event.eventList.clear();
		Event.memoryList.clear();
		Event.waiting.clear();
		Event.table.clear();
		Event.block.clear();
	}
	
}
