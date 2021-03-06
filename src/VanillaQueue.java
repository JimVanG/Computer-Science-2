

// VanillaQueue.java
// =================
// This is a vanilla implementation of queues that supports O(1) enqueu() and
// dequeue() operations. Notice that we are able to easily re-use our
// VanillaLinkedList code to implement queues.
//
// O(1) enqueue is achieved by inserting nodes at the tail of the linked list
// O(1) dequeue is achieved by removing nodes from the head of the linked list
//
// Note: We can't remove nodes from the end of our lists in O(1) time because
//       our nodes don't have 'previous' pointers. Therefore, we don't want to
//       enqueue at the head of our list and dequeue from the tail; that would
//       give those operations O(1) and O(n) runtimes, respectively.


import java.io.*;

// A basic queue class
public class VanillaQueue {

	// We'll store our information in a linked list (using the VanillaLinkedList
	// class, not the fancy version with generics).
	VanillaLinkedList L;

	// enqueue an element (insert at the end of the list)
	void enqueue(int data) {
		L.insert(data);
	}

	// dequeue an element (remove from the head of the list)
	Integer dequeue() {
		return L.removeHead();
	}

	// is the list empty?
	boolean isEmpty() {
		return (L.isEmpty());
	}

	// constructor; create the linked list
	VanillaQueue() {
		L = new VanillaLinkedList();
	}
	
	public static void main(String [] args) {

		// create a new queue
		VanillaQueue q = new VanillaQueue();

		// load up the queue with some random integers
		for (int i = 0; i < 10; i++)
		{
			int SomeRandomJunk = (int)(Math.random() * 100) + 1;
			System.out.println("Enqueuing " + SomeRandomJunk);
			q.enqueue(SomeRandomJunk);
		}

		// empty out the queue, printing the elements as we go
		while (!q.isEmpty())
			System.out.print(q.dequeue() + " ");
		System.out.println();
	}
}