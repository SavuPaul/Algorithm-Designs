import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Servere {
	static class Task {
		public static final String INPUT_FILE = "servere.in";
		public static final String OUTPUT_FILE = "servere.out";

		int n;
		int[] power;
		int[] limit;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				power = new int[n];
				for (int i = 0; i < n; i++) {
					power[i] = sc.nextInt();
				}
				limit = new int[n];
				for (int i = 0; i < n; i++) {
					limit[i] = sc.nextInt();
				}
				sc.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		private void writeOutput(double count) {
			try {
				PrintWriter pw = new PrintWriter(new File(OUTPUT_FILE));
				pw.printf("%.1f\n", count);
				pw.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		// Gets the maximum value in a given array
		private int getMaximum(int[] v) {
			int max = Integer.MIN_VALUE;
			for (Integer elem: v) {
				if (elem > max) {
					max = elem;
				}
			}
			return max;
		}

		// Gets the minimum value in a given array list
		private double getMinimum(ArrayList<Double> array) {
			double min = Double.POSITIVE_INFINITY;
			for (Double elem: array) {
				if (elem < min) {
					min = elem;
				}
			}
			return min;
		}

		// Gets the minimum value in a given array
		private int getMinimum(int[] v) {
			int min = Integer.MAX_VALUE;
			for (Integer elem: v) {
				if (elem < min) {
					min = elem;
				}
			}
			return min;
		}

		// Computes the server power values for a certain given electricity supply (x)
		private ArrayList<Double> getValues(int[] pow, int[] lim, double x) {
			ArrayList<Double> values = new ArrayList<>();

			for (int i = 0; i < n; i++) {
				double result = pow[i] - Math.abs(x - lim[i]);
				values.add(result);
			}

			return values;
		}

		private double getResult() {
			int left = getMinimum(limit);
			int right = getMaximum(limit);
			int mid = 0;
			double minMiddle = 0, minPrev = 0, minNext = 0;
			double result = Double.NEGATIVE_INFINITY;
			// binary search
			while (left < right) {
				mid = (left + right) / 2;
				// calculate neighbors
				minMiddle = getMinimum(getValues(power, limit, mid));
				minPrev = getMinimum(getValues(power, limit, mid - 1));
				minNext = getMinimum(getValues(power, limit, mid + 1));

				if (minMiddle >= minPrev && minMiddle >= minNext) {
					result = minMiddle;
					// compare with mid +- 0.5
					double minLeft = getMinimum(getValues(power, limit, mid - 0.5));
					if (minLeft > result) {
						result = minLeft;
						break;
					}
					double minRight = getMinimum(getValues(power, limit, mid + 0.5));
					if (minRight > result) {
						result = minRight;
						break;
					}
					break;
				} else if (minMiddle > minPrev) {
					// search to the right
					result = minNext;
					left = mid + 1;
				} else {
					// search to the left
					result = minPrev;
					right = mid - 1;
				}
			}
			return result;
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
