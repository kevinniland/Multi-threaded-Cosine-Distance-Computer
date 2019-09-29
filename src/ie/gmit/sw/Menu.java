package ie.gmit.sw;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author Kevin Niland
 * @version 1.0
 * @since 1.8
 *
 * Used to generate primitive UI. Allows user to define the size of the blocking queue, read in the folder containing the subject
 * files, and read in the query file
 */
public class Menu {
	private Scanner scanner = new Scanner(System.in);
	private Calculator calculator = new Calculator();
	private int /*menuOption,*/ size = 0, fileCounter = 1, i;
	private String fileFolder, queryFile;

	/**
	 * @throws InterruptedException
	 * @param run() Calls the menu function
	 */
	public void run() throws InterruptedException {
		menu();
	}

	/**
	 * @throws InterruptedException
	 * @param menu() Generates primitive UI. Asks the user to define the size of the blocking queue, and read in the files to 
	 * be compared
	 */
	public void menu() throws InterruptedException {
		System.out.println("Define the size of the blocking queue: ");
		size = scanner.nextInt();
		
//		System.out.println(
//				"Enter 2 to read in the folder containing the subject files, or\nEnter 3 to read in the query file: ");
//		menuOption = scanner.nextInt();

		BlockingQueue<Word> blockingQueue = new ArrayBlockingQueue<Word>(size);

//		switch (menuOption) {
//		case 2:
			System.out.print("Please enter name of folder containing the subject files: ");
			fileFolder = scanner.next();

			File files = new File(fileFolder);
			File[] fileArray = files.listFiles();

			/* Iterates over the file. Checks if file is a file. If it is, creates and starts a new thread and new 
			 * instance of FileHandler
			 */
			for (File file : fileArray) {
				if (file.isFile()) {
					new Thread(new FileHandler(blockingQueue, file, fileCounter)).start();
				}

				fileCounter++;
			}

			Thread fileThread = new Thread(new QueueCreator(blockingQueue, calculator, fileCounter));

			fileThread.start();
//			break;
//		case 3:
			System.out.print("Enter name of query file to compare against the files in the folder directory: ");
			queryFile = scanner.next();

			File qFile = new File(queryFile);
			Thread queryThread = new Thread(new QueryHandler(calculator, qFile));

			queryThread.start();
//			break;
//		}

//		if (fileThread.isAlive() && queryThread.isAlive()) {
			fileThread.join();
			queryThread.join();
			
			System.out.println("\nCosine distance for files relative to the query file:");

			for (File fileLoop : fileArray) {
				if (fileLoop.isFile()) {
					i++;
					
					System.out.println(i + ": " + fileLoop.getName() + ", " + qFile.getName() + " = "
							+ calculator.getCosine(fileLoop.getName()));
				}
			}
//		} else {
//			System.out.println("File folder and/or query file has not been read in. Please try again.");
//		}
	}
}
