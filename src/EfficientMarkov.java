import java.util.*;

public class EfficientMarkov implements MarkovInterface<String> {
	private String myText;
	private Random myRandom;
	private int myOrder;
	private Map<String, ArrayList<String>> myMap;

	private static String PSEUDO_EOS = "";
	private static long RANDOM_SEED = 1234;
	
	public EfficientMarkov(int order) {
		myRandom = new Random(RANDOM_SEED);
		myOrder = order;
	}
	
	public EfficientMarkov() {
		//sets myOrder to default of 3
		this(3);
	}
	
	public int size() {
		return myText.length();
	}
	
	public void setTraining(String text) {
		Map<String, ArrayList<String>> myLocalMap = new HashMap<String, ArrayList<String>>();
		for (int i = 0; i < text.length() - myOrder; i++) {
			String key = text.substring(i, i + myOrder);
			if (!myLocalMap.containsKey(key)) {
				myLocalMap.put(key, new ArrayList<String>());
			}
			ArrayList<String> value = myLocalMap.get(key);
			value.add(Character.toString(text.charAt(i + myOrder)));
		}
		myMap = myLocalMap;
		myText = text;
	}
	
	public String getRandomText(int length) {
		StringBuilder sb = new StringBuilder();
		int index = myRandom.nextInt(myText.length() - myOrder);

		String current = myText.substring(index, index + myOrder);
		//System.out.printf("first random %d for '%s'\n",index,current);
		sb.append(current);
		for(int k=0; k < length-myOrder; k++){
			ArrayList<String> follows = getFollows(current);
			if (follows.size() == 0){
				break;
			}
			index = myRandom.nextInt(follows.size());
			
			String nextItem = follows.get(index);
			if (nextItem.equals(PSEUDO_EOS)) {
				//System.out.println("PSEUDO");
				break;
			}
			sb.append(nextItem);
			current = current.substring(1)+ nextItem;
		}
		return sb.toString();
	}
	
	public ArrayList<String> getFollows(String key) {
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
