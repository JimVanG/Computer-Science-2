public class Program1TEST3 {

	public static int CantHasSum(int[] array) {
		// An 0(n) method that returns the smallest integer p >= 1 such that no
		// subset of elements from array sums to p.
		// array is an array of positive integers sorted in in non-decreasing
		// order. May contain duplicates
		int setSum = 0, i = 0, retVal = 0;

		if (array.length != 0) {
			// if the first number in the array is greater than 1, then
			// 'p' is 1.
			if ((i == 0) && (array[i] > 1) || (array[0] == 0 && array[1] > 1)) {
				retVal = 1;
				// break;
			} else if ((array[1] - array[0]) >= 2) {
				retVal = 2;
				// break;
			} else {

				while (i < array.length) {

					if ((array[i + 1] - array[i]) <= 1) {
						setSum += array[i];
						++i;
						// j++;
						retVal = setSum;
						if ((i + 1) >= array.length) {
							setSum += array[array.length - 1];
							retVal = setSum + 1;
							break;
						}
					} else if (array[i + 1] > setSum) {
						setSum += array[i];

						if (setSum <= array[i + 1]) {
							setSum += array[i + 1];
						}
						retVal = setSum + 1;
						break;

					}

				} // closes while() loop
			}
		} else {
			retVal = 0;
		}
		// retVal = floor;
		return retVal;
	}

	public static int difficultyRating() {
		// return an integer 1 - 5 rating the difficulty of the assignment.

		return 3;
	}

	public static void main(String[] args) {
		System.out.println("The smallest vacant value is: "
				+ Program1TEST3.CantHasSum(new int[] { 1, 1, 1, 1, 6 }));
		System.out.printf("The difficulty of the assignment was %d out of 5.",
				Program1TEST3.difficultyRating());

	}

}
