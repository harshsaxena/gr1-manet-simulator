/**  
 * ----------------------------------------------------------
 * This software is for educational purposes only.
 * The base of this software was created by IntelliJ IDEA.
 * Additions to the base have been made by the Hood College
 * Computer Science Department, Graduate Group 1.
 * ----------------------------------------------------------
 *
 * History:
 * @version: $Revision$
 * @date: $Date$
 * @author: $Author$
 */

package UI.utility;

// TODO exchange() produces array out of bounds exception
public class NthMinimum {
	private static void exchange(GNodeWrapper[] A, int i, int j) {
		GNodeWrapper temp = A[j];
		A[j] = A[i];
		A[i] = temp;
	}

	private static int partition(GNodeWrapper[] A, int p, int r) {
		GNodeWrapper x = A[r];
		int i = p - 1;
		for (int j = p; j < r; j++) {
			if (A[j].compareTo(x) <= 0) {
				i++;
				exchange(A, i, j);
			}
		}
		exchange(A, i + 1, r);
		return i + 1;
	}

	private static int randomized_Partition(GNodeWrapper[] A, int p, int r) {
		int i = (int) (Math.random() * (r - p + 1)) + p;
		exchange(A, i, r);
		return partition(A, p, r);
	}

	/**
	 * generates n th minimum of array A
	 * 
	 * @param A
	 * @param p
	 * @param r
	 * @param i
	 * @return index of nth minimum in A
	 */
	public static int randomized_Select(GNodeWrapper[] A, int p, int r, int i) {
		if (p == r) {
			return p;
		}
		int q = randomized_Partition(A, p, r);
		int k = q - p + 1;
		if (i == k) {
			return q;
		} else if (i < k) {
			return randomized_Select(A, p, q - 1, i);
		} else {
			return randomized_Select(A, q + 1, r, i - k);
		}
	}
}
