package ie.gmit.sw;

/**
 * 
 * @author Kevin Niland
 * @version 1.0
 * @since 1.8
 * 
 * Gets and sets the words contained in the subject files
 *
 */
public class Word {
	// Instance variables
	private String file;
	private String word;
	private int fileCount;
	
	public Word() {
		
	}
	
	public Word(String file, String word, int fileCount) {
		super();
		
		this.file = file;
		this.word = word;
		this.fileCount = fileCount;
	}
	
	public String getFile() {
		return file;
	}
	
	public void setFile(String file) {
		this.file = file;
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
	
	public int getFileCount() {
		return fileCount;
	}
	
	public void setFileCount(int fileCount) {
		this.fileCount = fileCount;
	}
}