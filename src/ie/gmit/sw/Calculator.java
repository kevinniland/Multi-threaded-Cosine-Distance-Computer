package ie.gmit.sw;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kevin Niland
 * @version 1.0
 * @since 1.8
 * 
 * <i><b>N.B: Help with this class was gotten from some fellow students. There may be a few similarities in how certain 
 * things were done </b></i>
 */
public class Calculator {
	private Map<String, Integer> fileMap = new HashMap<String, Integer>();
	private Map<String, Integer> queryMap = new HashMap<String, Integer>();
	private Map<String, Integer> cosineMap = new HashMap<String, Integer>();

	public Calculator() {
		
	}

	// Adds words from files to the fileMap
	public synchronized void addWord(Word word) {
		String file = word.getFile();
		String words = word.getWord();
		
		String wordString = file + " " + words;
		
		if (fileMap.containsKey(wordString)) {
			int fileVal = fileMap.get(wordString);
			
			fileMap.put(wordString, fileVal + 1);
		} else {
			fileMap.put(wordString, 1);
		}
	}

	// Adds words from query file to queryMap
	public void addQuery(Word word) {
		String words = word.getWord();
		
		if (queryMap.containsKey(words)) {
			int queryVal = queryMap.get(words);
			
			queryMap.put(words, queryVal + 1);
		} else {
			queryMap.put(words, 1);
		}
	}

	/**
	 * Returns the cosine calculated from the function 'calculateCosine'
	 * 
	 * @param cosVal
	 * @return cosAnswer
	 */
	public double getCosine(String cosVal) {
		double cosAnswer;
		
		for (String wordLoop : fileMap.keySet()) {
			if (wordLoop.contains(cosVal)) {
				String wordString = wordLoop.substring(cosVal.length() + 1, wordLoop.length());
				
				int cosineVal = fileMap.get(wordLoop);
				
				cosineMap.put(wordString, cosineVal);
			}
		}
		
		cosAnswer = calculateCosine(queryMap, cosineMap);
		
		cosineMap.clear();
		
		return cosAnswer;
	}

	/**
	 * Iterates over the two maps. Calculates the frequency of file one and file two and calculates the dot product
	 * from this
	 * 
	 * @param queryMap Takes in the queryMap
	 * @param cosineMap Takes in the cosineMap
	 * @return
	 */
	public double calculateCosine(Map<String, Integer> queryMap, Map<String, Integer> cosineMap) {
		int fileOneFreq = 0, fileTwoFreq = 0, counter;
		float dotProductFreq = 0;
		double cosine = 0, cosDist;
	
		for (String i : queryMap.keySet()) {
			counter = 0;
			
			for (String j : cosineMap.keySet()) {
				if (i.equalsIgnoreCase(j)) {
					int fileOne = queryMap.get(i);
					int fileTwo = cosineMap.get(j);
					
					int dotProduct = fileOne * fileTwo;
					
					fileOneFreq += fileOne;
					fileTwoFreq += fileTwo;
					dotProductFreq += dotProduct;
					
					counter++;
				}
			}
			
			if (counter == 0) {
				int fileOne = queryMap.get(i);
				fileOneFreq += fileOne;
			}
		}
		
		for (String i : cosineMap.keySet()) {
			counter = 0;
			
			for (String j : queryMap.keySet()) {
				if (i.equalsIgnoreCase(j)) {
					counter++;
				}
			}
			
			if (counter == 0) {
				int fileTwo = cosineMap.get(i);
				fileTwoFreq += fileTwo;
			}
		}
		
		double fOneCalc = Math.sqrt(fileOneFreq);
		double fTwoCalc = Math.sqrt(fileTwoFreq);
		
		cosDist = (fOneCalc* fTwoCalc);
		
		if (dotProductFreq == 0) {
			cosine = 0.0;
		} else {
			if (cosDist > dotProductFreq) {
				cosine = dotProductFreq / cosDist;
			} else {
				cosine = cosDist / dotProductFreq;
			}
		}
		
		return cosine;
	}
}