package application;

import java.util.ArrayList;

public class Queue {
	public static ArrayList<Integer> queue = new ArrayList<Integer>();
	public static int queueLength;
	public static int maxQueLength;
	
	public static void UpdateQueueLength(int timeInterval) {
		if(queueLength>=queue.size()) {
			maxQueLength = queueLength;
			while (queueLength!=queue.size()) {
				queue.add(0);
			}
			queue.add(queueLength, timeInterval);
		}
		else
			queue.set(queueLength, queue.get(queueLength)+timeInterval);
	}
	
	public static float AverageQueueLength(){
		float totalQueueLength=0F;
		for (int num = 0; num < queue.size(); num++) {
			totalQueueLength+=(num*queue.get(num));
		}
		return totalQueueLength/Event.previousTime;
	} 
}