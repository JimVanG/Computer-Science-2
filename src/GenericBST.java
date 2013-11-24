/*
 * James Van Gaasbeck
 * PID: J2686979
 * COP 3503 : Computer Science II
 * Programming Assignment #2 : Generic BST
 * Due: Sunday, September 22, 11:59PM
 */



//class that will be used as each tree BST_Node
//Makes the class extend comparable and have the data type be generic,
//also forces the class to implement the compareTo() interface method.
class BST_Node<T extends Comparable<T>> implements Comparable<BST_Node<T>>
{
	//we need the data of the BST_Node to be of the generic type 'T'
	T data;
	BST_Node<T> left, right;

	//constructor that sets the passed in data as the BST_Nodes data
	BST_Node(T data)
	{
		this.data = data;
	}

	//the compare to method that is overridden.
	//must take in a generic BST_Node
	@Override
	public int compareTo(BST_Node<T> that) {
		//if both the data is null return a 0, because there is nothing there.
		if(this.data == null && that.data == null)
			return 0;
		//if this.data is null, then we say that, it is lessThan(-1) that.data
		if(this.data == null)
			return -1;
		//if that.data is null, then we say that, this.data is greaterThan(1) that.data
		if(that.data == null)
			return 1;
		//System.out.println("compareTo(): " + data.compareTo(that.data));
		//otherwise, do a regular ol' compareTo method on the data.
		return this.data.compareTo(that.data);
	}
	
	
}

//the actual binary search tree class
//The signature syntax is pretty much saying that in order for the argument to be valid,
//'T' (the generic object name I used) must extend/implement Comparable.
//In other words, If you want to use the GenericBST class, the objects u put in it
//must be comparable.
public class GenericBST<T extends Comparable<T>>
{
	//BST_Node variable that will be instantiated later
	private BST_Node<T> root;

	//public insert function that calls the private actual insert function
	//only takes in the data.
	public void insert(T data)
	{
		//System.out.println("Inserting in: " + data);
		root = insert(root, data);
	}

	//the actual insert function takes in the root BST_Node and the data
	private BST_Node<T> insert(BST_Node<T> root, T data)
	{
		//create a BST_Node with the data in it so we can use our compareTo() method
		BST_Node<T> noobBST_Node = new BST_Node<T>(data);
		
		//if the root is null just return the new BST_Node.
		//or if we finally reach the end of the tree and can add the new BST_Node/leaf
		if (root == null)
		{
			//return new BST_Node<T>(data); //creates the BST_Node
			return noobBST_Node;
		}
		//if the data is smaller that the root data, go to the left
		else if (noobBST_Node.compareTo(root) < 0)
		{
			root.left = insert(root.left, data);
		}
		//go to the right if the data is greater
		else if (noobBST_Node.compareTo(root) > 0)
		{
			root.right = insert(root.right, data);
		}
		//if the data is the same then don't add anything.
		else
		{
			// Stylistically, I have this here to explicitly state that we are
			// disallowing insertion of duplicate values.
			;
		}
		//return the root of the tree (first BST_Node)
		return root;
	}

	//public delete function that only takes in the data
	public void delete(T data)
	{
		//System.out.println("Deleting: " + data);
		root = delete(root, data);
	}
	
	//private delete function that takes in the data and root BST_Node
	private BST_Node<T> delete(BST_Node<T> root, T data)
	{
		//create a BST_Node with the data in it so we can use our compareTo() method
		BST_Node<T> deleteBST_Node = new BST_Node<T>(data);
		
		//if the root is null then there's no tree
		if (root == null)
		{
			//System.out.println("There was no tree.");
			return null;
		}
		//if the data is less go to the left
		else if (deleteBST_Node.compareTo(root) < 0)
		{
			root.left = delete(root.left, data);
		}
		//otherwise go to the right
		else if (deleteBST_Node.compareTo(root) > 0)
		{
			root.right = delete(root.right, data);
		}
		//else, we have a hit, so find out how we are going to delete it
		else
		{
			//if there are no children then return null
			//because we can delete the BST_Node without worries
			if (root.left == null && root.right == null)
			{
				//System.out.println("Delete, no children BST_Nodes");
				return null;
			}
			//if there is no right-child then return the left
			//
			else if (root.right == null)
			{
				//System.out.println("Delete, no right-children BST_Nodes");
				return root.left;
			}
			//if there is no left child return the right
			else if (root.left == null)
			{
				//System.out.println("Delete, no left-children BST_Nodes");
				return root.right;
			}
			else
			{
				//if it is a parent BST_Node, we need to find the max of the lowest so we can fix the BST
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is non-empty.
	private T findMax(BST_Node<T> root)
	{
		//traverse all of the right-BST_Nodes of the root to find the last one at the right.
		while (root.right != null)
		{
			root = root.right;
		}
		//being that it is a BST it is safe to assume that the last BST_Node to the right of the root
		//is the largest value
		return root.data;
	}

	// Returns true if the value is contained in the BST, false otherwise.
	public boolean contains(T data)
	{
		return contains(root, data);
	}
	
	//traverses tree seeing if the data is in the tree
	private boolean contains(BST_Node<T> root, T data)
	{
		//create a BST_Node with the data in it so we can use our compareTo() method
		BST_Node<T> compareBST_Node = new BST_Node<T>(data);
		
		if (root == null)
		{
			return false;
		}
		else if (compareBST_Node.compareTo(root) < 0)
		{
			return contains(root.left, data);
		}
		else if (compareBST_Node.compareTo(root) > 0)
		{
			return contains(root.right, data);
		}
		else
		{
			return true;
		}
	}
	
	public void inorder()
	{
		System.out.print("In-order Traversal:");
		inorder(root);
		System.out.println();
	}
	//prints the tree out inorder
	private void inorder(BST_Node<T> root)
	{
		if (root == null)
			return;

		inorder(root.left);
		System.out.print(" " + root.data);
		inorder(root.right);
	}

	public void preorder()
	{
		System.out.print("Pre-order Traversal:");
		preorder(root);
		System.out.println();
	}
	//prints the tree out in preorder
	private void preorder(BST_Node<T> root)
	{
		if (root == null)
			return;

		System.out.print(" " + root.data);
		preorder(root.left);
		preorder(root.right);
	}

	public void postorder()
	{
		System.out.print("Post-order Traversal:");
		postorder(root);
		System.out.println();
	}
	//prints the tree out in postorder
	private void postorder(BST_Node<T> root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static void main(String [] args)
	{


/*		for (int i = 0; i < 5; i++)
		{
			int r = (int)(Math.random() * 100) + 1;
			System.out.println("Inserting " + r + "...");
			myTree.insert(r);
			//System.out.println(myTree.contains(r));
		}*/
		GenericBST<String> myTree = new GenericBST<String>();
		myTree.insert("e");
		myTree.insert("g");
		myTree.insert("2");
		myTree.insert("t");
		myTree.insert("a");
		myTree.insert("ab");
		myTree.insert("y");
		myTree.insert("qq");
		myTree.insert("gg");
		myTree.insert("y");
		myTree.delete("ab");
		myTree.delete("rr");
//		System.out.println(myTree.contains("t"));
//		System.out.println(myTree.contains("k"));
		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
		
//		Dog ruffus = new Dog("Ruffus", 3, 21);
//		Dog sparky = new Dog("Sparky", 10, 111);
//		Dog olYeller = new Dog("ol' Yeller", 2, 14);
//		Dog smelly = new Dog("Smelly", 8, 64);
//		Dog bingo = new Dog("Bingo", 6, 45);
//		
//		GenericBST<Dog> tree = new GenericBST<Dog>(); 
//		tree.insert(sparky);
//		tree.insert(ruffus);
//		tree.insert(bingo);
//		tree.insert(smelly);
//		tree.insert(olYeller);
//		tree.delete(bingo);
//		System.out.println(tree.contains(bingo));
//		System.out.println(tree.contains(smelly));
//
//		tree.inorder();
//		tree.preorder();
//		tree.postorder();
		
		
//		System.out.println("My difficulty rating: " + GenericBST.difficultyRating());
//		System.out.println("My hours spent: " + GenericBST.hoursSpent());

	}
	
	public static double difficultyRating(){
		//really not to hard of an assignment at all. If you paid attention in class the days when you 
		//turned a linked list, queue, and stack into their generic cousins the assignment was a breeze.
		return 1.899;
	}
	
	public static double hoursSpent(){
		//spent around 5hours on the assignment. A good amount of the time was spent tracing thru the BST code
		//and re-remembering the logic behind adding/deleting/traversing a BST.
		return 5.0;
	}
	
}

//class Dog implements Comparable<Dog>{
//
//	public String name;
//	public int ageInHumanYears;
//	public int ageInDogYears;
//	
//	Dog(String name, int humanYears, int dogYears){
//		this.name = name;
//		ageInHumanYears = humanYears;
//		ageInDogYears = dogYears;
//	}
////	@Override
////	public int compareTo(Dog o) {
////		if(this.ageInDogYears < o.ageInDogYears){
////			return -1;
////		}
////		else if(this.ageInDogYears > o.ageInDogYears){
////			return 1;
////		}
////		else
////			return 0;	
////	}
//	
////	@Override
//		//Doesn't take into account null string.
////	public int compareTo(Dog o) {
////		return this.name.compareToIgnoreCase(o.name);
////	}
////	
//	
//	public String toString(){
//		return "(Name=" + name + ": humanYears="+ ageInHumanYears+": dogYears="+ageInDogYears +")";
//		}
//	
//}
