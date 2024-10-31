import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Oferta {
	static class Task {
		public static final String INPUT_FILE = "oferta.in";
		public static final String OUTPUT_FILE = "oferta.out";

		int N, K;
		int pret;
		double[][] dp;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				N = sc.nextInt();
				dp = new double[N + 1][N + 1];
				K = sc.nextInt();
				for (int i = 1; i <= N; i++) {
					pret = sc.nextInt();
					dp[i][i] = pret;
				}
				for (int i = 1; i <= N; i++) {
					for (int j = 1; j <= N; j++) {
						if (i != j) {
							dp[i][j] = -1;
						}
					}
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(double count) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%f.1\n", count);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// maximum between 2 numbers
		private double max(double a, double b) {
			return Math.max(a, b);
		}

		// minimum between 2 numbers
		private double min(double a, double b) {
			return Math.min(a, b);
		}

		// minimum between 3 numbers
		private double min(double a, double b, double c) {
			return Math.min(Math.min(a, b), c);
		}

		// minimum between 4 numbers
		private double min(double a, double b, double c, double d) {
			return Math.min(Math.min(Math.min(a, b), c), d);
		}

		// computes sum of individual product prices
		private double sum(int i, int j) {
			double sum = 0;
			for (int k = i; k <= j; k++) {
				sum += dp[k][k];
			}
			return sum;
		}

		// computes the best way to group products so that the final price is minimized
		private double minSum(int i, int j) {
			if (i == j) {
				return dp[i][i];
			} else if (j - i == 1) {
				if (dp[i][j] != -1) {
					return dp[i][j];
				}
				dp[i][j] = (min(dp[i][i], dp[j][j]) / 2)
						+ max(dp[i][i], dp[j][j]);
				return dp[i][j];
			} else if (j - i == 2) {
				if (dp[i][j] != -1) {
					return dp[i][j];
				}
				// gets the element between i and j
				double mid = dp[(i + j) / 2][(i + j) / 2];
				dp[i][j] = min(dp[i][i] + min(mid, dp[j][j]) / 2 + max(mid, dp[j][j]),
						minSum(i, (i + j) / 2) + dp[j][j],
						sum(i, j) - min(dp[i][i], mid, dp[j][j]));
				return dp[i][j];
			} else if (j - i == 3) {
				if (dp[i][j] != -1) {
					return dp[i][j];
				}
				dp[i][j] = min(dp[i][i] + minSum(i + 1, j),
						minSum(i, i + 1) + minSum(j - 1, j),
						minSum(i, j - 1) + dp[j][j],
						dp[i][i] + minSum(i + 1, j - 1) + dp[j][j]);
				return dp[i][j];
			} else if (j - i > 3) {
				dp[i][j] = min(minSum(i, j - 3) + minSum(j - 2, j),
						minSum(i, j - 4) + minSum(j - 3, j),
						minSum(i, j - 2) + minSum(j - 1, j));
				return dp[i][j];
			}
			return 0.0;
		}

		private double getResult() {
			return minSum(1, N);
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
