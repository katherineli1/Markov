import java.io.File;

public class WordMarkovDriver {
	public static void main(String[] args) {
		String filename = "data/alice.txt";
		if (args.length > 0) {
			filename = args[1];
		}
		File f = new File(filename);
		String text = TextSource.textFromFile(f);
		
		double start = System.nanoTime();
		for(int k = 5; k <= 5; k++) {
			//MarkovInterface<WordGram> markov = new BruteWordMarkov(k);
			MarkovInterface<WordGram> markov = new EfficientWordMarkov(k);
			markov.setTraining(text);
			String random = markov.getRandomText(1600);
			System.out.printf("%d markov model with %d words\n", k,random.split("\\s").length);
			printNicely(random,60);
		}
		double end = System.nanoTime();
		System.out.printf("total time = %2.3f\n", (end-start)/1e9);
	}

	private static void printNicely(String random, int screenWidth) {
		String[] words = random.split("\\s+");
		int psize = 0;
		System.out.println("----------------------------------");
		for(int k=0; k < words.length; k++){
			System.out.print(words[k]+ " ");
			psize += words[k].length() + 1;
			if (psize > screenWidth) {
				System.out.println();
				psize = 0;
			}
		}
		System.out.println("\n----------------------------------");
	}
}
