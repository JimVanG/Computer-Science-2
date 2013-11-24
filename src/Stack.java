// Stack.java
// ==========
// This is a very powerful implementation stacks that lets you cram *any* type
// of data into your nodes. We achieve this using "generics" in Java. Notice
// that we are able to easily re-use our LinkedList code to implement stacks.
//
// O(1) push is achieved by inserting nodes at the head of the linked list
// O(1) pop is achieved by removing nodes from the head of the linked list


import java.io.*;

// a powerful stack class that can handle any type of element
public class Stack<SomeType> {

	// We'll implement our stacks using linked lists. Notice that the LinkedList
	// class wants to know what type of data we're dealing with.
	LinkedList<SomeType> L;

	// push an element (insert at the head of the list)
	void push(SomeType data) {
		L.headInsert(data);
	}

	// pop an element (remove from the head of the list)
	SomeType pop() {
		return L.removeHead();
	}

	// is the list empty?
	boolean isEmpty() {
		return (L.isEmpty());
	}

	// constructor; create the linked list
	Stack() {
		L = new LinkedList<SomeType>();
	}
	
	public static void main(String [] args) {

		// create a new stack that holds integers
		Stack<Integer> s1 = new Stack<Integer>();
		
		for (int i = 0; i < 10; i++)
		{
			// this inserts random values on the range [1, 100]
			int SomeRandomJunk = (int)(Math.random() * 100) + 1;
			System.out.println("Pushing " + SomeRandomJunk);
			s1.push(SomeRandomJunk);
		}

		// pop things off the stack and print them out
		while (!s1.isEmpty())
			System.out.print(s1.pop() + " ");
		System.out.println();


		// create another stack (this time, one that holds strings)
		Stack<String> s2 = new Stack<String>();
		
		// push some strings onto the stack
		s2.push("pushing");
		s2.push("like");
		s2.push("nobody's");
		s2.push("business");

		// pop strings off the stack and print them out
		while (!s2.isEmpty())
			System.out.print(s2.pop() + " ");
		System.out.println();
	}
}