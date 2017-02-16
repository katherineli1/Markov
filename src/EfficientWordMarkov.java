import java.util.*;

public class EfficientWordMarkov implements MarkovInterface<WordGram> {
	private String[] myText;
	private Random myRandom;
	private int myOrder;
	private Map<WordGram, ArrayList<String>> myMap;

	private static String PSEUDO_EOS = "";
	private static long RANDOM_SEED = 1234;
	
	public EfficientWordMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order;
	}
	
	public EfficientWordMarkov() {
		//sets myOrder to default of 3
		this(3);
	}
	
	public int size() {
		return myText.length;
	}
	
	public void setTraining(String text) {
		myText = text.split("\\s+");
		
		Map<WordGram, ArrayList<String>> myLocalMap = new HashMap<WordGram, ArrayList<String>>();
		for (int i = 0; i < myText.length - myOrder; i++) {
			WordGram key = new WordGram(myText, i, myOrder);
			if (!myLocalMap.containsKey(key)) {
				myLocalMap.put(key, new ArrayList<String>());
			}
			ArrayList<String> value = myLocalMap.get(key);
			value.add(myText[i + myOrder]);
		}
		myMap = myLocalMap;
	}
	
	public String getRandomText(int numWords) {
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length - myOrder);
		
		WordGram current = new WordGram(myText, index, myOrder);

		sb.append(current.toString());
		
		for(int k = 0; k < numWords - myOrder; k++) {
			ArrayList<String> follows = getFollows(current);
			if (follows == null || follows.size() == 0) {
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
				//System.out.println("PSEUDO");
				break;
			}
			sb.append(" ");
			sb.append(nextItem);
			current = current.shiftAdd(nextItem);
		}
		return sb.toString();
	}
	
	public ArrayList<String> getFollows(WordGram key) {
		return myMap.get(key);
	}
	
	@Override
	public int getOrder() {
		return myOrder;
	}

	@Override
	public void setSeed(long seed) {
		RANDOM_SEED = seed;
		myRandom = new Random(RANDOM_SEED);	
	}
}
