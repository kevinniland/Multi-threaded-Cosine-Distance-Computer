package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 
 * @author Kevin Niland
 * @version 1.0
 * @since 1.8
 * 
 * Reads in the query file that will be compared against the subject files contained inside a specified folder.
 * Implements <b>Runnable</b>
 */
public class QueryHandler implements Runnable {
	// Sets the instance variables
	private Calculator calculator;
	private File file;

	public QueryHandler(Calculator calculator, File file) {
		this.calculator = calculator;
		this.file = file;
	}

	/**
	 * @param words Reads in the query file line by line and stores them inside the String array 'words'. 
	 * Iterates over array and adds each word to the query map by passing them into the function 'addQuery' 
	 * of the class 'Calculator'
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
					calculator.addQuery(new Word(file.getName(), wordLoop, 0));
				}
			}
			
			bufferedReader.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
}
