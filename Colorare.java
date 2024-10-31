import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Colorare {
	static class Task {
		public static final String INPUT_FILE = "colorare.in";
		public static final String OUTPUT_FILE = "colorare.out";
		public static final long LIM = 1000000007;

		int k;
		int X;
		String T;

		public void solve() {
			writeOutput(getResult());
		}

		private void writeOutput(long count) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%d\n", count);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// calculates power with divide et impera
		public static long power(int base, int exponent) {
			if (exponent == 0) {
				return 1;
			} else if (exponent == 1) {
				return base % LIM;
			} else {
				long newP = power(base, exponent / 2);

				if (exponent % 2 == 0) {
					return (newP * newP) % LIM;
				} else {
					return (newP * newP * base) % LIM;
				}
			}
		}

		private long getResult() {
			// read k and the first pair
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));

				k = sc.nextInt();

				long number = 0;
				X = sc.nextInt();
				T = sc.next();

				// cases for the first piece
				if (T.equals("H")) {
					number = 6;
					number *= power(3, X - 1);
					number = number % LIM;
				} else if (T.equals("V")) {
					number = 3;
					number *= power(2, X - 1);
					number = number % LIM;
				}

				String lastT = T;

				for (int i = 0; i < k - 1; i++) {
					X = sc.nextInt();
					T = sc.next();

					switch (T) {
						// if piece is horizontal
						case "H":
							// if previous piece is horizontal
							if (lastT.equals("H")) {
								number *= power(3, X);
								number = number % LIM;
							} else if (lastT.equals("V")) {
								// if previous piece is vertical
								number *= 2;
								number = number % LIM;
								number *= power(3, X - 1);
								number = number % LIM;
							}
							break;
						// if current piece is vertical
						case "V":
							// if previous piece is vertical
							if (lastT.equals("V")) {
								number *= power(2, X);
								number = number % LIM;
							} else if (lastT.equals("H")) {
								// if previous piece is horizontal
								number *= power(2, X - 1);
								number = number % LIM;
							}
							break;
						default:
							number = -1;
							i = k;
							break;
					}
					// update previous piece to actual one
					lastT = T;
				}
				sc.close();
				return number;
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
