package application;

public class Deallocation {
	public static Node.MemoryNode memoryPointer;

	public static void Deallocate(Node.JobNode deloJob) {
		memoryPointer = Event.memoryList.getFirst();
		while(memoryPointer.runningJob != Event.eventPointer.eventJob.jobNum){
			memoryPointer = memoryPointer.next;
		}
		memoryPointer.runningJob = 0;
		Event.block.set(Event.memoryList.indexOf(memoryPointer), "Empty " + "| Memory: " +memoryPointer.memSize);
		//Now only deallocate one block at a time
		if(Main.partitionChoice==2){
			memoryPointer = Event.memoryList.getFirst();
			while (memoryPointer != null) {
				while(memoryPointer.next!=null && memoryPointer.runningJob == 0 && memoryPointer.next.runningJob == 0){
					Node.MemoryNode bufferPointer;
					bufferPointer = memoryPointer.next;
					memoryPointer.memSize += bufferPointer.memSize;
					//
					Event.block.set(Event.memoryList.indexOf(memoryPointer), "Empty " +"| Memory Size: "+memoryPointer.memSize);
					Event.block.remove(Event.memoryList.indexOf(bufferPointer));
					//
					memoryPointer.next = bufferPointer.next;
					Event.memoryList.remove(bufferPointer);
				}
				memoryPointer=memoryPointer.next;
			}
		}
	}
}