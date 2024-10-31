import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Compresie {
	static class Task {
		public static final String INPUT_FILE = "compresie.in";
		public static final String OUTPUT_FILE = "compresie.out";

		int n, m;
		ArrayList<Integer> A;
		ArrayList<Integer> B;

		public void solve() {
			readInput();
			writeOutput(getResult());
		}

		private void readInput() {
			try {
				Scanner sc = new Scanner(new File(INPUT_FILE));
				n = sc.nextInt();
				A = new ArrayList<>();
				for (int i = 0; i < n; i++) {
					A.add(sc.nextInt());
				}
				m = sc.nextInt();
				B = new ArrayList<>();
				for (int i = 0; i < m; i++) {
					B.add(sc.nextInt());
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

		// checks if 2 arrays are equal or not
		private boolean compareArrays(ArrayList<Integer> A, ArrayList<Integer> B) {
			if (A.size() != B.size()) {
				return false;
			} else {
				for (int i = 0; i < A.size(); i++) {
					if (!A.get(i).equals(B.get(i))) {
						return false;
					}
				}
			}
			return true;
		}

		private int getResult() {
			int idx = 0;
			int sum = 0;
			// traverse both arrays at the same time
			while (idx < A.size() && idx < B.size()) {
				if (A.get(idx) > B.get(idx)) {
					int j = idx + 1;
					sum = B.get(idx);
					// compute the sum
					while (sum < A.get(idx)) {
						sum += B.get(j);
						B.remove(j);
					}
					// if sum is equal A[idx], set the sum in B
					if (sum == A.get(idx)) {
						B.set(idx, sum);
						// and continue
						idx++;
					} else {
						B.set(idx, sum);
					}
				} else if (A.get(idx) < B.get(idx)) {
					int j = idx + 1;
					sum = A.get(idx);
					// compute the sum
					while (sum < B.get(idx)) {
						sum += A.get(j);
						A.remove(j);
					}
					// if sum is equal B[idx], set the sum in A
					if (sum == B.get(idx)) {
						A.set(idx, sum);
						// continue
						idx++;
					} else {
						A.set(idx, sum);
					}
				} else {
					idx++;
				}
			}

			// compare the arrays
			if (compareArrays(A, B)) {
				return A.size();
			} else {
				// compression cannot be done
				return -1;
			}
		}
	}

	public static void main(String[] args) {
		new Task().solve();
	}
}
