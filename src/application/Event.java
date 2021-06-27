package application;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;


public class Event implements Initializable{
	
	public static LinkedList<Node.EventNode> eventList = new LinkedList<Node.EventNode>();
	public static LinkedList<Node.MemoryNode> memoryList = new LinkedList<Node.MemoryNode>();
	public static Node.EventNode eventPointer;
	public static int previousTime,timeInterval,totalRunJob;
	public static float totalProcessTime;
	
	@FXML public TableView<Node.Cell> eventTable;
	@FXML public ListView<Integer> waitingList;
	@FXML public ListView<String> memoryBlock;
	@FXML public TableColumn<Node.Cell,Integer> eventTime;
	@FXML public TableColumn<Node.Cell,Integer> jobNum;
	@FXML public TableColumn<Node.Cell,String> event;
	//
	@FXML public TableColumn<Node.Cell,Integer> jobSize;
	@FXML public TableColumn<Node.Cell,Integer> processTime;
	//
	@FXML public TextField throughput;
	@FXML public TextField avrQueueLength;
	@FXML public TextField maxQueLength;
	@FXML public TextField totalWaitingTime;
	@FXML public TextField avrWaitingTime;
	@FXML public TextField minWaitingTime;
	@FXML public TextField maxWaitingTime;
	@FXML public TextField totalFrag;
	@FXML public TextField avrFrag;
	@FXML public Label currentTime;
	public static ObservableList<Node.Cell> table = FXCollections.observableArrayList();
	public static ObservableList<Integer> waiting = FXCollections.observableArrayList();
	public static ObservableList<String> block = FXCollections.observableArrayList();
	
	public Event() {
		for (Node.JobNode i : ReadFile.jobList) {
			Node.EventNode newEvent = new Node.EventNode(i, i.arrivalTime, "Running");
			//Change the default event to running since most jobs can be run as it arrives
			if(!eventList.isEmpty())
			eventList.getLast().next=newEvent;
			eventList.add(newEvent);
		}
		memoryList = CreateMemoryList();
		eventPointer = eventList.getFirst();
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		eventTime.setCellValueFactory(new PropertyValueFactory<Node.Cell, Integer>("eventTime"));
		jobNum.setCellValueFactory(new PropertyValueFactory<Node.Cell, Integer>("jobNum"));
		event.setCellValueFactory(new PropertyValueFactory<Node.Cell, String>("event"));
		//
		jobSize.setCellValueFactory(new PropertyValueFactory<Node.Cell, Integer>("jobSize"));
		processTime.setCellValueFactory(new PropertyValueFactory<Node.Cell, Integer>("processTime"));
		//
		eventTable.setItems(table);
		waitingList.setItems(waiting);
		memoryBlock.setItems(block);
	}
	
	public static LinkedList<Node.MemoryNode> CreateMemoryList() {
		Node.MemoryNode newMemory;
		if (Main.partitionChoice==1) {
			for (int i=0; i<Main.memoryBlock; i++) {
				newMemory = new Node.MemoryNode(ReadFile.memoryList.get(i).memSize, 0);
				if (!memoryList.isEmpty()) {
					memoryList.getLast().next=newMemory;
				}
				memoryList.add(newMemory);
				block.add("Empty "+"| Memory Size: "+newMemory.memSize);
			}
		}
		else {
			newMemory = new Node.MemoryNode(Main.memoryBlock,0);
			block.add("Empty "+"| Memory Size: "+newMemory.memSize);
			memoryList.add(newMemory);
		}
		return memoryList;
	}
	
	public static void CreateDoneEvent(Node.JobNode doneJob) {
		int currentEvent;
		int doneTime=eventPointer.eventTime + doneJob.processTime;
		currentEvent = eventList.indexOf(eventPointer);
		Node.EventNode newDoneNode = new Node.EventNode(doneJob,doneTime,"Done");
		totalProcessTime += doneJob.processTime;
		totalRunJob++;
	
		while(eventPointer.next != null && doneTime > eventPointer.next.eventTime) {
			eventPointer = eventPointer.next;
		}
		
		if(eventPointer.next != null){
			newDoneNode.next = eventPointer.next;
			eventPointer.next = newDoneNode;
			eventList.add(eventList.indexOf(eventPointer)+1, newDoneNode);
		}
		else {
			eventList.getLast().next=newDoneNode;
			newDoneNode.next = null;
			eventList.add(newDoneNode);
		}
		eventPointer = eventList.get(currentEvent);
	}
		
	public void RunEvent() {
		/*while(eventPointer!=null) {
			if(eventPointer.eventTime != previousTime) {
				timeInterval = eventPointer.eventTime - previousTime;
				currentTime.setText("EVENT TIME "+eventPointer.eventTime);
				previousTime= eventPointer.eventTime;
				if (!Waiting.waitingList.isEmpty()) {
					Waiting.UpdateWaitingTime(timeInterval);
				}
				Queue.UpdateQueueLength(timeInterval);
			}
			
			if(eventPointer.event=="Running") {
				if(!Allocation.Allocate(eventPointer.eventJob))
				{
					eventPointer.event="Waiting";
					Waiting.AddWaitingJob(eventPointer.eventJob);
					Queue.queueLength++;
				}
				Display();
				table.add(new Node.Cell(
						eventPointer.eventTime,
						eventPointer.eventJob.jobNum,
						eventPointer.event,
						eventPointer.eventJob.jobSize,
						eventPointer.eventJob.processTime));
			}
			else if(eventPointer.event=="Done"){
				Deallocation.Deallocate(eventPointer.eventJob);
				Display();
				//Display done job in eventTable
				table.add(new Node.Cell(
						eventPointer.eventTime,
						eventPointer.eventJob.jobNum,
						eventPointer.event,
						eventPointer.eventJob.jobSize,
						eventPointer.eventJob.processTime));
				if(!Waiting.waitingList.isEmpty() && (eventPointer.next==null || eventPointer.eventTime!=eventPointer.next.eventTime || eventPointer.next.event!="Done"))
				{
					eventPointer.event="Dequeue";
					Waiting.waitingPointer=Waiting.waitingList.getFirst();
					continue;
				}
			}
			else //event is Dequeue
			{
				while(Waiting.waitingPointer!=null)
				{
					if(Allocation.Allocate(Waiting.waitingPointer))
					{
						Queue.queueLength--;
						Display();
						//Display dequeue job in eventTable
						table.add(new Node.Cell(
								eventPointer.eventTime,
								Waiting.waitingPointer.jobNum,
								eventPointer.event,
								eventPointer.eventJob.jobSize,
								eventPointer.eventJob.processTime));
						Waiting.DeleteWaitingJob();
						continue;
					}
					else {
						//Check the next waiting job
						Waiting.waitingPointer=Waiting.waitingPointer.next;
					}
				}
				eventPointer=eventPointer.next;
				continue;
			}
			if(previousTime!=eventPointer.eventTime) {
				currentTime.setText("EVENT TIME "+eventPointer.eventTime);
				previousTime= eventPointer.eventTime;
			}
			eventPointer=eventPointer.next;
		}*/

		if(eventPointer != null){	
			if(eventPointer.eventTime != previousTime) {
				timeInterval = eventPointer.eventTime - previousTime;
				currentTime.setText("EVENT TIME "+eventPointer.eventTime);
				previousTime= eventPointer.eventTime;
				if (!Waiting.waitingList.isEmpty()) {
					Waiting.UpdateWaitingTime(timeInterval);
				}
				Queue.UpdateQueueLength(timeInterval);
			}
			
			if(eventPointer.event=="Running") {
				if(!Allocation.Allocate(eventPointer.eventJob))
				{
					eventPointer.event="Waiting";
					Waiting.AddWaitingJob(eventPointer.eventJob);
					Queue.queueLength++;
				}
				Display();
				table.add(new Node.Cell(
						eventPointer.eventTime,
						eventPointer.eventJob.jobNum,
						eventPointer.event,
						eventPointer.eventJob.jobSize,
						eventPointer.eventJob.processTime));
			}
			else if(eventPointer.event=="Done"){
				Deallocation.Deallocate(eventPointer.eventJob);
				Display();
				//Display done job in eventTable
				table.add(new Node.Cell(
						eventPointer.eventTime,
						eventPointer.eventJob.jobNum,
						eventPointer.event,
						eventPointer.eventJob.jobSize,
						eventPointer.eventJob.processTime));
				if(!Waiting.waitingList.isEmpty() && (eventPointer.next==null || eventPointer.eventTime!=eventPointer.next.eventTime || eventPointer.next.event!="Done"))
				{
					eventPointer.event="Dequeue";
					Waiting.waitingPointer=Waiting.waitingList.getFirst();
					return;
				}
			}
			else //event is Dequeue
			{
				while(Waiting.waitingPointer!=null)
				{
					if(Allocation.Allocate(Waiting.waitingPointer))
					{
						Queue.queueLength--;
						Display();
						//Display dequeue job in eventTable
						table.add(new Node.Cell(
								eventPointer.eventTime,
								Waiting.waitingPointer.jobNum,
								eventPointer.event,
								eventPointer.eventJob.jobSize,
								eventPointer.eventJob.processTime));
						Waiting.DeleteWaitingJob();
						return;
					}
					else {
						//Check the next waiting job
						Waiting.waitingPointer=Waiting.waitingPointer.next;
					}
				}
				eventPointer=eventPointer.next;
				RunEvent();
				return;
			}
			if(previousTime!=eventPointer.eventTime) {
				currentTime.setText("EVENT TIME "+eventPointer.eventTime);
				previousTime= eventPointer.eventTime;
			}
			eventPointer=eventPointer.next;
		}
		else {
			currentTime.setText("DONE");
		}
	}
	
	public void Display() {
		throughput.setText(Float.toString(totalProcessTime/previousTime));
		minWaitingTime.setText(Integer.toString(Waiting.minWaitingTime));
		maxWaitingTime.setText(Integer.toString(Waiting.maxWaitingTime));
		totalWaitingTime.setText(Float.toString(Waiting.totalWaitingTime));
		avrWaitingTime.setText(Float.toString(Waiting.totalWaitingTime/40));
		maxQueLength.setText(Integer.toString(Queue.maxQueLength));
		avrQueueLength.setText(Float.toString(Queue.AverageQueueLength()));
		totalFrag.setText(Float.toString(Allocation.totalFrag));
		avrFrag.setText(Float.toString(Allocation.totalFrag/totalRunJob));
	}
	
	public static void Return(ActionEvent event) {
		//Clear everything then go back to the main menu
		totalProcessTime=0;
		totalRunJob=0;
		previousTime=0;
		Allocation.totalFrag=0;
		Waiting.minWaitingTime=0;
		Waiting.maxWaitingTime=0;
		Waiting.totalWaitingTime=0;
		Waiting.waitingList.clear();
		Queue.queueLength=0;
		Queue.maxQueLength=0;
		Queue.queue.clear();
		eventList.clear();
		memoryList.clear();
		//Main.MainMenu
	}
	
}