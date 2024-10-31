import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Criptat {
	static class Task {
		public static final String INPUT_FILE = "criptat.in";
		public static final String OUTPUT_FILE = "criptat.out";

		int N;
		ArrayList<String> words;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				N = sc.nextInt();
				words = new ArrayList<>();
				for (int i = 0; i < N; i++) {
					words.add(sc.next());
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(int count) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", count);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// gets the letters used in the words
		private ArrayList<Character> getLetters() {
			ArrayList<Character> letters = new ArrayList<>();
			for (String word: words) {
				for (int i = 0; i < word.length(); i++) {
					if (!letters.contains(word.charAt(i))) {
						letters.add(word.charAt(i));
					}
				}
			}
			return letters;
		}

		// orders the initial words based on the criteria established
		private ArrayList<String> computeList(ArrayList<Double> values,
			char letter, List<Map<String, Integer>> list) {
			ArrayList<String> orderedWords = new ArrayList<>();
			for (Double number : values) {
				for (Map<String, Integer> elem : list) {
					for (Map.Entry<String, Integer> entry : elem.entrySet()) {
						if (entry.getValue() == 0) {
							int counter = countAppearances(letter, entry.getKey());
							if (computeRatio(counter, entry.getKey()) == number) {
								orderedWords.add(entry.getKey());
								// set as used
								entry.setValue(1);
							}
						}
					}
				}
			}
			return orderedWords;
		}

		// computes the value of appearances/total length for each word
		private ArrayList<Double> getValues(char letter) {
			ArrayList<Double> values = new ArrayList<>();
			for (String word: words) {
				int appearances = countAppearances(letter, word);
				values.add((double) appearances / word.length());
			}
			return values;
		}

		private double computeRatio(int appearances, String word) {
			return (double) appearances / word.length();
		}

		// counts the total appearances of a letter in a word
		private int countAppearances(char letter, String word) {
			int appearances = 0;
			for (int i = 0; i < word.length(); i++) {
				if (word.charAt(i) == letter) {
					appearances++;
				}
			}
			return appearances;
		}

		// computes the best length password
		private int getFinalLength(char letter, ArrayList<String> orderedWords) {
			int totalLength = 0;
			int appearances = 0;

			for (String word: orderedWords) {
				int tempAppearances = countAppearances(letter, word);
				int tempLength = word.length();

				if (appearances + tempAppearances > (totalLength + tempLength) / 2) {
					totalLength += tempLength;
					appearances += tempAppearances;
				}
			}

			return totalLength;
		}

		// creates a map with the given words where values are set to 0
		private List<Map<String, Integer>> wordsToMap() {
			List<Map<String, Integer>> list = new ArrayList<>();
			for (String word : words) {
				Map<String, Integer> map = new HashMap<>();
				map.put(word, 0);
				list.add(map);
			}
			return list;
		}

		private int getResult() {
			// creates a map of the frequency of the letters
			ArrayList<Character> letters = getLetters();
			int length = 0;

			// do this for each letter
			for (char letter: letters) {
				ArrayList<Double> values = getValues(letter);

				values.sort((e1, e2) -> {
					if (e1 > e2) {
						return -1;
					} else if (e1 < e2) {
						return 1;
					} else {
						return 0;
					}
				});

				// adds all words to a list of maps and mark them with 0 (unused)
				List<Map<String, Integer>> wordsMap = wordsToMap();

				// order the words based on the values array
				ArrayList<String> orderedWords = computeList(values, letter, wordsMap);

				int len = getFinalLength(letter, orderedWords);
				// update length
				if (len > length) {
					length = len;
				}
			}

			return length;
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
