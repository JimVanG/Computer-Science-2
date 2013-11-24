import java.util.BitSet;


/*
 * THIS IS A TEST FILE!!!
 * 
 * DO NOT SUBMIT THIS FILE!
 * 
 */
public class Program1TEST {

	// this station is non-operational

	public static int CantHasSum(int[] array) {
		// An 0(n) method that returns the smallest integer p >= 1 such that no
		// subset of elements from array sums to p.
		// array is an array of positive integers sorted in in non-decreasing
		// order. May contain duplicates
		
		//bit set to store the used numbers and easily find the smallest vacant number.
		BitSet listOfSumsFound = new BitSet();
		int j = 0;
		//bools used for the summation/permutation logic
		boolean changeInitialIndex = true, newPermutation = false;
		
		//first loop
		for (int i = 0; i < array.length; i++) {
			//initialize the sum
			int subsetSum = 0;
			//if the list is empty let's start the inner loop at zero, otherwise start it at the i.
			if(listOfSumsFound.isEmpty()){
				j = 0;
			}else{
				j = i;
			}
			
			//inner loop
			while(j < array.length) {
				//if the the indexes are different and we are at a newPermutation then that means that
				//we should add the first index and the second index together, rather than add the second
				//index to the subsetSum.
				if(i != j && newPermutation){
					//add the two indexes together, because the subsetSum is zero and we are starting
					//a new permutation
					subsetSum = array[i] + array[j];
					//add it to the bitSet.
					listOfSumsFound.set(subsetSum);
					//change the permutation flag to false, because it isn't the beginning of the perm anymore.
					newPermutation = false;
					
					//if the indexes are at the same spot, then just add the number to the list. 
					//This takes into account a single numbered set.
				}else if(i != j){
					//add the number to the subsetSum THEN add the subsetSum to the list,
					//doing this makes for continuing the sum of the permutation easier.
					//the solo-set is still treated as a solo-set though.
					subsetSum += array[j];
					listOfSumsFound.set(subsetSum);
					newPermutation = false;
				}
				//if none of those tests passed then we are casually walking along an already existing
				//permutation, so add the second index to the subsetSum.
				else{
					subsetSum = array[j];
					listOfSumsFound.set(array[j]);
					newPermutation = false;
				}
				//
				//This is the logic for testing how the permutation will change the indexes of the arrays.
				//
				//if our second index is at the end of the array then we need do some tests to see how we will
				//handle the permutation change.
				if(j == array.length - 1){
					//if the first index is at 0 then it should means that we should
					//leave the first index the same and move the second index to the 3rd element.
					//Yes the logic is crazy but it works. This condition should only pass once.
					if(i == 0 && changeInitialIndex){
						changeInitialIndex = false;
						j = i + 2;
						newPermutation = true;
						subsetSum = 0;		
						
						//if the both indexes are at the end of the array then break out of the inner loop
						//and start a new permutation at a new first index spot.
					}else if((i == (j - 1)) && (j == array.length - 1)){
						//changeInitialIndex = false;
						newPermutation = true;
						subsetSum = 0;
						break;
					}
					//else just move the second index. Should most likely end the inner loop if this is hit.
					else{
						j++;
					}
					//just move the second index.
				}else{
					j++;
				}
			}// closes inner loop
			
			//add the sum of the set to the list
			listOfSumsFound.set(subsetSum);
		}// closes outer loop

		System.out.println("The sums found: "+ listOfSumsFound);
		//return the "nextClearBit" this returns the smallest number that wasn't summed up.
		//I used a bitSet for this purpose.
		return listOfSumsFound.nextClearBit(0);
	}

	public static int difficultyRating() {
		// return an integer 1 - 5 rating the difficulty of the assignment.

		return 3;
	}

	public static void main(String[] args) {
		int[] array = {1,2,3,5};
		int ret = Program1TEST.CantHasSum(array);
		System.out.print(ret);
		
		System.out.print("\nMy Difficulty Rating: " + Program1TEST.difficultyRating());
	}
}
