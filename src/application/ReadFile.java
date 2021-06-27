package application;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class ReadFile {
	public static LinkedList<Node.JobNode> jobList = new LinkedList<Node.JobNode>();
	public static LinkedList<Node.MemoryNode> memoryList = new LinkedList<Node.MemoryNode>();
	
	public static void ReadJobFile() {
		try {
			File jobFile = new File("Joblist.txt");
			Scanner file = new Scanner(jobFile);
			file.nextInt();
			while (file.hasNextLine()) {
				Node.JobNode newJob = new Node.JobNode(file.nextInt(),file.nextInt(),file.nextInt(),file.nextInt());
					if (!jobList.isEmpty()) {
					jobList.getLast().next=newJob;
				}
					jobList.add(newJob);
			}
			file.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("JobList.txt is not found!");
		}
	}
	
	public static void ReadMemoryFile() {
		try {
			File memoryFile = new File("MemoryList.txt");
			Scanner file = new Scanner(memoryFile);
			file.next();
			while (file.hasNextInt()) {
				Node.MemoryNode newBlock = new Node.MemoryNode(file.nextInt(),0);
				if(!memoryList.isEmpty()) {
				}
				memoryList.add(newBlock);
			}
			file.close();
		}
		catch(FileNotFoundException e) {
			System.out.println("MemoryList.txt is not found!");
		}
	}
}