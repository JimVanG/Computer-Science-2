// Sean Szumlanski
// COP 3503, Fall 2013

// BST.java
// ========
// Basic binary search tree (BST) implementation that supports insert() and
// delete() operations. This framework is provide for you to modify as part of
// Programming Assignment #2.


import java.io.*;
import java.util.*;

//class that will be used as each tree NodeTest
class NodeTest
{
	int data;
	NodeTest left, right;

	//constructor that sets the passed in data as the NodeTests data
	NodeTest(int data)
	{
		this.data = data;
	}
}

//the actual binary search tree class
public class BST
{
	//NodeTest variable that will be instantiated later
	private NodeTest root;

	//public insert function that calls the private actual insert function
	//only takes in the data.
	public void insert(int data)
	{
		System.out.println("Inserting in: " + data);
		root = insert(root, data);
	}

	//the actual insert function takes in the root NodeTest and the data
	private NodeTest insert(NodeTest root, int data)
	{
		//if the root is null just return the new NodeTest.
		//or if we finally reach the end of the tree and can add the new NodeTest/leaf
		if (root == null)
		{
			return new NodeTest(data); //creates the NodeTest
		}
		//if the data is smaller that the root data, go to the left
		else if (data < root.data)
		{
			root.left = insert(root.left, data);
		}
		//go to the right if the data is greater
		else if (data > root.data)
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
		//return the root of the tree (first NodeTest)
		return root;
	}

	//public delete function that only takes in the data
	public void delete(int data)
	{
		System.out.println("Deleting: " + data);
		root = delete(root, data);
	}
	
	//private delete function that takes in the data and root NodeTest
	private NodeTest delete(NodeTest root, int data)
	{
		//if the root is null then there's no tree
		if (root == null)
		{
			System.out.println("There was no tree.");
			return null;
		}
		//if the data is less go to the left
		else if (data < root.data)
		{
			root.left = delete(root.left, data);
		}
		//otherwise go to the right
		else if (data > root.data)
		{
			root.right = delete(root.right, data);
		}
		//else, we have a hit, so find out how we are going to delete it
		else
		{
			//if there are no children then return null
			//because we can delete the NodeTest without worries
			if (root.left == null && root.right == null)
			{
				System.out.println("Delete, no children NodeTests");
				return null;
			}
			//if there is no right-child then return the left
			//
			else if (root.right == null)
			{
				System.out.println("Delete, no right-children NodeTests");
				return root.left;
			}
			//if there is no left child return the right
			else if (root.left == null)
			{
				System.out.println("Delete, no left-children NodeTests");
				return root.right;
			}
			else
			{
				//if it is a parent NodeTest, we need to find the max of the lowest so we can fix the BST
				root.data = findMax(root.left);
				root.left = delete(root.left, root.data);
			}
		}

		return root;
	}

	// This method assumes root is non-null, since this is only called by
	// delete() on the left subtree, and only when that subtree is non-empty.
	private int findMax(NodeTest root)
	{
		//traverse all of the right NodeTests of the root to find the last one at the right.
		while (root.right != null)
		{
			root = root.right;
		}
		//being that it is a BST it is safe to assume that the last NodeTest to the right of the root
		//is the largest value
		return root.data;
	}

	// Returns true if the value is contained in the BST, false otherwise.
	public boolean contains(int data)
	{
		return contains(root, data);
	}
	
	//traverses tree seeing if the data is in the tree
	private boolean contains(NodeTest root, int data)
	{
		if (root == null)
		{
			return false;
		}
		else if (data < root.data)
		{
			return contains(root.left, data);
		}
		else if (data > root.data)
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

	private void inorder(NodeTest root)
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

	private void preorder(NodeTest root)
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

	private void postorder(NodeTest root)
	{
		if (root == null)
			return;

		postorder(root.left);
		postorder(root.right);
		System.out.print(" " + root.data);
	}

	public static void main(String [] args)
	{
		BST myTree = new BST();

/*		for (int i = 0; i < 5; i++)
		{
			int r = (int)(Math.random() * 100) + 1;
			System.out.println("Inserting " + r + "...");
			myTree.insert(r);
			//System.out.println(myTree.contains(r));
		}*/
		
		myTree.insert(44);
		myTree.insert(123);
		myTree.insert(33);
		myTree.insert(54);
		myTree.insert(666);
		myTree.insert(2);
		myTree.insert(87);
		myTree.insert(33);
		myTree.delete(33);

		
		myTree.inorder();
		myTree.preorder();
		myTree.postorder();
	}
}
