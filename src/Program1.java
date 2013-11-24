/*
 * James Van Gaasbeck
 * PID: J2686979
 * Programming Assignment #1: CantHasSum
 * Due: Friday, September 6, 11:59pm
 * Computer Science 2 : COP 3505
 * Professor: Dr. Sean Szumlanski
 */

public class Program1 {

	public static int CantHasSum(int[] array) {
		// An 0(n) method that returns the smallest integer p >= 1 such that no
		// subset of elements from array sums to p.
		// array is an array of positive integers sorted in in non-decreasing
		// order. May contain duplicates
		int retVal = 0;

		if (array.length != 0) {
			// if the first number in the array is greater than 1, then
			// 'p' is 1.
			if ((array[0] > 1) || (array[0] == 0 && array[1] > 1)) {
				retVal = 1;
				// if the difference between the first and second is greater
				// than 2
				// the the answer is 2.
			} else if ((array[1] - array[0]) >= 2) {
				retVal = 2;
			} else {
				int setSum = 0;
				// loop to loop through the main set
				for (int j = 0; j < array.length; j++) {
					// if we are on the first, or the difference between two
					// adjacent
					// elements is <=1 then just add to the setSum.
					if ((j == 0) || array[j] - array[j - 1] <= 1) {
						setSum += array[j];
						// if we are at the last element in the array we are
						// done,
						// so add 1 to the sum and then the program will end.
						if (j >= array.length - 1) {
							setSum++;
						}
						// if the difference between adjacent elements isn't <=
						// 1, then...
					} else {
						// if the element is greater than the setSum then we
						// have found the answer,
						// so add one to the setSum then break the loop.
						if (array[j] > setSum + 1) {
							setSum++;
							break;
							// if the element isn't greater than the setSum+1,
							// just add that element to the list and keep
							// walking.
						} else {
							setSum += array[j];
							// but, if the element is at the end of array, add 1
							// to it and then end it.
							if (j >= array.length - 1) {
								setSum++;
							}
						}
					}
				} // closes for() loop
				retVal = setSum;
			} //closes inner else
		}else{
			//if the array passed in is empty, return 1.
			retVal = 1;
		}
		// return the setSum (it's actually going to be the (setSum + 1)).
		return retVal;
	}

	public static int difficultyRating() {
		// return an integer 1 - 5 rating the difficulty of the assignment.

		return 3;
	}

	public static void main(String[] args) {

		 int[] array = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024};

		 System.out.print(Program1.CantHasSum(array));

		 System.out.print("\nMy Difficulty Rating: "
		 + Program1.difficultyRating());
	}

}
