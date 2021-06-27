package application;

import java.util.LinkedList;

public class Waiting {
	
	public static LinkedList<Node.JobNode> waitingList = new LinkedList<Node.JobNode>();
	public static Node.JobNode waitingPointer = null;
	public static float totalWaitingTime=0;
	public static int maxWaitingTime;
	public static int minWaitingTime;
	public static boolean startWaitingList=false;
	
	public static void AddWaitingJob(Node.JobNode waitingJob) {
		if (startWaitingList==false) {
			if (Main.partitionChoice==2)
				Allocation.totalFrag=Event.memoryList.getLast().memSize;
			startWaitingList=true;
		}
		Node.JobNode newNode = new Node.JobNode(waitingJob.jobNum, 0, waitingJob.processTime, waitingJob.jobSize);
		if(!waitingList.isEmpty()) {
			waitingList.getLast().next=newNode;
		}
		waitingList.add(newNode);
		Event.waiting.add(newNode.jobNum);
	}
	
	public static void UpdateWaitingTime(int timeInterval) {
		waitingPointer = waitingList.getFirst();
		while (waitingPointer!=null) {
			totalWaitingTime+=timeInterval;
			waitingPointer.arrivalTime+=timeInterval;
			waitingPointer = waitingPointer.next;
		}
	}
	
	public static void DeleteWaitingJob() {
		if(waitingPointer==waitingList.getFirst()) {
			if(waitingPointer.arrivalTime>maxWaitingTime) {
				maxWaitingTime=waitingPointer.arrivalTime;
			}
			if(minWaitingTime==0 || waitingPointer.arrivalTime<minWaitingTime) {
				minWaitingTime=waitingPointer.arrivalTime;
			}
			Event.waiting.remove(0);
			waitingList.removeFirst();
			if(!waitingList.isEmpty()) {
				waitingPointer = waitingList.getFirst();
			}
			else waitingPointer = null;
		}
		else {
			Node.JobNode tmp = waitingList.getFirst();
			while(tmp!=null) {
				if (tmp.next==waitingPointer) {
					if(waitingPointer.arrivalTime>maxWaitingTime) {
						maxWaitingTime=waitingPointer.arrivalTime;
					}
					else if(minWaitingTime==0 || waitingPointer.arrivalTime<minWaitingTime) {
						minWaitingTime=waitingPointer.arrivalTime;
					}
					tmp.next = waitingPointer.next;
					Event.waiting.remove(waitingList.indexOf(waitingPointer));
					waitingList.remove(waitingPointer);
					waitingPointer = tmp.next;
					break;
				}
				else tmp = tmp.next;
			}
		}
	}
}