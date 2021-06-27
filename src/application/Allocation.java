package application;

public class Allocation{
	public static float totalFrag;
	public static Node.MemoryNode memoryPointer;
	public static Node.MemoryNode countPointer;

	public static boolean Allocate(Node.JobNode job) {
		memoryPointer = Event.memoryList.getFirst();
		while(memoryPointer != null) {
			if(memoryPointer.runningJob == 0 && memoryPointer.memSize >= job.jobSize)
			{	
				// For Best Fit Algorithm
				if (Main.algoOption == 2){
					Node.MemoryNode bestMemoryPointer;
					bestMemoryPointer = memoryPointer;
					
					while(memoryPointer != null) {
						//Find the best memory block
						if (memoryPointer.runningJob == 0 && memoryPointer.memSize >= job.jobSize && memoryPointer.memSize < bestMemoryPointer.memSize){
							bestMemoryPointer = memoryPointer;					
						}
						memoryPointer = memoryPointer.next;
					}
					memoryPointer = bestMemoryPointer;
				}
				
				// For Worst Fit Algorithm
				else if (Main.algoOption == 3) {
					Node.MemoryNode worstMemoryPointer;
					worstMemoryPointer = memoryPointer;
					
					while(memoryPointer != null) {
						//Find the worst memory block
						if(memoryPointer.runningJob == 0 && memoryPointer.memSize > worstMemoryPointer.memSize) {
							worstMemoryPointer = memoryPointer;
						}
						memoryPointer = memoryPointer.next;
					}
					memoryPointer = worstMemoryPointer;
				}
				
				memoryPointer.runningJob = job.jobNum;
				Event.block.set(Event.memoryList.indexOf(memoryPointer),"Running Job: "+ job.jobNum + " | Memory: " + job.jobSize);
				
				Event.CreateDoneEvent(job);
				
				if (Waiting.startWaitingList || Main.partitionChoice == 1) {		
					// Save total fragmentation 
					totalFrag += memoryPointer.memSize - job.jobSize;
				}

				if (Main.partitionChoice == 2) 
				{
					Node.MemoryNode fragment = new Node.MemoryNode(memoryPointer.memSize-job.jobSize,0);
					Event.memoryList.add(Event.memoryList.indexOf(memoryPointer)+1, fragment);
					memoryPointer.memSize = job.jobSize;
					memoryPointer.runningJob = job.jobNum;
					if (memoryPointer.next!=null) { //insert in between
						fragment.next=memoryPointer.next;
						Event.block.add(Event.memoryList.indexOf(fragment), "Empty "+"| Memory Size: "+fragment.memSize);
					}
					else {
						Event.block.add("Empty "+"| Memory Size: "+fragment.memSize);
					}
					memoryPointer.next=fragment;
				}
				
				return true;
			}
			else 
				memoryPointer = memoryPointer.next;
			}
		return false;
	}
}

