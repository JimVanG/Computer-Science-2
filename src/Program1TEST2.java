import java.util.BitSet;

/*
 * THIS IS A TEST FILE!!!
 * 
 * DO NOT SUBMIT THIS FILE!
 * 
 */
public class Program1TEST2 {
	public static BitSet sumSet = new BitSet();
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int [] array = {1,1,1,1,2};
		System.out.println("\nThe smallest vacant number is: " + CantHasSum(array));

	}
	
	public static int CantHasSum(int [] array) {
		// An 0(n) method that returns the smallest integer p >= 1 such that no
		// subset of elements from array sums to p.
		// array is an array of positive integers sorted in in non-decreasing
		// order. May contain duplicates
		
		recursivePerm(array, 0, 0);

		return sumSet.nextClearBit(0);
	}
	
	public static void recursivePerm(int[] numbers, int index, int sum){
		
		if(index == numbers.length){
			System.out.println("Sum: " + sum);
			sumSet.set(sum);
			return;
		}
		recursivePerm(numbers, index + 1, sum + numbers[index]);
		
		recursivePerm(numbers, index + 1, sum);
		
	}

}
