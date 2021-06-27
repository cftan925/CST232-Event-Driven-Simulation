package application;


public class Node {
	
	public static class JobNode {
		public int jobNum;
		public int jobSize;
		public int arrivalTime;
		public int processTime;
		public JobNode next;
		
		JobNode(int jobNum, int arrivalTime, int processTime, int jobSize){
			this.jobNum = jobNum;
			this.arrivalTime = arrivalTime;
			this.processTime = processTime;
			this.jobSize = jobSize;
			this.next = null;
		}
	}
	
	public static class EventNode {
		public JobNode eventJob;
		public int eventTime;
		public String event;
		public EventNode next;
		
		EventNode(JobNode eventJob, int eventTime, String event){
			this.eventJob = eventJob;
			this.eventTime = eventTime;
			this.event = event;
			this.next = null;
		}
	}
	
	public static class MemoryNode {
		public int memSize;
		public int runningJob;
		public MemoryNode next;
		
		MemoryNode(int memSize, int runningJob){
			this.memSize = memSize;
			this.runningJob = runningJob;
			this.next = null;
		}
	}
	
	public static class Cell{
		int eventTime;
		int jobNum;
		String event;
		int jobSize;
		int processTime;
		
		Cell(int eventTime,int jobNum, String event, int jobSize, int processTime){
			this.eventTime= eventTime;
			this.jobNum= jobNum;
			this.event= event;
			this.jobSize= jobSize;
			this.processTime= processTime;
		}
		
		public int getEventTime() {
			return eventTime;
		}
		public int getJobNum() {
			return jobNum;
		}
		public String getEvent() {
			return event;
		}
		public int getJobSize() {
			return jobSize;
		}
		public int getProcessTime() {
			return processTime;
		}
	}
}

