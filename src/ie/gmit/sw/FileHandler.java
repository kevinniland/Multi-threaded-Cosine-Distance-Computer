package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

/**
 * @author Kevin Niland
 * @version 1.0
 * @since 1.8
 * 
 * Reads in the files contained inside a specified folder that will be compared against the query file.
 * Implements <b>Runnable</b>
 *
 */
public class FileHandler implements Runnable {
	private BlockingQueue<Word> blockingQueue;
	private File file;
	private int counter;

	public FileHandler(BlockingQueue<Word> blockingQueue, File file, int counter) {
		this.blockingQueue = blockingQueue;
		this.file = file;
		this.counter = counter;
	}

	/**
	 * @param words Reads in the subject files line by line and stores them inside the String array 'words'. 
	 * Iterates over array and adds each word to the blocking queue using the .put() method
	 */
	@Override
	public void run() {
		try {
			FileInputStream fileInputStream = new FileInputStream(file);
			DataInputStream dataInputStream = new DataInputStream(fileInputStream);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream));
			
			String nextLine;
			
			while ((nextLine = bufferedReader.readLine()) != null) {
				String[] words = nextLine.toLowerCase().replaceAll("[^A-Za-z0-9 ]", " ").split(" ");
				
				for (String wordLoop : words) {
					blockingQueue.put(new Word(file.getName(), wordLoop, counter));
				}
			}
			
			blockingQueue.put(new WordInstance());
			
			bufferedReader.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} catch (InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}
	}
}

class QueueCreator implements Runnable {
		private BlockingQueue<Word> blockingQueue;
		private Calculator calculator;
		private int fileCount;
		private int counter = 0;
		private boolean keepAlive = true;

		public QueueCreator(BlockingQueue<Word> blockingQueue, Calculator calculator, int fileCount) {
			this.blockingQueue = blockingQueue;
			this.calculator = calculator;
			this.fileCount = fileCount;
		}

		/**
		 * @param run() While running, takes a word from the blocking queue and passes it to the instance of 'Word'. If the current
		 * word is not an instance of 'WordInstance', it will the word to the file map using the 'addWord' function contained inside
		 * the class 'Calculator'. 'counter' keeps track of the number of files. If the filecount, depracted by 1, equals the counter,
		 * then 'keepAlive' is set to false and the while loop exits
		 */
		@Override
		public void run() {
			try {
				while (keepAlive) {
					Word word = blockingQueue.take();
					
					if (word instanceof WordInstance) {
						counter++;
					} else {
						calculator.addWord(word);
					}
					
					if (counter == fileCount - 1) {
						keepAlive = false;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
