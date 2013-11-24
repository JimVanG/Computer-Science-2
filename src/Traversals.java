// Sean Szumlanski
// COP 3503, Fall 2013

// Traversals.java
// ===============
// For Assignment #3, your treaps must be compatible with these tree traversal
// methods. You will have to implement a Node class in Treap.java that has
// 'data', 'left', and 'right' fields. You will also have to implement a
// public getRoot() method that returns a reference to your treap's root node.
//
// You should be able to call these methods from your Treap class's main()
// method using, e.g.:
//
//    // create a new treap that holds strings
//    Treap<String> t = new Treap<String>();
//
//    // insert some strings
//    ...
//
//    // print the in-order traversal
//    Traversals.inorder(t);
//
// Do not modify this file. Do not submit this file with your program. I will
// use my own copy of Traversals.java when testing your code.


public class Traversals
{
	public static void inorder(Treap<?> t)
	{
		System.out.print("In-order Traversal:");

		inorder(t.getRoot());

		System.out.println();
	}

	public static void preorder(Treap<?> t)
	{
		System.out.print("Pre-order Traversal:");

		preorder(t.getRoot());

		System.out.println();
	}

	public static void postorder(Treap<?> t)
	{
		System.out.print("Post-order Traversal:");

		postorder(t.getRoot());

		System.out.println();
	}

	private static void inorder(Node<?> n)
	{
		if (n == null)
			return;

		inorder(n.left);
		System.out.print(" " + n.data);
		inorder(n.right);
	}

	private static void preorder(Node<?> n)
	{
		if (n == null)
			return;

		System.out.print(" " + n.data);
		preorder(n.left);
		preorder(n.right);
	}

	private static void postorder(Node<?> n)
	{
		if (n == null)
			return;

		postorder(n.left);
		postorder(n.right);
		System.out.print(" " + n.data);
	}
}
