import java.util.*;

public class WordGram implements Comparable<WordGram>{
	private String[] myWords;
	private int myHash;
	
	public WordGram(String[] words, int start, int size) {
		myWords = new String[size];
		for (int i = start; i < size + start; i++) {
			myWords[i - start] = words[i];
		}
	}
	
	public int hashCode() {
		myHash = 0;
		for (int i = 0; i < myWords.length; i++) {
			myHash += myHash*i + myWords[i].hashCode();
		}
		return myHash;
	}
	
	public boolean equals(Object other) {
		if (other == this) return true;
		if (other == null || !(other instanceof WordGram)) return false;
		
		WordGram wg = (WordGram) other;
		for (int i = 0; i < myWords.length; i++) {
			if (!(myWords[i].equals(wg.myWords[i]))) return false;
		}
		return true;
	}
	
	public int compareTo(WordGram o) {
		for (int i = 0; i < Math.max(myWords.length, o.myWords.length); i++) {
			if (myWords.length > o.myWords.length) {
				return 1;
			}
			else if (myWords.length < o.myWords.length) {
				return -1;
			}
			else if (myWords[i].compareTo(o.myWords[i]) > 0) {
				return myWords[i].compareTo(o.myWords[i]);
			}
			else if (myWords[i].compareTo(o.myWords[i]) < 0) {
				return myWords[i].compareTo(o.myWords[i]);
			}
		}
		return 0;
	}
	
    public int length() {
    	return myWords.length;
    }
    
    public String toString() {
    	String str = "{";
    	for (String s : myWords) {
    		str += s + ", ";
    	}
    	return str.substring(0, str.length() - 2) + "}";
    }
    
    public WordGram shiftAdd(String last) {
    	WordGram myWordsNew = new WordGram(myWords, 0, myWords.length);
    	for (int i = 0; i < myWords.length - 1; i++) {
    		myWordsNew.myWords[i] = myWords[i + 1];
    	}
    	myWordsNew.myWords[myWords.length - 1] = last;
    	return myWordsNew;
    }
    
	
}
