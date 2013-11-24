
// LinkedList.java
// ===============
// This is a very powerful implementation of linked lists that lets you cram
// *any* type of data into your nodes. We achieve this using "generics" in Java.


import java.io.*;

// A node class whose 'data' field is designed to hold any type of data.
class node1<AnyType> {

	AnyType data;
	node1<AnyType> next;
	
	// Constructor; sets this object's 'data' field to 'data'.
	node1(AnyType data) {
		this.data = data;
	}
}

// A linked list class designed to hold nodes with any type of data.
public class LinkedList<AnyType> {
	
	// Notice that when you create a LinkedList object (in main(), for example),
	// you tell it what kind of data it'll be holding. The LinkedList class
	// needs to pass that information on to the node class, as well. That's
	// what's happening here.
	private node1<AnyType> head, tail;

	// insert at the tail of the list
	void insert(AnyType data) {
		// if the list is empty, set 'head' and 'tail' to the new node
		if (head == null) {
			head = tail = new node1<AnyType>(data);
		}
		// otherwise, append the new node to the end of the list and move the
		// tail reference forward
		else {
			tail.next = new node1<AnyType>(data);
			tail = tail.next;
		}
	}

	// insert at the head of the list
	void headInsert(AnyType data) {
		// first, create the node to be inserted
		node1<AnyType> YouCanJustMakeANewNode = new node1<AnyType>(data);

		// insert it at the beginning of the list
		YouCanJustMakeANewNode.next = head;
		head = YouCanJustMakeANewNode;

		// if the list was empty before adding this node, 'head' AND 'tail'
		// need to reference this new node
		if (tail == null)
			tail = YouCanJustMakeANewNode;
	}

	// print the contents of the linked list
	void printList() {
		for (node1<AnyType> temp = head; temp != null; temp = temp.next)
			System.out.print(temp.data + " ");
		System.out.println();
	}

	// Remove the head of the list (and return its 'data' value).
	AnyType removeHead() {
		// if the list is empty, signify that by returning null
		if (head == null)
			return null;
		
		// Store the data from the head, then move the head reference forward.
		// Java will take care of the memory management when it realizes there
		// are no references to the old head anymore.
		AnyType temp = head.data;
		head = head.next;
		
		// If the list is now empty (i.e., if the node we just removed was the
		// only node in the list), update the tail reference, too!
		if (head == null)
			tail = null;
		
		// Return the value from the old head node.
		return temp;
	}

	// returns true if the list is empty, false otherwise
	boolean isEmpty() {
		return (head == null);
	}

	public static void main(String [] args) {

		// create a new linked list that holds integers
		LinkedList<Integer> L1 = new LinkedList<Integer>();
		
		for (int i = 0; i < 10; i++)
		{
			// this inserts random values on the range [1, 100]
			int SomeRandomJunk = (int)(Math.random() * 100) + 1;
			System.out.println("Inserting " + SomeRandomJunk);
			L1.insert(SomeRandomJunk);
		}

		// print the list to verify everything got in there correctly
		L1.printList();


		// create another linked list (this time, one that holds strings)
		LinkedList<String> L2 = new LinkedList<String>();
		
		L2.insert("Sean");
		L2.insert("Szumlanski");
		L2.insert("wrote");
		L2.insert("this");
		L2.insert("fancy");
		L2.insert("beast!");

		// print the new list to verify everything got in there correctly
		while (!L2.isEmpty())
			System.out.print(L2.removeHead() + " ");
		System.out.println();

		// print the old list just to verify that there weren't any static
		// problems that messed things up when we created L2
		L1.printList();
	}
}